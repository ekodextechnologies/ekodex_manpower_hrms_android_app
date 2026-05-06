package com.ekodex.manpowerhrms.Leaves_Management

import android.app.DatePickerDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
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
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.VoucherData
import com.ekodex.manpowerhrms.VoucherManagementFragmentDirections
import com.ekodex.manpowerhrms.VoucherViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException
import org.json.JSONObject
import java.util.Calendar
import java.util.HashMap


class LeavesAdapter(var data: List<LeavesData>, var activity: FragmentActivity, private val onRefresh: (() -> Unit)? = null) :
    Adapter<LeavesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeavesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.leave_item_view, parent, false)
        return LeavesViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeavesViewHolder, position: Int) {
        val item = data[position]
        holder.reason.text = item.desc
      if(item.type.equals("Half Day",ignoreCase = true))
      {
          holder.date.text = item.from + " At " + item.from_time
      }
        else
      {
          holder.date.text = item.from + " To " + item.to
      }
        holder.type.text = "Leave Type: " + item.type + " | Status: " + item.status
        holder.status.text = item.status
        holder.total.text = "Total ${item.totaldays} days"

        var user = SharedPrefManager.getInstance(holder.approve.context).user
        if(user.role.equals("admin",ignoreCase = true))
        {
            holder.client.text = "${item.client.safe()}"
            holder.site.text = "${item.site.safe()}"
            holder.client.visibility = View.VISIBLE
            holder.site.visibility = View.VISIBLE
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

            "Rejected" -> {
                val drawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_clear_24)?.apply {
                    setTint(Color.parseColor("#F44336"))
                }
                holder.status_image.setImageDrawable(drawable)
            }
        }

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
        } else if (item.status == "Rejected") {
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

        holder.layout.setOnClickListener {
            it.findNavController().navigate(LeavesManagementFragmentDirections.actionLeavesManagementFragmentToLeavesDetailFragment(item.id))
        }

     /*   holder.edit.setOnClickListener {
            it.findNavController().navigate(
                VoucherManagementFragmentDirections.actionVoucherManagementFragmentToEditVoucherFragment(
                    item.voucher_no
                )
            )
        }*/


        holder.approve.setOnClickListener {it
            AlertDialog.Builder(it.context)
                .setTitle("Approve Leave")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes") { dialog, _ ->
                    approveLeave(holder, item)
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    // No clicked
                    dialog.dismiss()
                }
                .create()
                .show()
        }

        holder.reject.setOnClickListener {it
            AlertDialog.Builder(it.context)
                .setTitle("Reject Leave")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes") { dialog, _ ->
                    rejectLeave(holder, item)
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

    private fun approveLeave(
        holder: LeavesViewHolder,
        item: LeavesData
    ) {
        holder.approve.isEnabled = false
        holder.loading.visibility = View.VISIBLE
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_APPROVE_EMPLOYEE_LEAVE,
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
                params["leave_id"] =  item.id
                params["approved_by"] =
                    SharedPrefManager.getInstance(holder.loading.context.applicationContext).user.id
                return params
            }
        }

        VolleySingleton.getInstance(holder.loading.context.applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun rejectLeave(holder: LeavesViewHolder, item: LeavesData) {
        holder.reject.isEnabled = false
        holder.loading.visibility = View.VISIBLE
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_REJECT_EMPLOYEE_LEAVE,
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

                params["leave_id"] =  item.id
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

class LeavesViewHolder(itemView: View) : ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.textView346)
    val reason: TextView = itemView.findViewById(R.id.textView347)
    val client: TextView = itemView.findViewById(R.id.textView464)
    val site: TextView = itemView.findViewById(R.id.textView465)
    val gang: TextView = itemView.findViewById(R.id.textView470)
    val date: TextView = itemView.findViewById(R.id.textView349)
    val status_image: ImageView = itemView.findViewById(R.id.statusDot)
    val total: TextView = itemView.findViewById(R.id.textView352)
    val status: TextView = itemView.findViewById(R.id.textView345)
    val type: TextView = itemView.findViewById(R.id.textView371)
    val edit: FloatingActionButton = itemView.findViewById(R.id.editVoucher)
    val approve: FloatingActionButton = itemView.findViewById(R.id.editVoucher2)
    val reject: FloatingActionButton = itemView.findViewById(R.id.editVoucher3)
    val loading: ProgressBar = itemView.findViewById(R.id.progressBar10)
    val layout: CardView = itemView.findViewById(R.id.voucherCard)
}


