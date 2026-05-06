package com.ekodex.manpowerhrms.Employee_Directory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.databinding.FragmentEmployeeDirectoryBinding
import org.json.JSONException
import org.json.JSONObject

class EmployeeDirectoryFragment : Fragment() {

    lateinit var binding: FragmentEmployeeDirectoryBinding
    private var adapter: EmployeeDirectoryAdapter? = null
    var empDirectoryList = mutableListOf<EmployeeDirectoryData>()
    var searchList = arrayListOf<EmployeeDirectoryData>()
    private var progressDialog: AlertDialog? = null
    var from_date = ""
    var to_date = ""

    private var isLoading = false
    private var offset = 0
    private val limit = 50
    private var totalRows = 0
    var status = "Total"
    var keyword = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_employee_directory, container, false)

        adapter = EmployeeDirectoryAdapter(empDirectoryList)
        binding.empDirectoryList.adapter = adapter

        // Search filtering
        binding.searchEmployee.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                activitiesFilter(newText)
                return true
            }
        })

        // Pagination
        binding.empDirectoryList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) return // only scroll down

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                Log.i("PAGINATION", "lastVisible=$lastVisibleItem, totalItems=$totalItemCount, isLoading=$isLoading")

                if (!isLoading && lastVisibleItem >= totalItemCount - 5 && empDirectoryList.size < totalRows) {
                    // Do not increment offset here
                    getEmployeeDetails()
                }
            }
        })


        return binding.root
    }

    override fun onResume() {
        super.onResume()

        try {
            val args = EmployeeDirectoryFragmentArgs.fromBundle(requireArguments())
            status = args.status ?: "Total"
            from_date = args.fromDate ?: ""
            to_date = args.toDate ?: ""
        } catch (e: Exception) {
            // If arguments are missing (e.g. when opened from Nav Drawer)
            status = "Total"
        }

        if (Internet().checkConnection(requireContext())) {
            resetListAndLoad()
        } else {
            Toast.makeText(requireContext(), "No internet connection!!", Toast.LENGTH_LONG).show()
        }
    }


    private fun resetListAndLoad() {
        // Reset everything for fresh load
        offset = 0
        totalRows = 0
        isLoading = false
        empDirectoryList.clear()
        searchList.clear()
        adapter?.notifyDataSetChanged()
        binding.empDirectoryList.scrollToPosition(0)

        getEmployeeDetails()
    }

    private fun getEmployeeDetails() {
        if (isLoading) return
        isLoading = true
        showProgressDialog()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_ALL_EMPLOYEES,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        totalRows = obj.optInt("total_rows", 0)
                        binding.textView77.text = "Total results $totalRows"

                        val array = obj.getJSONArray("user")

                        // If no data returned and list is empty
                        if (array.length() == 0 && empDirectoryList.isEmpty()) {
                            binding.empDirectoryList.visibility = View.INVISIBLE
                            binding.notAvailable1.visibility = View.VISIBLE
                            hideProgressDialog()
                        } else {
                            for (i in 0 until array.length()) {
                                val item = array.getJSONObject(i)
                                val employee = EmployeeDirectoryData(
                                    item.optString("id"),
                                    item.optString("empcode"),
                                    item.optString("first_name") + " " + item.optString("last_name"),
                                    item.optString("rank"),
                                    item.optString("phone"),
                                    item.optString("gender"),
                                    item.optString("status")
                                )
                                empDirectoryList.add(employee)
                            }

                            // Increment offset **after successfully adding items**
                            offset += array.length()

                            adapter?.notifyDataSetChanged()
                            searchList = ArrayList(empDirectoryList)

                            binding.empDirectoryList.visibility = View.VISIBLE
                            binding.notAvailable1.visibility = View.GONE
                            hideProgressDialog()
                        }
                    } else {
                        hideProgressDialog()
                        if (empDirectoryList.isEmpty()) {
                            binding.empDirectoryList.visibility = View.INVISIBLE
                            binding.notAvailable1.visibility = View.VISIBLE
                        }
                        Toast.makeText(requireContext(), obj.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                  //  
                    hideProgressDialog()
                    if (empDirectoryList.isEmpty()) {
                        binding.empDirectoryList.visibility = View.INVISIBLE
                        binding.notAvailable1.visibility = View.VISIBLE
                    }
                    binding.textView77.text = "Total results 0"
                } finally {
                   // binding.progressBar2.visibility = View.GONE
                    hideProgressDialog()
                    isLoading = false
                }
            },
            Response.ErrorListener {
               // Toast.makeText(requireContext(), error.message ?: "Something went wrong", Toast.LENGTH_SHORT).show()
              //  binding.progressBar2.visibility = View.GONE
                hideProgressDialog()
                isLoading = false
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                val user = SharedPrefManager.getInstance(requireContext()).user

                params["client_id"] = user.client_id
                params["site_id"] = user.site_id
                params["role"] = user.role
                params["status"] = status
                params["from_date"] = from_date
                params["to_date"] = to_date
                params["limit"] = limit.toString()
                params["offset"] = offset.toString() // offset now correctly represents next page
                params["keyword"] = keyword
                return params
            }
        }

        stringRequest.tag = "EMPLOYEE_DIRECTORY"
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        VolleySingleton.getInstance(requireActivity()).requestQueue.cancelAll("EMPLOYEE_DIRECTORY")
    }

    private fun activitiesFilter(newText: String?) {
        keyword = newText.toString()
        resetListAndLoad()
//        val newFilteredList = searchList.filter {
//            it.name.contains(newText ?: "", true) || it.phone.contains(newText ?: "", true)
//        }
//        adapter?.filtering(ArrayList(newFilteredList))
    }

    private fun showProgressDialog() {
        if (progressDialog == null) {
            val progressBar = ProgressBar(requireContext())
            progressBar.isIndeterminate = true
            val padding = 50
            progressBar.setPadding(padding, padding, padding, padding)

            progressDialog = AlertDialog.Builder(requireContext())
                .setTitle("Loading Data")
                .setMessage("Please wait...")
                .setView(progressBar)
                .setCancelable(false)
                .create()
        }
        progressDialog?.show()
    }

    private fun hideProgressDialog() {
        progressDialog?.dismiss()
    }

}
