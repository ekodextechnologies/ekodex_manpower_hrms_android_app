package com.ekodex.manpowerhrms

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentTravelcreateRequestBinding
import org.json.JSONException
import org.json.JSONObject

class TravelcreateRequestFragment : Fragment() {

    lateinit var binding: FragmentTravelcreateRequestBinding
    var bill_to = ""
    var trav_type = ""
    var work_type = ""
    var adv_reqd = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_travelcreate_request,
            container,
            false
        )


        var i1 = Internet()
        val user = SharedPrefManager.getInstance(requireActivity().applicationContext).user

        binding.textView127.text = user.fname + " " + user.lname
        binding.textView135.text = user.emp_code
        binding.textView139.text = user.rank

        var purposes = arrayOf("Tech services meeting", "Collection of prototypes", "Others")
        val adapter1 = ArrayAdapter(requireContext(), R.layout.pay_to_dropdown_layout, purposes)
        binding.travelPurpose.setAdapter(adapter1)

        //create travel request
        binding.button6.setOnClickListener {
            if (i1.checkConnection(requireContext())) {
                createTravelRequest()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please Check internet connection!!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.button6.setOnClickListener {
              createTravelRequest()
        }


        binding.billTo.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            var radioButton = view?.findViewById<RadioButton>(checkedId)
            bill_to = radioButton?.getText().toString()
        }
        )

        binding.travelType.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            var radioButton = view?.findViewById<RadioButton>(checkedId)
            trav_type = radioButton?.getText().toString()
        }
        )

        binding.workType.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            var radioButton = view?.findViewById<RadioButton>(checkedId)
            work_type = radioButton?.getText().toString()
        }
        )

        binding.advRqd.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            var radioButton = view?.findViewById<RadioButton>(checkedId)
            adv_reqd = radioButton?.getText().toString()
        }
        )
        return binding.root

    }

    private fun createTravelRequest() {
        var purpose = binding.travelPurpose.text.toString()
        var contact_no = binding.editTextTextPersonName64.text.toString()
        var contact_person = binding.editTextTextPersonName60.text.toString()
        var desc = binding.editTextTextPersonName65.text.toString()


        if (TextUtils.isEmpty(purpose)) {
            binding.travelPurpose.error = "Please select travel purpose!!"
            binding.travelPurpose.requestFocus()
            return
        }

        if (TextUtils.isEmpty(contact_person)) {
            binding.editTextTextPersonName60.error = "Please enter contact person!!"
            binding.editTextTextPersonName60.requestFocus()
            return
        }

        if (TextUtils.isEmpty(contact_no)) {
            binding.editTextTextPersonName64.error = "Please enter contact number!!"
            binding.editTextTextPersonName64.requestFocus()
            return
        }



        if (TextUtils.isEmpty(desc)) {
            binding.editTextTextPersonName65.error = "Please enter descriptio!!"
            binding.editTextTextPersonName65.requestFocus()
            return
        }

        if (bill_to == "") {
            Toast.makeText(requireContext(), "Please select bill to!!", Toast.LENGTH_LONG).show()
        } else if (trav_type == "") {
            Toast.makeText(requireContext(), "Please select travel type!!", Toast.LENGTH_LONG)
                .show()
        } else if (work_type == "") {
            Toast.makeText(requireContext(), "Please select work type!!", Toast.LENGTH_LONG).show()
        } else if (adv_reqd == "") {
            Toast.makeText(requireContext(), "Please select advance required!!", Toast.LENGTH_LONG)
                .show()
        } else {
            val stringRequest = object : StringRequest(
                Request.Method.POST, URLs.URL_CREATE_TRAVEL,
                Response.Listener
                { response ->

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

                            findNavController().navigate(
                                TravelcreateRequestFragmentDirections.actionTravelcreateRequestFragmentToTravelScheduleFragment(
                                    obj.getString("lastid")
                                )
                            )

                        } else {
                            Toast.makeText(
                                requireContext(),
                                obj.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        
                    }
                },
                Response.ErrorListener { error ->
                    if (error.message != null) {
                        Toast.makeText(
                            requireContext(),
                            error.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()

                    val user = SharedPrefManager.getInstance(requireContext()).user

                    params["name"] = user.fname + " " + user.lname
                    params["empid"] = user.emp_code
                    params["rank"] = user.rank

                    params["purpose"] = purpose
                    params["billto"] = bill_to
                    params["travtype"] = trav_type
                    params["worktype"] = work_type

                    params["contact_person"] = contact_person
                    params["contact_number"] = contact_no
                    params["description"] = desc
                    params["adv_required"] = adv_reqd
                    params["created_by"] = user.id

                    return params
                }
            }

            VolleySingleton.getInstance(requireActivity().applicationContext)
                .addToRequestQueue(stringRequest)
        }


    }

}