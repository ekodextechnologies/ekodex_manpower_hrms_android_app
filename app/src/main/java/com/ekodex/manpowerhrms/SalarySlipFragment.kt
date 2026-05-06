package com.ekodex.manpowerhrms

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentSalarySlipBinding
import org.json.JSONException
import org.json.JSONObject
import java.util.Calendar

class SalarySlipFragment : Fragment() {

    private lateinit var binding: FragmentSalarySlipBinding

    private var adapter: SalarySlipAdapter? = null
    var salarySlipList = mutableListOf<SalarySlipData>()
    private var progressDialog: AlertDialog? = null
    private val STORAGE_PERMISSION_CODE = 101
    private val NOTIFICATION_PERMISSION_CODE = 102

    var selectedMonth = 0
    var selectedYear = 0

    private var isLoading = false
    private var offset = 0
    private val limit = 50
    private var totalRows = 0
    var status = "Total"
    var keyword = ""

    var empname = ""
    var empcode = ""

    lateinit var searchView: SearchView
    var searchLists =  arrayListOf<SalarySlipData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_salary_slip, container, false)

//        binding.download.setOnClickListener {
//            handlePermissionsAndSavePdf()
//        }

        var user = SharedPrefManager.getInstance(requireContext()).user
        var i1= Internet()

        if (adapter == null) {
            adapter = SalarySlipAdapter(salarySlipList,this)
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
                getAllSlips()
            }
            else
            {
                Toast.makeText(requireContext(),"Check your internet connection!!",Toast.LENGTH_LONG).show()
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


        // Pagination
        binding.salarySlipList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) return // only scroll down

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                Log.i("PAGINATION", "lastVisible=$lastVisibleItem, totalItems=$totalItemCount, isLoading=$isLoading")

                if (!isLoading && lastVisibleItem >= totalItemCount - 5 && salarySlipList.size < totalRows) {
                    // Do not increment offset here
                    getAllSlips()
                }
            }
        })


        setHasOptionsMenu(true)
        return binding.root
    }

    private fun getAllSlips() {

        if (isLoading) return
        isLoading = true
        Log.i("11111","Offset - ${offset}, Limit - ${limit}")
        binding.progressBar11.visibility = View.GONE
        showProgressDialog()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_ALL_SALARY_SLIPS,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        totalRows = obj.optInt("total_rows", 0)
                        binding.textView234.text = "Total results $totalRows"

                        val array = obj.getJSONArray("user")

                        // If no data returned and list is empty
                        if (array.length() == 0 && salarySlipList.isEmpty()) {
                            binding.textView234.text = "Total 0 results"
                            binding.salarySlipList.visibility = View.INVISIBLE
                            binding.notAvailable1.visibility = View.VISIBLE
                            hideProgressDialog()
                        } else {
                            for (i in 0 until array.length()) {
                                val item = array.getJSONObject(i)
                                val slip = SalarySlipData(
                                    item.optString("name"),
                                    item.optString("code"),
                                    item.optString("rank")
                                )
                                salarySlipList.add(slip)
                            }

                            // Increment offset **after successfully adding items**
                            offset += array.length()

                            adapter?.notifyDataSetChanged()
                            //  searchList = ArrayList(empDirectoryList)

                            binding.salarySlipList.visibility = View.VISIBLE
                            binding.notAvailable1.visibility = View.GONE
                            hideProgressDialog()
                        }
                    } else {
                        if (salarySlipList.isEmpty()) {
                            binding.salarySlipList.visibility = View.INVISIBLE
                            binding.notAvailable1.visibility = View.VISIBLE
                            binding.textView234.text = "Total 0 results"

                        }
                        hideProgressDialog()
                        Toast.makeText(requireContext(), obj.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    
                    if (salarySlipList.isEmpty()) {
                        binding.salarySlipList.visibility = View.INVISIBLE
                        binding.notAvailable1.visibility = View.VISIBLE
                    }
                    binding.textView234.text = "Total results 0"
                    hideProgressDialog()
                } finally {
                    binding.progressBar11.visibility = View.GONE
                    isLoading = false
                    hideProgressDialog()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(requireContext(), error.message ?: "Something went wrong", Toast.LENGTH_SHORT).show()
                binding.progressBar11.visibility = View.GONE
                isLoading = false
                hideProgressDialog()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                val user = SharedPrefManager.getInstance(requireContext()).user

                params["client_id"] = user.client_id
                params["site_id"] = user.site_id
                params["month"] = selectedMonth.toString()
                params["year"] = selectedYear.toString()
                params["limit"] = limit.toString()
                params["offset"] = offset.toString()
                params["keyword"] = keyword
                return params
            }
        }

        stringRequest.tag = "SALARY_SLIP"
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        VolleySingleton.getInstance(requireActivity()).requestQueue.cancelAll("SALARY_SLIP")
    }

     fun handlePermissionsAndSavePdf(name: String, code: String) {

         empname = name
         empcode = code

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

//    private fun savePdf() {
//        val bitmap = getBitmapFromView(binding.salarySlip)
//        val uri = createPdfFromBitmap(requireContext(), bitmap)
//        if (uri != null) {
//            showDownloadNotification(requireContext(), uri)
//            Toast.makeText(requireContext(), "PDF saved successfully!", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(requireContext(), "Error saving PDF", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun getBitmapFromView(view: View): Bitmap {
//        val widthSpec = View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
//        val heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//        view.measure(widthSpec, heightSpec)
//        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
//
//        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmap)
//        view.draw(canvas)
//        return bitmap
//    }
//
//    private fun createPdfFromBitmap(context: Context, bitmap: Bitmap): Uri? {
//        val pdfDocument = PdfDocument()
//        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
//        val page = pdfDocument.startPage(pageInfo)
//        page.canvas.drawBitmap(bitmap, 0f, 0f, null)
//        pdfDocument.finishPage(page)
//
//        val fileName = "SalarySlip_${System.currentTimeMillis()}.pdf"
//        var uri: Uri? = null
//
//        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                val contentValues = ContentValues().apply {
//                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
//                    put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
//                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
//                }
//                val resolver = context.contentResolver
//                uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
//                uri?.let { resolver.openOutputStream(it)?.use { out -> pdfDocument.writeTo(out) } }
//            } else {
//                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//                val file = File(downloadsDir, fileName)
//                FileOutputStream(file).use { pdfDocument.writeTo(it) }
//                uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
//            }
//        } catch (e: Exception) {
//            
//        } finally {
//            pdfDocument.close()
//        }
//
//        return uri
//    }

    @SuppressLint("MissingPermission")
    private fun showDownloadNotification(context: Context, pdfUri: Uri) {
        val channelId = "pdf_download_channel"
        val notificationId = 101

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(pdfUri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "PDF Downloads", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("PDF Downloaded")
            .setContentText("Tap to view your Salary Slip")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(context).notify(notificationId, notification)
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
                    isLoading = false
                    offset = 0
                    binding.download.visibility = View.GONE
                    binding.textView234.visibility = View.VISIBLE
                    binding.salarySlipList.visibility = View.VISIBLE
                    if(i1.checkConnection(requireContext()))
                    {
                        salarySlipList.clear()

                        getAllSlips()
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
            var url = "https://shreesaiventures.com/payslip/${user.site_id}_${empcode}_${selectedMonth}_${selectedYear}.pdf"
            //var url = "https://www.aeee.in/wp-content/uploads/2020/08/Sample-pdf.pdf"
            var fileName = "${empname}_${selectedMonth}_${selectedYear}_Salary_Slip.pdf"
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
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)

        val item = menu.findItem(R.id.att_search)
        searchView = item.actionView as SearchView
        searchView.queryHint="Search"

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
        keyword = newText.toString()
        resetListAndLoad()
//        var newFilteredList = arrayListOf<SalarySlipData>()
//
//        for (i in searchLists)
//        {
//            if (i.emp_name!!.contains(newText!!,true) || i.emp_code!!.contains(newText!!,true)){
//                newFilteredList.add(i)
//                //binding.totalprod.text = "Total " + newFilteredList.size.toString() + " results"
//            }
//        }
//        adapter?.filtering(newFilteredList)

    }

    private fun resetListAndLoad() {
        // Reset everything for fresh load
        offset = 0
        totalRows = 0
        isLoading = false
        salarySlipList.clear()
        searchLists.clear()
        adapter?.notifyDataSetChanged()
       // binding.empDirectoryList.scrollToPosition(0)
       getAllSlips()
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
