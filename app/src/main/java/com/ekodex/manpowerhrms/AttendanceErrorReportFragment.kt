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
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Employee_Directory.EmployeeAttendanceSummaryData
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentAttendanceErrorReportBinding
import com.ekodex.manpowerhrms.databinding.FragmentAttendanceReportBinding
import com.ekodex.manpowerhrms.databinding.FragmentSupervisorAttendanceBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AttendanceErrorReportFragment : Fragment() {

    lateinit var binding: FragmentAttendanceErrorReportBinding
    var adapter:SupervisorAttendanceAdapter? = null
    private var progressDialog: AlertDialog? = null
    var attendanceList = mutableListOf<SupervisorAttendanceData>()
    lateinit var searchView: SearchView
    var searchLists =  arrayListOf<SupervisorAttendanceData>()

    //download button
    var attendanceListForDownload = mutableListOf<EmployeeAttendanceSummaryData>()
    lateinit var csvDataResponse: JSONArray


    //date filter
    lateinit var fromDate: String
    lateinit var toDate: String
    lateinit var from_flag: String
    lateinit var to_flag: String

    var status = ""

    //pagination
    private var isLoading = false
    private var offset = 0
    private val limit = 50
    private var totalRows = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_attendance_error_report,
            container,
            false
        )

        if (adapter == null) {
            adapter = SupervisorAttendanceAdapter(attendanceList,requireActivity(),"")
        }
        binding.attendanceList.adapter = adapter

        // adapter = SupervisorAttendanceAdapter(mutableListOf<SupervisorAttendanceData>(),requireActivity(),"")

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val sdf1 = SimpleDateFormat("yyyy-MM-dd")
        val curtDate = sdf1.format(Date())

        //date filter initialization
        from_flag = "true"
        to_flag = "true"

        //set the initial date
        if(!requireArguments().isEmpty)
        {
            //!args.from.isNullOrEmpty() && !args.to.isNullOrEmpty()
            var args = SupervisorAttendanceFragmentArgs.fromBundle(requireArguments())
            fromDate = args.from
            toDate = args.to
            status = args.status

            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
            val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.UK)

            var temp_from = outputFormat.format(inputFormat.parse(fromDate))
            var temp_to = outputFormat.format(inputFormat.parse(toDate))

            binding.button24.text = temp_from
            binding.button25.text = temp_to
        }
        else
        {
            fromDate = curtDate
            toDate = curtDate
            //set current date to textviews of table
            val currtDate = sdf.format(Date())
            binding.button24.text = currtDate
            binding.button25.text = currtDate
        }

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
                var i1 = Internet()
                if(i1.checkConnection(requireContext()))
                {
                    isLoading = false
                    offset = 0
                    totalRows = 0
                    attendanceList.clear()
                    csvDataResponse = JSONArray()
                    getAttendanceSupervisorForDownload(fromDate,toDate)

                    getAttendanceSupervisor(fromDate, toDate)
                }
                else
                {
                    Toast.makeText(
                        requireContext(),
                        "please check your internet connection!!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "please select both from & to date",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.attendanceList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) return // Only trigger on scroll down

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                Log.i("11111","size - ${attendanceList.size}")
                Log.i("11111","total - ${totalRows}")
                Log.i("11111","isLoading - ${isLoading}")

                // Trigger pagination if last visible item is near the end and more data exists
                if (!isLoading && lastVisibleItem >= attendanceList.size - 5 && attendanceList.size < totalRows) {
                    offset += limit
                    getAttendanceSupervisor(fromDate, toDate) // Fetch next page
                }
            }
        })

        setHasOptionsMenu(true)
        return binding.root
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
        Log.i("oooooooooooooooooo", fromDate)
    }

    private fun updateToDateLable(calener: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
// Here we can use calendar selected date
        binding.button25.setText((sdf.format(calener.time)))

        val myFormat2 = "yyyy-MM-dd"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.UK)
        toDate = (sdf2.format(calener.time))

        Log.i("oooooooooooooooooo", toDate)
    }


    override fun onResume() {
        super.onResume()
        var i1 = Internet()
        if (i1.checkConnection(requireContext())) {
            attendanceList.clear()
            getAttendanceSupervisor(fromDate, toDate)
            getAttendanceSupervisorForDownload(fromDate,toDate)
            binding.progressBar6.visibility = View.VISIBLE
            binding.notAvailable1.visibility = View.GONE
        } else {
            //binding.notAvailable6.visibility = View.VISIBLE
            Toast.makeText(
                requireContext(),
                "No internet connection!!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun getAttendanceSupervisor(fr: String, too: String) {
        if (isLoading) return
        isLoading = true
        Log.i("11111","Offset - ${offset}, Limit - ${limit}")
        binding.progressBar6.visibility = View.GONE
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
            Request.Method.POST, URLs.URL_GET_ATTENDANCE_ERROR_REPORT,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        totalRows = obj.optInt("total_rows", 0)
                        Log.i("11111","total - ${totalRows}")
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            Toast.makeText(requireContext(),"No attendance for this dates!!",Toast.LENGTH_SHORT).show()
                            binding.notAvailable1.visibility = View.VISIBLE
                            binding.attendanceList.visibility = View.INVISIBLE
                            binding.progressBar6.visibility = View.GONE
                            hideProgressDialog()

                        }
                        else
                        {
                            //attendanceList.clear()
                            binding.notAvailable1.visibility = View.GONE
                            binding.progressBar6.visibility = View.GONE
                            binding.attendanceList.visibility = View.VISIBLE

                            for (i in 0 until array.length()) {
                                val objectArtist = array.getJSONObject(i)

                                val banners = SupervisorAttendanceData(
                                    objectArtist.optString("id"),
                                    objectArtist.optString("emp_name"),
                                    objectArtist.optString("emp_code"),
                                    objectArtist.optString("rank"),
                                    objectArtist.optString("status"),
                                    objectArtist.optString("date"),
                                    objectArtist.optString("created_by"),
                                    objectArtist.optString("check_in"),
                                    objectArtist.optString("check_out")
                                )
                                attendanceList.add(banners)
                                searchLists = attendanceList as ArrayList<SupervisorAttendanceData>
                                //  adapter = SupervisorAttendanceAdapter(attendanceList,requireActivity(),"SupervisorAttendanceFrag")
                                //  binding.attendanceList.adapter = adapter
                                binding.attendanceList.visibility = View.VISIBLE
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
                        binding.attendanceList.visibility = View.INVISIBLE
                        binding.progressBar6.visibility = View.GONE
                        hideProgressDialog()
                    }

                } catch (e: JSONException) {

                    binding.notAvailable1.visibility = View.VISIBLE
                    binding.attendanceList.visibility = View.INVISIBLE
                    binding.progressBar6.visibility = View.GONE
                    hideProgressDialog()
                }
                isLoading = false

            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    requireActivity().applicationContext,
                    error.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
                hideProgressDialog()
                isLoading = false
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                Log.i("11111","status - ${status}")
                params["status"] = status
                params["from_date"] = from
                params["to_date"] = to
                params["site_id"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.site_id
                params["client_code"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.client_code
                params["role"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.role
                params["emp_code"] = ""
                params["limit"] = limit.toString()
                params["offset"] = offset.toString()
                return params

            }
        }
        stringRequest.tag = "attendance_error_report"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getAttendanceSupervisorForDownload(fr: String, too: String) {
        csvDataResponse = JSONArray()
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
            Request.Method.POST, URLs.URL_GET_SUPERVISOR_ATTENDANCE_DOWNLOAD,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")
                        if(array.length()>0)
                        {
                            Log.i("test","download dates: ${from} - ${to}")
                            Log.i("test","download data: ${array}")
                            csvDataResponse = array
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
                Toast.makeText(
                    requireActivity().applicationContext,
                    error.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                Log.i("11111","status - ${status}")
                params["status"] = status
                params["from_date"] = from
                params["to_date"] = to
                params["site_id"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.site_id
                params["client_code"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.client_code
                params["role"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.role
                params["emp_code"] = ""
                return params

            }
        }
        stringRequest.tag = "attendance_error_report"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        VolleySingleton.getInstance(requireActivity()).requestQueue.cancelAll("attendance_error_report")
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)

        val item = menu.findItem(R.id.att_search)
        searchView = item.actionView as SearchView
        searchView.queryHint="Search Attendance"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                pickupsFilter(newText)
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    private fun pickupsFilter(newText: String?) {
        var newFilteredList = arrayListOf<SupervisorAttendanceData>()

        for (i in searchLists)
        {
            if (i.emp_name!!.contains(newText!!,true) || i.emp_code!!.contains(newText!!,true)){
                newFilteredList.add(i)
                //binding.totalprod.text = "Total " + newFilteredList.size.toString() + " results"
            }
        }
        adapter?.filtering(newFilteredList)

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

    private fun splitdata(csvDataResponse: JSONArray) {
        //StringBuilder  to store the data
        val data = StringBuilder()
        var array: JSONArray


        data.append("Employee Name,Rank,Attendance Date,Status,Employee Code,Created By")
        array = csvDataResponse


        if (array.length() == 0) {
            Toast.makeText(requireContext(), "No data to download", Toast.LENGTH_SHORT).show()
        } else {
            for (i in 0 until array.length()) {
                val item = array.getJSONObject(i)

                val empName = item.optString("emp_name", "N/A")
                val rank = item.optString("rank", "N/A")
                val date = item.optString("date", "N/A")
                val status = item.optString("status", "N/A")
                val empCode = item.optString("emp_code", "N/A")
                val createdBy = item.optString("created_by", "N/A")

                data.append(
                    "\n" +
                            empName + "," +
                            rank + "," +
                            date + "," +
                            status + "," +
                            empCode + "," +
                            createdBy
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

            val newFile = File(
                requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                "HRMS Report Files"
            )

            if (!newFile.exists()) {
                newFile.mkdirs()
            }

            try {
                var csvName =
                    requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/HRMS Report Files/" + "HRMS_ATTENDANCE_REPORT_${time}.csv"
                var csvPath =
                    requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/HRMS Report Files/"


                val file = File(newFile, "HRMS_ATTENDANCE_REPORT_${time}.csv")
                val fout = FileOutputStream(file)
                fout.write(data.toString().toByteArray())
                fout.close()

                Toast.makeText(requireContext(), "file saved at: $csvPath", Toast.LENGTH_LONG)
                    .show()
                createNotificationChannel()
                showNotification(csvName)

            } catch (e: Exception) {
                // Toast.makeText(requireContext(),e.message,Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {

            Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_MEDIA_IMAGES,
                    android.Manifest.permission.READ_MEDIA_VIDEO,
                    android.Manifest.permission.READ_MEDIA_AUDIO,
                    android.Manifest.permission.POST_NOTIFICATIONS
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
        val notificationManager =
            requireActivity().getSystemService(NotificationManager::class.java)
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
        return PendingIntent.getBroadcast(
            requireActivity(),
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            100 -> if (grantResults.size > 0) {
                val p1 = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val p2 = grantResults[1] == PackageManager.PERMISSION_GRANTED
                val p3 = grantResults[2] == PackageManager.PERMISSION_GRANTED
                val p4 = grantResults[3] == PackageManager.PERMISSION_GRANTED
                val p5 = grantResults[4] == PackageManager.PERMISSION_GRANTED

                if (p1 && p2 && p3 && p4 && p4 && p5) {
                    Toast.makeText(requireContext(), "granted all", Toast.LENGTH_SHORT).show()
                } else {
                }
            }
        }

    }

}