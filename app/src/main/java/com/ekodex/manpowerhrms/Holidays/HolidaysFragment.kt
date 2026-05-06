package com.ekodex.manpowerhrms.Holidays

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.databinding.FragmentHolidaysBinding
import org.json.JSONException
import org.json.JSONObject


class HolidaysFragment : Fragment() {

    lateinit var binding:FragmentHolidaysBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_holidays, container, false)

        var i1 = Internet()

        if(i1.checkConnection(requireContext()))
        {
            getHolidays()
        }
        else
        {
            Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
            binding.holidaysList.visibility = View.INVISIBLE
            binding.notAvailable1.visibility = View.GONE
            binding.noInternet.visibility = View.VISIBLE
        }

        return  binding.root

    }

    private fun getHolidays() {
        var holidayList = mutableListOf<HolidayData>()
        var adapter:HolidayAdapter

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_HOLIDAYS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        // empDirectoryList.clear()

                        if (array.length() <= 0) {
                            //Toast.makeText(requireContext(),"No sales for this date!!",Toast.LENGTH_SHORT).show()
                            binding.notAvailable1.visibility = View.VISIBLE
                            binding.holidaysList.visibility = View.INVISIBLE
                            binding.progressBar2.visibility = View.GONE
                            binding.textView77.text = "Total results 0"
                        }
                        else{
                            binding.progressBar2.visibility = View.GONE
                            binding.textView77.text = "Total results ${array.length()}"

                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)

                                val banners = HolidayData(
                                    objectArtist.optString("id"),
                                    objectArtist.optString("name"),
                                    objectArtist.optString("date"),
                                    objectArtist.optString("status")
                                )
                                holidayList.add(banners)
                                // searchList = empDirectoryList as ArrayList<EmployeeDirectoryData>
                                adapter = HolidayAdapter(holidayList)
                                binding.holidaysList.adapter = adapter
                                binding.holidaysList.visibility = View.VISIBLE
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
                        binding.textView77.text = "Total results 0"

                    }

                } catch (e: JSONException) {
                    
                    binding.notAvailable1.visibility = View.VISIBLE
                    binding.progressBar2.visibility = View.GONE
                    binding.textView77.text = "Total results 0"

                }

            },
            Response.ErrorListener { error ->
                
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["state"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.state

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }


}