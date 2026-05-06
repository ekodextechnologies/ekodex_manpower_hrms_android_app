package com.ekodex.manpowerhrms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ekodex.manpowerhrms.R

class SupervisorAttendanceReportAdapter(var data: List<SupervisorAttendanceReportData>) :
    Adapter<SupervisorAttendanceReportViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupervisorAttendanceReportViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.supervisor_attendance_report_item_view, parent, false)
        return SupervisorAttendanceReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: SupervisorAttendanceReportViewHolder, position: Int) {
        val item = data[position]
        holder.date.text = item.date
        if(item.date.isNotBlank() && item.date!="null")
        {
            holder.date.text = item.date
        }
        else
        {
            holder.total_expected.text = "NA"
        }

        if(item.total_expected.isNotBlank() && item.total_expected!="null")
        {
            holder.total_expected.text = item.total_expected
        }
        else
        {
            holder.total_expected.text = "0"
        }

        if(item.total_present.isNotBlank() && item.total_present!="null")
        {
            holder.total_present.text = item.total_present
        }
        else
        {
            holder.total_present.text = "0"
        }

        if(item.total_absent.isNotBlank() && item.total_absent!="null")
        {
            holder.total_absent.text = item.total_absent
        }
        else
        {
            holder.total_absent.text = "0"
        }

        holder.layout.setOnClickListener {
            it.findNavController().navigate(AttendanceReportFragmentDirections.actionAttendanceReportFragmentToSupervisorAttendanceDetailsFragment(item.date,item.date,"",""))
        }

    }

    override fun getItemCount() = data.size

}

class SupervisorAttendanceReportViewHolder(itemView: View) : ViewHolder(itemView) {
    val date: TextView = itemView.findViewById(R.id.textView307)
    val total_present: TextView = itemView.findViewById(R.id.textView308)
    val total_expected: TextView = itemView.findViewById(R.id.textView300)
    val total_absent: TextView = itemView.findViewById(R.id.textView324)
    val layout: ConstraintLayout = itemView.findViewById(R.id.attendance_history_card)
}


