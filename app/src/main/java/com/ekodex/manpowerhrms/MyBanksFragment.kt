package com.ekodex.manpowerhrms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Employee_Directory.BanksAdapter
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentMyBanksBinding
import org.json.JSONException
import org.json.JSONObject

class MyBanksFragment : Fragment() {

    lateinit var binding: FragmentMyBanksBinding
    var banks = mutableListOf<BankData>()
    var bankAdapter: BanksAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_my_banks,
            container,
            false
        )

        if (bankAdapter == null) {
            bankAdapter = BanksAdapter(banks)
        }
        binding.banksList.adapter = bankAdapter

        var i1 = Internet()

        if(i1.checkConnection(requireContext()))
        {
            getBankDetails()
        }
        else
        {
            Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
        }

        binding.addBank.setOnClickListener {
            findNavController().navigate(MyBanksFragmentDirections.actionMyBanksFragmentToAddNewBanksFragment())
        }

        return binding.root
    }

    private fun getBankDetails() {
        banks.clear()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_PARTICULAR_USER_BANKS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            binding.textView216.visibility = View.GONE
                            binding.banksList.visibility = View.INVISIBLE
                            binding.notAvailable1.visibility = View.VISIBLE
                        }
                        else{
                            binding.notAvailable1.visibility = View.GONE
                            binding.banksList.visibility = View.VISIBLE
                            binding.textView216.text = "Total ${array.length()} results"
                            for (i in 0..(array.length() - 1)) {
                                val objectArtist = array.getJSONObject(i)
                                banks.add(BankData(objectArtist.getString("id"),objectArtist.getString("bank_name"),objectArtist.getString("account_number"),objectArtist.getString("bank_ifsc"),objectArtist.getString("bank_address"),objectArtist.getString("bank_state"),objectArtist.getString("bank_city"),objectArtist.getString("bank_micr"),objectArtist.getString("card_no"),objectArtist.getString("ac_holder_name")))
                            }
                            bankAdapter?.notifyDataSetChanged()
                        }
                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        binding.textView216.visibility = View.GONE
                        binding.banksList.visibility = View.INVISIBLE
                        binding.notAvailable1.visibility = View.VISIBLE
                    }

                } catch (e: JSONException) {
                    
                    binding.textView216.visibility = View.GONE
                    binding.banksList.visibility = View.INVISIBLE
                    binding.notAvailable1.visibility = View.VISIBLE

                }

            },
            Response.ErrorListener { error ->
                
                binding.textView216.visibility = View.GONE
                binding.banksList.visibility = View.INVISIBLE
                binding.notAvailable1.visibility = View.VISIBLE
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

//                params["client_id"] = company_id
//                params["site_id"] = site_id
                params["user_id"] = SharedPrefManager.getInstance(requireContext()).user.id
                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

}