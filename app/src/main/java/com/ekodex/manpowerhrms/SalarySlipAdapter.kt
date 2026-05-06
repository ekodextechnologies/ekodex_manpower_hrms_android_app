package com.ekodex.manpowerhrms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SalarySlipAdapter(
    var data: MutableList<SalarySlipData>,
    var salarySlipFragment: SalarySlipFragment
) :
    Adapter<SalarySlipViewHolder>() {

    fun filtering(newFilteredList: ArrayList<SalarySlipData>)
    {
        data = newFilteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalarySlipViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.salary_slip_table_item_view, parent, false)
        return SalarySlipViewHolder(view)
    }

    override fun onBindViewHolder(holder: SalarySlipViewHolder, position: Int) {
        val item = data[position]
        holder.emp_name.text = item.emp_name + "\n" + item.rank
        holder.slip_date.text = item.rank + "\n" + "${salarySlipFragment.selectedMonth}-${salarySlipFragment.selectedYear}"
        holder.download.setOnClickListener {
           salarySlipFragment.handlePermissionsAndSavePdf(item.emp_name, item.emp_code)
        }
    }

    override fun getItemCount() = data.size

}

class SalarySlipViewHolder(itemView: View) : ViewHolder(itemView) {
    val emp_name: TextView = itemView.findViewById(R.id.textView233)
    val slip_date: TextView = itemView.findViewById(R.id.textView232)
    val download: FloatingActionButton = itemView.findViewById(R.id.download)
}


