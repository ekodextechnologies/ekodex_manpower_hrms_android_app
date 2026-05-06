package com.ekodex.manpowerhrms.Travel_Requests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.Recruitment.RecruitmentFragmentDirections


class TravelRequestsAdapter(var data: List<TravelRequestsData>) :
    Adapter<TravelRequestsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelRequestsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.travel_request_item_view, parent, false)
        return TravelRequestsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TravelRequestsViewHolder, position: Int) {

        val item = data[position]
        holder.fromdate.text = item.from
        //"₹" +
        holder.todate.text = item.to
        holder.destination.text = item.destination

        holder.layout.setOnClickListener {
            it.findNavController().navigate(RecruitmentFragmentDirections.actionRecruitmentFragmentToEditJobPostFragment(item.id))
        }

    }

    override fun getItemCount() = data.size

}

class TravelRequestsViewHolder(itemView: View) : ViewHolder(itemView) {
    val fromdate: TextView = itemView.findViewById(R.id.textView86)
    val todate: TextView = itemView.findViewById(R.id.textView87)
    val destination: TextView = itemView.findViewById(R.id.textView90)
    val layout: CardView = itemView.findViewById(R.id.post)
}



