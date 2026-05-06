package com.ekodex.manpowerhrms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ekodex.manpowerhrms.Employee_Directory.EmployeeAttendanceSummaryData

class EmployeeNameAdapter(
    var data: List<EmployeeAttendanceSummaryData>
) : RecyclerView.Adapter<EmployeeNameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeNameViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.employee_name_item_view, parent, false)
        return EmployeeNameViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeNameViewHolder, position: Int) {
        val item = data[position]
        holder.name.text = item.name
    }

    override fun getItemCount(): Int = data.size
}

class EmployeeNameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.tvEmployeeName)
}