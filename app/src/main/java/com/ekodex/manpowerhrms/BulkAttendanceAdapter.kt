package com.ekodex.manpowerhrms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class BulkAttendanceAdapter(
    var data: MutableList<SalarySlipData>,
    var salarySlipFragment: BulkAttendanceDownloadFragment
) :
    Adapter<BulkAttendanceViewHolder>() {

    fun filtering(newFilteredList: ArrayList<SalarySlipData>)
    {
        data = newFilteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BulkAttendanceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.bulk_attendance_item_view, parent, false)
        return BulkAttendanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: BulkAttendanceViewHolder, position: Int) {
        val item = data[position]
        holder.name.text = "${item.emp_name}_attendance"
    }

    override fun getItemCount() = data.size

}

class BulkAttendanceViewHolder(itemView: View) : ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.textView233)

}


