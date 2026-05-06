package com.ekodex.manpowerhrms

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentBulkAttendanceDownloadBinding
import org.json.JSONException
import org.json.JSONObject
import java.util.Calendar

class BulkAttendanceDownloadFragment : Fragment() {

    private lateinit var binding: FragmentBulkAttendanceDownloadBinding
    private var adapter: BulkAttendanceAdapter? = null
    var salarySlipList = mutableListOf<SalarySlipData>()

    private val STORAGE_PERMISSION_CODE = 101
    private val NOTIFICATION_PERMISSION_CODE = 102

    var selectedMonth = 0
    var selectedYear = 0

    var company_id = ""
    var site_id = ""
    lateinit var companies:MutableList<Client_Data>
    lateinit var branches:MutableList<Site_Data>
    lateinit var companies_names:MutableList<String>
    lateinit var branches_names:MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bulk_attendance_download, container, false)

//        binding.download.setOnClickListener {
//            handlePermissionsAndSavePdf()
//        }

        var user = SharedPrefManager.getInstance(requireContext()).user
        var i1= Internet()

        if(i1.checkConnection(requireContext()))
        {
            if(user.copy_client_id.contains(","))
            {
                val list = (user.copy_client_id.split(",").toTypedArray())
                list.forEach {
                    getCompanies(it)
                }
            }
            else
            {
                site_id = user.site_id
                company_id = user.client_id
            }
        }
        else
        {
            Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
        }


        companies = mutableListOf()
        branches =  mutableListOf()
        companies_names = mutableListOf()
        branches_names =  mutableListOf()


        if (adapter == null) {
            adapter = BulkAttendanceAdapter(salarySlipList,this)
        }
        binding.salarySlipList.adapter = adapter

        val calendar = Calendar.getInstance()
        selectedMonth = calendar.get(Calendar.MONTH) + 1  // Month is 0-based
        selectedYear = calendar.get(Calendar.YEAR)
        val formattedMonthYear = String.format("%02d-%d", selectedMonth, selectedYear)
        binding.button2.text = formattedMonthYear

        if (!user.role.equals("Employee", ignoreCase = true)) {
            binding.download.visibility = View.GONE
            binding.textView234.visibility = View.VISIBLE
            binding.salarySlipList.visibility = View.VISIBLE
            if(i1.checkConnection(requireContext()))
            {
               getAllAttendance()
            }
            else
            {
                Toast.makeText(requireContext(),"Check your internet connection!!", Toast.LENGTH_LONG).show()
            }
        }

        binding.button2.setOnClickListener {
            showMonthYearPickerDialog()
        }

        binding.download.setOnClickListener {
            var user = SharedPrefManager.getInstance(requireContext()).user
            var temp_name = user.fname + " " + user.lname
            handlePermissionsAndSavePdf(temp_name,user.emp_code)
        }

        binding.company.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                company_id = companies.get(position).id
                Log.i("11111","Client- ${company_id}")
                branches.clear()
                branches_names.clear()
                binding.branch.text.clear()
                getSites(company_id)
            }

        })

        binding.branch.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                site_id = branches.get(position).id
                Log.i("11111","Site- ${site_id}")
            }

        })

        //reset button
        binding.addLeave2.setOnClickListener {
            company_id = ""
            site_id = ""
            binding.company.text.clear()
            binding.branch.text.clear()
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun getAllAttendance() {
        salarySlipList.clear()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_BULK_ATTENDANCE,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                            binding.salarySlipList.visibility = View.VISIBLE
                            binding.download.visibility = View.VISIBLE
                            binding.notAvailable1.visibility = View.GONE

                            var salaryData = SalarySlipData("${selectedMonth}_${selectedYear}","","")
                            salarySlipList.add(salaryData)

                    } else {
                        if (salarySlipList.isEmpty()) {
                            binding.salarySlipList.visibility = View.INVISIBLE
                            binding.notAvailable1.visibility = View.VISIBLE
                            binding.textView234.text = "Total 0 results"
                        }
                        binding.download.visibility = View.GONE
                        Toast.makeText(requireContext(), obj.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    
                    if (salarySlipList.isEmpty()) {
                        binding.salarySlipList.visibility = View.INVISIBLE
                        binding.notAvailable1.visibility = View.VISIBLE
                    }
                    binding.textView234.text = "Total results 0"
                    binding.download.visibility = View.GONE
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(requireContext(), error.message ?: "Something went wrong", Toast.LENGTH_SHORT).show()
                binding.progressBar11.visibility = View.GONE
                binding.download.visibility = View.GONE
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                val user = SharedPrefManager.getInstance(requireContext()).user
                params["client_code"] = user.client_code
                params["site_id"] = user.site_id
                params["month"] = selectedMonth.toString()
                params["year"] = selectedYear.toString()
                return params
            }
        }

        stringRequest.tag = "ATTENDANCE"
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

    fun handlePermissionsAndSavePdf(name: String, code: String) {

        // 1️⃣ Check notification permission (API 33+)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
//            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                requireActivity(),
//                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
//                NOTIFICATION_PERMISSION_CODE
//            )
//            return
//        }

        // 2️⃣ Check storage permission for API 23–28
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q &&
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_CODE
            )
            return
        }

        // Permissions granted → save PDF
        downloadPdf()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            STORAGE_PERMISSION_CODE, NOTIFICATION_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //downloadPdf(name,code)
                } else {
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showMonthYearPickerDialog() {

        var user = SharedPrefManager.getInstance(requireContext()).user
        var i1= Internet()

        val dialogView = layoutInflater.inflate(R.layout.dialog_month_year_picker, null)
        val monthPicker = dialogView.findViewById<NumberPicker>(R.id.monthPicker)
        val yearPicker = dialogView.findViewById<NumberPicker>(R.id.yearPicker)

        monthPicker.minValue = 1
        monthPicker.maxValue = 12
        monthPicker.value = Calendar.getInstance().get(Calendar.MONTH) + 1

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        yearPicker.minValue = currentYear - 50
        yearPicker.maxValue = currentYear + 10
        yearPicker.value = currentYear

        AlertDialog.Builder(requireContext())
            .setTitle("Select Month and Year")
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                selectedMonth = monthPicker.value
                selectedYear = yearPicker.value
                binding.button2.text = String.format("%02d-%d", selectedMonth, selectedYear)

                if (!user.role.equals("Employee", ignoreCase = true)) {
                    binding.download.visibility = View.GONE
                    binding.textView234.visibility = View.VISIBLE
                    binding.salarySlipList.visibility = View.VISIBLE
                    if(i1.checkConnection(requireContext()))
                    {
                        salarySlipList.clear()

                        getAllAttendance()
                    }
                    else
                    {
                        Toast.makeText(requireContext(),"Check your internet connection!!",Toast.LENGTH_LONG).show()
                    }
                }

            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    fun downloadPdf() {
        try {
            var user = SharedPrefManager.getInstance(requireContext()).user
//            var url = "https://shreesaiventures.com/attendance/printvik?site=${site_id}&m=${selectedMonth}&y=${selectedYear}&type=Both&action=printpdf.pdf"
            var url = "https://hrms.myapplications.co.in/attendance/printvik?site=${site_id}&m=${selectedMonth}&y=${selectedYear}&type=Both&action=printpdf.pdf"

            //var url = "https://www.aeee.in/wp-content/uploads/2020/08/Sample-pdf.pdf"
            var fileName = "Site_${site_id}_${selectedMonth}_${selectedYear}_Attendance.pdf"
            val request = DownloadManager.Request(Uri.parse(url))
                .setTitle(fileName)
                .setDescription("Downloading PDF...")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

            val dm = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)

            Toast.makeText(requireContext(), "Download started", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
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
                            val company_adapter = ArrayAdapter(requireContext(),R.layout.pay_to_dropdown_layout,companies_names)
                            binding.company.setAdapter(company_adapter)

                            binding.tl1.visibility = View.VISIBLE
                            binding.tl2.visibility = View.VISIBLE
                            binding.textView204.visibility = View.VISIBLE
                            binding.textView205.visibility = View.VISIBLE
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
        stringRequest.tag = "ATTENDANCE"
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
        stringRequest.tag = "ATTENDANCE"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        VolleySingleton.getInstance(requireActivity()).requestQueue.cancelAll("ATTENDANCE")
    }

}