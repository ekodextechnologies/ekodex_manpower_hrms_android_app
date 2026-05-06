package com.ekodex.manpowerhrms.Expenses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.databinding.FragmentExpensesBinding
import org.json.JSONException
import org.json.JSONObject

class ExpensesFragment : Fragment() {

    lateinit var binding: FragmentExpensesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_expenses, container, false)


        var i1 = Internet()

        if(i1.checkConnection(requireContext()))
        {
            getExpenses()
        }
        else
        {
            Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
            binding.expensesList.visibility = View.INVISIBLE
            binding.notAvailable1.visibility = View.GONE
            binding.noInternet.visibility = View.VISIBLE
        }

        binding.addExpense.setOnClickListener {
            it.findNavController().navigate(ExpensesFragmentDirections.actionExpensesFragmentToAddExpenseFragment())
        }


        return binding.root
    }

    private fun getExpenses() {
        var adapter: ExpenseAdapter
        var expenseList = mutableListOf<ExpenseData>()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_EXPENSES,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        expenseList.clear()

                        if (array.length() <= 0) {
                            //Toast.makeText(requireContext(),"No sales for this date!!",Toast.LENGTH_SHORT).show()
                            binding.notAvailable1.visibility = View.VISIBLE
                            binding.expensesList.visibility = View.INVISIBLE
                            binding.progressBar2.visibility = View.GONE
                            binding.textView77.text = "Total results 0"
                        }
                        else{

                            binding.progressBar2.visibility = View.GONE
                            binding.textView77.text = "Total results ${array.length()}"

                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)

                                val banners = ExpenseData(
                                    objectArtist.optString("amount"),
                                    objectArtist.optString("remark"),
                                    objectArtist.optString("date")
                                )
                                expenseList.add(banners)
                                adapter = ExpenseAdapter(expenseList)
                                binding.expensesList.adapter = adapter
                                binding.expensesList.visibility = View.VISIBLE
                                binding.notAvailable1.visibility = View.GONE
                            }
                        }
                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()

                        binding.progressBar2.visibility = View.GONE
                        binding.notAvailable1.visibility = View.VISIBLE
                        binding.expensesList.visibility = View.INVISIBLE
                        binding.textView77.text = "Total results 0"

                    }

                } catch (e: JSONException) {
                    
                    binding.notAvailable1.visibility = View.VISIBLE
                    binding.progressBar2.visibility = View.GONE
                    binding.expensesList.visibility = View.INVISIBLE
                    binding.textView77.text = "Total results 0"
                }

            },
            Response.ErrorListener { error ->
                
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["user_id"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.id

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }


}