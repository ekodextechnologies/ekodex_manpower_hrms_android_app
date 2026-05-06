package com.ekodex.manpowerhrms

import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import org.json.JSONException
import org.json.JSONObject

class SupervisorAttendanceAdapter(var data: MutableList<SupervisorAttendanceData>,var activity: FragmentActivity,var fragme:String) :
    Adapter<SupervisorAttendanceViewHolder>() {

    private var progressDialog: AlertDialog? = null


    var atte_status = ""
    fun filtering(newFilteredList: ArrayList<SupervisorAttendanceData>)
    {
        data = newFilteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupervisorAttendanceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.supervisor_attendance_item_view, parent, false)
        return SupervisorAttendanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: SupervisorAttendanceViewHolder, position: Int) {
        val item = data[position]
        holder.emp_name.text = item.emp_name
        holder.emp_code.text = item.emp_code

        val checkIn = if (
            item.check_in.isNullOrBlank() ||
            item.check_in.equals("null", ignoreCase = true)
        ) {
            "00:00"
        } else {
            item.check_in
        }

        val checkOut = if (
            item.check_out.isNullOrBlank() ||
            item.check_out.equals("null", ignoreCase = true)
        ) {
            "00:00"
        } else {
            item.check_out
        }

        holder.checkin.text = "Check In: $checkIn | Check Out: $checkOut"

        holder.created_by.text = if (
            item.created_by.isNullOrBlank() ||
            item.created_by.equals("null", ignoreCase = true)
        ) {
            "NA"
        } else {
            item.created_by
        }

        val status = item.status?.takeIf { it.isNotBlank() && it != "null" } ?: "NA"

        holder.emp_status.text = status
        holder.emp_status.setTextColor(
            if (status == "A") Color.RED else Color.BLACK
        )

        holder.date.text = item.date
        holder.emp_rank.text = "(" + item.rank + ")"
        atte_status = item.status

/*
        holder.edit.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(it.context)

            val inflater = activity.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.edit_attendance_status_layout, null)
            dialogBuilder.setView(dialogView)

            var ok = dialogView.findViewById<Button>(R.id.button21)
            var new_status = dialogView.findViewById<AutoCompleteTextView>(R.id.attendance_status_edit)
            var other_status = dialogView.findViewById<EditText>(R.id.editTextTextPersonName98)
            var loading = dialogView.findViewById<ProgressBar>(R.id.progressBar7)

            var status = arrayListOf<String>("P","A","W","PP","PPP","HF","D","N","SL","CL","PL","H","Other")
            val adapter1 = ArrayAdapter(it.context,R.layout.pay_to_dropdown_layout,status)
            new_status.setAdapter(adapter1)

            //set default status from web
            new_status.setText(item.status,false)

            val alertDialog = dialogBuilder.create()
            //alertDialog.setCancelable(false)
            //alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()

            ok.setOnClickListener {
                atte_status =  new_status.text.toString()

                if (atte_status == "Other") {
                    if(TextUtils.isEmpty(other_status.text.toString()))
                    {
                        other_status.error = "Please enter attendance status"
                        other_status.requestFocus()
                        return@setOnClickListener
                    }
                    else
                    {
                        atte_status = other_status.text.toString()
                        loading.visibility = View.VISIBLE
                        updateStatus(holder,item,alertDialog,loading,position)
                    }
                }
                else if(new_status.length()>0)
                {
                    atte_status = new_status.text.toString()
                    updateStatus(holder, item, alertDialog, loading,position)
                }
                else
                {
                    new_status.error = "Please select attendance status"
                    new_status.requestFocus()
                    return@setOnClickListener
                }
            }

            //status click listner for Other
            new_status.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    if (parent.getItemAtPosition(position) as String == "Other") {
                        other_status.visibility = View.VISIBLE
                        atte_status = ""
                    } else {
                        other_status.visibility = View.GONE
                        atte_status = new_status.text.toString()
                    }

                }
        }
*/

        holder.edit.setOnClickListener {
            val currentPosition = holder.adapterPosition
            if (currentPosition == RecyclerView.NO_POSITION) return@setOnClickListener

            val item = data[currentPosition]

            val dialogBuilder = AlertDialog.Builder(it.context)
            val inflater = activity.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.edit_attendance_status_layout, null)
            dialogBuilder.setView(dialogView)

            val ok = dialogView.findViewById<Button>(R.id.button21)
            val new_status = dialogView.findViewById<AutoCompleteTextView>(R.id.attendance_status_edit)
            val other_status = dialogView.findViewById<EditText>(R.id.editTextTextPersonName98)
            val loading = dialogView.findViewById<ProgressBar>(R.id.progressBar7)

            // --- Time fields ---
            val checkInEdit = dialogView.findViewById<TextView>(R.id.checkin_time_edit)
            val checkOutEdit = dialogView.findViewById<TextView>(R.id.checkout_time_edit)

            checkInEdit.text = if(item.check_in.isNullOrBlank() || item.check_in.equals("null", true)) "00:00" else item.check_in
            checkOutEdit.text = if(item.check_out.isNullOrBlank() || item.check_out.equals("null", true)) "00:00" else item.check_out

            checkInEdit.setOnClickListener { openTimePicker(checkInEdit) }
            checkOutEdit.setOnClickListener { openTimePicker(checkOutEdit) }

            // --- Status dropdown ---
            val statusList = arrayListOf("P","A","W","PP","PPP","HF","D","N","SL","CL","PL","H","Other")
            val adapter1 = ArrayAdapter(it.context, R.layout.pay_to_dropdown_layout, statusList)
            new_status.setAdapter(adapter1)
            new_status.setText(if(item.status.isNullOrBlank() || item.status.equals("null", true)) "" else item.status, false)

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            // Status dropdown "Other" listener
            new_status.onItemClickListener = AdapterView.OnItemClickListener { parent, _, _, _ ->
                val selected = parent.getItemAtPosition(0) as String
                if (selected == "Other") {
                    other_status.visibility = View.VISIBLE
                } else {
                    other_status.visibility = View.GONE
                }
            }

            ok.setOnClickListener {
                val adapterPos = holder.adapterPosition
                if (adapterPos == RecyclerView.NO_POSITION) return@setOnClickListener

                var atte_status = new_status.text.toString()
                if (atte_status == "Other") {
                    if (TextUtils.isEmpty(other_status.text.toString())) {
                        other_status.error = "Please enter attendance status"
                        other_status.requestFocus()
                        return@setOnClickListener
                    } else {
                        atte_status = other_status.text.toString()
                    }
                } else if (new_status.text.isNullOrBlank()) {
                    new_status.error = "Please select attendance status"
                    new_status.requestFocus()
                    return@setOnClickListener
                }

                loading.visibility = View.VISIBLE
                showProgressDialog()

                val stringRequest = object : StringRequest(
                    Request.Method.POST, URLs.URL_UPDATE_SUPERVISOR_ATTENDANCE_STATUS,
                    Response.Listener { response ->
                        try {
                            val obj = JSONObject(response)
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(holder.edit.context.applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                                alertDialog.dismiss()

                                data[adapterPos].status = atte_status
                                data[adapterPos].check_in = checkInEdit.text.toString()
                                data[adapterPos].check_out = checkOutEdit.text.toString()

                                holder.emp_status.text = atte_status
                                holder.checkin.text = "Check In: ${data[adapterPos].check_in} | Check Out: ${data[adapterPos].check_out}"
                                notifyItemChanged(adapterPos)
                            } else {
                                Toast.makeText(holder.edit.context.applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        } finally {
                            loading.visibility = View.GONE
                            hideProgressDialog()
                        }
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(holder.edit.context.applicationContext, error.message ?: "Error", Toast.LENGTH_SHORT).show()
                        loading.visibility = View.GONE
                        hideProgressDialog()
                    }
                ) {
                    override fun getParams(): Map<String, String> {
                        return hashMapOf(
                            "id" to item.id,
                            "user_id" to SharedPrefManager.getInstance(holder.itemView.context).user.id.toString(),
                            "status" to atte_status,
                            "check_in" to checkInEdit.text.toString(),
                            "check_out" to checkOutEdit.text.toString()
                        )
                    }
                }

                VolleySingleton.getInstance(holder.edit.context.applicationContext).addToRequestQueue(stringRequest)
            }
        }

    }

/*    private fun openTimePicker(tv: TextView) {
        val currentTime = tv.text.toString().split(":")
        var hour = currentTime[0].toIntOrNull() ?: 9
        var minute = currentTime[1].toIntOrNull() ?: 0

        // Convert existing 24-hour time to 12-hour if needed
        val isPM = hour >= 12
        if (hour > 12) hour -= 12
        if (hour == 0) hour = 12

        val timePicker = android.app.TimePickerDialog(activity,
            { _, selectedHour, selectedMinute ->
                // Convert 24-hour selection to 12-hour with AM/PM
                val amPm = if (selectedHour >= 12) "PM" else "AM"
                var hour12 = selectedHour % 12
                if (hour12 == 0) hour12 = 12
                tv.text = String.format("%02d:%02d %s", hour12, selectedMinute, amPm)
            }, hour, minute, false // false = 12-hour mode
        )
        timePicker.show()
    }*/


    private fun openTimePicker(tv: TextView) {
        val timeText = tv.text.toString().trim()

        var hour = 9
        var minute = 0

        if (timeText.contains(":")) {
            // Split into time and optional AM/PM
            val parts = timeText.split(" ")
            val timePart = parts[0].split(":")

            hour = timePart.getOrNull(0)?.toIntOrNull() ?: 9
            minute = timePart.getOrNull(1)?.toIntOrNull() ?: 0

            // Handle AM/PM if present
            if (parts.size > 1) {
                val isPM = parts[1].equals("PM", ignoreCase = true)
                if (isPM && hour < 12) hour += 12
                if (!isPM && hour == 12) hour = 0
            }
        }

        val timePicker = android.app.TimePickerDialog(
            activity,
            { _, selectedHour, selectedMinute ->
                // Convert to 12-hour format with AM/PM
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


    private fun updateStatus(
        holder: SupervisorAttendanceViewHolder,
        item: SupervisorAttendanceData,
        alertDialog: AlertDialog,
        loading: ProgressBar,
        position: Int
    ) {
        showProgressDialog()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_UPDATE_SUPERVISOR_ATTENDANCE_STATUS,
            Response.Listener
            { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(holder.edit.context.applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                        alertDialog.dismiss()
                        loading.visibility = View.GONE
                        Log.i("11111",fragme)
                        data[position].status = atte_status
                        holder.emp_status.text = atte_status
                        notifyItemChanged(position)
                        hideProgressDialog()
                    }
                    else {
                         Toast.makeText(
                            holder.edit.context.applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                   hideProgressDialog()
                    }
                } catch (e: JSONException) {
                    
                    hideProgressDialog()
                }
            },
            Response.ErrorListener { error ->
                if (error.message != null) {
                    Toast.makeText(holder.edit.context.applicationContext, error.message, Toast.LENGTH_SHORT).show()
             hideProgressDialog()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id"] = item.id
                params["user_id"] = SharedPrefManager.getInstance(holder.itemView.context).user.id.toString()
                params["status"] = atte_status

                return params
            }
        }

        VolleySingleton.getInstance(holder.edit.context.applicationContext)
            .addToRequestQueue(stringRequest)

    }


    override fun getItemCount() = data.size

    private fun showProgressDialog() {
        if (progressDialog == null) {
            val progressBar = ProgressBar(activity)
            progressBar.isIndeterminate = true
            val padding = 50
            progressBar.setPadding(padding, padding, padding, padding)

            progressDialog = AlertDialog.Builder(activity)
                .setTitle("Updating Status")
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

class SupervisorAttendanceViewHolder(itemView: View) : ViewHolder(itemView) {
    val emp_name: TextView = itemView.findViewById(R.id.textView300)
    val created_by: TextView = itemView.findViewById(R.id.textView51)
    val emp_code: TextView = itemView.findViewById(R.id.textView307)
    val emp_status: TextView = itemView.findViewById(R.id.textView308)
    val emp_rank: TextView = itemView.findViewById(R.id.textView309)
    val date: TextView = itemView.findViewById(R.id.textView344)
    val edit: ImageView = itemView.findViewById(R.id.imageView54)
    val checkin: TextView = itemView.findViewById(R.id.textView485)





    //val layout: CardView = itemView.findViewById(R.id.attendance_history_card)
}


