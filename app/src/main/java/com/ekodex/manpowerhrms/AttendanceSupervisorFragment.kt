package com.ekodex.manpowerhrms

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.MultiSpinnerListener
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentAttendanceSupervisorBinding
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class AttendanceSupervisorFragment : Fragment() {

    lateinit var binding: FragmentAttendanceSupervisorBinding
    private var progressDialog: AlertDialog? = null
    var atte_status = ""
    lateinit var employees: MutableList<Employee_Data>
    lateinit var employee_names: MutableList<String>
    var emp_id = ""
    var emp_code = ""
    var emp_rank = ""
    var att_date = ""
    var selected_emp_name = ""
    lateinit var spinner_data: MutableList<KeyPairBoolData>
    lateinit var selected_spinner: MutableList<KeyPairBoolData>
    val selectedEmployees = mutableListOf<Employee_Data>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_attendance_supervisor,
            container,
            false
        )

        spinner_data = mutableListOf()
        selected_spinner = mutableListOf()
        employees = mutableListOf()
        employee_names = mutableListOf()

        if (SharedPrefManager.getInstance(requireActivity().applicationContext).user.role == "Supervisor") {
            binding.button17.visibility = View.INVISIBLE
        }

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val sdf1 = SimpleDateFormat("yyyy-MM-dd")

        //set current date to textviews of table
        val currtDate = sdf.format(Date())
        binding.button24.text = currtDate
        att_date = sdf1.format(Date())


        // date clicklistner
        val calender1 = Calendar.getInstance()
        val datePicker1 =
            DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                calender1.set(Calendar.YEAR, year)
                calender1.set(Calendar.MONTH, month)
                calender1.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateToDateLable(calender1)
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
        }


        var i1 = Internet()
//        var status = arrayListOf<String>("P-Present","A-Absent","W-Weekly Off","P1 - Overtime","PP - Overtime","PPP - Overtime","HF - Half Day","D - Day"," N - Night","Other")
        var status = arrayListOf<String>(
            "P",
            "A",
            "W",
            "PP",
            "PPP",
            "HF",
            "D",
            "N",
            "SL",
            "CL",
            "PL",
            "H",
            "Other"
        )
        val adapter1 = ArrayAdapter(requireContext(), R.layout.pay_to_dropdown_layout, status)
        binding.attendanceStatus.setAdapter(adapter1)

        if (i1.checkConnection(requireContext())) {
            getAllEmployees(att_date)
        } else {
            Toast.makeText(
                requireContext(),
                "Please Check internet connection!!",
                Toast.LENGTH_LONG
            ).show()
        }

        //Attendance button
        binding.button20.setOnClickListener {
            if (i1.checkConnection(requireContext())) {
                if (selectedEmployees.size > 0) {
                    addAttendance()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "First select an employee!!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please Check internet connection!!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        //Job left
        binding.button28.setOnClickListener {
            if (i1.checkConnection(requireContext())) {
                if (selectedEmployees.size > 0) {
                    markJobLeft()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "First select an employee!!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please Check internet connection!!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.button17.setOnClickListener {
            it.findNavController()
                .navigate(AttendanceSupervisorFragmentDirections.actionAttendanceSupervisorFragmentToAddNewEmployeeFragment())
        }

        // -------------------------------------------------------------------------------------------

        binding.button27.setOnClickListener {
            val builder = androidx.appcompat.app.AlertDialog.Builder(
                it.context,
                R.style.MaterialAlertDialog_OK_color
            )
            //set title for alert dialog
            builder.setTitle("All Attendance!!")
            //set message for alert dialog
            builder.setMessage("Confirm to mark attendance of everyone?")
            builder.setIcon(R.mipmap.ic_launcher)

            //performing positive action
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                setAllAttendance()
            }
            //performing negative action
            builder.setNegativeButton("No") { dialogInterface, which ->
                Toast.makeText(it.context.applicationContext, "It's ok", Toast.LENGTH_LONG)
                    .show()
            }
            // Create the AlertDialog
            val alertDialog: androidx.appcompat.app.AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
        // -------------------------------------------------------------------------------------------

        binding.attendanceStatus.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                if (parent.getItemAtPosition(position) as String == "Other") {
                    binding.editTextTextPersonName77.visibility = View.VISIBLE
                    atte_status = ""
                } else {
                    binding.editTextTextPersonName77.visibility = View.GONE
                    atte_status = binding.attendanceStatus.text.toString()
                }

            }

        binding.empName.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = binding.empName.text.toString()
                val originalIndex = employee_names.indexOf(selectedItem)
                emp_id = employees.get(originalIndex).id
                emp_code = employees.get(originalIndex).emp_code
                emp_rank = employees.get(originalIndex).rank
                selected_emp_name = employees.get(originalIndex).name
                //Toast.makeText(requireContext(),emp_id,Toast.LENGTH_LONG).show()
            }

        }

        binding.editTextTextPersonName77.doOnTextChanged { text, start, before, count ->
            try {
                atte_status = text.toString()
                //Toast.makeText(requireContext(),atte_status,Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                atte_status = ""
            }
        }

        // Pass true If you want searchView above the list. Otherwise false. default = true.
        // Pass true If you want searchView above the list. Otherwise false. default = true.
        binding.multipleItemSelectionSpinner.isSearchEnabled = true

        // A text that will display in search hint.
        binding.multipleItemSelectionSpinner.setSearchHint("Search Employee")

        // Set text that will display when search result not found...

        // Set text that will display when search result not found...
        binding.multipleItemSelectionSpinner.setEmptyTitle("Not Data Found!")

        // If you will set the limit, this button will not display automatically.

        // If you will set the limit, this button will not display automatically.
        binding.multipleItemSelectionSpinner.isShowSelectAllButton = true

        //A text that will display in clear text button

        //A text that will display in clear text button
        binding.multipleItemSelectionSpinner.setClearText("Close & Clear")

        // Removed second parameter, position. Its not required now..
        // If you want to pass preselected items, you can do it while making listArray,
        // Pass true in setSelected of any item that you want to preselect

        // Removed second parameter, position. Its not required now..
        // If you want to pass preselected items, you can do it while making listArray,
        // Pass true in setSelected of any item that you want to preselect


        binding.checkinTimeEdit.setOnClickListener { openTimePicker(binding.checkinTimeEdit) }
        binding.checkoutTimeEdit.setOnClickListener { openTimePicker(binding.checkoutTimeEdit) }

        return binding.root

    }

    private fun setAllAttendance() {
        if (employees.size > 0) {

            showProgressDialog()

            val employeesJsonArray = org.json.JSONArray()
            for (emp in employees) {
                val obj = org.json.JSONObject()
                obj.put("emp_name", emp.name ?: "")
                obj.put("emp_code", emp.emp_code ?: "")
                obj.put("emp_id", emp.id ?: "")
                obj.put("rank", emp.rank ?: "")
                employeesJsonArray.put(obj)
            }

            //   binding.progressBar4.visibility = View.VISIBLE

            // for (i in 1..employees.size - 1) {
            val stringRequest = object : StringRequest(
                Request.Method.POST, URLs.URL_ADD_SUPERVISOR_ATTENDANCE,
                Response.Listener { response ->

                    try {
                        //converting response to json object
                        val obj = JSONObject(response)

                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            //execute only on last loop
                            // if (i == employees.size - 1) {
                            //    binding.progressBar4.visibility = View.GONE
                            hideProgressDialog()
                            Toast.makeText(
                                requireActivity().applicationContext,
                                obj.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().navigate(
                                AttendanceSupervisorFragmentDirections.actionAttendanceSupervisorFragmentSelf()
                            )

                            // }
                        } else {
                            Toast.makeText(
                                requireActivity().applicationContext,
                                obj.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()
                            //    binding.progressBar4.visibility = View.GONE
                            hideProgressDialog()
                        }
                    } catch (e: JSONException) {

                        hideProgressDialog()
                    }
                },
                Response.ErrorListener { error ->
                    if (error.message != null) {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            error.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        //  binding.progressBar4.visibility = View.GONE
                        hideProgressDialog()
                    }
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()

                    //    params["emp_name"] = employees.get(i).name
//                        params["emp_code"] = employees.get(i).emp_code
//                        params["rank"] = employees.get(i).rank
                    Log.i("11111", employeesJsonArray.toString())
                    params["employees"] = employeesJsonArray.toString()
                    params["status"] = atte_status
                    params["attendance_date"] = att_date
                    params["created_by"] =
                        SharedPrefManager.getInstance(requireActivity().applicationContext).user.id
                    params["site_id"] =
                        SharedPrefManager.getInstance(requireActivity().applicationContext).user.site_id
                    params["client_code"] =
                        SharedPrefManager.getInstance(requireActivity().applicationContext).user.client_code

                    return params
                }
            }

            stringRequest.retryPolicy = DefaultRetryPolicy(
                300000, // 5 minutes
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )

            stringRequest.tag = "supervisor_attendance"
            VolleySingleton.getInstance(requireActivity().applicationContext)
                .addToRequestQueue(stringRequest)
        } else {
            Toast.makeText(requireContext(), "No employees available!!", Toast.LENGTH_LONG).show()
        }
    }

    private fun addAttendance() {

        Log.i("check in ",binding.checkinTimeEdit.text.toString())
        Log.i("check out ",binding.checkoutTimeEdit.text.toString())

        if (TextUtils.isEmpty(binding.attendanceStatus.text.toString())) {
            binding.attendanceStatus.error = "Please select attendance status"
            binding.attendanceStatus.requestFocus()
            return
        }

        if (atte_status == "Other") {
            if (TextUtils.isEmpty(binding.editTextTextPersonName77.text.toString())) {
                binding.editTextTextPersonName77.error = "Please enter attendance status"
                binding.editTextTextPersonName77.requestFocus()
                return
            } else {
                atte_status = binding.editTextTextPersonName77.text.toString()
            }
        }

        if (selectedEmployees.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Please select at least one employee",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        binding.button20.isEnabled = false
        showProgressDialog()

        val employeesJsonArray = org.json.JSONArray()
        for (emp in selectedEmployees) {
            val obj = org.json.JSONObject()
            obj.put("emp_name", emp.name ?: "")
            obj.put("emp_code", emp.emp_code ?: "")
            obj.put("emp_id", emp.id ?: "")
            obj.put("rank", emp.rank ?: "")
            employeesJsonArray.put(obj)
        }

        // binding.progressBar4.visibility = View.VISIBLE

//        var completedCount = 0
//        var successCount = 0
//        val total = selectedEmployees.size

        //    for (employee in selectedEmployees) {

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_ADD_SUPERVISOR_ATTENDANCE,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        hideProgressDialog()
                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
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
                        requireActivity().applicationContext,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    hideProgressDialog()
                }
//                    if (completedCount == total) {
//                        binding.progressBar4.visibility = View.GONE
//                        binding.button20.isEnabled = true
//                        Toast.makeText(
//                            requireActivity().applicationContext,
//                            "$successCount / $total attendance marked successfully",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        findNavController().navigate(
//                            AttendanceSupervisorFragmentDirections.actionAttendanceSupervisorFragmentSelf()
//                        )
//                    }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
//                    params["emp_name"] = employee.name ?: ""
//                    params["emp_code"] = employee.emp_code ?: ""
//                    params["rank"] = employee.rank ?: ""
                params["employees"] = employeesJsonArray.toString()
                params["status"] = atte_status
                params["attendance_date"] = att_date
                params["created_by"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.id
                params["site_id"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.site_id
                params["client_code"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.client_code
                params["check_in_time"] = binding.checkinTimeEdit.text.toString()
                params["check_out_time"] = binding.checkoutTimeEdit.text.toString()
                return params
            }
        }
        stringRequest.tag = "supervisor_attendance"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        VolleySingleton.getInstance(requireActivity()).requestQueue.cancelAll("supervisor_attendance")
    }

    /*    private fun addAttendance() {

            if (TextUtils.isEmpty(emp_name)) {
                binding.empName.text.clear()
                binding.empName.error = "Please select employee name"
                binding.empName.requestFocus()
                return
            }
            if (TextUtils.isEmpty(binding.attendanceStatus.text.toString())) {
                binding.attendanceStatus.error = "Please select attendance status"
                binding.attendanceStatus.requestFocus()
                return
            }
            if (atte_status == "Other") {
                if(TextUtils.isEmpty(binding.editTextTextPersonName77.text.toString()))
                {
                    binding.editTextTextPersonName77.error = "Please enter attendance status"
                    binding.editTextTextPersonName77.requestFocus()
                    return
                }
                else
                {
                 atte_status =  binding.editTextTextPersonName77.text.toString()
                }

            }

            binding.button20.isEnabled = false

                //Toast.makeText(requireContext(),att_date, Toast.LENGTH_LONG).show()
                binding.progressBar4.visibility = View.VISIBLE
                val stringRequest = object : StringRequest(
                    Request.Method.POST, URLs.URL_ADD_SUPERVISOR_ATTENDANCE,
                    Response.Listener { response ->

                        try {
                            //converting response to json object
                            val obj = JSONObject(response)

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                binding.progressBar4.visibility = View.GONE
                                Toast.makeText(requireActivity().applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                                findNavController().navigate(AttendanceSupervisorFragmentDirections.actionAttendanceSupervisorFragmentSelf())
                            }
                            else {
                                Toast.makeText(requireActivity().applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                                binding.progressBar4.visibility = View.GONE
                            }
                        } catch (e: JSONException) {
                            
                        }
                    },
                    Response.ErrorListener { error ->
                        if (error.message != null) {
                            Toast.makeText(requireActivity().applicationContext, error.message, Toast.LENGTH_SHORT).show()
                            binding.progressBar4.visibility = View.GONE
                        }
                    }) {
                    @Throws(AuthFailureError::class)
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()

                        params["emp_name"] = emp_name
                        params["emp_code"] = emp_code
                        params["rank"] = emp_rank
                        params["status"] = atte_status
                        params["attendance_date"] = att_date
                        params["created_by"] = SharedPrefManager.getInstance(requireActivity().applicationContext).user.id
                        params["site_id"] = SharedPrefManager.getInstance(requireActivity().applicationContext).user.site_id
                        params["client_code"] = SharedPrefManager.getInstance(requireActivity().applicationContext).user.client_code

                        return params
                    }
                }

                VolleySingleton.getInstance(requireActivity().applicationContext)
                    .addToRequestQueue(stringRequest)
            }*/

    private fun markJobLeft() {


        binding.button28.isEnabled = false
        showProgressDialog()

        val employeesJsonArray = org.json.JSONArray()
        for (emp in selectedEmployees) {
            val obj = org.json.JSONObject()
            obj.put("emp_code", emp.emp_code ?: "")
            employeesJsonArray.put(obj)
        }
        // binding.progressBar4.visibility = View.VISIBLE

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_UPDATE_JOB_LEFT_STATUS,
            Response.Listener
            { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        //  binding.progressBar4.visibility = View.GONE
                        hideProgressDialog()
                        findNavController().navigate(
                            AttendanceSupervisorFragmentDirections.actionAttendanceSupervisorFragmentSelf()
                        )

                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {

                }
            },
            Response.ErrorListener {
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()

                params["employees"] = employeesJsonArray.toString()

                return params
            }
        }
        stringRequest.tag = "supervisor_attendance"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)

    }

    private fun getAllEmployees(dates: String) {
        employee_names.clear()
        employees.clear()
        spinner_data.clear()
        selectedEmployees.clear()
        showGetEmployeesProgressDialog()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_ALL_EMPLOYEES_FOR_SUPERVISOR,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            Toast.makeText(
                                requireContext(),
                                "No employees available!!",
                                Toast.LENGTH_LONG
                            ).show()
                            binding.multipleItemSelectionSpinner.visibility = View.GONE


                            binding.button20.isEnabled = false
                            binding.button20.isClickable = false
                            binding.button20.setBackgroundColor(Color.parseColor("#D3D3D3"))
                            binding.button20.setTextColor(Color.parseColor("#FFFFFF"))

                            binding.button28.isEnabled = false
                            binding.button28.isClickable = false
                            binding.button28.setBackgroundColor(Color.parseColor("#D3D3D3"))
                            binding.button28.setTextColor(Color.parseColor("#FFFFFF"))

                            binding.textView188.visibility = View.GONE
                            binding.textView197.visibility = View.GONE


                            hideGetEmployeesProgressDialog()
                        } else {
                            //to mark all employee's at same time
                            employee_names.add("All")
                            employees.add(0, Employee_Data("", "All", "", "", ""))
                            //Toast.makeText(requireContext(),"No employees available!!", Toast.LENGTH_SHORT).show()

                            for (i in 0..(array.length() - 1)) {
                                val objectArtist = array.getJSONObject(i)
                                employee_names.add(
                                    objectArtist.getString("name") + " - " + objectArtist.getString(
                                        "emp_code"
                                    )
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

                                val data = KeyPairBoolData()
                                data.id =
                                    (objectArtist.getString("id")).toLong()  // 👈 set employee ID here
                                data.name =
                                    "${objectArtist.getString("name")} - ${objectArtist.getString("emp_code")}"
                                data.isSelected = false
                                spinner_data.add(data)

                                //old code
                                //spinner_data.add(i,KeyPairBoolData(objectArtist.getString("name"),false))

                            }
                            val adapter1 = ArrayAdapter(
                                requireContext(),
                                R.layout.pay_to_dropdown_layout,
                                employee_names
                            )
                            binding.empName.setAdapter(adapter1)
                            hideGetEmployeesProgressDialog()

                            //old code
                            binding.multipleItemSelectionSpinner.setItems(
                                spinner_data,
                                MultiSpinnerListener { items ->
                                    for (i in items.indices) {
                                        if (items[i].isSelected) {
                                            //Toast.makeText(requireContext(),i.toString() + " : " + items[i].name + " : " + employees.get(i).id,Toast.LENGTH_SHORT).show()
                                            selected_spinner.add(
                                                i,
                                                KeyPairBoolData(items[i].name, false)
                                            )
                                        }
                                    }
                                })

                            binding.multipleItemSelectionSpinner.setItems(
                                spinner_data,
                                MultiSpinnerListener { items ->


                                    for (item in items) {
                                        if (item.isSelected) {
                                            if (item.name == "All") {
                                                // user selected "All" → just take the full employees list
                                                selectedEmployees.clear()
                                                selectedEmployees.addAll(employees)
                                                break
                                            } else {
                                                // find matching employee by ID
                                                val emp =
                                                    employees.find { it.id == item.id.toString() }
                                                if (emp != null) {
                                                    selectedEmployees.add(emp)
                                                }
                                            }
                                        }
                                    }

                                    // ✅ Now you can use `selectedEmployees` anywhere
                                    for (emp in selectedEmployees) {
                                        Log.i(
                                            "11111",
                                            "ID=${emp.id}, Name=${emp.name}, Code=${emp.emp_code}, Rank=${emp.rank}"
                                        )
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
                        binding.button20.isEnabled = false
                        binding.button20.isClickable = false
                        binding.button20.setBackgroundColor(Color.parseColor("#D3D3D3"))
                        binding.button20.setTextColor(Color.parseColor("#FFFFFF"))

                        binding.button28.isEnabled = false
                        binding.button28.isClickable = false
                        binding.button28.setBackgroundColor(Color.parseColor("#D3D3D3"))
                        binding.button28.setTextColor(Color.parseColor("#FFFFFF"))

                        binding.textView188.visibility = View.GONE
                        binding.textView197.visibility = View.GONE

                        hideGetEmployeesProgressDialog()
                    }

                } catch (e: JSONException) {

                    binding.multipleItemSelectionSpinner.visibility = View.GONE
                    binding.button20.isEnabled = false
                    binding.button20.isClickable = false
                    binding.button20.setBackgroundColor(Color.parseColor("#D3D3D3"))
                    binding.button20.setTextColor(Color.parseColor("#FFFFFF"))

                    binding.button28.isEnabled = false
                    binding.button28.isClickable = false
                    binding.button28.setBackgroundColor(Color.parseColor("#D3D3D3"))
                    binding.button28.setTextColor(Color.parseColor("#FFFFFF"))

                    binding.textView188.visibility = View.GONE
                    binding.textView197.visibility = View.GONE

                    hideGetEmployeesProgressDialog()
                }

            },
            Response.ErrorListener { error ->
//                Toast.makeText(
//                    requireActivity().applicationContext,
//                    error.message,
//                    Toast.LENGTH_LONG
//                ).show()
                binding.multipleItemSelectionSpinner.visibility = View.GONE
                binding.button20.isEnabled = false
                binding.button20.isClickable = false
                binding.button20.setBackgroundColor(Color.parseColor("#D3D3D3"))
                binding.button20.setTextColor(Color.parseColor("#FFFFFF"))

                binding.button28.isEnabled = false
                binding.button28.isClickable = false
                binding.button28.setBackgroundColor(Color.parseColor("#D3D3D3"))
                binding.button28.setTextColor(Color.parseColor("#FFFFFF"))

                binding.textView188.visibility = View.GONE
                binding.textView197.visibility = View.GONE

                hideGetEmployeesProgressDialog()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["client_id"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.client_id
                params["site_id"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.site_id
                params["date"] = dates

                return params

            }
        }
        stringRequest.tag = "supervisor_attendance"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun updateToDateLable(calener: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
// Here we can use calendar selected date
        binding.button24.text = (sdf.format(calener.time))
        val myFormat2 = "yyyy-MM-dd"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.UK)
        att_date = (sdf2.format(calener.time))
        getAllEmployees(att_date)
    }


    private fun showProgressDialog() {
        if (progressDialog == null) {
            val progressBar = ProgressBar(requireContext())
            progressBar.isIndeterminate = true
            val padding = 50
            progressBar.setPadding(padding, padding, padding, padding)

            progressDialog = AlertDialog.Builder(requireContext())
                .setTitle("Adding Attendance")
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

    private fun showGetEmployeesProgressDialog() {
        if (progressDialog == null) {
            val progressBar = ProgressBar(requireContext())
            progressBar.isIndeterminate = true
            val padding = 50
            progressBar.setPadding(padding, padding, padding, padding)

            progressDialog = AlertDialog.Builder(requireContext())
                .setTitle("Getting Employees")
                .setMessage("Please wait...")
                .setView(progressBar)
                .setCancelable(false)
                .create()
        }
        progressDialog?.show()
    }

    private fun hideGetEmployeesProgressDialog() {
        progressDialog?.dismiss()
    }

    private fun openTimePicker(tv: TextView) {

        val timeText = tv.text.toString()

        var hour = 9
        var minute = 0

        if (timeText.contains(":")) {
            val parts = timeText.split(" ")

            val timePart = parts[0].split(":")
            hour = timePart.getOrNull(0)?.toIntOrNull() ?: 9
            minute = timePart.getOrNull(1)?.toIntOrNull() ?: 0

            // Handle AM/PM if present
            if (parts.size > 1) {
                val isPM = parts[1].equals("PM", true)
                if (isPM && hour < 12) hour += 12
                if (!isPM && hour == 12) hour = 0
            }
        }

        val timePicker = TimePickerDialog(
            activity,
            { _, selectedHour, selectedMinute ->

                val amPm = if (selectedHour >= 12) "PM" else "AM"
                var hour12 = selectedHour % 12
                if (hour12 == 0) hour12 = 12

                tv.text = String.format("%02d:%02d %s", hour12, selectedMinute, amPm)
            },
            hour,
            minute,
            false
        )

        timePicker.show()
    }
}