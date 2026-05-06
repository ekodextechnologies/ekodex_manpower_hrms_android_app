package com.ekodex.manpowerhrms.Events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ekodex.manpowerhrms.R


class EventsAdapter(var data: List<EventsData>) :
    Adapter<EventsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.events_item_view, parent, false)
        return EventsViewHolder(view)

    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {

        val item = data[position]
        holder.name.text = item.name
        holder.date.text = item.from + " To " + item.to
        holder.description.text = "(" + item.description + ")"
        holder.venue.text = "Venue : " + item.venue
    }

    override fun getItemCount() = data.size

}

class EventsViewHolder(itemView: View) : ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.textView48)
    val date: TextView = itemView.findViewById(R.id.textView49)
    val description: TextView = itemView.findViewById(R.id.textView63)
    val venue: TextView = itemView.findViewById(R.id.textView64)

}


