package com.ekodex.manpowerhrms.Expenses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ekodex.manpowerhrms.R


class ExpenseAdapter(var data: List<ExpenseData>) :
    Adapter<ExpenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.expense_item_view, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {

        val item = data[position]
        holder.amount.text = "₹" +  item.amount
        holder.remark.text = item.remark
        holder.advDate.text = item.advdate

    }

    override fun getItemCount() = data.size

}

class ExpenseViewHolder(itemView: View) : ViewHolder(itemView) {
    val amount: TextView = itemView.findViewById(R.id.textView22)
    val remark: TextView = itemView.findViewById(R.id.textView23)
    val advDate: TextView = itemView.findViewById(R.id.textView24)
}



