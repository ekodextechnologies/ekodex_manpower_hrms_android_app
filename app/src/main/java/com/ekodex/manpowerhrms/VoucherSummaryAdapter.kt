package com.ekodex.manpowerhrms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ekodex.manpowerhrms.R

class VoucherSummaryAdapter(var data: List<VoucherSummaryData>) :
    Adapter<VoucherSummaryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoucherSummaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.voucher_summary_item_view, parent, false)
        return VoucherSummaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: VoucherSummaryViewHolder, position: Int) {
        val item = data[position]
        holder.date.text = item.date
        if(item.date.isNotBlank() && item.date!="null")
        {
            holder.date.text = item.date
        }
        else
        {
            holder.date.text = "NA"
        }

        if(item.total.isNotBlank() && item.total!="null")
        {
            holder.total.text = item.total
        }
        else
        {
            holder.total.text = "0"
        }


        if(item.pending.isNotBlank() && item.pending!="null")
        {
            holder.pending.text = item.pending
        }
        else
        {
            holder.pending.text = "0"
        }

        if(item.rejected.isNotBlank() && item.rejected!="null")
        {
            holder.rejected.text = item.rejected
        }
        else
        {
            holder.rejected.text = "0"
        }

        if(item.approved.isNotBlank() && item.approved!="null")
        {
            holder.approved.text = item.approved
        }
        else
        {
            holder.approved.text = "0"
        }

        if(item.paid.isNotBlank() && item.paid!="null")
        {
            holder.paid.text = item.paid
        }
        else
        {
            holder.paid.text = "0"
        }

//        holder.layout.setOnClickListener {
//            it.findNavController().navigate(AttendanceReportFragmentDirections.actionAttendanceReportFragmentToSupervisorAttendanceDetailsFragment(item.date))
//        }

    }

    override fun getItemCount() = data.size

}

class VoucherSummaryViewHolder(itemView: View) : ViewHolder(itemView) {
    val date: TextView = itemView.findViewById(R.id.textView307)
    val total: TextView = itemView.findViewById(R.id.textView300)
    val pending: TextView = itemView.findViewById(R.id.textView399)
    val rejected: TextView = itemView.findViewById(R.id.textView308)
    val approved: TextView = itemView.findViewById(R.id.textView324)
    val paid: TextView = itemView.findViewById(R.id.textView466)
    val layout: ConstraintLayout = itemView.findViewById(R.id.attendance_history_card)
}


