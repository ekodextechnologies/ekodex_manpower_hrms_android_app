package com.ekodex.manpowerhrms

import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentVoucherSummaryBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class VoucherSummaryFragment : Fragment() {

    lateinit var binding: FragmentVoucherSummaryBinding
    private var progressDialog: AlertDialog? = null

    lateinit var companies:MutableList<Client_Data>
    lateinit var branches:MutableList<Site_Data>
    lateinit var companies_names:MutableList<String>
    lateinit var branches_names:MutableList<String>

    var company_id = ""
    var site_id = ""

     var adapter:VoucherSummaryAdapter? = null
    var voucherSummaryList = mutableListOf<VoucherSummaryData>()
    var voucherSummaryListForDownload = mutableListOf<VoucherSummaryData>()


    //pagination
    private var isLoading = false
    private var offset = 0
    private val limit = 50
    private var totalRows = 0


    //date filter
    lateinit var fromDate: String
    lateinit var toDate: String
    lateinit var from_flag: String
    lateinit var to_flag: String
    lateinit var csvDataResponse: JSONArray


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_voucher_summary, container, false)

        companies = mutableListOf()
        branches =  mutableListOf()
        companies_names = mutableListOf()
        branches_names =  mutableListOf()

        var user = SharedPrefManager.getInstance(requireContext()).user
        if(!user.role.equals("admin",ignoreCase = true))
        {
            company_id = user.client_id
            site_id = user.site_id
        }
        else
        {
            var i1 = Internet()
            if(i1.checkConnection(requireContext()))
            {
                if(user.copy_client_id.contains(","))
                {
                    val list = (user.copy_client_id.split(",").toTypedArray())
                    list.forEach {
                        getCompanies(it)
                    }
                }
//            else
//            {
//                getCompanies(user.copy_client_id)
//            }
            }
            else
            {
                Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
            }
        }

        if (adapter == null) {
            adapter = VoucherSummaryAdapter(voucherSummaryList)
        }
        binding.attendanceReportList.adapter = adapter


        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val sdf1 = SimpleDateFormat("yyyy-MM-dd")
        val curtDate = sdf1.format(Date())

        //date filter initialization
        from_flag = "true"
        to_flag = "true"
        fromDate = curtDate
        toDate = curtDate

        //set current date to textviews of table
        val currtDate = sdf.format(Date())
        binding.button24.text = currtDate
        binding.button25.text = currtDate

        //----------------------------------------------------------------------------------------
        var i1 = Internet()

        if(i1.checkConnection(requireContext()))
        {

            getVoucherSummary(fromDate, toDate)
            getVoucherSummaryForDownload(fromDate, toDate)
            getGangs()
        }
        else
        {
            Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
        }

        binding.company.setOnItemClickListener{ parent, _, position, _ ->
            //  val selectedCompanyName = parent.getItemAtPosition(position) as String
            // val selectedCompany = companies.find { it.title == selectedCompanyName }
            val selectedCompany = parent.getItemAtPosition(position) as Client_Data


            Log.i("11111","Position - ${position}")
            Log.i("11111","client - ${selectedCompany.toString()} , id - ${selectedCompany.id}")


            if (selectedCompany != null) {
                Toast.makeText(requireContext(), "Getting Branch!!", Toast.LENGTH_LONG).show()
                binding.branch!!.text.clear()
                company_id = selectedCompany.id
                branches.clear()
                branches_names.clear()
                getSites(company_id)
            }
        }

        binding.branch.setOnItemClickListener{ parent, _, position, _ ->
            val selectedBranchName = parent.getItemAtPosition(position) as String
            val selectedBranch = branches.find { it.title == selectedBranchName }

            if (selectedBranch != null) {
                site_id = selectedBranch.id
                //  client_code = selectedBranch.client_code
            }
        }

//        binding.gang.setOnItemClickListener(object : AdapterView.OnItemClickListener {
//            override fun onItemClick(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                getVoucherSummary(fromDate, toDate)
//                getVoucherSummaryForDownload(fromDate, toDate)
//            }
//
//        })

        //clear all filters
        binding.addLeave2.setOnClickListener {
            company_id = ""
            site_id = ""

            binding.company.text.clear()
            binding.branch.text.clear()
            binding.gang.text.clear()
        }

        //----------------------------------------------------------------------------

        // date clicklistner
        val calender1 = Calendar.getInstance()
        val datePicker1 =
            DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                calender1.set(Calendar.YEAR, year)
                calender1.set(Calendar.MONTH, month)
                calender1.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateFromDateLable(calender1)
            }

        val calender2 = Calendar.getInstance()
        val datePicker2 =
            DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                calender2.set(Calendar.YEAR, year)
                calender2.set(Calendar.MONTH, month)
                calender2.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateToDateLable(calender2)
            }


        binding.button24.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                datePicker1,
                calender1.get(Calendar.YEAR),
                calender1.get(Calendar.MONTH),
                calender1.get(Calendar.DAY_OF_MONTH)
            )
                .show()
            from_flag = "true"
        }
        binding.button25.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                datePicker2,
                calender2.get(Calendar.YEAR),
                calender2.get(Calendar.MONTH),
                calender2.get(Calendar.DAY_OF_MONTH)
            )
                .show()
            to_flag = "true"
        }

        binding.button26.setOnClickListener {
            if (from_flag == "true" && to_flag == "true") {
                getVoucherSummary(fromDate, toDate)
                getVoucherSummaryForDownload(fromDate, toDate)
            } else {
                Toast.makeText(
                    requireContext(),
                    "please select both from & to date",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.downloadReport.setOnClickListener {
            var i1 = Internet()
           // Toast.makeText(requireContext(), "clicked",Toast.LENGTH_SHORT).show()

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (requireContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && requireContext().checkSelfPermission(android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED
                    && requireContext().checkSelfPermission(android.Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED
                    && requireContext().checkSelfPermission(android.Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED
                    && requireContext().checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermission()
                }
                else
                {
                   // Toast.makeText(requireContext(), "22222",Toast.LENGTH_SHORT).show()

                    if(i1.checkConnection(requireContext())) {
                       // Toast.makeText(requireContext(), "22222",Toast.LENGTH_SHORT).show()

                        if(csvDataResponse.length() <= 0)
                        {
                            Toast.makeText(requireContext(),"No report data to download",Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            splitdata(csvDataResponse)
                        }

                    }
                    else
                    {
                        Toast.makeText(
                            requireContext(),
                            "No internet connection!!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            }
        //}


        binding.attendanceReportList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) return // Only trigger on scroll down

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                // Trigger pagination if last visible item is near the end and more data exists
                if (!isLoading && lastVisibleItem >= voucherSummaryList.size - 5 && voucherSummaryList.size < totalRows) {
                    offset += limit
                    getVoucherSummary(fromDate, toDate) // Fetch next page
                }
            }
        })


        return binding.root


    }

    //to get voucher summary
    private fun getVoucherSummary(fr: String, too: String) {

        if (isLoading) return
        isLoading = true
        Log.i("11111", "Offset - ${offset}, Limit - ${limit}")
        binding.progressBar.visibility = View.GONE
        showProgressDialog()

        var from = fr
        var to = too

        if (from == "" || from.length == 0) {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val currtDate = sdf.format(Date())
            from = currtDate
        }

        if (to == "" || to.length == 0) {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val currtDate = sdf.format(Date())
            to = currtDate
        }

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_VOUCHER_SUMMARY,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        totalRows = obj.optInt("total_rows", 0)
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            Toast.makeText(requireContext(),"No vouchers for this dates!!",Toast.LENGTH_SHORT).show()
                            binding.notAvailable1.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            binding.attendanceReportList.visibility = View.INVISIBLE
                            hideProgressDialog()
                        }
                        else
                        {
                            voucherSummaryList.clear()
                            binding.notAvailable1.visibility = View.GONE
                            binding.progressBar.visibility = View.GONE
                            binding.attendanceReportList.visibility = View.VISIBLE

                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)

                                val banners = VoucherSummaryData(
                                    objectArtist.optString("date"),
                                    objectArtist.optString("total"),
                                    objectArtist.optString("pending"),
                                    objectArtist.optString("rejected"),
                                    objectArtist.optString("approved"),
                                    objectArtist.optString("paid"),
                                )
                                voucherSummaryList.add(banners)
//                                adapter = VoucherSummaryAdapter(voucherSummaryList)
//                                binding.attendanceReportList.adapter = adapter
                                binding.attendanceReportList.visibility = View.VISIBLE
                            }
                            adapter?.notifyDataSetChanged()
                            hideProgressDialog()
                        }
                    }
                    else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        binding.notAvailable1.visibility = View.VISIBLE
                        binding.attendanceReportList.visibility = View.INVISIBLE
                        hideProgressDialog()
                    }

                } catch (e: JSONException) {
                    
                    binding.notAvailable1.visibility = View.VISIBLE
                    binding.attendanceReportList.visibility = View.INVISIBLE
                    hideProgressDialog()
                }
                isLoading = false
            },
            Response.ErrorListener { error ->
                
                isLoading = false
                binding.notAvailable1.visibility = View.VISIBLE
                binding.attendanceReportList.visibility = View.INVISIBLE
                hideProgressDialog()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["from_date"] = from
                params["to_date"] = to
                params["company_id"] = company_id
                params["site_id"] = site_id
                params["gang"] = binding.gang.text.toString()
                params["created_by"] = SharedPrefManager.getInstance(requireActivity().applicationContext).user.id
                params["role"] = SharedPrefManager.getInstance(requireActivity().applicationContext).user.role
                params["limit"] = limit.toString()
                params["offset"] = offset.toString()
                return params

            }
        }
        stringRequest.tag = "voucher_summary"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getVoucherSummaryForDownload(fr: String, too: String) {

        var from = fr
        var to = too
        csvDataResponse = JSONArray()

        if (from == "" || from.length == 0) {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val currtDate = sdf.format(Date())
            from = currtDate
        }

        if (to == "" || to.length == 0) {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val currtDate = sdf.format(Date())
            to = currtDate
        }

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_VOUCHER_SUMMARY_FOR_DOWNLOAD,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            Toast.makeText(requireContext(),"No vouchers for this dates!!",Toast.LENGTH_SHORT).show()
                            binding.notAvailable1.visibility = View.VISIBLE
                            binding.attendanceReportList.visibility = View.INVISIBLE
                        }
                        else
                        {
                            voucherSummaryListForDownload.clear()
                            csvDataResponse = array
                            binding.notAvailable1.visibility = View.GONE
                            binding.attendanceReportList.visibility = View.VISIBLE

                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)

                                val banners = VoucherSummaryData(
                                    objectArtist.optString("date"),
                                    objectArtist.optString("total"),
                                    objectArtist.optString("pending"),
                                    objectArtist.optString("rejected"),
                                    objectArtist.optString("approved"),
                                    objectArtist.optString("paid")
                                )
                                voucherSummaryListForDownload.add(banners)
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

                params["from_date"] = from
                params["to_date"] = to
                params["company_id"] = company_id
                params["site_id"] = site_id
                params["gang"] = binding.gang.text.toString()
                params["created_by"] = SharedPrefManager.getInstance(requireActivity().applicationContext).user.id
                params["role"] = SharedPrefManager.getInstance(requireActivity().applicationContext).user.role
                return params

            }
        }
        stringRequest.tag = "voucher_summary"
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
        stringRequest.tag = "voucher_summary"
        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)
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

                                //set default company
//                                if(objectArtist.getString("id") == SharedPrefManager.getInstance(requireActivity().applicationContext).user.client_id)
//                                {
//                                    binding.company.setText(objectArtist.getString("title"))
//                                }

                                companies.add(Client_Data(objectArtist.getString("id"),objectArtist.getString("title")))
                                companies_names.add(objectArtist.getString("title"))
                            }
                            val company_adapter = ArrayAdapter(requireContext(),R.layout.pay_to_dropdown_layout,companies)
                            binding.company.setAdapter(company_adapter)

                            binding.tl1.visibility = View.VISIBLE
                            binding.tl2.visibility = View.VISIBLE
                            binding.tl3.visibility = View.VISIBLE
                            binding.textView204.visibility = View.VISIBLE
                            binding.textView205.visibility = View.VISIBLE
                            binding.textView206.visibility = View.VISIBLE
                            binding.addLeave2.visibility = View.VISIBLE
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
        stringRequest.tag = "voucher_summary"

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

                                //set default site
//                                if(objectArtist.getString("id") == SharedPrefManager.getInstance(requireActivity().applicationContext).user.site_id)
//                                {
//                                    binding.branch.setText(objectArtist.getString("title"))
//                                }

                                branches.add(Site_Data(objectArtist.getString("id"),objectArtist.getString("title"),objectArtist.getString("client_code")))
                                branches_names.add(objectArtist.getString("title"))
                            }
                            val site_adapter = ArrayAdapter(requireContext(),R.layout.pay_to_dropdown_layout,branches_names)
                            binding.branch.setAdapter(site_adapter)
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
        stringRequest.tag = "voucher_summary"

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    //date filter functions
    private fun updateFromDateLable(calener: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
// Here we can use calendar selected date
        binding.button24.setText((sdf.format(calener.time)))
        val myFormat2 = "yyyy-MM-dd"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.UK)
        fromDate = (sdf2.format(calener.time))
    }

    private fun updateToDateLable(calener: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
// Here we can use calendar selected date
        binding.button25.setText((sdf.format(calener.time)))

        val myFormat2 = "yyyy-MM-dd"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.UK)
        toDate = (sdf2.format(calener.time))
    }



    override fun onDestroyView() {
        super.onDestroyView()
        VolleySingleton.getInstance(requireActivity()).requestQueue.cancelAll("voucher_summary")
    }

    private fun splitdata(csvDataResponse: JSONArray) {
        //StringBuilder  to store the data
        val data = StringBuilder()
        var array = csvDataResponse
        data.append("Voucher Date,Total,Total Pending,Total Rejected,Total Approved,Total Paid")

        if(array.length() == 0)
        {
            Toast.makeText(requireContext(),"No data to download",Toast.LENGTH_SHORT).show()
        }
        else{
           // Toast.makeText(requireContext(), array.toString(),Toast.LENGTH_SHORT).show()
            for (i in (array.length()-1) downTo 0) {

                val objectArtist = array.getJSONObject(i)
                data.append(
                    "\n" + objectArtist.optString("date") + "," +
                            objectArtist.optString("total") + "," +
                            objectArtist.optString("pending") + "," +
                            objectArtist.optString("rejected") + "," +
                            objectArtist.optString("approved") + "," +
                            objectArtist.optString("paid")
                )
            }

            //before i gave below cust name as ->    this.csvDataResponse.getJSONObject(0).optString("custname")

            CreateCSV(data)

        }
    }

    private fun CreateCSV(data: java.lang.StringBuilder) {
        val calendar = Calendar.getInstance()
        val time = calendar.timeInMillis

        try {

            val newFile = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "HRMS Report Files")

            if (!newFile.exists()) {
                newFile.mkdirs()
            }

            try
            {
                //old path
                //val newFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath, "Qikbill/Pos Excel Files")

                var csvName =  requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/HRMS Report Files/" + "HRMS_VOUCHER_REPORT_${time}.csv"
                var csvPath =  requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/HRMS Report Files/"


                val file = File(newFile, "HRMS_VOUCHER_REPORT_${time}.csv")
                val fout = FileOutputStream(file)
                fout.write(data.toString().toByteArray())
                fout.close()

                Toast.makeText(requireContext(),"file saved at: $csvPath",Toast.LENGTH_LONG).show()
                createNotificationChannel()
                showNotification(csvName)
            }

            catch (e:Exception)
            {
                 Toast.makeText(requireContext(),"Something is wrong!!",Toast.LENGTH_LONG).show()
            }

        } catch (e: Exception) {
            //
            Toast.makeText(requireContext(),"Something is wrong!!",Toast.LENGTH_LONG).show()
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_MEDIA_IMAGES,
                    android.Manifest.permission.READ_MEDIA_VIDEO,
                    android.Manifest.permission.POST_NOTIFICATIONS,
                    android.Manifest.permission.READ_MEDIA_AUDIO
                ), 100
            )
        }
    }


    // new notification code
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MyChannel"
            val descriptionText = "Channel for notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(csvName: String) {
        val notificationManager = requireActivity().getSystemService(NotificationManager::class.java)
        val notificationBuilder = NotificationCompat.Builder(requireActivity(), "CHANNEL_ID")
            .setSmallIcon(R.drawable.tps_logo) // Your notification icon
            .setContentTitle("Open CSV File")
            .setContentText("Click to open your CSV file.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(getPendingIntent(csvName)) // Set pending intent
            .setAutoCancel(true)

        notificationManager.notify(1, notificationBuilder.build())
    }

    private fun getPendingIntent(csvName: String): PendingIntent {
        val intent = Intent(requireActivity(), OpenCsvReceiver::class.java)
        intent.putExtra("FILE_URI", csvName) // Adjust file name as necessary
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        return PendingIntent.getBroadcast(requireActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    )
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            100-> if (grantResults.size > 0) {
                val p1 = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val p2 = grantResults[1] == PackageManager.PERMISSION_GRANTED
                val p3 = grantResults[2] == PackageManager.PERMISSION_GRANTED
                val p4 = grantResults[3] == PackageManager.PERMISSION_GRANTED
                val p5 = grantResults[4] == PackageManager.PERMISSION_GRANTED

                if (p1 && p2 && p3 && p4 && p4 && p5) {
                    Toast.makeText(requireContext(),"granted all",Toast.LENGTH_SHORT).show()
                } else {
                }
            }
        }

    }

    private fun showProgressDialog() {
        if (progressDialog == null) {
            val progressBar = ProgressBar(requireContext())
            progressBar.isIndeterminate = true
            val padding = 50
            progressBar.setPadding(padding, padding, padding, padding)

            progressDialog = AlertDialog.Builder(requireContext())
                .setTitle("Loading Data")
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