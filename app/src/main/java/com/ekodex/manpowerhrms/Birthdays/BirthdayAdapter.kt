package com.ekodex.manpowerhrms.Birthdays

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ekodex.manpowerhrms.R


class BirthdayAdapter(var data: List<BirthdayData>) :
    Adapter<BirthdayViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirthdayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.birthday_item_view, parent, false)
        return BirthdayViewHolder(view)

    }

    override fun onBindViewHolder(holder: BirthdayViewHolder, position: Int) {

        val item = data[position]
        holder.name.text = item.name
        holder.date.text = item.date

    }

    override fun getItemCount() = data.size

}

class BirthdayViewHolder(itemView: View) : ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.textView48)
    val date: TextView = itemView.findViewById(R.id.textView49)
}


