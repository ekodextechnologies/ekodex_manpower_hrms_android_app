package com.ekodex.manpowerhrms.Leave_Add

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.SingleSpinnerListener
import com.ekodex.manpowerhrms.Employee_Data
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.databinding.FragmentAddLeaveBinding
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class AddLeaveFragment : Fragment() {

    lateinit var binding:FragmentAddLeaveBinding
    //date filter
    lateinit var fromDate: String
    lateinit var toDate: String
    lateinit var from_flag: String
    lateinit var to_flag: String
    var fromTime = ""

    private var progressDialog: AlertDialog? = null
    lateinit var employees: MutableList<Employee_Data>
    lateinit var employee_names: MutableList<String>

    lateinit var spinner_data: MutableList<KeyPairBoolData>
    lateinit var selected_spinner: MutableList<KeyPairBoolData>
    val selectedEmployees = mutableListOf<Employee_Data>()

    var cl_total = "0"
    var cl_used = "0"

    var sl_total = "0"
    var sl_used = "0"

    var pl_total = "0"
    var pl_used = "0"

    var ml_total = "0"
    var ml_used = "0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_leave, container, false)

        from_flag = "false"
        to_flag = "false"
        fromDate = ""
        toDate = ""

        spinner_data = mutableListOf()
        selected_spinner = mutableListOf()
        employees = mutableListOf()
        employee_names = mutableListOf()


        var status = arrayListOf<String>("CL","SL","PL","ML","Half Day")
        val adapter1 = ArrayAdapter(requireContext(), R.layout.pay_to_dropdown_layout, status)
        binding.leaveType.setAdapter(adapter1)


        getAllEmployees()

        var i1 = Internet()

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


        binding.button4.setOnClickListener {
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
        binding.button5.setOnClickListener {
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

        //add leave
        binding.button6.setOnClickListener {
            if(i1.checkConnection(requireContext()))
            {
                addLeave()
            }
            else
            {
                Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
            }
        }

        binding.button35.setOnClickListener {

            val calendar = Calendar.getInstance()

            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = android.app.TimePickerDialog(
                requireContext(),
                { _, selectedHour, selectedMinute ->

                    val cal = Calendar.getInstance()
                    cal.set(Calendar.HOUR_OF_DAY, selectedHour)
                    cal.set(Calendar.MINUTE, selectedMinute)

                    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
                    fromTime = sdf.format(cal.time)

                    binding.button6.text = fromTime

                },
                hour,
                minute,
                false // FALSE = AM PM format
            )

            timePickerDialog.show()
        }

        binding.leaveType.setOnItemClickListener { _, _, _, _ ->

            val type = binding.leaveType.text.toString()

            if (type.equals("Half Day", true)) {

                binding.button5.visibility = View.GONE      // Hide To Date
                binding.button35.visibility = View.VISIBLE
                binding.textView521.visibility = View.VISIBLE// Show Time Picker

                toDate = ""

            } else {

                binding.button5.visibility = View.VISIBLE   // Show To Date
                binding.button35.visibility = View.GONE     // Hide Time Picker
                binding.textView521.visibility = View.GONE
                fromTime = ""
            }
        }

        binding.multipleItemSelectionSpinner.setSearchEnabled(true)
        binding.multipleItemSelectionSpinner.setSearchHint("Search Employee")
        binding.multipleItemSelectionSpinner.setEmptyTitle("No Data Found!")

        return binding.root

    }

    private fun getAllEmployees() {
       showProgressDialog("Getting Employees")
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
                        hideProgressDialog()
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
                                    "0"
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
                            binding.multipleItemSelectionSpinner.setItems(spinner_data, object :
                                SingleSpinnerListener {

                                override fun onItemsSelected(selectedItem: KeyPairBoolData) {
                                    // Clear previous selection
                                    selectedEmployees.clear()


                                    Log.i("11111","selected emp id - ${selectedItem.id}")
                                    val emp = employees.find { it.id == selectedItem.id.toString() }
                                    if (emp != null)
                                    {
                                        selectedEmployees.add(emp)
                                        getEmployeesLeavesBalance(emp.emp_code)
                                    }
                                }

                                override fun onClear() {
                                    selectedEmployees.clear()
                                }
                            })



                        }

                    } else {
                        hideProgressDialog()
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
                                "0"
                            )
                        )
                    }

                } catch (e: JSONException) {
                    hideProgressDialog()
                }

            },
            Response.ErrorListener {
                hideProgressDialog()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                params["client_id"] = SharedPrefManager.getInstance(requireContext()).user.client_id
                params["site_id"] = SharedPrefManager.getInstance(requireContext()).user.site_id
                return params
            }
        }
        stringRequest.tag = "add_leave"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getEmployeesLeavesBalance(employee_code: String) {
        showProgressDialog("Getting leaves balance")
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_EMPLOYEES_LEAVE_BALANCE,
            Response.Listener { response ->

                try {

                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {

                        val dataArray = obj.getJSONArray("data")
                        hideProgressDialog()
                        if (dataArray.length() > 0) {


                            val dataObj = dataArray.getJSONObject(0)

                            cl_total = dataObj.optString("cl_total", "0")
                            cl_used = dataObj.optString("cl_used", "0")

                            sl_total = dataObj.optString("sl_total", "0")
                            sl_used = dataObj.optString("sl_used", "0")

                            pl_total = dataObj.optString("pl_total", "0")
                            pl_used = dataObj.optString("pl_used", "0")

                            ml_total = dataObj.optString("ml_total", "0")
                            ml_used = dataObj.optString("ml_used", "0")

                            binding.textView501.text = "${cl_used}/${cl_total}"
                            binding.textView502.text = "Left: ${cl_total.toInt() - cl_used.toInt()}"

                            binding.textView511.text = "${sl_used}/${sl_total}"
                            binding.textView510.text = "Left: ${sl_total.toInt() - sl_used.toInt()}"

                            binding.textView514.text = "${pl_used}/${pl_total}"
                            binding.textView513.text = "Left: ${pl_total.toInt() - pl_used.toInt()}"

                            binding.textView517.text = "${ml_used}/${ml_total}"
                            binding.textView516.text = "Left: ${ml_total.toInt() - ml_used.toInt()}"

                        }

                    } else {
                        hideProgressDialog()
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    hideProgressDialog()
                }

            },
            Response.ErrorListener {
                Toast.makeText(requireContext(), "Error fetching leave balance", Toast.LENGTH_SHORT).show()
                hideProgressDialog()
            }) {

            override fun getParams(): Map<String, String> {

                val params = HashMap<String, String>()
                params["emp_code"] = employee_code
                return params
            }
        }
        stringRequest.tag = "add_leave"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }


    private fun addLeave() {

        val reason = binding.editTextTextPersonName56.text.toString().trim()
        val leaveType = binding.leaveType.text.toString().trim()

        if (selectedEmployees.size <= 0) {
            Toast.makeText(requireContext(), "Please select employee", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(leaveType)) {
            binding.leaveType.error = "Please select leave type"
            binding.leaveType.requestFocus()
            return
        }

        if (TextUtils.isEmpty(fromDate)) {
            binding.button4.error = "Please select from date"
            binding.button4.requestFocus()
            return
        }

        if (!leaveType.equals("Half Day", true)) {

            if (TextUtils.isEmpty(toDate)) {
                binding.button5.error = "Please select to date"
                binding.button5.requestFocus()
                return
            }

        } else {

            if (TextUtils.isEmpty(fromTime)) {
                binding.button35.error = "Please select to time"
                binding.button35.requestFocus()
                return
            }
        }

        if (TextUtils.isEmpty(reason)) {
            binding.editTextTextPersonName56.error = "Please enter leave reason"
            binding.editTextTextPersonName56.requestFocus()
            return
        }

        val requestedDays = calculateLeaveDays()

        var availableLeaves = 0.0

        when (leaveType) {

            "CL" -> availableLeaves = (cl_total.toDouble() - cl_used.toDouble())

            "SL" -> availableLeaves = (sl_total.toDouble() - sl_used.toDouble())

            "PL" -> availableLeaves = (pl_total.toDouble() - pl_used.toDouble())

            "ML" -> availableLeaves = (ml_total.toDouble() - ml_used.toDouble())

            "Half Day" -> availableLeaves = (cl_total.toDouble() - cl_used.toDouble()) // default CL
        }

        if (requestedDays > availableLeaves) {

            Toast.makeText(
                requireContext(),
                "Not enough leave balance. Available: $availableLeaves",
                Toast.LENGTH_LONG
            ).show()

            return
        }

        showProgressDialog("Applying Leave")

        val selectedEmployee = selectedEmployees[0]

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_ADD_EMPLOYEE_LEAVE,
            Response.Listener { response ->

                hideProgressDialog()

                try {
                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {

                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()

                        findNavController().popBackStack()

                    } else {

                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {

                hideProgressDialog()

                Toast.makeText(
                    requireContext(),
                    "Failed to apply leave",
                    Toast.LENGTH_SHORT
                ).show()
            }) {

            override fun getParams(): Map<String, String> {

                val params = HashMap<String, String>()

                params["emp_id"] = selectedEmployee.id
                params["site_id"] = SharedPrefManager.getInstance(requireContext()).user.site_id
                params["client_id"] = SharedPrefManager.getInstance(requireContext()).user.client_id
                params["from_date"] = fromDate
                params["to_date"] = toDate
                params["from_time"] = fromTime
                params["to_time"] = ""
                params["type"] = leaveType
                params["reason"] = reason
                params["created_by"] = SharedPrefManager.getInstance(requireContext()).user.id


                return params
            }
        }
        stringRequest.tag = "add_leave"
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

    private fun calculateLeaveDays(): Double {

        val leaveType = binding.leaveType.text.toString()

        if (leaveType.equals("Half Day", true)) {
            return 0.5
        }

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val start = sdf.parse(fromDate)
        val end = sdf.parse(toDate)

        val diff = end.time - start.time

        val days = (diff / (1000 * 60 * 60 * 24)) + 1

        return days.toDouble()
    }

    //date filter functions
    private fun updateFromDateLable(calener: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
// Here we can use calendar selected date
        binding.button4.setText((sdf.format(calener.time)))
        val myFormat2 = "yyyy-MM-dd"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.UK)
        fromDate = (sdf2.format(calener.time))
        //Log.i("oooooooooooooooooo", fromDate)
    }

    private fun updateToDateLable(calener: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
// Here we can use calendar selected date
        binding.button5.setText((sdf.format(calener.time)))

        val myFormat2 = "yyyy-MM-dd"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.UK)
        toDate = (sdf2.format(calener.time))

        // Log.i("oooooooooooooooooo", toDate)
    }

    private fun showProgressDialog(message:String) {
        if (progressDialog == null) {
            val progressBar = ProgressBar(requireContext())
            progressBar.isIndeterminate = true
            val padding = 50
            progressBar.setPadding(padding, padding, padding, padding)

            progressDialog = AlertDialog.Builder(requireContext())
                .setTitle(message)
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

    override fun onDestroyView() {
        super.onDestroyView()
        VolleySingleton.getInstance(requireActivity()).requestQueue.cancelAll("add_leave")
    }
}



