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
import com.androidbuts.multispinnerfilter.SingleSpinnerListener
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentAddEmployeeVoucherBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class AddEmployeeVoucherFragment : Fragment() {

    lateinit var binding: FragmentAddEmployeeVoucherBinding
    private var progressDialog: AlertDialog? = null
    var voucher_date = ""
    var company_id = ""
    var site_id = ""
    lateinit var banks: MutableList<BankData>
    lateinit var companies: MutableList<Client_Data>
    lateinit var branches: MutableList<Site_Data>
    lateinit var companies_names: MutableList<String>
    lateinit var branches_names: MutableList<String>

    lateinit var employees: MutableList<Employee_Data>
    lateinit var employee_names: MutableList<String>
    private var employeeBank: BankData? = null
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
            R.layout.fragment_add_employee_voucher,
            container,
            false
        )

        var userObj = SharedPrefManager.getInstance(requireContext()).user


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

        //==
        splitAdapter = SplitPaymentAdapter(selectedEmployees) {
            calculateSplitTotal()
        }
        binding.splitRcv.adapter = splitAdapter

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
        selectedPicture = ""
        banks = mutableListOf()

        proofadapter =
            SelectedImagesAdapter(proofImages, "Proof", requireActivity()) { removeImage(it, 1) }
        binding.proofList.adapter = proofadapter


        company_id =
            SharedPrefManager.getInstance(requireActivity().applicationContext).user.client_id
        getSites(company_id)
        site_id = SharedPrefManager.getInstance(requireActivity().applicationContext).user.site_id

        if (SharedPrefManager.getInstance(requireContext()).user.role.equals(
                "Employee",
                ignoreCase = true
            )
        ) {
            binding.multipleItemSelectionSpinner.visibility = View.GONE
            binding.textView388.visibility = View.GONE
            var name = "${userObj.fname} ${userObj.lname}"
            selectedEmployees.add(
                Employee_Data(
                    userObj.id,
                    name,
                    userObj.rank,
                    userObj.emp_code,
                    "0"
                )
            )
        } else {
            getAllEmployees()
        }

        getBankDetails()

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
                binding.multipleItemSelectionSpinner

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
                getAllEmployees()
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


        binding.multipleItemSelectionSpinner.setSearchEnabled(true)
        binding.multipleItemSelectionSpinner.setSearchHint("Search Employee")
        binding.multipleItemSelectionSpinner.setEmptyTitle("No Data Found!")


        return binding.root
    }

    private fun getAllEmployees() {
        employee_names.clear()
        employees.clear()
        spinner_data.clear()

        if (!SharedPrefManager.getInstance(requireContext()).user.role.equals(
                "Employee", ignoreCase = true
            )
        ) {
            selectedEmployees.clear()
        }

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_ALL_EMPLOYEES_FOR_SUPERVISOR,
            Response.Listener { response ->

                try {
                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            Toast.makeText(
                                requireContext(),
                                "No employees available!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.multipleItemSelectionSpinner.visibility = View.GONE

                            selectedEmployees.add(
                                Employee_Data(
                                    "No Employee", "", "", "",
                                    binding.editTextTextPersonName107.text.toString()
                                )
                            )
                        } else {

                            // default "No Employee"
                            employee_names.add("No Employee")
                            employees.add(Employee_Data("0", "No Employee", "", "", ""))

                            spinner_data.add(
                                KeyPairBoolData().apply {
                                    id = 0L
                                    name = "No Employee"
                                    isSelected = false
                                }
                            )

                            for (i in 0 until array.length()) {
                                val objectArtist = array.getJSONObject(i)

                                employee_names.add(
                                    "${objectArtist.getString("name")} - ${
                                        objectArtist.getString("emp_code")
                                    }"
                                )

                                employees.add(
                                    Employee_Data(
                                        objectArtist.getString("id"),
                                        objectArtist.getString("name"),
                                        objectArtist.getString("rank"),
                                        objectArtist.getString("emp_code"),
                                        ""
                                    )
                                )

                                spinner_data.add(
                                    KeyPairBoolData().apply {
                                        id = objectArtist.getString("id").toLong()
                                        name =
                                            "${objectArtist.getString("name")} - ${
                                                objectArtist.getString("emp_code")
                                            }"
                                        isSelected = false
                                    }
                                )
                            }

                            val adapter1 = ArrayAdapter(
                                requireContext(),
                                R.layout.pay_to_dropdown_layout,
                                employee_names
                            )

                            // -----------------------------
                            // 🔥 FIXED SINGLE SELECTION LOGIC
                            // -----------------------------
                            binding.multipleItemSelectionSpinner.setItems(spinner_data, object : SingleSpinnerListener {

                                override fun onItemsSelected(selectedItem: KeyPairBoolData) {
                                    // Clear previous selection
                                    selectedEmployees.clear()


                                    Log.i("11111","selected emp id - ${selectedItem.id}")
                                    val emp = employees.find { it.id == selectedItem.id.toString() }
                                    if (emp != null)
                                    {
                                        selectedEmployees.add(emp)
                                        getEmployeeBankDetail(emp.id)
                                    }

                                    splitAdapter.notifyDataSetChanged()
                                    autoSplitEqually()
                                }

                                override fun onClear() {
                                    selectedEmployees.clear()
                                }
                            })



                        }

                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()

                        binding.multipleItemSelectionSpinner.visibility = View.GONE

                        selectedEmployees.add(
                            Employee_Data(
                                "",
                                "No Employee",
                                "",
                                "",
                                binding.editTextTextPersonName107.text.toString()
                            )
                        )
                    }

                } catch (e: JSONException) {
                    
                }

            },
            Response.ErrorListener {
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["client_id"] = company_id
                params["site_id"] = site_id
                return params
            }
        }
        stringRequest.tag = "employee_voucher"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getBankDetails() {
        banks.clear()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_PARTICULAR_USER_BANKS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            binding.bankRadioGroup.visibility = View.GONE
                            binding.textView455.visibility = View.GONE
                        } else {
                            for (i in 0..(array.length() - 1)) {
                                val objectArtist = array.getJSONObject(i)
                                banks.add(
                                    BankData(
                                        objectArtist.getString("id").safe(),
                                        objectArtist.getString("bank_name").safe(),
                                        objectArtist.getString("account_number").safe(),
                                        objectArtist.getString("bank_ifsc").safe(),
                                        objectArtist.getString("bank_address").safe(),
                                        objectArtist.getString("bank_state").safe(),
                                        objectArtist.getString("bank_city").safe(),
                                        objectArtist.getString("bank_micr").safe(),
                                        objectArtist.getString("card_no").safe(),
                                        objectArtist.getString("ac_holder_name").safe()
                                    )
                                )
                            }
                            setupBankRadioButtons()
                        }
                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        binding.bankRadioGroup.visibility = View.GONE
                        binding.textView455.visibility = View.GONE
                    }

                } catch (e: JSONException) {
                    
                    binding.bankRadioGroup.visibility = View.GONE
                    binding.textView455.visibility = View.GONE

                }

            },
            Response.ErrorListener { error ->
                
                binding.bankRadioGroup.visibility = View.GONE
                binding.textView455.visibility = View.GONE
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["user_id"] = SharedPrefManager.getInstance(requireContext()).user.id
                return params

            }
        }
        stringRequest.tag = "employee_voucher"
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
                
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["id"] = com_id

                return params

            }
        }
        stringRequest.tag = "employee_voucher"
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
        stringRequest.tag = "employee_voucher"
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
        stringRequest.tag = "employee_voucher"
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
        stringRequest.tag = "employee_voucher"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
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

        if (selectedEmployees.size <= 0) {
            binding.editTextTextPersonName107.error = "Please select employee!!"
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

        val employeesJsonArray = org.json.JSONArray()
        for (emp in selectedEmployees) {
            val obj = org.json.JSONObject()
            obj.put("emp_id", emp.id ?: "")
            obj.put("emp_name", emp.name ?: "")
            obj.put("emp_code", emp.emp_code ?: "")
            obj.put("rank", emp.rank ?: "")
            obj.put("amt", emp.amt_to_pay ?: "")
            employeesJsonArray.put(obj)
        }

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
                            AddEmployeeVoucherFragmentDirections.actionAddEmployeeVoucherFragmentToVoucherManagementFragment("","")
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
                params["gang"] = ""
                params["mode"] = mode
                params["type"] = type
                params["image"] = selectedPicture
                params["created_by"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.id
                params["part1"] = part1
                params["employees"] = employeesJsonArray.toString()
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
        stringRequest.tag = "employee_voucher"
        stringRequest.retryPolicy = DefaultRetryPolicy(80000,0,1f)
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

    private fun setupBankRadioButtons() {

        val group = binding.bankRadioGroup
        group.removeAllViews()

        // If both lists empty
        if (banks.isEmpty() && employeeBank == null) {
            group.visibility = View.GONE
            return
        }

        group.visibility = View.VISIBLE

        var rbIndex = 0

        // 1️⃣ Add Employee Bank First (IF AVAILABLE)
        employeeBank?.let { bank ->
            val rb = RadioButton(requireContext())
            rb.id = View.generateViewId()
            rb.text = "${bank.bank_name.safe()}, ${bank.acc_no.safe()}, ${bank.ifsc} - ${bank.holder}"
            rb.tag = "emp"                // UNIQUE TAG
            rb.setPadding(10, 10, 10, 10)
            group.addView(rb)
        }

        // 2️⃣ Add User's Saved Banks
        for ((index, bank) in banks.withIndex()) {
            val rb = RadioButton(requireContext())
            rb.id = View.generateViewId()
            rb.text = "${bank.bank_name.safe()}, ${bank.acc_no.safe()}, ${bank.ifsc} - ${bank.holder}"
            rb.tag = index                // original index
            rb.setPadding(10, 10, 10, 10)
            group.addView(rb)
        }

        // 3️⃣ Add "Add Manually"
        val noBank = RadioButton(requireContext())
        noBank.id = View.generateViewId()
        noBank.text = "Add Manually"
        noBank.tag = -1
        group.addView(noBank)

        // Listener
        group.setOnCheckedChangeListener { _, checkedId ->
            val selected = group.findViewById<RadioButton>(checkedId)
            val tag = selected.tag

            when (tag) {

                "emp" -> {     // Employee bank
                    employeeBank?.let { bank ->
                        binding.editTextTextPersonName119.setText(bank.holder)
                        binding.editTextTextPersonName120.setText(bank.bank_name)
                        binding.editTextTextPersonName121.setText(bank.acc_no)
                        binding.editTextTextPersonName122.setText(bank.ifsc)
                        binding.editTextTextPersonName123.setText(bank.city)
                    }
                }

                -1 -> {         // Add Manually
                    binding.editTextTextPersonName119.setText("")
                    binding.editTextTextPersonName120.setText("")
                    binding.editTextTextPersonName121.setText("")
                    binding.editTextTextPersonName122.setText("")
                    binding.editTextTextPersonName123.setText("")
                }

                else -> {       // User's saved banks
                    val bank = banks[tag as Int]
                    binding.editTextTextPersonName119.setText(bank.holder)
                    binding.editTextTextPersonName120.setText(bank.bank_name)
                    binding.editTextTextPersonName121.setText(bank.acc_no)
                    binding.editTextTextPersonName122.setText(bank.ifsc)
                    binding.editTextTextPersonName123.setText(bank.city)
                }
            }
        }
    }

    private fun getEmployeeBankDetail(empid:String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_EMPLOYEE_BANK_DETAILS,
            Response.Listener { response ->

                try {
                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {

                        val userJson = obj.getJSONObject("user")

                        // SAVE THE BANK IN SEPARATE VARIABLE (NOT IN banks list)
                        val bank = BankData(
                            "",
                            userJson.getString("bank").safe(),
                            userJson.getString("account_no").safe(),
                            userJson.getString("ifsc").safe(),
                            userJson.getString("address").safe(),
                            userJson.getString("state").safe(),
                            userJson.getString("city").safe(),
                            userJson.getString("micr").safe(),
                            userJson.getString("cardno").safe(),
                            userJson.getString("holder").safe()
                        )

                        employeeBank = if (isValidEmployeeBank(bank)) {
                            bank
                        } else {
                            null        // IMPORTANT: remove employee bank completely
                        }

                        setupBankRadioButtons()


                    } else {
                        Toast.makeText(requireContext(), obj.getString("message"), Toast.LENGTH_LONG).show()
                    }

                } catch (e: JSONException) {  }

            },
            Response.ErrorListener { error ->
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
            }
        ) {

            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id"] = empid
                Log.i("11111","passed id - ${empid}")
                return params
            }
        }
        stringRequest.tag = "employee_voucher"
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
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

    override fun onDestroyView() {
        super.onDestroyView()
        VolleySingleton.getInstance(requireActivity()).requestQueue.cancelAll("employee_voucher")
    }


}