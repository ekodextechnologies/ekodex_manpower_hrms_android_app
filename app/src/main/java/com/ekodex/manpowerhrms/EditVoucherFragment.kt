package com.ekodex.manpowerhrms

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ekodex.manpowerhrms.Employee_Directory.EmployeeDirectoryData
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentEditVoucherBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class EditVoucherFragment : Fragment() {

    lateinit var binding: FragmentEditVoucherBinding
    private var progressDialog: AlertDialog? = null
    var voucher_date = ""
    var company_id = ""
    var site_id = ""
    var emp_id = ""
    var emp_name = ""
    var emp_code = ""
    lateinit var companies:MutableList<Client_Data>
    lateinit var branches:MutableList<Site_Data>
    lateinit var companies_names:MutableList<String>
    lateinit var branches_names:MutableList<String>
    lateinit var employees:MutableList<EmployeeDirectoryData>
    lateinit var emp_names:MutableList<String>
    lateinit var voucher_ids:MutableList<String>

    //images list
    private val proofImages = mutableListOf<Uri>()
    private lateinit var proofadapter: SelectedImagesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_voucher,
            container,
            false
        )
        companies = mutableListOf()
        branches =  mutableListOf()
        employees =  mutableListOf()
        companies_names = mutableListOf()
        branches_names =  mutableListOf()
        emp_names = mutableListOf()
        voucher_ids = mutableListOf()

        proofadapter = SelectedImagesAdapter(proofImages,"Proof",requireActivity()) { removeImage(it,1) }
        binding.proofList.adapter = proofadapter


        var i1 = Internet()

        if(i1.checkConnection(requireContext())) {
            getVouchers()
            getVoucherImages()
        }
        //---------------------------------------------------------------------------------------
        binding.company.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                company_id = companies.get(position).id

                branches.clear()
                branches_names.clear()

                employees.clear()
                emp_names.clear()
                emp_id = ""
                emp_name = ""
                emp_code = ""

                binding.site.text.clear()

                getSites(company_id)
            }

        })

        binding.site.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                site_id = branches.get(position).id

                employees.clear()
                emp_names.clear()
                emp_id = ""
                emp_name = ""
                emp_code = ""

                getEmployeeDetails()
            }

        })

//        binding.employee.setOnItemClickListener(object : AdapterView.OnItemClickListener {
//            override fun onItemClick(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                emp_id = employees.get(position).id
//                emp_code = employees.get(position).empcode
//                emp_name = emp_names.get(position)
//            }
//
//        })

        //mode dropdown
        var modes = arrayListOf<String>("Cash","Online","GPAY","PhonePay","Upi Payment","Cheque")
        val adapter1 = ArrayAdapter(requireContext(),R.layout.pay_to_dropdown_layout,modes)
        binding.mode.setAdapter(adapter1)


        // date clicklistner
        val calender1 = Calendar.getInstance()
        val datePicker1 =
            DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                calender1.set(Calendar.YEAR, year)
                calender1.set(Calendar.MONTH, month)
                calender1.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateToDateLable(calender1)
            }

        binding.button29.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                datePicker1,
                calender1.get(Calendar.YEAR),
                calender1.get(Calendar.MONTH),
                calender1.get(Calendar.DAY_OF_MONTH)
            )
                .show()
        }

        binding.button30.setOnClickListener {
            if(i1.checkConnection(requireContext()))
            {
                updateVoucher()
            }
            else
            {
                Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
            }
        }

        binding.addDoc.setOnClickListener {
            selectImages()
        }

        return binding.root
    }

    private fun selectImages() {
        //cropping image selection
        ImagePicker.with(this)
            .crop()	    			//Crop image(Optional), Check Customization for more option
            .compress(1024)			//Final image size will be less than 1 MB(Optional)
            //.maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
            .start(100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            data?.let {
                if (it.clipData != null) {
                    for (i in 0 until it.clipData!!.itemCount) {
                        proofImages.add(it.clipData!!.getItemAt(i).uri)
                    }
                } else {
                    it.data?.let { uri -> proofImages.add(uri) }
                }
                proofadapter.notifyDataSetChanged()
            }
        }
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

                var args = EditVoucherFragmentArgs.fromBundle(requireArguments())
                params["voucher_no"] = args.voucherNo

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getVoucherTypes() {
        val typeList = mutableListOf<String>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_ALL_TYPES_FOR_VOUCHER,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0..array.length()-1) {
                            val objectArtist = array.getJSONObject(i)
                            typeList.add(objectArtist.getString("type"))
                        }

                        val adapter1 = ArrayAdapter(requireContext(),R.layout.pay_to_dropdown_layout,typeList)
                        binding.type.setAdapter(adapter1)

                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    
                }
            },
            { volleyError ->
                Toast.makeText(
                    requireContext(),
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            })

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)
    }

    private fun updateToDateLable(calener: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
// Here we can use calendar selected date
        binding.button29.text = (sdf.format(calener.time))
        val myFormat2 = "yyyy-MM-dd"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.UK)
        voucher_date = (sdf2.format(calener.time))
        //getAllEmployees(att_date)
    }

    private fun getCompanies(com_id: String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_ALL_COMPANIES,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            // Toast.makeText(requireContext(),"No companies available!!", Toast.LENGTH_SHORT).show()

                        }
                        else{
                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)
                                companies.add(Client_Data(objectArtist.getString("id"),objectArtist.getString("title")))
                                companies_names.add(objectArtist.getString("title"))
                            }
                            val company_adapter = ArrayAdapter(requireContext(),R.layout.pay_to_dropdown_layout,companies_names)
                            binding.company.setAdapter(company_adapter)
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

                params["id"] = com_id

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getSites(sites_id: String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_ALL_BRANCHES,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            // Toast.makeText(requireContext(),"No companies available!!", Toast.LENGTH_SHORT).show()

                        }
                        else{
                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)
                                branches.add(Site_Data(objectArtist.getString("id"),objectArtist.getString("title"),objectArtist.getString("client_code")))
                                branches_names.add(objectArtist.getString("title"))
                            }
                            val site_adapter = ArrayAdapter(requireContext(),R.layout.pay_to_dropdown_layout,branches_names)
                            binding.site.setAdapter(site_adapter)
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

                params["id"] = sites_id


                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getGangs() {
        val gangList = mutableListOf<String>()
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_ALL_GANGS,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0..array.length()-1) {
                            val objectArtist = array.getJSONObject(i)
                            gangList.add(objectArtist.getString("gang"))
                        }

                        val adapter1 = ArrayAdapter(requireContext(),R.layout.pay_to_dropdown_layout,gangList)
                        binding.gang.setAdapter(adapter1)

                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    
                }
            },
            { volleyError ->
                Toast.makeText(
                    requireContext(),
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            })

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)
    }

    private fun getEmployeeDetails() {
        emp_names.clear()
        employees.clear()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_ALL_EMPLOYEES,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            Toast.makeText(requireContext(),"No employees!!",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)
                                employees.add(EmployeeDirectoryData(
                                    objectArtist.optString("id"),
                                    objectArtist.optString("empcode"),
                                    objectArtist.optString("first_name") + " " + objectArtist.optString("last_name"), objectArtist.optString("rank"), objectArtist.optString("phone"), objectArtist.optString("gender"),""))
                                emp_names.add(objectArtist.optString("first_name") + " " + objectArtist.optString("last_name"))
                            }
                            val emp_adapter = ArrayAdapter(requireContext(),R.layout.pay_to_dropdown_layout,emp_names)

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
                params["client_id"] = company_id
                params["site_id"] = site_id
                params["role"] = SharedPrefManager.getInstance(requireActivity().applicationContext).user.role
                params["status"] = "Working"

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun updateVoucher() {

        var company = binding.company.text.toString()
        var site =  binding.site.text.toString()
        var gang = binding.gang.text.toString()
        var mode =  binding.mode.text.toString()
        var part1 =  binding.editTextTextPersonName106.text.toString()
        var amt1 =  binding.editTextTextPersonName107.text.toString()

        var part2 =  binding.editTextTextPersonName108.text.toString()
        var amt2 =  binding.editTextTextPersonName109.text.toString()

        var part3 =  binding.editTextTextPersonName110.text.toString()
        var amt3 =  binding.editTextTextPersonName111.text.toString()

        var part4 =  binding.editTextTextPersonName112.text.toString()
        var amt4 =  binding.editTextTextPersonName113.text.toString()

        var part5 =  binding.editTextTextPersonName115.text.toString()
        var amt5 =  binding.editTextTextPersonName114.text.toString()


//        var from_bank =  binding.fromBank.text.toString()
//        var to_bank = binding.toBank.text.toString()
//        var vendor_commission =  binding.editTextTextPersonName105.text.toString()

        if (TextUtils.isEmpty(company)) {
            binding.company.error = "Please select company!!"
            binding.company.requestFocus()
            return
        }

        if (TextUtils.isEmpty(site)) {
            binding.site.error = "Please select site!!"
            binding.site.requestFocus()
            return
        }

//        if (TextUtils.isEmpty(gang)) {
//            binding.gang.error = "Please select gang!!"
//            binding.gang.requestFocus()
//            return
//        }
//
//        if (TextUtils.isEmpty(employee)) {
//            binding.employee.error = "Please select employee!!"
//            binding.employee.requestFocus()
//            return
//        }

//        if (TextUtils.isEmpty(type)) {
//            binding.type.error = "Please select type!!"
//            binding.type.requestFocus()
//            return
//        }

        /*      if (TextUtils.isEmpty(from_bank)) {
                  binding.fromBank.error = "Please select from bank!!"
                  binding.fromBank.requestFocus()
                  return
              }

              if (TextUtils.isEmpty(to_bank)) {
                  binding.toBank.error = "Please select to bank!!"
                  binding.toBank.requestFocus()
                  return
              }

              if (TextUtils.isEmpty(vendor_commission)) {
                  binding.editTextTextPersonName105.error = "Please enter vendor commission!!"
                  binding.editTextTextPersonName105.requestFocus()
                  return
              }*/

        if (TextUtils.isEmpty(mode)) {
            binding.mode.error = "Please select mode!!"
            binding.mode.requestFocus()
            return
        }
        if (TextUtils.isEmpty(part1)) {
            binding.editTextTextPersonName106.error = "Please add particular description!!"
            binding.editTextTextPersonName106.requestFocus()
            return
        }
        if (TextUtils.isEmpty(amt1)) {
            binding.editTextTextPersonName107.error = "Please add amount!!"
            binding.editTextTextPersonName107.requestFocus()
            return
        }

//        if (part2.length>0 && amt2.length==0) {
//            binding.editTextTextPersonName109.error = "Please add amount!!"
//            binding.editTextTextPersonName109.requestFocus()
//            return
//        }
//
//        if (amt2.length>0 && part2.length==0) {
//            binding.editTextTextPersonName108.error = "Please add particular description!!"
//            binding.editTextTextPersonName108.requestFocus()
//            return
//        }
//
//        if (part3.length>0 && amt3.length==0) {
//            binding.editTextTextPersonName110.error = "Please add amount!!"
//            binding.editTextTextPersonName110.requestFocus()
//            return
//        }
//
//        if (amt3.length>0 && part3.length==0) {
//            binding.editTextTextPersonName108.error = "Please add particular description!!"
//            binding.editTextTextPersonName108.requestFocus()
//            return
//        }
//
//        if (part4.length>0 && amt4.length==0) {
//            binding.editTextTextPersonName113.error = "Please add amount!!"
//            binding.editTextTextPersonName113.requestFocus()
//            return
//        }
//
//        if (amt4.length>0 && part4.length==0) {
//            binding.editTextTextPersonName112.error = "Please add particular description!!"
//            binding.editTextTextPersonName112.requestFocus()
//            return
//        }
//
//        if (part5.length>0 && amt5.length==0) {
//            binding.editTextTextPersonName114.error = "Please add amount!!"
//            binding.editTextTextPersonName114.requestFocus()
//            return
//        }
//
//        if (amt5.length>0 && part5.length==0) {
//            binding.editTextTextPersonName115.error = "Please add particular description!!"
//            binding.editTextTextPersonName115.requestFocus()
//            return
//        }

        binding.button30.isEnabled = false
        showProgressDialog()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_UPDATE_VOUCHER,
            Response.Listener { response ->
                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        hideProgressDialog()
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(EditVoucherFragmentDirections.actionEditVoucherFragmentToVoucherManagementFragment("",""))
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

                var args = EditVoucherFragmentArgs.fromBundle(requireArguments())
                var updateImgCount = 0
                var onlineImgCount = 0
                var newCounter = 0

//                if(proofImages.size>0)
//                {
//                    for(i in 0..proofImages.size-1)
//                    {
//                        if(proofImages.get(i).toString().contains("http"))
//                        {
//                            newCounter++
//                        }
//                    }
//                }

                params["client_id"] = company_id
                params["site_id"] = site_id
                params["client_name"] = binding.company.text.toString()
                params["site_name"] = binding.site.text.toString()
//                params["emp_code"] = emp_code
//                params["emp_id"] = emp_id
//                params["emp_name"] = emp_name
                params["adv_date"] = voucher_date
                //params["vendor_comm"] = vendor_commission
                params["gang"] = gang
               // params["type"] = type
                params["mode"] = mode
                params["modified_by"] = SharedPrefManager.getInstance(requireActivity().applicationContext).user.id

                params["part1"] = part1
                params["amt1"] = amt1

//                params["part2"] = part2
//                params["amt2"] = amt2
//
//                params["part3"] = part3
//                params["amt3"] = amt3
//
//                params["part4"] = part4
//                params["amt4"] = amt4
//
//                params["part5"] = part5
//                params["amt5"] = amt5

              //  params["count"] = voucher_ids.size.toString()
//                voucher_ids.forEachIndexed { index, s ->
//                    params["voucher$index"] = s
//                }
                params["voucher_no"] = args.voucherNo



                if(proofImages.size>0)
                {
                    for(i in 0..proofImages.size-1)
                    {
                        if(!proofImages.get(i).toString().contains("http"))
                        {
                            var num = (i+1).toString()
                            updateImgCount++
                            newCounter++
                            params["proof${newCounter}"] = imageUriToBase64(requireActivity().contentResolver,proofImages.get(i))
                        }
                        else
                        {
                            onlineImgCount++
                        }
                    }
                    //voucher images
                    params["proofCount"] = updateImgCount.toString()
                    params["onlineImgCount"] = onlineImgCount.toString()
                }
                else
                {
                    params["proofCount"] = "0"
                    params["onlineImgCount"] = "0"
                }



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

                                voucher_date = objectArtist.optString("date")
                                //set calener button date
                                val sdf = SimpleDateFormat("dd-MM-yyyy")

                                //set current date to textviews of table
                                val currtDate = sdf.format(Date())
                                val outputDate = convertDateString(objectArtist.optString("date"))
                                binding.button29.text = currtDate

                                binding.company.setText(objectArtist.optString("client"))
                                binding.site.setText(objectArtist.optString("site"))
                                binding.gang.setText(objectArtist.optString("gang"))
                                //binding.employee.setText(objectArtist.optString("emp_name"))
                                //binding.type.setText(objectArtist.optString("type"))
                                binding.mode.setText(objectArtist.optString("mode"))
                                company_id = objectArtist.optString("client_id")
                                site_id = objectArtist.optString("site_id")
                                binding.editTextTextPersonName106.setText(objectArtist.optString("particular"))
                                binding.editTextTextPersonName107.setText(objectArtist.optString("amount"))
//                                emp_id = objectArtist.optString("emp_id")
//                                emp_name = objectArtist.optString("emp_name")
//                                emp_code = objectArtist.optString("emp_code")

                                getSites(company_id)
                                //getEmployeeDetails()



//                                if(i==0)
//                                {
//                                    binding.editTextTextPersonName106.setText(objectArtist.optString("particular"))
//                                    binding.editTextTextPersonName107.setText(objectArtist.optString("amount"))
//                                }
//                                else if(i==1)
//                                {
//                                    binding.editTextTextPersonName108.setText(objectArtist.optString("particular"))
//                                    binding.editTextTextPersonName109.setText(objectArtist.optString("amount"))
//                                }
//                                else if(i==2)
//                                {
//                                    binding.editTextTextPersonName110.setText(objectArtist.optString("particular"))
//                                    binding.editTextTextPersonName111.setText(objectArtist.optString("amount"))
//                                }    else if(i==3)
//                                {
//                                    binding.editTextTextPersonName112.setText(objectArtist.optString("particular"))
//                                    binding.editTextTextPersonName113.setText(objectArtist.optString("amount"))
//                                }    else if(i==4)
//                                {
//                                    binding.editTextTextPersonName114.setText(objectArtist.optString("particular"))
//                                    binding.editTextTextPersonName115.setText(objectArtist.optString("amount"))
//                                }

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

                var args = EditVoucherFragmentArgs.fromBundle(requireArguments())
                params["voucher_no"] = args.voucherNo

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getVoucherIds() {

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_PARTICULAR_VOUCHER_IDS,
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
                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)
                                voucher_ids.add(objectArtist.optString("id"))
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

                var args = EditVoucherFragmentArgs.fromBundle(requireArguments())
                params["voucher_no"] = args.voucherNo

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
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

    private fun removeImage(imageUri: Uri,code:Int) {
        when(code)
        {
            1->{ proofImages.remove(imageUri)
                proofadapter.notifyDataSetChanged()}
        }
    }


    fun imageUriToBase64(contentResolver: ContentResolver, imageUri: Uri): String {
        val inputStream: InputStream? = contentResolver.openInputStream(imageUri)
        val byteArrayOutputStream = ByteArrayOutputStream()

        inputStream?.use { stream ->
            val buffer = ByteArray(1024)
            var bytesRead: Int

            while (stream.read(buffer).also { bytesRead = it } != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead)
            }
        }

        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    override fun onResume() {
        super.onResume()
        var user = SharedPrefManager.getInstance(requireActivity().applicationContext).user

        var i1 = Internet()
        if(i1.checkConnection(requireContext()))
        {

            //for multiple particulars and amounts
            //getVoucherIds()

            if(user.copy_client_id.contains(","))
            {
                val list = (user.copy_client_id.split(",").toTypedArray())
                list.forEach {
                    getCompanies(it)
                }
            }
            else
            {
                getCompanies(user.copy_client_id)
            }
            getGangs()
            //getVoucherTypes()
            //getFromBanks()
            //getToBanks()
        }
        else
        {
            Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
        }
    }

    private fun showProgressDialog() {
        if (progressDialog == null) {
            val progressBar = ProgressBar(requireContext())
            progressBar.isIndeterminate = true
            val padding = 50
            progressBar.setPadding(padding, padding, padding, padding)

            progressDialog = AlertDialog.Builder(requireContext())
                .setTitle("Updating Voucher")
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