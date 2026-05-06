package com.ekodex.manpowerhrms

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Employee_Directory.EmployeeDirectoryData
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentKycNotDoneBinding
import org.json.JSONException
import org.json.JSONObject

class KycNotDoneFragment : Fragment() {

    lateinit var binding: FragmentKycNotDoneBinding
    var adapter:KycSummaryAdapter? = null
    var empList = mutableListOf<EmployeeDirectoryData>()
    var type: String = ""

    //pagination
    private var isLoading = false
    private var offset = 0
    private val limit = 50
    private var totalRows = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_kyc_not_done, container, false)

        if (adapter == null) {
            adapter = KycSummaryAdapter(empList)
        }
        binding.empList.adapter = adapter

        binding.empList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) return // Only trigger on scroll down

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                // Trigger pagination if last visible item is near the end and more data exists
                if (!isLoading && lastVisibleItem >= empList.size - 5 && empList.size < totalRows) {
                    offset += limit
                    getKycSummary() // Fetch next page
                }
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Restore or fetch argument safely
        type = when {
            savedInstanceState != null -> savedInstanceState.getString("type", "dummy")
            arguments != null -> arguments?.getString("type", "dummy") ?: "dummy"
            else -> "dummy"
        }

        Log.i("11111", "Type received: $type")

        // Check internet and proceed
        if (Internet().checkConnection(requireContext())) {
            getKycSummary()
        } else {
            Toast.makeText(requireContext(), "No internet connection!!", Toast.LENGTH_LONG).show()
            binding.empList.visibility = View.INVISIBLE
            binding.notAvailable1.visibility = View.GONE
            binding.noInternet.visibility = View.VISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("type", type) // ✅ No need for check
        Log.i("11111", "saved state - $type")
    }

    private fun getKycSummary() {
        Log.i("11111","type - ${type}")
        if (isLoading) return
        isLoading = true
        Log.i("11111", "Offset - ${offset}, Limit - ${limit}")
        binding.progressBar2.visibility = View.GONE

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_KYC_SUMMARY_EMPLOYEES,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")
                        totalRows = obj.optInt("total_rows", 0)
                        binding.textView77.text = "Total ${totalRows} Results"


                        if (array.length() <= 0) {
                            Toast.makeText(requireContext(),"No vouchers!!", Toast.LENGTH_SHORT).show()
                            binding.notAvailable1.visibility = View.VISIBLE
                            binding.empList.visibility = View.INVISIBLE
                            binding.progressBar2.visibility = View.GONE

                        }
                        else
                        {
                            binding.notAvailable1.visibility = View.GONE
                            binding.progressBar2.visibility = View.GONE
                            binding.empList.visibility = View.VISIBLE

                            for (i in (array.length() - 1) downTo 0) {
                                val item = array.getJSONObject(i)

                                val banners = EmployeeDirectoryData(
                                    item.optString("id"),
                                    item.optString("empcode"),
                                    item.optString("first_name") + " " + item.optString("last_name"),
                                    item.optString("rank"),
                                    item.optString("phone"),
                                    item.optString("gender"),
                                    ""
                                )
                                empList.add(banners)
                                //searchLists = attendanceList as ArrayList<SupervisorAttendanceData>
                                // binding.pendingVouchersList.adapter = adapter
                                binding.empList.visibility = View.VISIBLE
                            }
                            adapter?.notifyDataSetChanged()
                        }
                    }
                    else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        binding.notAvailable1.visibility = View.VISIBLE
                        binding.empList.visibility = View.INVISIBLE
                        binding.progressBar2.visibility = View.GONE

                    }

                } catch (e: JSONException) {
                    
                    binding.notAvailable1.visibility = View.VISIBLE
                    binding.empList.visibility = View.INVISIBLE
                    binding.progressBar2.visibility = View.GONE
                }
                isLoading = false


            },
            Response.ErrorListener { error ->
                
                isLoading = false

            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["type"] = type
                params["status"] = "not done"
                params["client_id"] = SharedPrefManager.getInstance(requireActivity().applicationContext).user.client_id
                params["site_id"] = SharedPrefManager.getInstance(requireActivity().applicationContext).user.site_id
                params["limit"] = limit.toString()
                params["offset"] = offset.toString()
                params["keyword"] = ""
                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

}