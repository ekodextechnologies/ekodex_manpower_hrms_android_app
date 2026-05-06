package com.ekodex.manpowerhrms

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
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
import com.ekodex.manpowerhrms.databinding.FragmentAddSiteVoucherBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class AddSiteVoucherFragment : Fragment() {

    lateinit var binding: FragmentAddSiteVoucherBinding
    private var progressDialog: AlertDialog? = null
    var voucher_date = ""
    var company_id = ""
    var site_id = ""
    var vendor_id = ""
    var vendor_name = "NA"
    lateinit var companies: MutableList<Client_Data>
    lateinit var branches: MutableList<Site_Data>
    lateinit var companies_names: MutableList<String>
    lateinit var branches_names: MutableList<String>
    lateinit var vendors: MutableList<Site_Data>
    lateinit var vendor_names: MutableList<String>

    lateinit var employees: MutableList<Employee_Data>
    lateinit var employee_names: MutableList<String>

    private val userBanks = mutableListOf<BankData>()
    private val siteBanks = mutableListOf<BankData>()
    private val vendorBanks = mutableListOf<BankData>()

    var emp_id = ""
    var emp_code = ""
    lateinit var spinner_data: MutableList<KeyPairBoolData>
    lateinit var selected_spinner: MutableList<KeyPairBoolData>
     private val selectedSite = mutableListOf<Site_Data>()

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
            R.layout.fragment_add_site_voucher,
            container,
            false
        )


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

        Log.i(
            "11111",
            "user id - ${SharedPrefManager.getInstance(requireContext()).user.id}"
        )

        site_id = SharedPrefManager.getInstance(requireContext()).user.site_id
        company_id = SharedPrefManager.getInstance(requireContext()).user.client_id

        spinner_data = mutableListOf()
        selected_spinner = mutableListOf()
        employees = mutableListOf()
        employee_names = mutableListOf()

        companies = mutableListOf()
        branches = mutableListOf()
        vendors = mutableListOf()
        employees = mutableListOf()
        companies_names = mutableListOf()
        branches_names = mutableListOf()
        vendor_names = mutableListOf()
        selectedPicture = ""

        proofadapter =
            SelectedImagesAdapter(proofImages, "Proof", requireActivity()) { removeImage(it, 1) }
        binding.proofList.adapter = proofadapter


        company_id =
            SharedPrefManager.getInstance(requireActivity().applicationContext).user.client_id
        getSites(company_id)
        site_id = SharedPrefManager.getInstance(requireActivity().applicationContext).user.site_id

        getUserBanks(SharedPrefManager.getInstance(requireContext()).user.id)


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
            getVoucherTypes()
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
                site_id = ""
                branches.clear()
                branches_names.clear()
                siteBanks.clear()

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

                Log.i("11111","company id - ${company_id}")
                Log.i("11111","site id - ${site_id}")

                vendor_id = ""
                vendor_name = ""
                vendors.clear()
                vendor_names.clear()
                vendorBanks.clear()

                // binding.employee.text.clear()
                employees.clear()
                emp_id = ""
                emp_code = ""

                getSiteBanks(site_id)
                getVendors()
                Log.i("11111","site id - ${site_id}")
            }
        }

        binding.vendor!!.setOnItemClickListener { parent, _, position, _ ->
            val selectedVendorhName = parent.getItemAtPosition(position) as String
            val selectedVendor = vendors.find { it.title == selectedVendorhName }

            if (selectedVendorhName != null) {
                vendor_id = selectedVendor!!.id
                vendor_name = selectedVendor!!.title
                getVendorBanks(vendor_id)
                Log.i("11111","vendor id - ${vendor_id}")
                Log.i("11111","vendor name - ${vendor_name}")
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
                                    company_id = objectArtist.getString("id")
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
                
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["id"] = com_id

                return params

            }
        }
        stringRequest.tag = "site_voucher"
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
                                    site_id = objectArtist.getString("id")
                                    getSiteBanks(objectArtist.getString("id"))
                                    getVendors()
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
        stringRequest.tag = "site_voucher"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getVendors() {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_ALL_VENDORS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        vendors.add(
                            Site_Data(
                               "",
                              "NA" ,
                                ""
                            )
                        )
                        vendor_names.add("NA")

                        if (array.length() <= 0) {
                            val vendor_adapter = ArrayAdapter(
                                requireContext(),
                                R.layout.pay_to_dropdown_layout,
                                vendor_names
                            )
                            binding.vendor.setAdapter(vendor_adapter)
                            // Toast.makeText(requireContext(),"No companies available!!", Toast.LENGTH_SHORT).show()
//                            binding.tl10.visibility = View.GONE
//                            binding.textView478.visibility = View.GONE
                        } else {
                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)

                                vendors.add(
                                    Site_Data(
                                        objectArtist.getString("id"),
                                        objectArtist.getString("title"),
                                        ""
                                    )
                                )
                                vendor_names.add(objectArtist.getString("title"))
                            }
                            val vendor_adapter = ArrayAdapter(
                                requireContext(),
                                R.layout.pay_to_dropdown_layout,
                                vendor_names
                            )
                            binding.vendor.setAdapter(vendor_adapter)

                            binding.tl10.visibility = View.VISIBLE
                            binding.textView478.visibility = View.VISIBLE
                        }


                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()

                        vendors.add(
                            Site_Data(
                                "",
                                "NA" ,
                                ""
                            )
                        )
                        vendor_names.add("NA")

                        val vendor_adapter = ArrayAdapter(
                            requireContext(),
                            R.layout.pay_to_dropdown_layout,
                            vendor_names
                        )
                        binding.vendor.setAdapter(vendor_adapter)
//                        binding.tl10.visibility = View.GONE
//                        binding.textView478.visibility = View.GONE
                    }

                } catch (e: JSONException) {
                    
                    vendors.add(
                        Site_Data(
                            "",
                            "NA" ,
                            ""
                        )
                    )
                    vendor_names.add("NA")

                    val vendor_adapter = ArrayAdapter(
                        requireContext(),
                        R.layout.pay_to_dropdown_layout,
                        vendor_names
                    )
                    binding.vendor.setAdapter(vendor_adapter)
//                    binding.tl10.visibility = View.GONE
//                    binding.textView478.visibility = View.GONE

                }

            },
            Response.ErrorListener { error ->
                

                vendors.add(
                    Site_Data(
                        "",
                        "NA" ,
                        ""
                    )
                )
                vendor_names.add("NA")

                val vendor_adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.pay_to_dropdown_layout,
                    vendor_names
                )
                binding.vendor.setAdapter(vendor_adapter)
//                binding.tl10.visibility = View.GONE
//                binding.textView478.visibility = View.GONE
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                params["client_id"] = company_id
                params["site_id"] = site_id
                return params

            }
        }
        stringRequest.tag = "site_voucher"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
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
        stringRequest.tag = "site_voucher"
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
        stringRequest.tag = "site_voucher"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }


    private fun setupBankRadioButtons() {

        val group = binding.bankRadioGroup
        group.removeAllViews()

        if (userBanks.isEmpty() && siteBanks.isEmpty() && vendorBanks.isEmpty()) {
            group.visibility = View.GONE
            return
        }

        group.visibility = View.VISIBLE

        // 1️⃣ SITE BANKS FIRST (REPLACEABLE)
        for ((index, bank) in siteBanks.withIndex()) {
            val rb = RadioButton(requireContext())
            rb.id = View.generateViewId()
            rb.text = "${bank.bank_name.safe()}, ${bank.acc_no.safe()}, ${bank.ifsc} - ${bank.holder}"
            rb.tag = "site_$index"
            rb.setPadding(10, 10, 10, 10)
            group.addView(rb)
        }

        // 1️⃣ VENDOR BANKS FIRST (REPLACEABLE)
        for ((index, bank) in vendorBanks.withIndex()) {
            val rb = RadioButton(requireContext())
            rb.id = View.generateViewId()
            rb.text = "${bank.bank_name.safe()}, ${bank.acc_no.safe()}, ${bank.ifsc} - ${bank.holder}"
            rb.tag = "vendor_$index"
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
                    val bank = siteBanks[tag.removePrefix("site_").toInt()]
                    binding.editTextTextPersonName119.setText(bank.holder)
                    binding.editTextTextPersonName120.setText(bank.bank_name)
                    binding.editTextTextPersonName121.setText(bank.acc_no)
                    binding.editTextTextPersonName122.setText(bank.ifsc)
                    binding.editTextTextPersonName123.setText(bank.city)
                }

                tag.startsWith("vendor_") -> {
                    val bank = vendorBanks[tag.removePrefix("vendor_").toInt()]
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

    private fun addVoucher() {

        var company = binding.company.text.toString()
        var site = binding.site.text.toString()
        var type = binding.type.text.toString()
        var mode = binding.mode.text.toString()
        var part1 = binding.editTextTextPersonName106.text.toString()
        var amt1 = binding.editTextTextPersonName107.text.toString()

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
        showProgressDialog()

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
                            AddSiteVoucherFragmentDirections.actionAddSiteVoucherFragmentToVoucherManagementFragment("","")
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
                params["vendor_id"] = vendor_id
                params["vendor_name"] = vendor_name
                params["client_name"] = binding.company.text.toString()
                params["site_name"] = binding.site.text.toString()
                params["adv_date"] = voucher_date
                params["gang"] = ""
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
        stringRequest.tag = "site_voucher"
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

    private fun isValidEmployeeBank(bank: BankData): Boolean {
        return listOf(
            bank.bank_name,
            bank.acc_no,
            bank.ifsc,
            bank.city,
            bank.holder
        ).any { it.isNotBlank() && !it.equals("NA", true) }
    }


    fun String?.safe() = this?.takeIf { it.isNotBlank() && !it.equals("null", true) } ?: "NA"

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
        stringRequest.tag = "site_voucher"

        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

    private fun getSiteBanks(siteId: String) {

        siteBanks.clear()   // 🔥 REPLACE OLD SITE BANKS

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
                                siteBanks.add(bank)
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
        stringRequest.tag = "site_voucher"
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

    private fun getVendorBanks(vendorId: String) {

        vendorBanks.clear()   // 🔥 REPLACE OLD SITE BANKS

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
                                vendorBanks.add(bank)
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
                return hashMapOf("user_id" to vendorId)
            }
        }
        stringRequest.tag = "site_voucher"
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        VolleySingleton.getInstance(requireActivity()).requestQueue.cancelAll("site_voucher")
    }
}