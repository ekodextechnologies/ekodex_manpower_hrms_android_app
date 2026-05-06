package com.ekodex.manpowerhrms.Onboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.MainActivity
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.databinding.FragmentOnboard3Binding
import org.json.JSONException
import org.json.JSONObject

class Onboard3Fragment : Fragment() {

    lateinit var binding: FragmentOnboard3Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_onboard3,container,false)

        binding.textView80.setOnClickListener {
            updateOnboardStatus()
        }

        return binding.root

    }

    private fun updateOnboardStatus() {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_UPDATE_ONBOARD_STATUS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        //  here change sharedpref onboarding

                        val sharedPreferences = SharedPrefManager.ctx?.getSharedPreferences(
                            SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE)
                        val editor = sharedPreferences?.edit()

                        editor?.putString(SharedPrefManager.KEY_ONBOARDING,"1")

                        editor?.apply()

                        //Now change is sharedpref , bcoz it was showing onboard twice
                        requireActivity().finish()
                        startActivity(Intent(requireActivity().applicationContext, MainActivity::class.java))


                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    
                }
            },
            Response.ErrorListener { error ->
//                Toast.makeText(
//                    requireActivity().applicationContext,
//                    error.message,
//                    Toast.LENGTH_LONG
                // ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()

                params["user_id"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.id

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }


}