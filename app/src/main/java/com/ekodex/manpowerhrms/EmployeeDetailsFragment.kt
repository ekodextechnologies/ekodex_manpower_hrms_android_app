package com.ekodex.manpowerhrms

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentEmployeeDetailsBinding
import org.json.JSONException
import org.json.JSONObject

class EmployeeDetailsFragment : Fragment() {

    lateinit var binding: FragmentEmployeeDetailsBinding
    var jobLeftStatus = ""
    var aadhar = ""
    var pan = ""
    var uan = ""
    var pf = ""
    var esis = ""
    var passport = ""
    var fname = ""
    var lname = ""

    var uan_date = ""
    var esis_date = ""
    var passport_date = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_employee_details,
            container,
            false
        )

        var i1 = Internet()

        if(i1.checkConnection(requireContext()))
        {
           getEmpDetails()
           getEmpDocs()
        }
        else
        {
            Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
        }

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked)
            {
                jobLeftStatus = "Left"
                updateJobLeftStatus()
            }
            else
            {
                jobLeftStatus = "Working"
                updateJobLeftStatus()
            }
        }

        //update bank details
        binding.cardView16.setOnClickListener {
            var args = EmployeeDetailsFragmentArgs.fromBundle(requireArguments())
            it.findNavController().navigate(EmployeeDetailsFragmentDirections.actionEmployeeDetailsFragmentToUpdateEmployeeBankDetailsFragment(args.id))
        }

        //update personal details
        binding.cardView15.setOnClickListener {
            var args = EmployeeDetailsFragmentArgs.fromBundle(requireArguments())
            it.findNavController().navigate(EmployeeDetailsFragmentDirections.actionEmployeeDetailsFragmentToUpdateEmployeePersonalDetailsFragment(args.id))
        }

        //update document details
        binding.cardView17.setOnClickListener {
            var args = EmployeeDetailsFragmentArgs.fromBundle(requireArguments())
            it.findNavController().navigate(EmployeeDetailsFragmentDirections.actionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment(args.id,aadhar,pan,uan,pf, esis, passport, fname, lname, uan_date, esis_date, passport_date))
        }


        return binding.root

    }

    private fun updateJobLeftStatus() {
            val stringRequest = object : StringRequest(
                Request.Method.POST, URLs.URL_UPDATE_JOB_LEFT_STATUS_INDIVIDUAL,
                Response.Listener
                { response ->

                    try {
                        //converting response to json object
                        val obj = JSONObject(response)

                        //if no error in response
                        if (!obj.getBoolean("error")) {
//                            Toast.makeText(
//                                requireContext(),
//                                obj.getString("message"),
//                                Toast.LENGTH_SHORT
//                            ).show()

                        }
                        else {
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

                    var args = EmployeeDetailsFragmentArgs.fromBundle(requireArguments())
                    params["id"] = args.id
                    params["status"] = jobLeftStatus
                    return params
                }
            }

            VolleySingleton.getInstance(requireActivity().applicationContext)
                .addToRequestQueue(stringRequest)

    }

    private fun getEmpDetails() {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_EMPLOYEE_DETAILS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val userJson = obj.getJSONObject("user")

                        var name = ""
                        var address = ""

                       jobLeftStatus = userJson.getString("jobLeftStatus")

                        //------------------------   name   --------------------------------

                        if(userJson.getString("initial").isNotBlank() && userJson.getString("initial")!="null")
                        {
                            name+= userJson.getString("initial") + " "
                        }

                        if(userJson.getString("fname").isNotBlank() && userJson.getString("fname")!="null")
                        {
                            name+= userJson.getString("fname") + " "
                            fname = userJson.getString("fname")
                        }

                        if(userJson.getString("mname").isNotBlank() && userJson.getString("mname")!="null")
                        {
                            name+= userJson.getString("mname") + " "
                        }

                        if(userJson.getString("lname").isNotBlank() && userJson.getString("lname")!="null")
                        {
                            name+= userJson.getString("lname")
                            lname = userJson.getString("lname")
                        }

                        if(name == "" || name == "null")
                        {
                            name = "Name: Not Available"
                            binding.textView211.setText(name)
                        }
                        else
                        {
                            binding.textView211.setText("Name: " + name)
                        }


                        //------------------------   address   --------------------------------

                        if(userJson.getString("address").isNotBlank() && userJson.getString("address")!="null")
                        {
                            address+= userJson.getString("address") + " "
                        }

                        if(userJson.getString("state").isNotBlank() && userJson.getString("state")!="null")
                        {
                            address+= userJson.getString("state") + " "
                        }

                        if(userJson.getString("city").isNotBlank() && userJson.getString("city")!="null")
                        {
                            address+= userJson.getString("city") + " "
                        }

                        if(userJson.getString("pincode").isNotBlank() && userJson.getString("pincode")!="null")
                        {
                            address+= userJson.getString("pincode")
                        }


                        if(address == "" || address == "null")
                        {
                            address = "Address: Not Available"
                            binding.textView215.setText(address)
                        }
                        else
                        {
                            binding.textView215.setText("Address: " + address)
                        }
                        //------------------------   rank   --------------------------------
                        if(userJson.getString("rank").isNotBlank() && userJson.getString("rank")!="null")
                        {
                            binding.textView212.setText("Rank: " + userJson.getString("rank"))
                        }
                        else
                        {
                            binding.textView212.setText("Rank: Not Available")
                        }
                        //------------------------   phone   --------------------------------
                        if(userJson.getString("phone").isNotBlank() && userJson.getString("phone")!="null")
                        {
                            binding.textView213.setText("Phone: " + userJson.getString("phone"))
                        }
                        else
                        {
                            binding.textView213.setText("Phone: Not Available")
                        }
                        //------------------------   email   --------------------------------
                        if(userJson.getString("email").isNotBlank() && userJson.getString("email")!="null")
                        {
                            binding.textView214.setText("Email: " + userJson.getString("email"))
                        }
                        else
                        {
                            binding.textView214.setText("Email: Not Available")
                        }

                        //------------------------   gender   --------------------------------
                        if(userJson.getString("gender").isNotBlank() && userJson.getString("gender")!="null")
                        {
                            binding.textView299.setText("Gender: " + userJson.getString("gender"))
                        }
                        else
                        {
                            binding.textView299.setText("Gender: Not Available")
                        }

                        //------------------------   empcode   --------------------------------
                        if(userJson.getString("empcode").isNotBlank() && userJson.getString("empcode")!="null")
                        {
                            binding.textView298.setText("Employee Code: " + userJson.getString("empcode"))
                        }
                        else
                        {
                            binding.textView298.setText("Employee Code: Not Available")
                        }





                        //------------------------   bank name   --------------------------------
                        if(userJson.getString("bank").isNotBlank() && userJson.getString("bank")!="null")
                        {
                            binding.textView216.setText("Bank Name: " + userJson.getString("bank"))
                        }
                        else
                        {
                            binding.textView216.setText("Bank Name: Not Available")
                        }

                        //------------------------   holder name   --------------------------------
                        if(userJson.getString("holder").isNotBlank() && userJson.getString("holder")!="null")
                        {
                            binding.textView217.setText("Account Holder Name: " + userJson.getString("holder"))
                        }
                        else
                        {
                            binding.textView217.setText("Account Holder Name: Not Available")
                        }

                        //------------------------   acc no   --------------------------------
                        if(userJson.getString("account_no").isNotBlank() && userJson.getString("account_no")!="null")
                        {
                            binding.textView218.setText("Account Number: " + userJson.getString("account_no"))
                        }
                        else
                        {
                            binding.textView218.setText("Account Number: Not Available")
                        }

                        //------------------------   ifsc   --------------------------------
                        if(userJson.getString("ifsc").isNotBlank() && userJson.getString("ifsc")!="null")
                        {
                            binding.textView219.setText("IFSC Code: " + userJson.getString("ifsc"))
                        }
                        else
                        {
                            binding.textView219.setText("IFSC Code: Not Available")
                        }

                        //------------------------   esis   --------------------------------
                        if(userJson.getString("esis").isNotBlank() && userJson.getString("esis")!="null")
                        {
                            binding.textView301.setText("Esis No: " + userJson.getString("esis"))
                            esis = userJson.getString("esis")
                        }
                        else
                        {
                            binding.textView301.setText("Esis No: Not Available")

                        }
                        //------------------------   pf   --------------------------------
                        if(userJson.getString("pf").isNotBlank() && userJson.getString("pf")!="null")
                        {
                            binding.textView302.setText("PF No: " + userJson.getString("pf"))
                            pf = userJson.getString("pf")
                        }
                        else
                        {
                            binding.textView302.setText("PF No: Not Available")
                        }
                        //------------------------   passport   --------------------------------
                        if(userJson.getString("passport").isNotBlank() && userJson.getString("passport")!="null")
                        {
                            binding.textView303.setText("Passport No: " + userJson.getString("passport"))
                            passport = userJson.getString("passport")
                        }
                        else
                        {
                            binding.textView303.setText("Passport No: Not Available")
                        }
                        //------------------------   uan   --------------------------------
                        if(userJson.getString("uan").isNotBlank() && userJson.getString("uan")!="null")
                        {
                            binding.textView304.setText("UAN No: " + userJson.getString("uan"))
                            uan = userJson.getString("uan")
                        }
                        else
                        {
                            binding.textView304.setText("UAN No: Not Available")
                        }
                        //------------------------   aadhar   --------------------------------
                        if(userJson.getString("aadhar").isNotBlank() && userJson.getString("aadhar")!="null")
                        {
                            binding.textView305.setText("Aadhar Card No: " + userJson.getString("aadhar"))
                            aadhar = userJson.getString("aadhar")
                        }
                        else
                        {
                            binding.textView305.setText("Aadhar Card No: Not Available")
                        }
                        //------------------------   pan   --------------------------------
                        if(userJson.getString("pan").isNotBlank() && userJson.getString("pan")!="null")
                        {
                            binding.textView306.setText("Pancard No: " + userJson.getString("pan"))
                            pan = userJson.getString("pan")
                        }
                        else
                        {
                            binding.textView306.setText("Pancard No: Not Available")
                        }
                        //------------------------   profile image   --------------------------------
                        if(userJson.getString("gender") == "Male")
                        {
                            binding.imageView50.setImageResource(R.drawable.man)
                        }
                        else if (userJson.getString("gender") == "Female")
                        {
                            binding.imageView50.setImageResource(R.drawable.woman)
                        }
                        //------------------------   job left status   --------------------------------

                        if(jobLeftStatus == "Left")
                        {
                            binding.checkBox.isChecked = true
                        }
                        else if(jobLeftStatus == "Working")
                        {
                            binding.checkBox.isChecked = false
                        }

                        //--------------  dates to pass to edit page  --------------

                        if(userJson.getString("uan_date").isNotBlank() && userJson.getString("uan_date")!="null")
                        {
                            uan_date = userJson.getString("uan_date")
                           }

                        if(userJson.getString("esis_date").isNotBlank() && userJson.getString("esis_date")!="null")
                        {
                            esis_date = userJson.getString("esis_date")
                        }

                        if(userJson.getString("passport_date").isNotBlank() && userJson.getString("passport_date")!="null")
                        {
                            passport_date = userJson.getString("passport_date")
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

                var args = EmployeeDetailsFragmentArgs.fromBundle(requireArguments())
                params["id"] = args.id

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getEmpDocs() {
        var imagesList = mutableListOf<Uri>()
        var adapter: SelectedImagesAdapter

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_ALL_EMPLOYEE_DOCS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        // empDirectoryList.clear()

                        if (array.length() <= 0) {
                            Toast.makeText(requireContext(),"No documents available!!",Toast.LENGTH_SHORT).show()
                            binding.docsLayout.visibility = View.INVISIBLE
                        }
                        else{

                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)

                                imagesList.add(Uri.parse(objectArtist.optString("image")))
                                adapter = SelectedImagesAdapter(imagesList,"EmployeeDetails",requireActivity()){}
                                binding.docList.adapter = adapter
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
                    
                }

            },
            Response.ErrorListener { error ->
                
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()


                var args = EmployeeDetailsFragmentArgs.fromBundle(requireArguments())
                params["emp_id"] = args.id

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }


}

