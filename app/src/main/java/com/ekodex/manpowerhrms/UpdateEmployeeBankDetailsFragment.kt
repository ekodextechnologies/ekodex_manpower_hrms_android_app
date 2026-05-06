package com.ekodex.manpowerhrms

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentUpdateEmployeeBankDetailsBinding
import org.json.JSONException
import org.json.JSONObject

class UpdateEmployeeBankDetailsFragment : Fragment() {

    lateinit var binding: FragmentUpdateEmployeeBankDetailsBinding
    private var progressDialog: AlertDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_update_employee_bank_details,
            container,
            false
        )

        var i1 = Internet()

        if(i1.checkConnection(requireContext()))
        {
            getBankDetails()
        }
        else
        {
            Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
        }


        binding.button23.setOnClickListener {
            if(i1.checkConnection(requireContext()))
            {
                updateBankDetails()
            }
            else
            {
                Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
            }
        }
        return binding.root
    }

    private fun updateBankDetails() {
        var bank = binding.editTextTextPersonName81.text.toString()
        var holder =  binding.editTextTextPersonName83.text.toString()
        var acno = binding.editTextTextPersonName84.text.toString()
        var ifsc =  binding.editTextTextPersonName85.text.toString()
        var address = binding.editTextTextPersonName86.text.toString()
        var state =  binding.editTextTextPersonName87.text.toString()
        var city = binding.editTextTextPersonName88.text.toString()
        var micr =  binding.editTextTextPersonName89.text.toString()
        var card = binding.editTextTextPersonName90.text.toString()

        if (TextUtils.isEmpty(bank)) {
            binding.editTextTextPersonName81.error = "Please enter bank name!!"
            binding.editTextTextPersonName81.requestFocus()
            return
        }

        if (TextUtils.isEmpty(holder)) {
            binding.editTextTextPersonName83.error = "Please enter account holder name!!"
            binding.editTextTextPersonName83.requestFocus()
            return
        }

        if (TextUtils.isEmpty(acno)) {
            binding.editTextTextPersonName84.error = "Please enter account number!!"
            binding.editTextTextPersonName84.requestFocus()
            return
        }

        if (TextUtils.isEmpty(ifsc)) {
            binding.editTextTextPersonName85.error = "Please enter ifsc code"
            binding.editTextTextPersonName85.requestFocus()
            return
        }
//        if (TextUtils.isEmpty(address)) {
//            binding.editTextTextPersonName86.error = "Please enter bank address!!"
//            binding.editTextTextPersonName86.requestFocus()
//            return
//        }
//
//        if (TextUtils.isEmpty(state)) {
//            binding.editTextTextPersonName87.error = "Please enter bank state!!"
//            binding.editTextTextPersonName87.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(city)) {
//            binding.editTextTextPersonName88.error = "Please enter bank city!!"
//            binding.editTextTextPersonName88.requestFocus()
//            return
//        }
//
//        if (TextUtils.isEmpty(micr)) {
//            binding.editTextTextPersonName89.error = "Please enter bank micr!!"
//            binding.editTextTextPersonName89.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(card)) {
//            binding.editTextTextPersonName90.error = "Please enter card number!!"
//            binding.editTextTextPersonName90.requestFocus()
//            return
//        }

        showProgressDialog()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_UPDATE_EMPLOYEE_BANK_DETAILS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        hideProgressDialog()
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()

                        var args = UpdateEmployeeBankDetailsFragmentArgs.fromBundle(requireArguments())
                        findNavController().navigate(UpdateEmployeeBankDetailsFragmentDirections.actionUpdateEmployeeBankDetailsFragmentToEmployeeDetailsFragment(args.id))

                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        hideProgressDialog()

                    }

                } catch (e: JSONException) {
                    
                    hideProgressDialog()
                }

            },
            Response.ErrorListener { error ->
                
                hideProgressDialog()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                var args = UpdateEmployeeBankDetailsFragmentArgs.fromBundle(requireArguments())
                params["id"] = args.id
                params["bank"] = bank
                params["acno"] = acno
                params["ifsc"] = ifsc
                params["holder"] = holder
                params["address"] = address
                params["state"] = state
                params["city"] = city
                params["micr"] = micr
                params["card"] = card

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getBankDetails() {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_EMPLOYEE_BANK_DETAILS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val userJson = obj.getJSONObject("user")

                        if(userJson.getString("bank")!=null && (userJson.getString("bank"))!="" && userJson.getString("bank")!="null")
                        {
                            binding.editTextTextPersonName81.setText(userJson.getString("bank"))
                        }
                        if(userJson.getString("holder")!=null && (userJson.getString("holder"))!="" && userJson.getString("holder")!="null")
                        {
                            binding.editTextTextPersonName83.setText(userJson.getString("holder"))
                        }
                        if(userJson.getString("account_no")!=null && (userJson.getString("account_no"))!="" && userJson.getString("account_no")!="null")
                        {
                            binding.editTextTextPersonName84.setText(userJson.getString("account_no"))
                        }
                        if(userJson.getString("ifsc")!=null && (userJson.getString("ifsc"))!="" && userJson.getString("ifsc")!="null")
                        {
                            binding.editTextTextPersonName85.setText(userJson.getString("ifsc"))
                        }

                        if(userJson.getString("address")!=null && (userJson.getString("address"))!="" && userJson.getString("address")!="null")
                        {
                            binding.editTextTextPersonName86.setText(userJson.getString("address"))
                        }
                        if(userJson.getString("state")!=null && (userJson.getString("state"))!="" && userJson.getString("state")!="null")
                        {
                            binding.editTextTextPersonName87.setText(userJson.getString("state"))
                        }
                        if(userJson.getString("city")!=null && (userJson.getString("city"))!="" && userJson.getString("city")!="null")
                        {
                            binding.editTextTextPersonName88.setText(userJson.getString("city"))
                        }
                        if(userJson.getString("micr")!=null && (userJson.getString("micr"))!="" && userJson.getString("micr")!="null")
                        {
                            binding.editTextTextPersonName89.setText(userJson.getString("micr"))
                        }
                        if(userJson.getString("cardno")!=null && (userJson.getString("cardno"))!="" && userJson.getString("cardno")!="null")
                        {
                            binding.editTextTextPersonName90.setText(userJson.getString("cardno"))
                        }

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
                
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                var args = UpdateEmployeeBankDetailsFragmentArgs.fromBundle(requireArguments())
                params["id"] = args.id

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
                .setTitle("Updating Details")
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