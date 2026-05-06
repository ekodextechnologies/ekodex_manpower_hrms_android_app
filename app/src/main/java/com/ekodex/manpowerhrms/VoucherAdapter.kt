package com.ekodex.manpowerhrms

import android.app.DatePickerDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class VoucherAdapter(var data: MutableList<VoucherData>, var activity: FragmentActivity, private val onRefresh: (() -> Unit)? = null) :
    Adapter<VoucherViewHolder>() {

    var tot_amount = 0
    var scheduled_date = ""
    var scheduled_date_formatted = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoucherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.voucher_item_view, parent, false)
        return VoucherViewHolder(view)
    }

    override fun onBindViewHolder(holder: VoucherViewHolder, position: Int) {
        val item = data[position]
        holder.name.text = item.name.safe()
        holder.type.text = item.type.safe()
        holder.mode.text = "Mode: " + item.mode.safe()
        holder.date.text = "Date: " + item.date.safe()
        holder.particular.text = "Description: " + item.particular.safe()
        scheduled_date = item.date


        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val sdf1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date = sdf1.parse(item.date.safe())   // String → Date
        scheduled_date_formatted = sdf.format(date!!)   // Date → String


        var user = SharedPrefManager.getInstance(holder.approve.context).user
        if(user.role.equals("admin",ignoreCase = true))
        {
            holder.client.text = "${item.client.safe()}"
            holder.gang.text = "${item.gang.safe()}"
            holder.site.text = "${item.site.safe()}"
            holder.client.visibility = View.VISIBLE
            holder.site.visibility = View.VISIBLE
            holder.gang.visibility = View.VISIBLE
        }

        val context = holder.status_image.context

        when (item.status) {

            "Pending" -> {
                val drawable = ContextCompat.getDrawable(context, R.drawable.status_dot)?.apply {
                    setTint(Color.parseColor("#FFFF00"))
                }
                holder.status_image.setImageDrawable(drawable)
            }

            "Approved" -> {
                val drawable = ContextCompat.getDrawable(context, R.drawable.baseline_check_24)?.apply {
                    setTint(Color.parseColor("#66BB6A"))
                }
                holder.status_image.setImageDrawable(drawable)
            }

            "Paid" -> {
                val drawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_24)?.apply {
                    setTint(Color.parseColor("#66BB6A"))
                }
                holder.status_image.setImageDrawable(drawable)
            }

            "Cancelled" -> {
                val drawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_clear_24)?.apply {
                    setTint(Color.parseColor("#F44336"))
                }
                holder.status_image.setImageDrawable(drawable)
            }
        }



        //commented temporary on 19th november as mam said
//        if ((SharedPrefManager.getInstance(holder.approve.context.applicationContext).user.role == "Admin" || SharedPrefManager.getInstance(
//                holder.approve.context.applicationContext
//            ).user.role == "admin") && item.date == todayDate && (item.status == "Pending" || item.status == "Approved")
//        ) {
//            holder.edit.visibility = View.VISIBLE
//        }
//        if (SharedPrefManager.getInstance(holder.approve.context.applicationContext).user.role != "Admin" && item.date == todayDate && item.status == "Pending") {
//            holder.edit.visibility = View.VISIBLE
//        } else {
//            holder.edit.visibility = View.GONE
//        }


        if (item.status == "Pending") {
            if (SharedPrefManager.getInstance(holder.approve.context.applicationContext).user.role == "Admin" || SharedPrefManager.getInstance(
                    holder.approve.context.applicationContext
                ).user.role == "admin"
            ) {
                holder.approve.visibility = View.VISIBLE
                holder.reject.visibility = View.VISIBLE

                val createdByList = listOf(
                    item.created_by.safe(),
                    item.created_on.safe()
                ).filter { it != "NA" }

                val created_by = createdByList.joinToString(", ").ifBlank { "NA" }

                holder.status.text = "Created By: $created_by"

            }
        } else if (item.status == "Approved") {
            if (item.approved_by != null && item.approved_by != "null" && item.approved_by != "")
            {
                val approvedByList = listOf(
                    item.approved_by.safe(),
                    item.approved_on.safe()
                ).filter { it != "NA" }

                val approved_by = approvedByList.joinToString(", ").ifBlank { "NA" }

                holder.status.text = "Approved By: $approved_by"

            }
            else
            {
                holder.status.text = "Approved By: NA"
            }
        } else if (item.status == "Cancelled") {
            if (item.rejected_by != null && item.rejected_by != "null" && item.rejected_by != "")
            {
                val rejectedByList = listOf(
                    item.rejected_by.safe(),
                    item.rejected_on.safe()
                ).filter { it != "NA" }   // remove NA values

                val rejected_by = rejectedByList.joinToString(", ").ifBlank { "NA" }

                holder.status.text = "Rejected By: $rejected_by"

            }
            else
            {
                holder.status.text = "Rejected By: NA"
            }
        }
        else if (item.status == "Paid") {
            if (item.paid_by != null && item.paid_by != "null" && item.paid_by != "")
            {
                val paidByList = listOf(
                    item.paid_by.safe(),
                    item.paid_on.safe()
                ).filter { it != "NA" }   // remove NA values

                val paid_by = paidByList.joinToString(", ").ifBlank { "NA" }

                holder.status.text = "Paid By: $paid_by"
            }
            else
            {
                holder.status.text = "Paid By: NA"
            }
        }

        if (item.amt != null && item.amt != "null" && item.amt != "") {
            holder.total.text = "Total Amount : ${item.amt}"
        } else {
            holder.total.text = "Total Amount : 0"
        }

        holder.layout.setOnClickListener {
            it.findNavController().navigate(VoucherManagementFragmentDirections.actionVoucherManagementFragmentToVoucherDetailsFragment(item.voucher_no))
        }

        holder.edit.setOnClickListener {
            it.findNavController().navigate(
                VoucherManagementFragmentDirections.actionVoucherManagementFragmentToEditVoucherFragment(
                    item.voucher_no
                )
            )
        }

        holder.approve.setOnClickListener {it
//            AlertDialog.Builder(it.context)
//                .setTitle("Approve Voucher")
//                .setMessage("Are you sure?")
//                .setPositiveButton("Yes") { dialog, _ ->
//                    approveVoucher(holder, item)
//                    dialog.dismiss()
//                }
//                .setNegativeButton("No") { dialog, _ ->
//                    // No clicked
//                    dialog.dismiss()
//                }
//                .create()
//                .show()

            val dialogBuilder = AlertDialog.Builder(it.context)

            val inflater = activity.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.voucher_scheduled_date_layout, null)
            dialogBuilder.setView(dialogView)

            var scheduled_date_btn = dialogView.findViewById<Button>(R.id.button29)
            var voucher_approve_btn = dialogView.findViewById<Button>(R.id.button30)

            scheduled_date_btn.setText(scheduled_date_formatted)

            val alertDialog = dialogBuilder.create()
            //alertDialog.setCancelable(false)
            //alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()

            voucher_approve_btn.setOnClickListener {
                approveVoucher(holder, item,alertDialog)
            }

            // date clicklistner
            val calender1 = Calendar.getInstance()
            val datePicker1 =
                DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                    calender1.set(Calendar.YEAR, year)
                    calender1.set(Calendar.MONTH, month)
                    calender1.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateToDateLable(calender1,scheduled_date_btn)
                }

            scheduled_date_btn.setOnClickListener {
                DatePickerDialog(
                    activity,
                    datePicker1,
                    calender1.get(Calendar.YEAR),
                    calender1.get(Calendar.MONTH),
                    calender1.get(Calendar.DAY_OF_MONTH)
                )
                    .show()
            }
        }

        holder.reject.setOnClickListener {it
            AlertDialog.Builder(it.context)
                .setTitle("Reject Voucher")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes") { dialog, _ ->
                    rejectVoucher(holder, item)
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    // No clicked
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    private fun updateToDateLable(calener: Calendar, scheduled_date_btn: Button) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
// Here we can use calendar selected date
        scheduled_date_btn.text = (sdf.format(calener.time))
        val myFormat2 = "yyyy-MM-dd"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.UK)
        scheduled_date = (sdf2.format(calener.time))
        //getAllEmployees(att_date)
    }

    private fun approveVoucher(
        holder: VoucherViewHolder,
        item: VoucherData,
        alertDialog: AlertDialog
    ) {
        holder.approve.isEnabled = false
        holder.loading.visibility = View.VISIBLE
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_APPROVE_VOUCHER,
            Response.Listener { response ->
                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        holder.loading.visibility = View.GONE
                        Toast.makeText(
                            holder.loading.context.applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        onRefresh?.invoke()
                        alertDialog.dismiss()
//                        holder.approve.findNavController()
//                            .navigate(VoucherManagementFragmentDirections.actionVoucherManagementFragmentSelf("",""))
                    } else {
                        Toast.makeText(
                            holder.loading.context.applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        holder.loading.visibility = View.GONE
                    }
                } catch (e: JSONException) {
                    
                    holder.loading.visibility = View.GONE
                }
            },
            Response.ErrorListener { error ->
                if (error.message != null) {
                    Toast.makeText(
                        holder.loading.context.applicationContext,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    holder.loading.visibility = View.GONE
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()

                params["voucher_no"] = item.voucher_no
                params["schedule_date"] = scheduled_date
                params["original_amount"] = item.amt
                params["approved_by"] =
                    SharedPrefManager.getInstance(holder.loading.context.applicationContext).user.id
                return params
            }
        }

        VolleySingleton.getInstance(holder.loading.context.applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun rejectVoucher(holder: VoucherViewHolder, item: VoucherData) {
        holder.reject.isEnabled = false
        holder.loading.visibility = View.VISIBLE
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_REJECT_VOUCHER,
            Response.Listener { response ->
                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        holder.loading.visibility = View.GONE
                        Toast.makeText(
                            holder.loading.context.applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        onRefresh?.invoke()
//                        holder.reject.findNavController()
//                            .navigate(VoucherManagementFragmentDirections.actionVoucherManagementFragmentSelf("",""))
                    } else {
                        Toast.makeText(
                            holder.loading.context.applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        holder.loading.visibility = View.GONE
                    }
                } catch (e: JSONException) {
                    
                    holder.loading.visibility = View.GONE
                }
            },
            Response.ErrorListener { error ->
                if (error.message != null) {
                    Toast.makeText(
                        holder.loading.context.applicationContext,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    holder.loading.visibility = View.GONE
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()

                params["voucher_no"] = item.voucher_no
                params["original_amount"] = item.amt
                params["rejected_by"] =
                    SharedPrefManager.getInstance(holder.loading.context.applicationContext).user.id
                return params
            }
        }

        VolleySingleton.getInstance(holder.loading.context.applicationContext)
            .addToRequestQueue(stringRequest)
    }

    fun String?.safe() = this?.takeIf { it.isNotBlank() && !it.equals("null", true) } ?: "NA"

    override fun getItemCount() = data.size
}

class VoucherViewHolder(itemView: View) : ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.textView346)
    val type: TextView = itemView.findViewById(R.id.textView347)
    val mode: TextView = itemView.findViewById(R.id.textView348)
    val client: TextView = itemView.findViewById(R.id.textView464)
    val site: TextView = itemView.findViewById(R.id.textView465)
    val gang: TextView = itemView.findViewById(R.id.textView470)
    val date: TextView = itemView.findViewById(R.id.textView349)
    val status: TextView = itemView.findViewById(R.id.textView345)
    val status_image: ImageView = itemView.findViewById(R.id.statusDot)
    val total: TextView = itemView.findViewById(R.id.textView352)
    val particular: TextView = itemView.findViewById(R.id.textView371)
    val edit: FloatingActionButton = itemView.findViewById(R.id.editVoucher)
    val approve: FloatingActionButton = itemView.findViewById(R.id.editVoucher2)
    val reject: FloatingActionButton = itemView.findViewById(R.id.editVoucher3)
    val loading: ProgressBar = itemView.findViewById(R.id.progressBar10)
    val layout: CardView = itemView.findViewById(R.id.voucherCard)
}






