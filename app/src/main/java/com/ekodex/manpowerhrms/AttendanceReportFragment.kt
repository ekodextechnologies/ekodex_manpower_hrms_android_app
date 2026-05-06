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
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentAttendanceReportBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AttendanceReportFragment : Fragment() {

    lateinit var binding: FragmentAttendanceReportBinding
    private var progressDialog: AlertDialog? = null
    var adapter: SupervisorAttendanceReportAdapter? = null
    var attendanceList = mutableListOf<SupervisorAttendanceReportData>()
    var attendanceListForDownload = mutableListOf<SupervisorAttendanceReportData>()

    //date filter
    lateinit var fromDate: String
    lateinit var toDate: String
    lateinit var from_flag: String
    lateinit var to_flag: String
    lateinit var csvDataResponse: JSONArray

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
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_attendance_report, container, false)

        if (adapter == null) {
            adapter = SupervisorAttendanceReportAdapter(attendanceList)
        }
        binding.attendanceReportList.adapter = adapter

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val sdf1 = SimpleDateFormat("yyyy-MM-dd")
        val curtDate = sdf1.format(Date())

        //date filter initialization
        from_flag = "true"
        to_flag = "true"
//        fromDate = curtDate
//        toDate = curtDate
//
//        //set current date to textviews of table
//        val currtDate = sdf.format(Date())
//        binding.button24.text = currtDate
//        binding.button25.text = currtDate

        val sdfUI = SimpleDateFormat("dd-MM-yyyy", Locale.UK)
        val sdfAPI = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
        val todayApi = sdfAPI.format(Date())
        val todayUi = sdfUI.format(Date())

// initialize flags
        from_flag = "true"
        to_flag = "true"

// ✅ FIRST TIME ONLY
        if (!::fromDate.isInitialized || !::toDate.isInitialized) {
            fromDate = todayApi
            toDate = todayApi
        }

// ✅ ALWAYS update UI from current values
        binding.button24.text = sdfUI.format(sdfAPI.parse(fromDate)!!)
        binding.button25.text = sdfUI.format(sdfAPI.parse(toDate)!!)


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
                getAttendanceSupervisor(fromDate, toDate)
                getAttendanceSupervisorForDownload(fromDate, toDate)
                //Toast.makeText(requireContext(),"fromdate $fromDate  todate  $toDate",Toast.LENGTH_SHORT).show()
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (requireContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && requireContext().checkSelfPermission(android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED
                    && requireContext().checkSelfPermission(android.Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED
                    && requireContext().checkSelfPermission(android.Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED
                    && requireContext().checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermission()
                } else {
                    if (i1.checkConnection(requireContext())) {
                        if (csvDataResponse.length() <= 0) {
                            Toast.makeText(
                                requireContext(),
                                "No report data to download",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            splitdata(csvDataResponse)
                        }

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "No internet connection!!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }
        }

        binding.attendanceReportList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) return // Only trigger on scroll down

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                // Trigger pagination if last visible item is near the end and more data exists
                if (!isLoading && lastVisibleItem >= attendanceList.size - 5 && attendanceList.size < totalRows) {
                    offset += limit
                    getAttendanceSupervisor(fromDate, toDate) // Fetch next page
                }
            }
        })

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
    }


    override fun onResume() {
        super.onResume()
        var i1 = Internet()
        if (i1.checkConnection(requireContext())) {
            getAttendanceSupervisor(fromDate, toDate)
            getAttendanceSupervisorForDownload(fromDate, toDate)
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
            Request.Method.POST, URLs.URL_GET_SUPERVISOR_ATTENDANCE_REPORT,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        totalRows = obj.optInt("total_rows", 0)
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            Toast.makeText(
                                requireContext(),
                                "No attendance for this dates!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.notAvailable1.visibility = View.VISIBLE
                            binding.attendanceReportList.visibility = View.INVISIBLE
                            binding.progressBar.visibility = View.GONE
                            hideProgressDialog()
                        } else {
                            attendanceList.clear()
                            binding.notAvailable1.visibility = View.GONE
                            binding.attendanceReportList.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE


                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)

                                val banners = SupervisorAttendanceReportData(
                                    objectArtist.optString("date"),
                                    objectArtist.optString("expected"),
                                    objectArtist.optString("count"),
                                    objectArtist.optString("absent")
                                )
                                attendanceList.add(banners)
                                //  adapter = SupervisorAttendanceReportAdapter(attendanceList)
                                // binding.attendanceReportList.adapter = adapter
                                binding.attendanceReportList.visibility = View.VISIBLE
                            }
                            adapter?.notifyDataSetChanged()
                            hideProgressDialog()
                        }
                    } else {
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
                
                binding.notAvailable1.visibility = View.VISIBLE
                binding.attendanceReportList.visibility = View.INVISIBLE
                isLoading = false
                hideProgressDialog()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["from_date"] = fr
                params["to_date"] = too
                params["site_id"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.site_id
                params["client_code"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.client_code
                params["limit"] = limit.toString()
                params["offset"] = offset.toString()
                return params

            }
        }
        stringRequest.tag = "attendance_report"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getAttendanceSupervisorForDownload(fr: String, too: String) {
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
            Request.Method.POST, URLs.URL_GET_SUPERVISOR_ATTENDANCE_REPORTFOR_DOWNLOAD,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {

                        } else {
                            attendanceListForDownload.clear()
                            csvDataResponse = array

                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)

                                val banners = SupervisorAttendanceReportData(
                                    objectArtist.optString("date"),
                                    objectArtist.optString("expected"),
                                    objectArtist.optString("count"),
                                    objectArtist.optString("absent")
                                )
                                attendanceListForDownload.add(banners)
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

                params["from_date"] = fr
                params["to_date"] = too
                params["site_id"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.site_id
                params["client_code"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.client_code
                return params

            }
        }
        stringRequest.tag = "attendance_report"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        VolleySingleton.getInstance(requireActivity()).requestQueue.cancelAll("attendance_report")
    }

    private fun splitdata(csvDataResponse: JSONArray) {
        //StringBuilder  to store the data
        val data = StringBuilder()
        var array: JSONArray


        data.append("Attendance Date,Total Expected,Total Present,Total Absent")
        array = csvDataResponse


        if (array.length() == 0) {
            Toast.makeText(requireContext(), "No data to download", Toast.LENGTH_SHORT).show()
        } else {
            for (i in (array.length() - 1) downTo 0) {

                val objectArtist = array.getJSONObject(i)
                data.append(
                    "\n" + objectArtist.optString("date") + "," +
                            objectArtist.optString("expected") + "," +
                            objectArtist.optString("count")+ "," +
                            objectArtist.optString("absent")
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