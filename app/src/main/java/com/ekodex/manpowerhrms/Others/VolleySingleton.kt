package com.ekodex.manpowerhrms.Others

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.android.volley.*
import com.android.volley.toolbox.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class VolleySingleton private constructor(context: Context) {

    private val appContext = context.applicationContext
    private val mRequestQueue: RequestQueue

    /**
     * Global server state listener
     */
    interface ServerStatusListener {
        fun onServerDown()
        fun onServerUp()
    }

    companion object {
        @Volatile
        private var mInstance: VolleySingleton? = null

        var serverStatusListener: ServerStatusListener? = null

        @Synchronized
        fun getInstance(context: Context): VolleySingleton {
            if (mInstance == null) {
                mInstance = VolleySingleton(context)
            }
            return mInstance!!
        }
    }

    val requestQueue: RequestQueue
        get() = mRequestQueue

    init {
        android.util.Log.e("SINGLETON_TEST", "VolleySingleton initialized")

        mRequestQueue = RequestQueue(
            DiskBasedCache(appContext.cacheDir, 10 * 1024 * 1024),
            CustomNetwork()
        ).apply { start() }
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

    /**
     * Custom Network Layer
     * Detects server failure globally
     */
    private class CustomNetwork : BasicNetwork(HurlStack()) {

        private var lastServerState = true
        private val mainHandler = Handler(Looper.getMainLooper())


        override fun performRequest(request: Request<*>): NetworkResponse {
            android.util.Log.e("VOLLEY_CHECK", "CustomNetwork is running")
            return try {
                val response = super.performRequest(request)

                // Convert response to string
                val body = String(response.data)

                // --- LOG EVERYTHING ---
                android.util.Log.e("VOLLEY_RESPONSE_CODE", response.statusCode.toString())
                android.util.Log.e("VOLLEY_RESPONSE_BODY", body)

                val serverProblem = isServerProblem(response.statusCode, body)

                if (!serverProblem) {
                    notifyServerUp()
                } else {
                    notifyServerDown()
                }

                response
            } catch (e: VolleyError) {
                // Log VolleyError details
                if (e.networkResponse != null) {
                    val body = String(e.networkResponse.data)
                    android.util.Log.e("VOLLEY_ERROR_CODE", e.networkResponse.statusCode.toString())
                    android.util.Log.e("VOLLEY_ERROR_BODY", body)
                } else {
                    android.util.Log.e("VOLLEY_ERROR", e.toString())
                }
                notifyServerDown()
                throw e
            } catch (e: Exception) {
                android.util.Log.e("VOLLEY_EXCEPTION", e.toString())
                notifyServerDown()
                throw VolleyError(e)
            }
        }

        /**
         * Detect real server issues only
         */
        private fun isServerProblem(statusCode: Int, body: String): Boolean {

            // Non 2xx HTTP codes
            if (statusCode < 200 || statusCode >= 300) return true

            // HTML response instead of JSON
            if (body.contains("<html", true)) return true

            // Common hosting/server errors
            if (body.contains("Service Unavailable", true)) return true
            if (body.contains("Service Suspended", true)) return true
            if (body.contains("Page Not Found", true)) return true

            return false
        }

        /**
         * Notify only once when state changes
         */
//        private fun notifyServerDown() {
//            if (lastServerState) {
//                lastServerState = false
//                mainHandler.post {
//                    serverStatusListener?.onServerDown()
//                }
//            }
//        }

        private fun notifyServerDown() {
            mainHandler.post {
                serverStatusListener?.onServerDown()
            }
        }


//        private fun notifyServerUp() {
//            if (!lastServerState) {
//                lastServerState = true
//                mainHandler.post {
//                    serverStatusListener?.onServerUp()
//                }
//            }
//        }

        private fun notifyServerUp() {
            mainHandler.post {
                serverStatusListener?.onServerUp()
            }
        }

    }
}