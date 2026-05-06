package com.ekodex.manpowerhrms.Attendance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ekodex.manpowerhrms.R

class AttendanceHistoryAdapter(var data: List<AttendanceHistoryData>) :
    Adapter<AttendanceHistoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.attendance_history_item_view, parent, false)
        return AttendanceHistoryViewHolder(view)

    }

    override fun onBindViewHolder(holder: AttendanceHistoryViewHolder, position: Int) {
        val item = data[position]
        holder.date.text = item.date
        holder.check_in.text = "Check in " + item.check_in_time
        holder.check_out.text = "Check out " + item.check_out_time
        // it.findNavController().navigate(ChooseCustomerFragmentDirections.actionChooseCustomerFragmentToCreateBillFragment("",note))

    }

    override fun getItemCount() = data.size

}

class AttendanceHistoryViewHolder(itemView: View) : ViewHolder(itemView) {
    val date: TextView = itemView.findViewById(R.id.textView22)
    val check_in: TextView = itemView.findViewById(R.id.textView23)
    val check_out: TextView = itemView.findViewById(R.id.textView24)
    val layout: CardView = itemView.findViewById(R.id.attendance_history_card)
}


