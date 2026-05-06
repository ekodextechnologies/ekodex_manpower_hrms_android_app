package com.ekodex.manpowerhrms

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DateFilterDialog : DialogFragment() {

    interface DateFilterCallback {
        fun onDateFilterSelected(label: String, startDate: String, endDate: String)
    }

    var callback: DateFilterCallback? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        // Inflate the layout
        val view = LayoutInflater.from(context).inflate(R.layout.date_filter_layout, null)

        val today = view.findViewById<TextView>(R.id.textView127)
        val yesterday = view.findViewById<TextView>(R.id.textView140)
        val last_week = view.findViewById<TextView>(R.id.textView150)
        val last_month = view.findViewById<TextView>(R.id.textView151)
        val custom_date = view.findViewById<TextView>(R.id.textView152)

        //--------------------------------------------------------------------------------------------------------
        today.setOnClickListener {
            callback?.onDateFilterSelected("Today", getTodayDateRange().first, getTodayDateRange().second)

            dismiss()
        }

        yesterday.setOnClickListener {
            callback?.onDateFilterSelected("Yesterday", getYesterdayDateRange().first, getYesterdayDateRange().second)
            dismiss()
        }

        last_week.setOnClickListener {
            callback?.onDateFilterSelected("Last Week", getLastWeekDateRange().first, getLastWeekDateRange().second)
            Log.i("11111","weekly ${getLastWeekDateRange().first}  -- ${getLastWeekDateRange().second}")
            dismiss()
        }

        last_month.setOnClickListener {
            callback?.onDateFilterSelected("Last Month", getLastMonthDateRange().first, getLastMonthDateRange().second)
            Log.i("11111","monthly ${getLastMonthDateRange().first}  -- ${getLastMonthDateRange().second}")
            dismiss()
        }

        custom_date.setOnClickListener {

            val datePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTheme(R.style.CustomDatePickerTheme)
                .setTitleText("Select date range")
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                val startDate = selection.first
                val endDate = selection.second
                val diffInDays = TimeUnit.MILLISECONDS.toDays(endDate - startDate) + 1

                if (diffInDays > 30) {
                    // Show toast if selection > 30 days
                    activity?.let {
                        Toast.makeText(it, "Please select a maximum of 30 days", Toast.LENGTH_LONG).show()
                    }
                    // Do NOT dismiss the dialog, let user pick again
                    return@addOnPositiveButtonClickListener
                }

                // Format dates
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val startDateFormatted = sdf.format(Date(startDate))
                val endDateFormatted = sdf.format(Date(endDate))

                // Pass the selected dates to your callback
                callback?.onDateFilterSelected(
                    "$startDateFormatted TO $endDateFormatted",
                    startDateFormatted,
                    endDateFormatted
                )

                // Only now dismiss the custom dialog
                dismiss()
            }

            datePicker.show(requireActivity().supportFragmentManager, datePicker.toString())
        }


    // close the dialog
        view.findViewById<ImageView>(R.id.imageView33).setOnClickListener {
            dismiss()
        }

        // Return a full-screen dialog
        dialog.setContentView(view)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return dialog
    }

private fun getTodayDateRange(): Pair<String, String> {
    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    return Pair(today, today)
}

private fun getYesterdayDateRange(): Pair<String, String> {
    val cal = Calendar.getInstance()
    cal.add(Calendar.DAY_OF_YEAR, -1)
    val yesterday = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)
    return Pair(yesterday, yesterday)
}

private fun getLastWeekDateRange(): Pair<String, String> {
    val cal = Calendar.getInstance()
    cal.add(Calendar.DAY_OF_YEAR, -7)
    val start = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)
    val end = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    return Pair(start, end)
}

private fun getLastMonthDateRange(): Pair<String, String> {
    val cal = Calendar.getInstance()

    // Set the calendar to the first day of the previous month
    cal.add(Calendar.MONTH, -1)
    cal.set(Calendar.DAY_OF_MONTH, 1)
    val start = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)

    // Set the calendar to the last day of the previous month
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
    val end = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)

    return Pair(start, end)
}
}
