package com.ekodex.manpowerhrms

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ekodex.manpowerhrms.Employee_Directory.EmployeeDirectoryData
import com.ekodex.manpowerhrms.KycSummaryManagementFragmentDirections
import com.ekodex.manpowerhrms.R

class KycSummaryAdapter(var data: List<EmployeeDirectoryData>) :
    Adapter<KycSummaryViewHolder>() {

    fun filtering(newFilteredList: ArrayList<EmployeeDirectoryData>)
    {
        data = newFilteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KycSummaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.employee_details_item_view, parent, false)
        return KycSummaryViewHolder(view)

    }

    override fun onBindViewHolder(holder: KycSummaryViewHolder, position: Int) {
        val item = data[position]
        holder.name.text = item.name
        holder.rank.text = item.rank
        if(item.gender == "Male")
        {
            holder. profileImg.setImageResource(R.drawable.man)
        }
        else if (item.gender == "Female")
        {
            holder. profileImg.setImageResource(R.drawable.woman)
        }

        if(item.phone == "" || item.phone==null || item.phone == "null" || item.phone.length<10)
        {
            holder.call.visibility = View.GONE
        }
        //-------------------------------------------------------------------------------

        //call employee
        holder.call.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", item.phone, null))
            startActivity(it.context,intent,null)
        }

        //emp info(only for supervisor)
        holder.layout.setOnClickListener {
            // if(SharedPrefManager.getInstance(holder.layout.context.applicationContext).user.role == "Supervisor")
            // {
            it.findNavController().navigate(KycSummaryManagementFragmentDirections.actionKycSummaryManagementFragmentToEmployeeDetailsFragment(item.id))
            // }
        }

    }

    override fun getItemCount() = data.size

}

class KycSummaryViewHolder(itemView: View) : ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.textView48)
    val rank: TextView = itemView.findViewById(R.id.textView49)
    val call: ImageView = itemView.findViewById(R.id.imageView35)
    val layout: CardView = itemView.findViewById(R.id.emp_card)
    val profileImg: ImageView = itemView.findViewById(R.id.imageView34)
}


