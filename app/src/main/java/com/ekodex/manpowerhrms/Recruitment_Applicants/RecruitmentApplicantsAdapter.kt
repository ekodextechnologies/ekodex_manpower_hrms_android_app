package com.ekodex.manpowerhrms.Recruitment_Applicants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.Recruitment.RecruitmentFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton


class RecruitmentApplicantsAdapter(var data: List<RecruitmentApplicantsData>) :
    Adapter<RecruitmentApplicantsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitmentApplicantsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recruitment_applicant_item_view, parent, false)
        return RecruitmentApplicantsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecruitmentApplicantsViewHolder, position: Int) {

        val item = data[position]
        holder.name.text = item.name
        holder.post.text = item.post
        holder.applied_on.text = item.created_on
        holder.status.text = item.status

        holder.viewResume.setOnClickListener {
            it.findNavController().navigate(RecruitmentFragmentDirections.actionRecruitmentFragmentToRecruitmentApplicantDocumentFragment(item.resumeLink))
        }

    }

    override fun getItemCount() = data.size

}

class RecruitmentApplicantsViewHolder(itemView: View) : ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.textView93)
    val post: TextView = itemView.findViewById(R.id.textView95)
    val applied_on: TextView = itemView.findViewById(R.id.textView96)
    val status: TextView = itemView.findViewById(R.id.textView98)
    val viewResume: FloatingActionButton = itemView.findViewById(R.id.openResume)

}



