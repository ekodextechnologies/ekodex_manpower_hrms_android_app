package com.ekodex.manpowerhrms.Holidays

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ekodex.manpowerhrms.R


class HolidayAdapter(var data: List<HolidayData>) :
    Adapter<HolidayViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolidayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.holidays_item_view, parent, false)
        return HolidayViewHolder(view)

    }

    override fun onBindViewHolder(holder: HolidayViewHolder, position: Int) {
        val item = data[position]
        holder.name.text = item.name
        holder.date.text = item.date

        if(item.status == "over")
        {
            holder.status.setImageResource(R.drawable.done_tick)
        }
        else if(item.status == "upcoming")
        {
            holder.status.setImageResource(R.drawable.red_circle)
        }
        //-------------------------------------------------------------------------------

        //call employee
//        holder.call.setOnClickListener {
//            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", item.phone, null))
//            startActivity(it.context,intent,null)
//        }

    }

    override fun getItemCount() = data.size

}

class HolidayViewHolder(itemView: View) : ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.textView48)
    val date: TextView = itemView.findViewById(R.id.textView49)
    val status: ImageView = itemView.findViewById(R.id.imageView36)

}


