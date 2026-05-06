package com.ekodex.manpowerhrms

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
import com.ekodex.manpowerhrms.databinding.FragmentVouchersBinding
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class VouchersFragment : Fragment() {

    lateinit var binding: FragmentVouchersBinding
    lateinit var adapter:VoucherAdapter
    var vouchersList = mutableListOf<VoucherData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vouchers, container, false)

        var i1 = Internet()

        if(i1.checkConnection(requireContext()))
        {
            getVouchers()
        }
        else
        {
            Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
            binding.voucherList.visibility = View.INVISIBLE
            binding.notAvailable1.visibility = View.GONE
            binding.noInternet.visibility = View.VISIBLE
        }

        return binding.root
    }

    private fun getVouchers() {

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_ALL_VOUCHERS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")


                        if (array.length() <= 0) {
                            Toast.makeText(requireContext(),"No vouchers!!",Toast.LENGTH_SHORT).show()
                            binding.notAvailable1.visibility = View.VISIBLE
                            binding.voucherList.visibility = View.INVISIBLE
                            binding.progressBar2.visibility = View.GONE

                        }
                        else
                        {
                            vouchersList.clear()
                            binding.textView77.text = "Total ${array.length()} Results"
                            binding.notAvailable1.visibility = View.GONE
                            binding.progressBar2.visibility = View.GONE
                            binding.voucherList.visibility = View.VISIBLE

                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)

                                val banners = VoucherData(
                                    objectArtist.optString("id"),
                                    objectArtist.optString("date"),
                                    objectArtist.optString("type"),
                                    objectArtist.optString("mode"),
                                    objectArtist.optString("status"),
                                    objectArtist.optString("vendor"),
                                    objectArtist.optString("approved_by"),
                                    objectArtist.optString("rejected_by"),
                                    objectArtist.optString("created_by"),
                                    objectArtist.optString("created_on"),
                                    objectArtist.optString("approved_on"),
                                    objectArtist.optString("rejected_on"),
                                    objectArtist.optString("paid_by"),
                                    objectArtist.optString("paid_on"),
                                    objectArtist.optString("emp_name"),
                                    objectArtist.optString("voucher_no"),
                                    objectArtist.optString("client"),
                                    objectArtist.optString("site"),
                                    objectArtist.optString("gang"),
                                    objectArtist.optString("client_id"),
                                    objectArtist.optString("site_id"),
                                    objectArtist.optString("particular"),
                                    objectArtist.optString("amount"),
                                    objectArtist.optString("utr_no")
                                )
                                vouchersList.add(banners)
                                //searchLists = attendanceList as ArrayList<SupervisorAttendanceData>
                                adapter = VoucherAdapter(vouchersList,requireActivity())
                                binding.voucherList.adapter = adapter
                                binding.voucherList.visibility = View.VISIBLE
                            }
                        }
                    }
                    else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        binding.notAvailable1.visibility = View.VISIBLE
                        binding.voucherList.visibility = View.INVISIBLE
                        binding.progressBar2.visibility = View.GONE

                    }

                } catch (e: JSONException) {
                    
                    binding.notAvailable1.visibility = View.VISIBLE
                    binding.voucherList.visibility = View.INVISIBLE
                    binding.progressBar2.visibility = View.GONE

                }

            },
            Response.ErrorListener { error ->
                
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["created_by"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.id

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

}