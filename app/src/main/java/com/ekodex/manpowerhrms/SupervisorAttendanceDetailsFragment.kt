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
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentSupervisorAttendancBinding
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class SupervisorAttendanceDetailsFragment : Fragment() {

    lateinit var binding:FragmentSupervisorAttendancBinding
    var adapter:SupervisorAttendanceAdapter? = null
    var attendanceList = mutableListOf<SupervisorAttendanceData>()

    //employee code
    var emp_code = ""
    var status = ""

    //pagination
    private var isLoading = false
    private var offset = 0
    private val limit = 50
    private var totalRows = 0

//    lateinit var searchView: SearchView
//    var searchLists =  arrayListOf<SupervisorAttendanceData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_supervisor_attendanc,
            container,
            false
        )

        var args = SupervisorAttendanceDetailsFragmentArgs.fromBundle(requireArguments())

        if (adapter == null) {
            adapter = SupervisorAttendanceAdapter(attendanceList,requireActivity(),"")
        }
        binding.attendanceList.adapter = adapter


        binding.attendanceList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) return // Only trigger on scroll down

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                Log.i("11111","size - ${attendanceList.size}")
                Log.i("11111","total - ${totalRows}")
                Log.i("11111","isLoading - ${isLoading}")

                // Trigger pagination if last visible item is near the end and more data exists
                if (!isLoading && lastVisibleItem >= attendanceList.size - 5 && attendanceList.size < totalRows) {
                    offset += limit
                    getAttendanceSupervisor(args.fromDate, args.toDate) // Fetch next page
                }
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        var args = SupervisorAttendanceDetailsFragmentArgs.fromBundle(requireArguments())

        var i1 = Internet()
        if (i1.checkConnection(requireContext())) {

            isLoading = false
            offset = 0
            totalRows = 0
            attendanceList.clear()

            if (!args.empCode.isNullOrBlank()) {
                emp_code = args.empCode!!
            }

            if (!args.status.isNullOrBlank()) {
                status = args.status!!
            }


            getAttendanceSupervisor(args.fromDate, args.toDate)
            binding.progressBar6.visibility = View.VISIBLE
            binding.notAvailable1.visibility = View.GONE
        } else {
            Toast.makeText(
                requireContext(),
                "No internet connection!!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun getAttendanceSupervisor(fr: String, too: String) {
        if (isLoading) return
        isLoading = true
        Log.i("11111","Offset - ${offset}, Limit - ${limit}")
        binding.progressBar6.visibility = View.GONE

        var from = fr
        var to = too

        if (from == "" || from.length == 0) {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val currtDate = sdf.format(Date())
            from = currtDate
        }

        if (to == "" || to.length == 0) {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val currtDate = sdf.format(Date())
            to = currtDate
        }

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_SUPERVISOR_ATTENDANCE,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        totalRows = obj.optInt("total_rows", 0)
                        Log.i("11111","total - ${totalRows}")
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            Toast.makeText(requireContext(),"No attendance for this dates!!",Toast.LENGTH_SHORT).show()
                            binding.notAvailable1.visibility = View.VISIBLE
                            binding.attendanceList.visibility = View.INVISIBLE
                            binding.progressBar6.visibility = View.GONE

                        }
                        else
                        {
                            //attendanceList.clear()
                            binding.notAvailable1.visibility = View.GONE
                            binding.progressBar6.visibility = View.GONE
                            binding.attendanceList.visibility = View.VISIBLE

                            for (i in 0 until array.length()) {
                                val objectArtist = array.getJSONObject(i)

                                val banners = SupervisorAttendanceData(
                                    objectArtist.optString("id"),
                                    objectArtist.optString("emp_name"),
                                    objectArtist.optString("emp_code"),
                                    objectArtist.optString("rank"),
                                    objectArtist.optString("status"),
                                    objectArtist.optString("date"),
                                    objectArtist.optString("created_by"),
                                    objectArtist.optString("check_in"),
                                    objectArtist.optString("check_out")
                                )
                                attendanceList.add(banners)
                              //  searchLists = attendanceList as ArrayList<SupervisorAttendanceData>
                                //  adapter = SupervisorAttendanceAdapter(attendanceList,requireActivity(),"SupervisorAttendanceFrag")
                                //  binding.attendanceList.adapter = adapter
                                binding.attendanceList.visibility = View.VISIBLE
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
                        binding.attendanceList.visibility = View.INVISIBLE
                        binding.progressBar6.visibility = View.GONE

                    }

                } catch (e: JSONException) {
                    
                    binding.notAvailable1.visibility = View.VISIBLE
                    binding.attendanceList.visibility = View.INVISIBLE
                    binding.progressBar6.visibility = View.GONE

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

                params["from_date"] = from
                params["to_date"] = to
                params["site_id"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.site_id
                params["client_code"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.client_code
                params["role"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.role
                params["emp_code"] = emp_code
                params["limit"] = limit.toString()
                params["offset"] = offset.toString()
                params["status"] = status.toString()
                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    /*
        private fun getAttendanceSupervisor(fr: String, too: String) {

            var from = fr
            var to = too

            if (from == "" || from.length == 0) {
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val currtDate = sdf.format(Date())
                from = currtDate
            }

            if (to == "" || to.length == 0) {
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val currtDate = sdf.format(Date())
                to = currtDate
            }

            val stringRequest = object : StringRequest(
                Request.Method.POST, URLs.URL_GET_SUPERVISOR_ATTENDANCE,
                Response.Listener { response ->

                    try {
                        //converting response to json object
                        val obj = JSONObject(response)
                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            val array = obj.getJSONArray("user")


                            if (array.length() <= 0) {
                                Toast.makeText(requireContext(),"No attendance for this dates!!", Toast.LENGTH_SHORT).show()
                                binding.notAvailable1.visibility = View.VISIBLE
                                binding.attendanceList.visibility = View.INVISIBLE
                                binding.progressBar6.visibility = View.GONE

                            }
                            else
                            {
                                attendanceList.clear()
                                binding.notAvailable1.visibility = View.GONE
                                binding.progressBar6.visibility = View.GONE
                                binding.attendanceList.visibility = View.VISIBLE

                                for (i in (array.length() - 1) downTo 0) {
                                    val objectArtist = array.getJSONObject(i)

                                    val banners = SupervisorAttendanceData(
                                        objectArtist.optString("id"),
                                        objectArtist.optString("emp_name"),
                                        objectArtist.optString("emp_code"),
                                        objectArtist.optString("rank"),
                                        objectArtist.optString("status"),
                                        objectArtist.optString("date")
                                    )
                                    attendanceList.add(banners)
                                   // searchLists = attendanceList as ArrayList<SupervisorAttendanceData>
                                    adapter = SupervisorAttendanceAdapter(attendanceList,requireActivity(),"SupervisorAttendanceDetailsFrag")
                                    binding.attendanceList.adapter = adapter
                                    binding.attendanceList.visibility = View.VISIBLE
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
                            binding.attendanceList.visibility = View.INVISIBLE
                            binding.progressBar6.visibility = View.GONE

                        }

                    } catch (e: JSONException) {
                        
                        binding.notAvailable1.visibility = View.VISIBLE
                        binding.attendanceList.visibility = View.INVISIBLE
                        binding.progressBar6.visibility = View.GONE

                    }

                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                        requireActivity().applicationContext,
                        error.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = java.util.HashMap<String, String>()

                    params["from_date"] = from
                    params["to_date"] = to
                    params["site_id"] =
                        SharedPrefManager.getInstance(requireActivity().applicationContext).user.site_id
                    params["client_code"] =
                        SharedPrefManager.getInstance(requireActivity().applicationContext).user.client_code

                    return params

                }
            }

            VolleySingleton.getInstance(requireActivity().applicationContext)
                .addToRequestQueue(stringRequest)
        }
    */

}