package com.ekodex.manpowerhrms.Birthdays

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.databinding.FragmentBirthdaysBinding
import org.json.JSONException
import org.json.JSONObject

class BirthdaysFragment : Fragment() {

    lateinit var binding:FragmentBirthdaysBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_birthdays, container, false)

        var i1 = Internet()

        if(i1.checkConnection(requireContext()))
        {
            getBirthdays()
        }
        else
        {
            Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
            binding.birthdayList.visibility = View.INVISIBLE
            binding.notAvailable1.visibility = View.GONE
            binding.noInternet.visibility = View.VISIBLE
        }

        return binding.root

    }

    private fun getBirthdays() {
        var birthdayList = mutableListOf<BirthdayData>()
        var adapter:BirthdayAdapter

        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_BIRTHDAYS,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            //Toast.makeText(requireContext(),"No sales for this date!!",Toast.LENGTH_SHORT).show()
                            binding.notAvailable1.visibility = View.VISIBLE
                            binding.birthdayList.visibility = View.INVISIBLE
                            binding.progressBar2.visibility = View.GONE
                            binding.textView77.text = "Total results 0"
                        }
                        else{

                            binding.progressBar2.visibility = View.GONE
                            binding.textView77.text = "Total results ${array.length()}"

                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)

                                val banners = BirthdayData(
                                    objectArtist.optString("id"),
                                    objectArtist.optString("first_name") + " " + objectArtist.optString("last_name"),
                                    objectArtist.optString("dob")
                                )
                                birthdayList.add(banners)
                                // searchList = empDirectoryList as ArrayList<EmployeeDirectoryData>
                                adapter = BirthdayAdapter(birthdayList)
                                binding.birthdayList.adapter = adapter
                                binding.birthdayList.visibility = View.VISIBLE
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
                        binding.birthdayList.visibility = View.INVISIBLE
                        binding.textView77.text = "Total results 0"
                    }
                } catch (e: JSONException) {
                   // 
                    binding.notAvailable1.visibility = View.VISIBLE
                    binding.progressBar2.visibility = View.GONE
                    binding.textView77.text = "Total results 0"
                }
            },
            { volleyError ->
//                Toast.makeText(
//                    requireContext(),
//                    volleyError.message,
//                    Toast.LENGTH_LONG
//                ).show()
            })

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)
    }

}