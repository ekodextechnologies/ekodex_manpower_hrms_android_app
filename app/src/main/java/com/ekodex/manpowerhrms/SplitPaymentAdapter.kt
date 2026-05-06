package com.ekodex.manpowerhrms

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SplitPaymentAdapter(var data: MutableList<Employee_Data>,
                          val onAmountChanged: () -> Unit   ) :
    RecyclerView.Adapter<SplitPaymentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SplitPaymentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.split_payment_item_view, parent, false)
        return SplitPaymentViewHolder(view)
    }

    override fun onBindViewHolder(holder: SplitPaymentViewHolder, position: Int) {

        val item = data[position]

        // Set the name
        holder.name.text = item.name

        // Remove previous watcher to prevent stacking
        holder.amt.removeTextChangedListener(holder.textWatcher)

        // Set the existing amount (if available)
        holder.amt.setText(item.amt_to_pay)

        // Create a new TextWatcher
        holder.textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val amtString = s?.toString() ?: ""
                if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                    data[holder.adapterPosition].amt_to_pay = amtString
                }
                onAmountChanged()
            }
        }

        // Attach the new listener
        holder.amt.addTextChangedListener(holder.textWatcher)
    }

    override fun getItemCount(): Int = data.size
}

class SplitPaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val name: TextView = itemView.findViewById(R.id.emp_name)
    val amt: EditText = itemView.findViewById(R.id.amt)

    // This stores the active TextWatcher for this row
    var textWatcher: TextWatcher? = null
}
