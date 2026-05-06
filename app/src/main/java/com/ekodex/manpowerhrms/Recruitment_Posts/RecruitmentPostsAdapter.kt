package com.ekodex.manpowerhrms.Recruitment_Posts

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


class RecruitmentPostsAdapter(var data: List<RecruitmentPostsData>) :
    Adapter<RecruitmentPostsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitmentPostsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recruitment_post_item_view, parent, false)
        return RecruitmentPostsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecruitmentPostsViewHolder, position: Int) {

        val item = data[position]
        holder.post_name.text = item.post_name
        //"₹" +
        holder.salary.text = item.salary + " Per month"
        holder.experience.text = item.experience
        holder.address.text = item.address
        holder.type.text = item.type
        holder.org_name.text = item.organization
        holder.recruited_count.text = item.recruited_count + "/" + item.total_post__count

        holder.layout.setOnClickListener {
            it.findNavController().navigate(RecruitmentFragmentDirections.actionRecruitmentFragmentToEditJobPostFragment(item.id))
        }

    }

    override fun getItemCount() = data.size

}

class RecruitmentPostsViewHolder(itemView: View) : ViewHolder(itemView) {
    val post_name: TextView = itemView.findViewById(R.id.textView85)
    val salary: TextView = itemView.findViewById(R.id.textView86)
    val address: TextView = itemView.findViewById(R.id.textView87)
    val type: TextView = itemView.findViewById(R.id.textView88)
    val experience: TextView = itemView.findViewById(R.id.textView90)
    val org_name: TextView = itemView.findViewById(R.id.textView89)
    val recruited_count: TextView = itemView.findViewById(R.id.textView91)
    val layout: CardView = itemView.findViewById(R.id.post)

}



