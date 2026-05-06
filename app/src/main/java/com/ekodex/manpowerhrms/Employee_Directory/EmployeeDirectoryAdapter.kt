package com.ekodex.manpowerhrms.Employee_Directory

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.R
import org.json.JSONException
import org.json.JSONObject

class EmployeeDirectoryAdapter(var data: List<EmployeeDirectoryData>) :
    Adapter<EmployeeDirectoryViewHolder>() {

    fun filtering(newFilteredList: ArrayList<EmployeeDirectoryData>)
    {
        data = newFilteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeDirectoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.employee_details_item_view, parent, false)
        return EmployeeDirectoryViewHolder(view)

    }

    override fun onBindViewHolder(holder: EmployeeDirectoryViewHolder, position: Int) {
        val item = data[position]

        //Left -> Working status update
        if(item.workingStatus == "Left")
        {
            holder.reapprove.visibility = View.VISIBLE
        }

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
               it.findNavController().navigate(EmployeeDirectoryFragmentDirections.actionEmployeeDirectoryFragmentToEmployeeDetailsFragment(item.id))
          // }
        }

        holder.reapprove.setOnClickListener {it
            AlertDialog.Builder(it.context)
                .setTitle("Reactivate employee")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes") { dialog, _ ->
                    updateJobLeftStatus(holder,item)
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    // No clicked
                    dialog.dismiss()
                }
                .create()
                .show()
        }


    }

    override fun getItemCount() = data.size

    private fun updateJobLeftStatus(
        holder: EmployeeDirectoryViewHolder,
        item: EmployeeDirectoryData
    ) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_UPDATE_JOB_LEFT_STATUS_INDIVIDUAL,
            Response.Listener
            { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                            Toast.makeText(
                                holder.reapprove.context.applicationContext,
                                obj.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()
                        holder.reapprove.visibility = View.GONE

                    }
                    else {
                        Toast.makeText(
                            holder.reapprove.context.applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    
                }
            },
            Response.ErrorListener { error ->
                if (error.message != null) {
                    Toast.makeText(
                        holder.reapprove.context.applicationContext,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()

                params["id"] = item.id
                params["status"] = "Working"
                return params
            }
        }

        VolleySingleton.getInstance(holder.reapprove.context.applicationContext)
            .addToRequestQueue(stringRequest)

    }


}

class EmployeeDirectoryViewHolder(itemView: View) : ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.textView48)
    val rank: TextView = itemView.findViewById(R.id.textView49)
    val reapprove: ImageView = itemView.findViewById(R.id.imageView57)
    val call: ImageView = itemView.findViewById(R.id.imageView35)
    val layout: CardView = itemView.findViewById(R.id.emp_card)
    val profileImg: ImageView = itemView.findViewById(R.id.imageView34)
}


