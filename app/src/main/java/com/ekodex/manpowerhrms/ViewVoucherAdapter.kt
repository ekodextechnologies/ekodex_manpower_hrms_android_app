package com.ekodex.manpowerhrms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ViewVoucherAdapter(var data: MutableList<VoucherData>) :
    Adapter<ViewVoucherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewVoucherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_voucher_item_view, parent, false)
        return ViewVoucherViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewVoucherViewHolder, position: Int) {
        val item = data[position]
        holder.name.text = item.name
        holder.type.text = item.type
        holder.mode.text = item.mode
        holder.date.text = item.date
        holder.particular.text = item.particular

        if(item.status == "Pending")
        {
            holder.status.visibility = View.GONE
        }
        else if(item.status == "Approved")
        {
            holder.status.text = "Approved By: ${item.approved_by}"
        }
        else if(item.status == "Rejected")
        {
            holder.status.text = "Rejected By: ${item.rejected_by}"
        }

        holder.amount.text = "Amount: ${item.amt}"

    }

    override fun getItemCount() = data.size
}

class ViewVoucherViewHolder(itemView: View) : ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.textView346)
    val type: TextView = itemView.findViewById(R.id.textView347)
    val mode: TextView = itemView.findViewById(R.id.textView348)
    val date: TextView = itemView.findViewById(R.id.textView349)
    val particular: TextView = itemView.findViewById(R.id.textView350)
    val status: TextView = itemView.findViewById(R.id.textView345)
    val amount: TextView = itemView.findViewById(R.id.textView351)
    //val layout: CardView = itemView.findViewById(R.id.voucherCard)
}


