package com.ekodex.manpowerhrms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ekodex.manpowerhrms.Dashboard.DashboardFragment
import com.ekodex.manpowerhrms.Dashboard.DashboardFragmentDirections
import com.ekodex.manpowerhrms.Employee_Directory.EmployeeAttendanceSummaryData

class EmployeeAttendanceSummaryAdapter(
    var data: List<EmployeeAttendanceSummaryData>,
    var dashboardFragment: DashboardFragment
) : RecyclerView.Adapter<EmployeeAttendanceSummaryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeAttendanceSummaryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.employee_attendance_summary_item_view, parent, false)
        return EmployeeAttendanceSummaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeAttendanceSummaryViewHolder, position: Int) {

        val item = data[position]

        holder.total.text = item.total
        holder.present.text = item.present
        holder.absent.text = item.absent
        holder.half.text = item.halfday
        holder.tot_leave.text = item.total_leaves
        holder.sl.text = item.total_sl
        holder.ml.text = item.total_ml
        holder.pl.text = item.total_pl
        holder.cl.text = item.total_cl

        holder.total.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToSupervisorAttendanceDetailsFragment(
                        dashboardFragment.from_date,
                        dashboardFragment.to_date,
                        item.empcode,
                        ""
                    )
            )
        }

        holder.present.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToSupervisorAttendanceDetailsFragment(
                        dashboardFragment.from_date,
                        dashboardFragment.to_date,
                        item.empcode,
                        "P"
                    )
            )
        }

        holder.absent.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToSupervisorAttendanceDetailsFragment(
                        dashboardFragment.from_date,
                        dashboardFragment.to_date,
                        item.empcode,
                        "A"
                    )
            )
        }

        holder.half.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToSupervisorAttendanceDetailsFragment(
                        dashboardFragment.from_date,
                        dashboardFragment.to_date,
                        item.empcode,
                        "HF"
                    )
            )
        }
    }

    override fun getItemCount(): Int = data.size
}

class EmployeeAttendanceSummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val total: TextView = itemView.findViewById(R.id.textView479)
    val present: TextView = itemView.findViewById(R.id.textView480)
    val absent: TextView = itemView.findViewById(R.id.textView482)
    val half: TextView = itemView.findViewById(R.id.textView483)
    val tot_leave: TextView = itemView.findViewById(R.id.textView62)
    val cl: TextView = itemView.findViewById(R.id.textView490)
    val ml: TextView = itemView.findViewById(R.id.textView491)
    val pl: TextView = itemView.findViewById(R.id.textView492)
    val sl: TextView = itemView.findViewById(R.id.textView493)

}