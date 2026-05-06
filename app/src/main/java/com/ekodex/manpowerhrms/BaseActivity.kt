package com.ekodex.manpowerhrms

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.ekodex.manpowerhrms.Others.SharedPrefManager

abstract class BaseActivity : AppCompatActivity() {

    private val timeoutMillis = 10 * 60 * 1000L // 10 minutes
    private val handler = Handler(Looper.getMainLooper())

    private var remainingMillis = timeoutMillis

    private val logoutRunnable = Runnable {
        performAutoLogout()
    }

    private val tickRunnable = object : Runnable {
        override fun run() {
            remainingMillis -= 1000

            Log.d(
                "IdleTimer",
                "Remaining seconds: ${remainingMillis / 1000}"
            )

            if (remainingMillis > 0) {
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        resetTimer()
        return super.dispatchTouchEvent(ev)
    }

    override fun onResume() {
        super.onResume()
        resetTimer()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(logoutRunnable)
        handler.removeCallbacks(tickRunnable)
    }

    private fun resetTimer() {
        handler.removeCallbacks(logoutRunnable)
        handler.removeCallbacks(tickRunnable)

        remainingMillis = timeoutMillis

        handler.postDelayed(logoutRunnable, timeoutMillis)
        handler.postDelayed(tickRunnable, 1000)
    }

    private fun performAutoLogout() {
        Log.d("IdleTimer", "Session expired. Logging out.")

        SharedPrefManager.getInstance(applicationContext).logout()
        finish()
    }
}
