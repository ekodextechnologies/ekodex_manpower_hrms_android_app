package com.ekodex.manpowerhrms

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentAddVoucherBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class AddVoucherFragment : Fragment() {

    lateinit var binding: FragmentAddVoucherBinding
    private var progressDialog: AlertDialog? = null
    var voucher_date = ""
    var company_id = ""
    var site_id = ""
    var gang_id = ""
    lateinit var companies: MutableList<Client_Data>

    private val userBanks = mutableListOf<BankData>()
    private val gangBanks = mutableListOf<BankData>()

    lateinit var branches: MutableList<Site_Data>
    lateinit var companies_names: MutableList<String>
    lateinit var branches_names: MutableList<String>

    lateinit var gangs: MutableList<Gang_Data>
    lateinit var gang_names: MutableList<String>

    lateinit var employees: MutableList<Employee_Data>
    lateinit var employee_names: MutableList<String>
    var emp_id = ""
    var emp_code = ""
    lateinit var spinner_data: MutableList<KeyPairBoolData>
    lateinit var selected_spinner: MutableList<KeyPairBoolData>
    val selectedEmployees = mutableListOf<Employee_Data>()

    //==
    private lateinit var splitAdapter: SplitPaymentAdapter


    //image list
    private val proofImages = mutableListOf<Uri>()
    private lateinit var proofadapter: SelectedImagesAdapter

    lateinit var selectedPicture: String
    var count = 0

    //for split of voucher amount
    var amtAllocated = "0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_voucher,
            container,
            false
        )

        var userObj = SharedPrefManager.getInstance(requireContext()).user

        getUserBanks(SharedPrefManager.getInstance(requireContext()).user.id)


        Log.i(
            "11111",
            "client id - ${SharedPrefManager.getInstance(requireContext()).user.client_id}"
        )
        Log.i("11111", "site id - ${SharedPrefManager.getInstance(requireContext()).user.site_id}")
        Log.i(
            "11111",
            "client copy - ${SharedPrefManager.getInstance(requireContext()).user.copy_client_id}"
        )
        Log.i(
            "11111",
            "site copy - ${SharedPrefManager.getInstance(requireContext()).user.copy_site_id}"
        )

        site_id = SharedPrefManager.getInstance(requireContext()).user.site_id
        company_id = SharedPrefManager.getInstance(requireContext()).user.client_id

        spinner_data = mutableListOf()
        selected_spinner = mutableListOf()
        employees = mutableListOf()
        employee_names = mutableListOf()

        companies = mutableListOf()
        branches = mutableListOf()
        employees = mutableListOf()
        companies_names = mutableListOf()
        branches_names = mutableListOf()
        gangs = mutableListOf()
        gang_names = mutableListOf()
        selectedPicture = ""

        proofadapter =
            SelectedImagesAdapter(proofImages, "Proof", requireActivity()) { removeImage(it, 1) }
        binding.proofList.adapter = proofadapter


        company_id =
            SharedPrefManager.getInstance(requireActivity().applicationContext).user.client_id
        getSites(company_id)
        site_id = SharedPrefManager.getInstance(requireActivity().applicationContext).user.site_id


        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val sdf1 = SimpleDateFormat("yyyy-MM-dd")

        //set current date to textviews of table
        val currtDate = sdf.format(Date())
        binding.button29.text = currtDate
        voucher_date = sdf1.format(Date())

        var user = SharedPrefManager.getInstance(requireActivity().applicationContext).user
        var i1 = Internet()
        if (i1.checkConnection(requireContext())) {

            if (user.copy_client_id.contains(",")) {
                val list = (user.copy_client_id.split(",").toTypedArray())
                list.forEach {
                    getCompanies(it)
                }
            } else {
                getCompanies(user.copy_client_id)
            }
            getGangs()
            getVoucherTypes()
            //getFromBanks()
            //getToBanks()
        } else {
            Toast.makeText(
                requireContext(),
                "Please Check internet connection!!",
                Toast.LENGTH_LONG
            ).show()
        }

        //---------------------------------------------------------------------------------------
        binding.company.setOnItemClickListener { parent, _, position, _ ->
            val selectedCompany = parent.getItemAtPosition(position) as Client_Data
            Log.i("11111","Position - ${position}")
          //  Log.i("11111","client - ${selectedCompany.toString()} , id - ${selectedCompany.id}")


            if (selectedCompany != null) {
                Toast.makeText(requireContext(), "Getting Branch!!", Toast.LENGTH_LONG).show()
                binding.site!!.text.clear()

                company_id = selectedCompany.id
                branches.clear()
                branches_names.clear()

                employees.clear()
                emp_id = ""
                emp_code = ""

                binding.site.text.clear()
                getSites(company_id)
            }
        }

        binding.site!!.setOnItemClickListener { parent, _, position, _ ->
            val selectedBranchName = parent.getItemAtPosition(position) as String
            val selectedBranch = branches.find { it.title == selectedBranchName }

            if (selectedBranch != null) {
                site_id = selectedBranch.id
                // binding.employee.text.clear()
                employees.clear()
                emp_id = ""
                emp_code = ""
            }
        }

        binding.gang!!.setOnItemClickListener { parent, _, position, _ ->
            val selectedGangName = parent.getItemAtPosition(position) as String
            val selectedGang = gangs.find { it.name == selectedGangName }

            if (selectedGang != null) {
                gang_id = selectedGang.id
                getGangBanks(gang_id)
                Log.i("11111","Selected Gang - ${gang_id}")
                Log.i("11111","Selected Gang - ${selectedGang.name}")

            }
        }

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

        binding.addDoc.setOnClickListener {
            selectImages()
        }


        //mode dropdown
        var modes =
            arrayListOf<String>("Cash", "Online", "GPAY", "PhonePay", "Upi Payment", "Cheque")
        val adapter1 = ArrayAdapter(requireContext(), R.layout.pay_to_dropdown_layout, modes)
        binding.mode.setAdapter(adapter1)

        binding.button30.setOnClickListener {
            if (i1.checkConnection(requireContext())) {
                addVoucher()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please Check internet connection!!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.editTextTextPersonName107.addTextChangedListener {
            autoSplitEqually()
        }

        return binding.root
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

                        for (i in 0..array.length() - 1) {
                            val objectArtist = array.getJSONObject(i)
                            typeList.add(objectArtist.getString("type"))
                        }

                        val adapter1 = ArrayAdapter(
                            requireContext(),
                            R.layout.pay_to_dropdown_layout,
                            typeList
                        )
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

                        } else {
                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)

                                //set default company
                                if (objectArtist.getString("id") == SharedPrefManager.getInstance(
                                        requireActivity().applicationContext
                                    ).user.client_id
                                ) {
                                    binding.company.setText(objectArtist.getString("title"))
                                }

                                companies.add(
                                    Client_Data(
                                        objectArtist.getString("id"),
                                        objectArtist.getString("title")
                                    )
                                )
                                companies_names.add(objectArtist.getString("title"))
                            }
                            val company_adapter = ArrayAdapter(
                                requireContext(),
                                R.layout.pay_to_dropdown_layout,
                                companies
                            )
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
                Toast.makeText(
                     requireActivity().applicationContext,
                    error.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["id"] = com_id

                return params

            }
        }
        stringRequest.tag = "add_voucher"
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

                        } else {
                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)

                                //set default site
                                if (objectArtist.getString("id") == SharedPrefManager.getInstance(
                                        requireActivity().applicationContext
                                    ).user.site_id
                                ) {
                                    binding.site.setText(objectArtist.getString("title"))
                                }

                                branches.add(
                                    Site_Data(
                                        objectArtist.getString("id"),
                                        objectArtist.getString("title"),
                                        objectArtist.getString("client_code")
                                    )
                                )
                                branches_names.add(objectArtist.getString("title"))
                            }
                            val site_adapter = ArrayAdapter(
                                requireContext(),
                                R.layout.pay_to_dropdown_layout,
                                branches_names
                            )
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
        stringRequest.tag = "add_voucher"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getGangs() {
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_ALL_GANGS,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        for (i in 0..array.length() - 1) {
                            val objectArtist = array.getJSONObject(i)

                            gangs.add(
                                Gang_Data(
                                    objectArtist.getString("id"),
                                    objectArtist.getString("gang")
                                )
                            )
                            gang_names.add(objectArtist.getString("gang"))
                        }
                        val gang_adapter = ArrayAdapter(
                            requireContext(),
                            R.layout.pay_to_dropdown_layout,
                            gang_names
                        )
                        binding.gang.setAdapter(gang_adapter)


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


    private fun getFromBanks() {

        val bankList = mutableListOf<String>()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_ALL_BANKS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            Toast.makeText(requireContext(), "No Banks!!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            for (i in 0..array.length() - 1) {
                                val objectArtist = array.getJSONObject(i)
                                bankList.add(objectArtist.getString("bank"))
                            }
                            val adapter1 = ArrayAdapter(
                                requireContext(),
                                R.layout.pay_to_dropdown_layout,
                                bankList
                            )
                            binding.fromBank.setAdapter(adapter1)

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
                params["role"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.role
                params["status"] = "Working"

                return params

            }
        }
        stringRequest.tag = "add_voucher"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getToBanks() {

        val bankList = mutableListOf<String>()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_ALL_BANKS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            Toast.makeText(requireContext(), "No Banks!!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            for (i in 0..array.length() - 1) {
                                val objectArtist = array.getJSONObject(i)
                                bankList.add(objectArtist.getString("bank"))
                            }

                            val adapter1 = ArrayAdapter(
                                requireContext(),
                                R.layout.pay_to_dropdown_layout,
                                bankList
                            )
                            binding.toBank.setAdapter(adapter1)

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
                params["role"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.role
                params["status"] = "Working"

                return params

            }
        }
        stringRequest.tag = "add_voucher"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun addVoucher() {

        var company = binding.company.text.toString()
        var site = binding.site.text.toString()
        var gang = binding.gang.text.toString()
        var mode = binding.mode.text.toString()
        var part1 = binding.editTextTextPersonName106.text.toString()
        var amt1 = binding.editTextTextPersonName107.text.toString()
        var type = binding.type.text.toString()


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

        if (TextUtils.isEmpty(amt1)) {
            binding.editTextTextPersonName107.error = "Please add amount!!"
            binding.editTextTextPersonName107.requestFocus()
            return
        }

        if (TextUtils.isEmpty(type)) {
            binding.type.error = "Please select voucher type!!"
            binding.type.requestFocus()
            return
        }

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

        binding.button30.isEnabled = false
        this.showProgressDialog()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_ADD_VOUCHER,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        // binding.progressBar9.visibility = View.GONE
                        hideProgressDialog()
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(
                            AddVoucherFragmentDirections.actionAddVoucherFragmentToVoucherManagementFragment(
                                "",
                                ""
                            )
                        )

                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        hideProgressDialog()
                        binding.button30.isEnabled = true
                    }
                } catch (e: JSONException) {
                    
                    hideProgressDialog()
                    binding.button30.isEnabled = true

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
                    binding.button30.isEnabled = true
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()

                params["client_id"] = company_id
                params["site_id"] = site_id
                params["vendor_id"] = ""
                params["vendor_name"] = ""
                params["client_name"] = binding.company.text.toString()
                params["site_name"] = binding.site.text.toString()
                params["adv_date"] = voucher_date
                params["gang"] = gang
                params["mode"] = mode
                params["type"] = type
                params["image"] = selectedPicture
                params["created_by"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.id
                params["part1"] = part1
                params["employees"] = ""
                params["bank"] = binding.editTextTextPersonName120.text.toString()
                params["beneficiary_name"] = binding.editTextTextPersonName119.text.toString()
                params["ifsc"] = binding.editTextTextPersonName122.text.toString()
                params["account_no"] = binding.editTextTextPersonName121.text.toString()
                params["branch"] = binding.editTextTextPersonName123.text.toString()
                params["original_amount"] = amt1


                //voucher images
                params["proofCount"] = proofImages.size.toString()

                if (proofImages.size > 0) {
                    for (i in 0..proofImages.size - 1) {
                        var num = (i + 1).toString()
                        params["proof$num"] =
                            imageUriToBase64Max100KB(requireActivity().contentResolver, proofImages.get(i))
                    }
                }

                return params
            }
        }
        stringRequest.tag = "add_voucher"
        stringRequest.retryPolicy = DefaultRetryPolicy(8000,0,1f)
        VolleySingleton.getInstance(requireActivity().applicationContext).addToRequestQueue(stringRequest)


    }

    private fun selectImages() {
        //cropping image selection
        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
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


    private fun removeImage(imageUri: Uri, code: Int) {
        when (code) {
            1 -> {
                proofImages.remove(imageUri)
                proofadapter.notifyDataSetChanged()
            }
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

    //to store images under 100kb
    fun imageUriToBase64Max100KB(
        contentResolver: ContentResolver,
        imageUri: Uri
    ): String {

        val originalBitmap =
            MediaStore.Images.Media.getBitmap(contentResolver, imageUri)

        // 🔹 Resize first (VERY IMPORTANT)
        val maxWidth = 1080
        val ratio = originalBitmap.height.toFloat() / originalBitmap.width.toFloat()
        val resizedBitmap = Bitmap.createScaledBitmap(
            originalBitmap,
            maxWidth,
            (maxWidth * ratio).toInt(),
            true
        )

        var quality = 90
        var base64String: String

        do {
            val baos = ByteArrayOutputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos)
            val bytes = baos.toByteArray()
            base64String = Base64.encodeToString(bytes, Base64.NO_WRAP)

            quality -= 5
        } while (bytes.size > 100 * 1024 && quality > 20)

        return base64String
    }

    /*    private fun calculateSplitTotal() {
            val finalAmtText = binding.editTextTextPersonName107.text.toString()

            val finalAmt = finalAmtText.toDoubleOrNull() ?: 0.0

            var total = 0.0
            for (emp in selectedEmployees) {
                total += emp.amt_to_pay.toDoubleOrNull() ?: 0.0
            }

            if (total == finalAmt) {
                Toast.makeText(requireContext(),"Amount Matched",Toast.LENGTH_SHORT).show()
    //            binding.amountStatus.text = "Amount Matched ✔"
    //            binding.amountStatus.setTextColor(resources.getColor(android.R.color.holo_green_dark))
            } else {
                Toast.makeText(requireContext(),"Remaining - ${finalAmt-total}",Toast.LENGTH_SHORT).show()

    //            binding.amountStatus.text = "Total ₹$total / ₹$finalAmt"
    //            binding.amountStatus.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            }
        }*/

    private fun calculateSplitTotal() {
        var total = 0.0

        selectedEmployees.forEach {
            total += it.amt_to_pay.toDoubleOrNull() ?: 0.0
        }

        val finalAmount = binding.editTextTextPersonName107.text.toString().toDoubleOrNull() ?: 0.0
        val isMatch = kotlin.math.abs(total - finalAmount) < 0.01

        if (isMatch) {
            binding.button30.isEnabled = true
            binding.button30.background.setTint(Color.parseColor("#303262"))
        } else {
            binding.button30.isEnabled = false
            binding.button30.background.setTint(Color.parseColor("#BDBDBD"))
        }
    }

    private fun autoSplitEqually() {
        val totalAmtText = binding.editTextTextPersonName107.text.toString()

        if (totalAmtText.isEmpty()) return
        if (selectedEmployees.isEmpty()) return

        val totalAmount = totalAmtText.toDouble()
        val count = selectedEmployees.size

        val rawSplit = totalAmount / count
        val roundedSplit = String.format("%.2f", rawSplit).toDouble()

        // Step 1: Assign rounded values
        selectedEmployees.forEach { it.amt_to_pay = roundedSplit.toString() }

        // Step 2: Fix rounding difference – adjust last person
        val currentSum = selectedEmployees.sumOf { it.amt_to_pay.toDouble() }
        val diff = totalAmount - currentSum

        // add difference (may be 0.01 or -0.01)
        val last = selectedEmployees.last()
        last.amt_to_pay = String.format("%.2f", last.amt_to_pay.toDouble() + diff)

        // Update UI
        splitAdapter.notifyDataSetChanged()
        calculateSplitTotal()
    }


    /*    private fun autoSplitEqually() {
            val totalAmtText = binding.editTextTextPersonName107.text.toString()

            if (totalAmtText.isEmpty()) return
            if (selectedEmployees.isEmpty()) return

            val finalAmount = totalAmtText.toDouble()
            val perPerson = String.format("%.2f", finalAmount / selectedEmployees.size)


            selectedEmployees.forEach { it.amt_to_pay = perPerson.toString() }

            splitAdapter.notifyDataSetChanged()
            calculateSplitTotal()
            Log.i("11111",selectedEmployees.toString())
        }*/

    /*    private fun getBankDetails() {
            val stringRequest = object : StringRequest(
                Request.Method.POST, URLs.URL_GET_BENEFICIARY_BANK_DETAILS,
                Response.Listener { response ->

                    try {
                        //converting response to json object
                        val obj = JSONObject(response)
                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            val userJson = obj.getJSONObject("user")

                            if(userJson.getString("bank")!=null && (userJson.getString("bank"))!="" && userJson.getString("bank")!="null")
                            {
                                binding.editTextTextPersonName120.setText(userJson.getString("bank"))
                            }
                            if(userJson.getString("name")!=null && (userJson.getString("name"))!="" && userJson.getString("name")!="null")
                            {
                                binding.editTextTextPersonName119.setText(userJson.getString("name"))
                            }
                            if(userJson.getString("account_no")!=null && (userJson.getString("account_no"))!="" && userJson.getString("account_no")!="null")
                            {
                                binding.editTextTextPersonName121.setText(userJson.getString("account_no"))
                            }
                            if(userJson.getString("ifsc")!=null && (userJson.getString("ifsc"))!="" && userJson.getString("ifsc")!="null")
                            {
                                binding.editTextTextPersonName122.setText(userJson.getString("ifsc"))
                            }

                            if(userJson.getString("city")!=null && (userJson.getString("city"))!="" && userJson.getString("city")!="null")
                            {
                                binding.editTextTextPersonName123.setText(userJson.getString("city"))
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
                    Toast.makeText(
                        requireActivity().applicationContext,
                        error.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = java.util.HashMap<String, String>()

                    params["id"] = SharedPrefManager.getInstance(requireContext()).user.id

                    return params

                }
            }

            VolleySingleton.getInstance(requireActivity().applicationContext)
                .addToRequestQueue(stringRequest)
        }*/


    private fun showProgressDialog() {
        if (progressDialog == null) {
            val progressBar = ProgressBar(requireContext())
            progressBar.isIndeterminate = true
            val padding = 50
            progressBar.setPadding(padding, padding, padding, padding)

            progressDialog = AlertDialog.Builder(requireContext())
                .setTitle("Adding Voucher")
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

    private fun getUserBanks(userId: String) {

        userBanks.clear()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_PARTICULAR_USER_BANKS,
            Response.Listener { response ->

                try {
                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {

                        val array = obj.getJSONArray("user")

                        for (i in 0 until array.length()) {
                            val o = array.getJSONObject(i)

                            val bank = BankData(
                                o.getString("id").safe(),
                                o.getString("bank_name").safe(),
                                o.getString("account_number").safe(),
                                o.getString("bank_ifsc").safe(),
                                o.getString("bank_address").safe(),
                                o.getString("bank_state").safe(),
                                o.getString("bank_city").safe(),
                                o.getString("bank_micr").safe(),
                                o.getString("card_no").safe(),
                                o.getString("ac_holder_name").safe()
                            )

                            // ✅ ADD ONLY IF VALID
                            if (
                                bank.bank_name.isNotBlank() && !bank.bank_name.equals("NA", true) ||
                                bank.acc_no.isNotBlank() && !bank.acc_no.equals("NA", true) ||
                                bank.ifsc.isNotBlank() && !bank.ifsc.equals("NA", true)
                            ) {
                                userBanks.add(bank)
                            }
                        }

                        setupBankRadioButtons()
                    }

                } catch (e: JSONException) {
                    
                }

            },
            Response.ErrorListener { }
        ) {
            override fun getParams(): Map<String, String> {
                return hashMapOf("user_id" to userId)
            }
        }
        stringRequest.tag = "add_voucher"
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

    private fun getGangBanks(siteId: String) {

        gangBanks.clear()   // 🔥 REPLACE OLD SITE BANKS

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_PARTICULAR_USER_BANKS,
            Response.Listener { response ->

                try {
                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {

                        val array = obj.getJSONArray("user")

                        for (i in 0 until array.length()) {
                            val o = array.getJSONObject(i)

                            val bank = BankData(
                                o.getString("id").safe(),
                                o.getString("bank_name").safe(),
                                o.getString("account_number").safe(),
                                o.getString("bank_ifsc").safe(),
                                o.getString("bank_address").safe(),
                                o.getString("bank_state").safe(),
                                o.getString("bank_city").safe(),
                                o.getString("bank_micr").safe(),
                                o.getString("card_no").safe(),
                                o.getString("ac_holder_name").safe()
                            )

                            // ✅ ADD ONLY IF VALID
                            if (
                                bank.bank_name.isNotBlank() && !bank.bank_name.equals("NA", true) ||
                                bank.acc_no.isNotBlank() && !bank.acc_no.equals("NA", true) ||
                                bank.ifsc.isNotBlank() && !bank.ifsc.equals("NA", true)
                            ) {
                                gangBanks.add(bank)
                            }
                        }

                        setupBankRadioButtons()
                    }

                } catch (e: JSONException) {
                    
                }

            },
            Response.ErrorListener { }
        ) {
            override fun getParams(): Map<String, String> {
                return hashMapOf("user_id" to siteId)
            }
        }
        stringRequest.tag = "add_voucher"
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

    fun String?.safe() = this?.takeIf { it.isNotBlank() && !it.equals("null", true) } ?: "NA"

    private fun setupBankRadioButtons() {

        val group = binding.bankRadioGroup
        group.removeAllViews()

        if (userBanks.isEmpty() && gangBanks.isEmpty()) {
            group.visibility = View.GONE
            return
        }

        group.visibility = View.VISIBLE

        // 1️⃣ SITE BANKS FIRST (REPLACEABLE)
        for ((index, bank) in gangBanks.withIndex()) {
            val rb = RadioButton(requireContext())
            rb.id = View.generateViewId()
            rb.text = "${bank.bank_name.safe()}, ${bank.acc_no.safe()}, ${bank.ifsc} - ${bank.holder}"
            rb.tag = "site_$index"
            rb.setPadding(10, 10, 10, 10)
            group.addView(rb)
        }

        // 2️⃣ USER BANKS (PRESERVED)
        for ((index, bank) in userBanks.withIndex()) {
            val rb = RadioButton(requireContext())
            rb.id = View.generateViewId()
            rb.text = "${bank.bank_name.safe()}, ${bank.acc_no.safe()}, ${bank.ifsc} - ${bank.holder}"
            rb.tag = "user_$index"
            rb.setPadding(10, 10, 10, 10)
            group.addView(rb)
        }

        // 3️⃣ ADD MANUALLY
        val noBank = RadioButton(requireContext())
        noBank.id = View.generateViewId()
        noBank.text = "Add Manually"
        noBank.tag = "manual"
        group.addView(noBank)

        group.setOnCheckedChangeListener { _, checkedId ->

            val selected = group.findViewById<RadioButton>(checkedId)
            val tag = selected.tag.toString()

            when {
                tag.startsWith("site_") -> {
                    val bank = gangBanks[tag.removePrefix("site_").toInt()]
                    binding.editTextTextPersonName119.setText(bank.holder)
                    binding.editTextTextPersonName120.setText(bank.bank_name)
                    binding.editTextTextPersonName121.setText(bank.acc_no)
                    binding.editTextTextPersonName122.setText(bank.ifsc)
                    binding.editTextTextPersonName123.setText(bank.city)
                }

                tag.startsWith("user_") -> {
                    val bank = userBanks[tag.removePrefix("user_").toInt()]
                    binding.editTextTextPersonName119.setText(bank.holder)
                    binding.editTextTextPersonName120.setText(bank.bank_name)
                    binding.editTextTextPersonName121.setText(bank.acc_no)
                    binding.editTextTextPersonName122.setText(bank.ifsc)
                    binding.editTextTextPersonName123.setText(bank.city)
                }

                tag == "manual" -> {
                    binding.editTextTextPersonName119.setText("")
                    binding.editTextTextPersonName120.setText("")
                    binding.editTextTextPersonName121.setText("")
                    binding.editTextTextPersonName122.setText("")
                    binding.editTextTextPersonName123.setText("")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        VolleySingleton.getInstance(requireActivity()).requestQueue.cancelAll("add_voucher")
    }

}