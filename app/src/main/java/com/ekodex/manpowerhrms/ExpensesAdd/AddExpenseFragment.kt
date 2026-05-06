package com.ekodex.manpowerhrms.ExpensesAdd

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.databinding.FragmentAddExpenseBinding
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class AddExpenseFragment : Fragment() {

    lateinit var binding: FragmentAddExpenseBinding
    lateinit var advDate: String
    lateinit var from_flag: String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_expense, container, false)

        advDate = ""
        from_flag = "false"

        var i1 = Internet()

        // date clicklistner
        val calender1 = Calendar.getInstance()

        val datePicker1 =
            DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                calender1.set(Calendar.YEAR, year)
                calender1.set(Calendar.MONTH, month)
                calender1.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateFromDateLable(calender1)
            }


        binding.button4.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                datePicker1,
                calender1.get(Calendar.YEAR),
                calender1.get(Calendar.MONTH),
                calender1.get(Calendar.DAY_OF_MONTH)
            )
                .show()
            from_flag = "true"
        }

        binding.button6.setOnClickListener {
            if(i1.checkConnection(requireContext()))
            {
                addExpense()
            }
            else
            {
                Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
            }
        }


        return binding.root

    }

    private fun addExpense() {

        var amount = binding.editTextTextPersonName58.text.toString()
        var remark =  binding.editTextTextPersonName57.text.toString()

        if (TextUtils.isEmpty(amount)) {
            binding.editTextTextPersonName58.error = "Please enter expense amount!!"
            binding.editTextTextPersonName58.requestFocus()
            return
        }

        if (TextUtils.isEmpty(remark)) {
            binding.editTextTextPersonName57.error = "Please enter expense remark"
            binding.editTextTextPersonName57.requestFocus()
            return
        }

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_ADD_EXPENSE,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()

                        findNavController().navigate(AddExpenseFragmentDirections.actionAddExpenseFragmentToExpensesFragment())

                    }
                    else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    
                }
            },
            Response.ErrorListener { 
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()

                params["user_id"] = SharedPrefManager.getInstance(requireContext()).user.id
                params["emp_code"] = SharedPrefManager.getInstance(requireContext()).user.emp_code
                params["amount"] = amount
                params["particular"] = remark
                params["voucher_no"] = ((11111..99999).random()).toString()
                params["adv_date"] = advDate

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }


    //date filter functions
    private fun updateFromDateLable(calener: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
// Here we can use calendar selected date
        binding.button4.setText((sdf.format(calener.time)))
        val myFormat2 = "yyyy-MM-dd"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.UK)
        advDate = (sdf2.format(calener.time))
        //Log.i("oooooooooooooooooo", fromDate)
    }

}