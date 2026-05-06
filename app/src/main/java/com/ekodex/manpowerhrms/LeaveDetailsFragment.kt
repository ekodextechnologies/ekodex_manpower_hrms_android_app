package com.ekodex.manpowerhrms

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
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentLeaveDetailsBinding
import com.ekodex.manpowerhrms.databinding.FragmentVoucherDetailsBinding
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class LeaveDetailsFragment : Fragment() {

    lateinit var binding: FragmentLeaveDetailsBinding
    private var progressDialog: AlertDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_leave_details,container,false)

        var i1 = Internet()

        if (i1.checkConnection(requireContext())) {
            getLeaveDetails()
        }

        binding.approve.setOnClickListener {
            AlertDialog.Builder(it.context)
                .setTitle("Approve Leave")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes") { dialog, _ ->
                    approveLeave()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    // No clicked
                    dialog.dismiss()
                }
                .create()
                .show()
        }

        return binding.root
    }


    private fun approveLeave() {
        showProgressDialog()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_APPROVE_EMPLOYEE_LEAVE,
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
                        binding.approve.visibility = View.GONE
                        hideProgressDialog()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        hideProgressDialog()
                    }
                } catch (e: JSONException) {

                    hideProgressDialog()
                }
            },
            Response.ErrorListener { error ->
                if (error.message != null) {
                    Toast.makeText(
                        requireContext(),
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    hideProgressDialog()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                var args = LeaveDetailsFragmentArgs.fromBundle(requireArguments())

                params["leave_id"] = args.leaveId
                params["approved_by"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.id
                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getLeaveDetails() {

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_PARTICULAR_LEAVE,
            Response.Listener { response ->

                try {
                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {

                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            Toast.makeText(requireContext(), "No Leave Found!!", Toast.LENGTH_SHORT)
                                .show()
                        } else {

                            for (i in 0 until array.length()) {

                                val objectArtist = array.getJSONObject(i)

                                val status = objectArtist.optString("status")

                                // SHOW APPROVE BUTTON
                                if (status.equals("Pending", ignoreCase = true) &&
                                    SharedPrefManager.getInstance(requireContext())
                                        .user.role.equals("admin", ignoreCase = true)
                                ) {
                                    binding.approve.visibility = View.VISIBLE
                                } else {
                                    binding.approve.visibility = View.GONE
                                }

                                // EMPLOYEE NAME
                                val empName = objectArtist.optString("emp_name")
                                binding.textView427.text =
                                    "Employee: " + if (empName.isNotEmpty() && empName != "null") empName else "NA"

                                // DESCRIPTION
                                val description = objectArtist.optString("description")
                                binding.textView428.text =
                                    "Reason: " + if (description.isNotEmpty() && description != "null") description else "NA"

                                // TYPE
                                val type = objectArtist.optString("type")
                                binding.textView430.text =
                                    "Type: " + if (type.isNotEmpty() && type != "null") type else "NA"

// DATE RANGE
                                val fromDate = objectArtist.optString("from_date")
                                val toDate = objectArtist.optString("to_date")
                                val totalDays = objectArtist.optString("total_days")
                                val fromTime = objectArtist.optString("from_time")

                                if (type.equals("Half Day", ignoreCase = true)) {

                                    binding.textView471.text =
                                        if (fromDate.isNotEmpty() && fromDate != "null")
                                            "Date: $fromDate at ($fromTime)"
                                        else
                                            "Date: NA"

                                } else {

                                    binding.textView471.text =
                                        "From: $fromDate  To: $toDate  ($totalDays days)"
                                }

                                // CLIENT
                                val client = objectArtist.optString("client")
                                binding.textView431.text =
                                    "Company: " + if (client.isNotEmpty() && client != "null") client else "NA"

                                // SITE
                                val site = objectArtist.optString("site")
                                binding.textView432.text =
                                    "Site: " + if (site.isNotEmpty() && site != "null") site else "NA"

                                // STATUS
                                binding.textView442.text =
                                    "Status: " + if (status.isNotEmpty() && status != "null") status else "NA"

                                // APPROVED / REJECTED SECTION
                                if (status.equals("Rejected", ignoreCase = true)) {

                                    val rejectedBy = objectArtist.optString("rejected_by")
                                    val rejectedOn = objectArtist.optString("rejected_on")

                                    binding.textView453.text =
                                        if (rejectedBy.isNotEmpty() && rejectedBy != "null")
                                            "Rejected By: $rejectedBy"
                                        else
                                            "Rejected By: NA"

                                    binding.textView454.text =
                                        if (rejectedOn.isNotEmpty() && rejectedOn != "null")
                                            "Rejected On: $rejectedOn"
                                        else
                                            "Rejected On: NA"

                                } else {

                                    val approvedBy = objectArtist.optString("approved_by")
                                    val approvedOn = objectArtist.optString("approved_on")

                                    binding.textView453.text =
                                        if (approvedBy.isNotEmpty() && approvedBy != "null")
                                            "Approved By: $approvedBy"
                                        else
                                            "Approved By: NA"

                                    binding.textView454.text =
                                        if (approvedOn.isNotEmpty() && approvedOn != "null")
                                            "Approved On: $approvedOn"
                                        else
                                            "Approved On: NA"
                                }
                            }
                        }

                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            },
            Response.ErrorListener { error ->
                error.printStackTrace()
            }
        ) {

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()

                val args = LeaveDetailsFragmentArgs.fromBundle(requireArguments())
                params["leave_id"] = args.leaveId   // ✅ fixed

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
                .setTitle("Approving Leave")
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