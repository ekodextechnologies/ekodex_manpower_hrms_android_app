package com.ekodex.manpowerhrms.Attendance

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.*
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.databinding.FragmentAttendanceBinding
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap


class AttendanceFragment : Fragment() {

lateinit var binding:FragmentAttendanceBinding
private var progressDialog: AlertDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_attendance,container,false)

        var i1 = Internet()

        if(i1.checkConnection(requireContext()))
        {
            getTodaysAttendance()
            getAttendanceHistory()
        }
        else
        {
            Toast.makeText(requireContext(),"Please Check internet connection!!",Toast.LENGTH_LONG).show()
            binding.attendaceHistoryList.visibility = View.INVISIBLE
            binding.notAvailable1.visibility = View.GONE
            binding.noInternet.visibility = View.VISIBLE
        }


        binding.button.setOnClickListener {
        if(i1.checkConnection(requireContext()))
        {
            val builder = androidx.appcompat.app.AlertDialog.Builder(it.context,
                R.style.MaterialAlertDialog_OK_color
            )
            //set title for alert dialog
            builder.setTitle("Check-In!!")
            //set message for alert dialog
            builder.setMessage("Do you really want to check-in?")
            builder.setIcon(R.mipmap.ic_launcher)

            //performing positive action
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                checkIn()
            }
            //performing negative action
            builder.setNegativeButton("No") { dialogInterface, which ->
                Toast.makeText(it.context.applicationContext, "Have a nice day", Toast.LENGTH_LONG)
                    .show()
            }
            // Create the AlertDialog
            val alertDialog: androidx.appcompat.app.AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
        else
        {
            Toast.makeText(requireContext(),"Please Check internet connection!!",Toast.LENGTH_LONG).show()
        }
        }


        binding.button3.setOnClickListener {
            if(i1.checkConnection(requireContext()))
            {
                checkCanWeOut()
            }
            else
            {
                Toast.makeText(requireContext(),"Please Check internet connection!!",Toast.LENGTH_LONG).show()
            }
        }

        return  binding.root
    }

    private fun getAttendanceHistory() {
        var adapter: AttendanceHistoryAdapter
        var dashBillList = mutableListOf<AttendanceHistoryData>()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_ATTENDANCE_HISTORY,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        dashBillList.clear()

                        if (array.length() <= 0) {
                            //Toast.makeText(requireContext(),"No sales for this date!!",Toast.LENGTH_SHORT).show()
                            binding.notAvailable1.visibility = View.VISIBLE
                            binding.attendaceHistoryList.visibility = View.INVISIBLE
                            binding.progressBar2.visibility = View.GONE
                            binding.textView77.text = "Total results 0"
                        }
                        else{

                            binding.progressBar2.visibility = View.GONE
                            binding.textView77.text = "Total results ${array.length()}"

                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)

                                val banners = AttendanceHistoryData(
                                    objectArtist.optString("id"),
                                    objectArtist.optString("check_in_time"),
                                    objectArtist.optString("check_out_time"),
                                    objectArtist.optString("check_in_date")
                                )
                                dashBillList.add(banners)
                                adapter = AttendanceHistoryAdapter(dashBillList)
                                binding.attendaceHistoryList.adapter = adapter
                                binding.attendaceHistoryList.visibility = View.VISIBLE
                                binding.notAvailable1.visibility = View.GONE
                            }
                        }
                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()

                        binding.progressBar2.visibility = View.GONE
                        binding.textView77.text = "Total results 0"

                    }

                } catch (e: JSONException) {
                  //  
                    binding.notAvailable1.visibility = View.VISIBLE
                    binding.progressBar2.visibility = View.GONE
                    binding.textView77.text = "Total results 0"

                }

            },
            Response.ErrorListener {
//                Toast.makeText(
//                    requireActivity().applicationContext,
//                    error.message,
//                    Toast.LENGTH_LONG
//                ).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["employee_id"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.id

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getTodaysAttendance() {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_ATTENDANCE_STATUS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {
//                        Toast.makeText(
//                            requireContext(),
//                            obj.getString("message"),
//                            Toast.LENGTH_SHORT
//                        ).show()

                        binding.textView18.text = obj.getString("checked_in_time")
                        binding.imageView.visibility = View.VISIBLE
                        binding.imageView.setImageResource(R.drawable.done_tick)

                        binding.button.text = "CHECKED IN"
                        binding.imageView.setImageResource(R.drawable.done_tick)


                        binding.textView16.text = obj.getString("todays_date")
                        binding.textView20.text = obj.getString("todays_date")

                        binding.button.setBackgroundResource(R.drawable.attendance_button)
                        binding.button.setTextColor(Color.parseColor("#939090"))
                        binding.button.isEnabled = false

                        if(obj.getString("checked_out_time") != "00:00")
                        {
                            //binding.imageView6.visibility = View.VISIBLE
                            binding.textView21.text = obj.getString("checked_out_time")
                            binding.imageView6.setImageResource(R.drawable.done_tick)
                            binding.button3.setBackgroundResource(R.drawable.attendance_button)
                            binding.button3.text = "CHECKED OUT"
                            binding.button3.setTextColor(Color.parseColor("#939090"))
                            binding.button3.isEnabled = false
                        }
                    }
                    else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                              Toast.LENGTH_SHORT
                        ).show()
                        binding.textView16.text = obj.getString("todays_date")
                        binding.textView20.text = obj.getString("todays_date")
                    }
                } catch (e: JSONException) {
                   // 
                }
            },
            Response.ErrorListener {
//                if (error.message != null) {
//                    Toast.makeText(
//                        requireContext(),
//                        error.message,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()

                params["employee_id"] = SharedPrefManager.getInstance(requireContext()).user.id

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun checkOut() {
        showProgressDialog()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_CHECK_OUT,
            Response.Listener { response ->
                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()

                        binding.button3.text = "Checked Out"
                        binding.imageView6.setImageResource(R.drawable.done_tick)
                        getTodaysAttendance()
                        hideProgressDialog()
                        getAttendanceHistory()

                    }
                    else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        hideProgressDialog()
                    }
                } catch (e: JSONException) {
                   // 
                    hideProgressDialog()
                }
            },
            Response.ErrorListener {
//                if (error.message != null) {
//                    Toast.makeText(
//                        requireContext(),
//                        error.message,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    hideProgressDialog()
//                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()

                params["employee_id"] = SharedPrefManager.getInstance(requireContext()).user.id
                params["client_id"] = SharedPrefManager.getInstance(requireContext()).user.client_id
                params["site_id"] = SharedPrefManager.getInstance(requireContext()).user.site_id
                params["rank"] = SharedPrefManager.getInstance(requireContext()).user.rank
                params["client_code"] = SharedPrefManager.getInstance(requireContext()).user.client_code
                params["emp_name"] = SharedPrefManager.getInstance(requireContext()).user.fname + " " + SharedPrefManager.getInstance(requireContext()).user.lname
                params["emp_code"] = SharedPrefManager.getInstance(requireContext()).user.emp_code
                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun checkIn() {
        showProgressDialog()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_CHECK_IN,
            Response.Listener { response ->
                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        hideProgressDialog()
                        getTodaysAttendance()

                    }
                    else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        hideProgressDialog()
                    }
                } catch (e: JSONException) {
                   // 
                    hideProgressDialog()
                }
            },
            Response.ErrorListener {
//                if (error.message != null) {
//                    Toast.makeText(
//                        requireContext(),
//                        error.message,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    hideProgressDialog()
//                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()

                params["employee_id"] = SharedPrefManager.getInstance(requireContext()).user.id
                params["emp_name"] = SharedPrefManager.getInstance(requireContext()).user.fname + " " + SharedPrefManager.getInstance(requireContext()).user.lname
                params["emp_code"] = SharedPrefManager.getInstance(requireContext()).user.emp_code
                params["rank"] = SharedPrefManager.getInstance(requireContext()).user.rank
                params["client_code"] = SharedPrefManager.getInstance(requireContext()).user.client_code
                params["site_id"] = SharedPrefManager.getInstance(requireContext()).user.site_id

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun checkCanWeOut() {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_CAN_WE_CHECK_OUT,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        if(obj.getString("message") == "yes")
                        {
                            val builder = androidx.appcompat.app.AlertDialog.Builder(requireActivity(),
                                R.style.MaterialAlertDialog_OK_color
                            )
                            //set title for alert dialog
                            builder.setTitle("Check-Out!!")
                            //set message for alert dialog
                            builder.setMessage("Do you really want to check-out?")
                            builder.setIcon(R.mipmap.ic_launcher)

                            //performing positive action
                            builder.setPositiveButton("Yes") { dialogInterface, which ->
                                checkOut()
                            }
                            //performing negative action
                            builder.setNegativeButton("No") { dialogInterface, which ->
                                Toast.makeText(requireActivity(), "Have a nice day", Toast.LENGTH_LONG)
                                    .show()
                            }
                            // Create the AlertDialog
                            val alertDialog: androidx.appcompat.app.AlertDialog = builder.create()
                            // Set other dialog properties
                            alertDialog.setCancelable(false)
                            alertDialog.show()
                        }
                        else if(obj.getString("message") == "no"){
                            Toast.makeText(requireActivity(), "Please first check in!!", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                    else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    //
                }
            },
            Response.ErrorListener {
//                if (error.message != null) {
//                    Toast.makeText(
//                        requireContext(),
//                        error.message,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()

                params["employee_id"] = SharedPrefManager.getInstance(requireContext()).user.id

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun showProgressDialog() {
        if (progressDialog == null) {
            val progressBar = ProgressBar(requireContext())
            progressBar.isIndeterminate = true
            val padding = 50
            progressBar.setPadding(padding, padding, padding, padding)

            progressDialog = AlertDialog.Builder(requireContext())
                .setTitle("Adding Attendance")
                .setMessage("Please wait...")
                .setView(progressBar)
                .setCancelable(false)
                .create()
        }
        progressDialog?.show()
    }

    private fun hideProgressDialog() {
        progressDialog?.dismiss()
    }
}