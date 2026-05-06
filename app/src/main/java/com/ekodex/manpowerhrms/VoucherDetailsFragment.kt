package com.ekodex.manpowerhrms

import android.net.Uri
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
import com.ekodex.manpowerhrms.databinding.FragmentVoucherDetailsBinding
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.HashMap
import java.util.Locale

class VoucherDetailsFragment : Fragment() {

    lateinit var binding: FragmentVoucherDetailsBinding
    //images list

    private val proofImages = mutableListOf<Uri>()
    private lateinit var proofadapter: SelectedImagesAdapter
    var adapter:SplitDetailsAdapter? = null
    var splitList = mutableListOf<SplitDetailsData>()
    private var progressDialog: AlertDialog? = null
    var total_amt = "0.0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_voucher_details, container, false)

        proofadapter = SelectedImagesAdapter(proofImages,"view",requireActivity()) { removeImage(it,1) }
        binding.proofList.adapter = proofadapter

        if (adapter == null) {
            adapter = SplitDetailsAdapter(splitList)
        }
        binding.splitList.adapter = adapter



        var i1 = Internet()

        if(i1.checkConnection(requireContext())) {
            getVouchers()
            getVoucherImages()
            getSplitDetails()
        }

        binding.approve.setOnClickListener {
            AlertDialog.Builder(it.context)
                .setTitle("Approve Voucher")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes") { dialog, _ ->
                    approveVoucher()
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

    private fun approveVoucher() {
        showProgressDialog()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_APPROVE_VOUCHER,
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
                var args = VoucherDetailsFragmentArgs.fromBundle(requireArguments())
                params["voucher_no"] = args.voucherNo
                params["original_amount"] = total_amt
                params["approved_by"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.id
                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }


    private fun getVoucherImages() {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_VOUCHER_IMAGES,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        proofImages.clear()

                        if (array.length() <= 0) {
                            //Toast.makeText(requireContext(),"No vouchers!!", Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)

                                proofImages.add(Uri.parse(objectArtist.optString("img")))
                                proofadapter.notifyDataSetChanged()
                            }
                        }
                    }
                    else {
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
                
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                var args = VoucherDetailsFragmentArgs.fromBundle(requireArguments())
                params["voucher_no"] = args.voucherNo

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getVouchers() {

        // proofImages.clear()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_PARTICULAR_VOUCHERS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            Toast.makeText(requireContext(),"No vouchers!!",Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            for (i in 0..(array.length() - 1)) {
                                val objectArtist = array.getJSONObject(i)

                           if(objectArtist.optString("status").equals("Pending",ignoreCase = true) && SharedPrefManager.getInstance(requireContext()).user.role.equals("admin",ignoreCase = true))
                           {
                               binding.approve.visibility = View.VISIBLE
                           }

                                // DATE
                                val rawDate = objectArtist.optString("date")
                                val outputDate = if (rawDate != "" && rawDate != "null") convertDateString(rawDate) else "NA"
                                binding.textView428.text = "Date: $outputDate"

// BASIC DETAILS
                                val gang = objectArtist.optString("gang")
                                binding.textView432.text = "Gang: " + if (gang != "" && gang != "null") gang else "NA"

                                val mode = objectArtist.optString("mode")
                                binding.textView429.text = " | Mode: " + if (mode != "" && mode != "null") mode else "NA"

                                val client = objectArtist.optString("client")
                                binding.textView430.text = "Company: " + if (client != "" && client != "null") client else "NA"

                                val site = objectArtist.optString("site")
                                binding.textView431.text = "Site: " + if (site != "" && site != "null") site else "NA"

                                val particular = objectArtist.optString("particular")
                                binding.textView427.text = "Description: " + if (particular != "" && particular != "null") particular else "NA"

// BANK DETAILS
                                val bank = objectArtist.optString("bank")
                                binding.textView433.text = "Bank Name: " + if (bank != "" && bank != "null") bank else "NA"

                                val accNo = objectArtist.optString("acc_no")
                                binding.textView434.text = "Account No: " + if (accNo != "" && accNo != "null") accNo else "NA"

                                val ifsc = objectArtist.optString("ifsc")
                                binding.textView435.text = " | IFSC Code: " + if (ifsc != "" && ifsc != "null") ifsc else "NA"

                                val branch = objectArtist.optString("bank_branch")
                                binding.textView436.text = "Branch: " + if (branch != "" && branch != "null") branch else "NA"

// AMOUNT
                                val amount = objectArtist.optString("amount")
                                binding.textView439.text = " | Total Amount: ₹" + if (amount != "" && amount != "null") amount else "0"
                                total_amt = if (amount != "" && amount != "null") amount else "0"

// BENEFICIARY
                                val beneficiary = objectArtist.optString("beneficiary_name")
                                binding.textView442.text =
                                    if (beneficiary != "" && beneficiary != "null")
                                        "Beneficiary Name: $beneficiary"
                                    else
                                        "Beneficiary Name: NA"

// TYPE
                                val type = objectArtist.optString("type")
                                binding.textView471.text =
                                    if (type != "" && type != "null")
                                        "Type: $type"
                                    else
                                        "Type: NA"

// APPROVED / REJECTED SECTION
                                val status = objectArtist.optString("status")

                                if (status.equals("Cancelled", ignoreCase = true)) {

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
                    }
                    else {
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
                
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                var args = VoucherDetailsFragmentArgs.fromBundle(requireArguments())
                params["voucher_no"] = args.voucherNo

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getSplitDetails() {

        // proofImages.clear()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_VOUCHER_SPLIT_DETAILS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")


                        if (array.length() <= 0) {
                            Toast.makeText(requireContext(),"No vouchers!!",Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            for (i in 0 until array.length()) {
                            val objectArtist = array.getJSONObject(i)
                                var banner = SplitDetailsData(
                                    objectArtist.optString("name"),
                                    objectArtist.optString("amt")
                                )
                                splitList.add(banner)
                                binding.splitList.visibility = View.VISIBLE
                            }

                            adapter?.notifyDataSetChanged()
                        }
                    }
                    else {
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
                
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                var args = VoucherDetailsFragmentArgs.fromBundle(requireArguments())
                params["voucher_no"] = args.voucherNo

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun removeImage(imageUri: Uri,code:Int) {
        when(code)
        {
            1->{ proofImages.remove(imageUri)
                proofadapter.notifyDataSetChanged()}
        }
    }

    fun convertDateString(inputDate: String): String? {
        return try {
            // Define the input and output date formats
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

            // Parse the input date string into a Date object
            val date = inputFormat.parse(inputDate)

            // Format the Date object into the desired output format
            outputFormat.format(date)
        } catch (e: Exception) {
            // Handle the error if the date parsing fails
            
            null
        }
    }

    private fun showProgressDialog() {
        if (progressDialog == null) {
            val progressBar = ProgressBar(requireContext())
            progressBar.isIndeterminate = true
            val padding = 50
            progressBar.setPadding(padding, padding, padding, padding)

            progressDialog = AlertDialog.Builder(requireContext())
                .setTitle("Approving Voucher")
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