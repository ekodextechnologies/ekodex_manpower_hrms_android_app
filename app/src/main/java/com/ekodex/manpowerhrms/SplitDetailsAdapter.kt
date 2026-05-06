package com.ekodex.manpowerhrms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.*

class SplitDetailsAdapter(var data: MutableList<SplitDetailsData>) :
    Adapter<SplitDetailsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SplitDetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.split_details_item_view, parent, false)
        return SplitDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SplitDetailsViewHolder, position: Int) {
        val item = data[position]
        holder.name.text = item.name
        holder.amt.text = "₹" + item.amt

    }

    override fun getItemCount() = data.size
}

class SplitDetailsViewHolder(itemView: View) : ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.textView426)
    val amt: TextView = itemView.findViewById(R.id.textView440)
}



