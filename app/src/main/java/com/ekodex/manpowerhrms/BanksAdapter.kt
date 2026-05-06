package com.ekodex.manpowerhrms.Employee_Directory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.BankData
import com.ekodex.manpowerhrms.MyBanksFragmentDirections
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class BanksAdapter(var data: List<BankData>) :
    Adapter<BanksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BanksViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.bank_item_view, parent, false)
        return BanksViewHolder(view)

    }

    override fun onBindViewHolder(holder: BanksViewHolder, position: Int) {
        val item = data[position]

        holder.name.text = item.bank_name
        holder.acc_no.text = item.acc_no

        //-------------------------------------------------------------------------------

        //edit bank
        holder.edit.setOnClickListener {
            holder.edit.findNavController()
                .navigate(MyBanksFragmentDirections.actionMyBanksFragmentToUpdateMyBankFragment(item.id))
        }

        //delete bank
        holder.delete.setOnClickListener {it
            AlertDialog.Builder(it.context)
                .setTitle("Delete Bank")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes") { dialog, _ ->
                    deleteBank(holder, item)
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

    private fun deleteBank(holder: BanksViewHolder, item: BankData) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_DELETE_PARTICULAR_BANK,
            Response.Listener { response ->
                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(
                            holder.name.context.applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        holder.name.findNavController()
                            .navigate(MyBanksFragmentDirections.actionToMyBanksFragment())
                    } else {
                        Toast.makeText(
                            holder.name.context.applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    
                }
            },
            Response.ErrorListener { error ->
                if (error.message != null) {
                    Toast.makeText(holder.name.context.applicationContext, error.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id"] = item.id
                return params
            }
        }

        VolleySingleton.getInstance(holder.name.context.applicationContext)
            .addToRequestQueue(stringRequest)
    }

}

class BanksViewHolder(itemView: View) : ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.textView457)
    val acc_no: TextView = itemView.findViewById(R.id.textView458)
    val edit: FloatingActionButton = itemView.findViewById(R.id.edit)
    val delete: FloatingActionButton = itemView.findViewById(R.id.delete)

}


