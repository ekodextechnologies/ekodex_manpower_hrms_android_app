package com.ekodex.manpowerhrms

import VoucherDateViewModel
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
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
import com.ekodex.manpowerhrms.databinding.FragmentRejectedVouchersBinding
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import androidx.fragment.app.activityViewModels


class RejectedVouchersFragment : Fragment() {

    lateinit var binding: FragmentRejectedVouchersBinding
    private var progressDialog: AlertDialog? = null
     var adapter:VoucherAdapter? = null
    var vouchersList = mutableListOf<VoucherData>()
    var expanded = false
    private var isFirstLoad = true

    var company_id = ""
    var site_id = ""
    lateinit var companies:MutableList<Client_Data>
    lateinit var branches:MutableList<Site_Data>
    lateinit var companies_names:MutableList<String>
    lateinit var branches_names:MutableList<String>

    //date filter
    private var fromDate = ""
    private var toDate = ""
    lateinit var from_flag: String
    lateinit var to_flag: String

    //pagination
    private var isLoading = false
    private var offset = 0
    private val limit = 50
    private var totalRows = 0

    private val dateViewModel: VoucherDateViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rejected_vouchers, container, false)

        if (adapter == null) {
            adapter = VoucherAdapter(vouchersList,requireActivity())
        }
        binding.rejectedVouchersList.adapter = adapter


        companies = mutableListOf()
        branches =  mutableListOf()
        companies_names = mutableListOf()
        branches_names =  mutableListOf()

        var user = SharedPrefManager.getInstance(requireContext()).user
        if(!user.role.equals("admin",ignoreCase = true))
        {
            company_id = user.client_id
            site_id = user.site_id
        }
        else
        {
            var i1 = Internet()
            if(i1.checkConnection(requireContext()))
            {
                if(user.copy_client_id.contains(","))
                {
                    val list = (user.copy_client_id.split(",").toTypedArray())
                    list.forEach {
                        getCompanies(it)
                    }
                }
//            else
//            {
//                getCompanies(user.copy_client_id)
//            }
            }
            else
            {
                Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
            }
        }

        //date filter initialization
        from_flag = "true"
        to_flag = "true"

        // date clicklistner
        val calender1 = Calendar.getInstance()
        val datePicker1 =
            DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                calender1.set(Calendar.YEAR, year)
                calender1.set(Calendar.MONTH, month)
                calender1.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateFromDateLable(calender1)
            }

        val calender2 = Calendar.getInstance()
        val datePicker2 =
            DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                calender2.set(Calendar.YEAR, year)
                calender2.set(Calendar.MONTH, month)
                calender2.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateToDateLable(calender2)
            }


        binding.button24.setOnClickListener {
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
        binding.button25.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                datePicker2,
                calender2.get(Calendar.YEAR),
                calender2.get(Calendar.MONTH),
                calender2.get(Calendar.DAY_OF_MONTH)
            )
                .show()
            to_flag = "true"
        }

        binding.button26.setOnClickListener {
            if (from_flag == "true" && to_flag == "true") {
                dateViewModel.setDates(fromDate, toDate)
                var i1 = Internet()
                if(i1.checkConnection(requireContext()))
                {
                    vouchersList.clear()
                    getRejectedVouchers(fromDate, toDate)
                }
                else
                {
                    Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
                    binding.rejectedVouchersList.visibility = View.INVISIBLE
                    binding.notAvailable1.visibility = View.GONE
                    binding.noInternet.visibility = View.VISIBLE
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "please select both from & to date",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        //main fab
        binding.addLeave.setOnClickListener{
            // it.findNavController().navigate(VoucherManagementFragmentDirections.actionVoucherManagementFragmentToAddVoucherFragment())
            if (!expanded) {
                // SHOW FABs
                binding.addLeave3.show()
                binding.addLeave4.show()
                binding.addLeave5.show()
                binding.addLeave6.show()

                // SHOW TEXT
                binding.textView472.visibility = View.VISIBLE
                binding.textView473.visibility = View.VISIBLE
                binding.textView474.visibility = View.VISIBLE
                binding.textView475.visibility = View.VISIBLE

                // ANIMATION (already stacked, so no translation needed)
                expanded = true

            } else {
                // HIDE FABs
                binding.addLeave3.hide()
                binding.addLeave4.hide()
                binding.addLeave5.hide()
                binding.addLeave6.hide()

                // HIDE TEXT
                binding.textView472.visibility = View.GONE
                binding.textView473.visibility = View.GONE
                binding.textView474.visibility = View.GONE
                binding.textView475.visibility = View.GONE

                expanded = false
            }
        }

        //split voucher
        binding.addLeave3.setOnClickListener {
            it.findNavController().navigate(VoucherManagementFragmentDirections.actionVoucherManagementFragmentToAddSplitVoucherFragment())
        }

        //site/vendor voucher
        binding.addLeave4.setOnClickListener {
            it.findNavController().navigate(VoucherManagementFragmentDirections.actionVoucherManagementFragmentToAddSiteVoucherFragment())
        }

        //gang voucher
        binding.addLeave5.setOnClickListener {
            it.findNavController().navigate(VoucherManagementFragmentDirections.actionVoucherManagementFragmentToAddVoucherFragment())
        }

        //split voucher
        binding.addLeave6.setOnClickListener {
            it.findNavController().navigate(VoucherManagementFragmentDirections.actionVoucherManagementFragmentToAddEmployeeVoucherFragment())
        }

        binding.rejectedVouchersList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) return // Only trigger on scroll down

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                // Trigger pagination if last visible item is near the end and more data exists
                if (!isLoading && lastVisibleItem >= vouchersList.size - 5 && vouchersList.size < totalRows) {
                    offset += limit
                    getRejectedVouchers(fromDate, toDate) // Fetch next page
                }
            }
        })

        binding.company.setOnItemClickListener{ parent, _, position, _ ->
            val selectedCompany = parent.getItemAtPosition(position) as Client_Data


            Log.i("11111","Position - ${position}")
            Log.i("11111","client - ${selectedCompany.toString()} , id - ${selectedCompany.id}")


            if (selectedCompany != null) {
                Toast.makeText(requireContext(), "Getting Branch!!", Toast.LENGTH_LONG).show()
                binding.branch!!.text.clear()
                company_id = selectedCompany.id
                branches.clear()
                branches_names.clear()
                getSites(company_id)
            }
        }

        binding.branch.setOnItemClickListener{ parent, _, position, _ ->
            val selectedBranchName = parent.getItemAtPosition(position) as String
            val selectedBranch = branches.find { it.title == selectedBranchName }

            if (selectedBranch != null) {
                site_id = selectedBranch.id
                //  client_code = selectedBranch.client_code
            }
        }

//        binding.company.setOnItemClickListener(object : AdapterView.OnItemClickListener {
//            override fun onItemClick(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//
//                company_id = companies.get(position).id
//                Log.i("11111","Client- ${company_id}")
//                branches.clear()
//                branches_names.clear()
//                binding.branch.text.clear()
//                getSites(company_id)
//            }
//
//        })
//
//        binding.branch.setOnItemClickListener(object : AdapterView.OnItemClickListener {
//            override fun onItemClick(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                site_id = branches.get(position).id
//                Log.i("11111","Site- ${site_id}")
//            }
//
//        })

        //reset button
        binding.addLeave2.setOnClickListener {
            company_id = ""
            site_id = ""
            binding.company.text.clear()
            binding.branch.text.clear()
        }

        return binding.root
    }

    private fun getRejectedVouchers(fr:String , too:String) {
        if (isLoading) return
        isLoading = true
        Log.i("11111", "Offset - ${offset}, Limit - ${limit}")
     //   binding.progressBar2.visibility = View.GONE
        showProgressDialog()

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
            Request.Method.POST, URLs.URL_GET_ALL_VOUCHERS,
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
                            binding.rejectedVouchersList.visibility = View.INVISIBLE
                            binding.progressBar2.visibility = View.GONE
                            binding.textView77.text = "Total 0 Results"
                            hideProgressDialog()

                        }
                        else
                        {
                            vouchersList.clear()
                            binding.notAvailable1.visibility = View.GONE
                            binding.progressBar2.visibility = View.GONE
                            binding.rejectedVouchersList.visibility = View.VISIBLE

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
                            }
                            adapter?.notifyDataSetChanged()
                            hideProgressDialog()
                            //searchLists = attendanceList as ArrayList<SupervisorAttendanceData>
                            //  adapter = VoucherAdapter(vouchersList)
                            //  binding.rejectedVouchersList.adapter = adapter
                            binding.rejectedVouchersList.visibility = View.VISIBLE
                        }
                    }
                    else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        binding.notAvailable1.visibility = View.VISIBLE
                        binding.rejectedVouchersList.visibility = View.INVISIBLE
                        binding.progressBar2.visibility = View.GONE
                        binding.textView77.text = "Total 0 Results"
                        hideProgressDialog()

                    }

                } catch (e: JSONException) {
                    
                    binding.notAvailable1.visibility = View.VISIBLE
                    binding.rejectedVouchersList.visibility = View.INVISIBLE
                    binding.progressBar2.visibility = View.GONE
                    binding.textView77.text = "Total 0 Results"
                    hideProgressDialog()
                }
                isLoading = false
            },
            Response.ErrorListener { error ->
                
                hideProgressDialog()
                binding.textView77.text = "Total 0 Results"
                isLoading = false
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                Log.i("11111","volley dates - ${from} - ${to}")
                params["created_by"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.id
                params["status"] = "Cancelled"
                params["from_date"] = from
                params["to_date"] = to
                params["role"] = SharedPrefManager.getInstance(requireActivity().applicationContext).user.role
                params["client_id"] = company_id
                params["site_id"] = site_id
                params["limit"] = limit.toString()
                params["offset"] = offset.toString()
                return params

            }
        }
        stringRequest.tag = "reje_vouchers"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        VolleySingleton.getInstance(requireActivity()).requestQueue.cancelAll("reje_vouchers")
    }

    //date filter functions
    private fun updateFromDateLable(calener: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
// Here we can use calendar selected date
        binding.button24.setText((sdf.format(calener.time)))
        val myFormat2 = "yyyy-MM-dd"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.UK)
        fromDate = (sdf2.format(calener.time))
        dateViewModel.setDates(fromDate, toDate)
        Log.i("oooooooooooooooooo", fromDate)
    }

    private fun updateToDateLable(calener: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
// Here we can use calendar selected date
        binding.button25.setText((sdf.format(calener.time)))

        val myFormat2 = "yyyy-MM-dd"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.UK)
        toDate = (sdf2.format(calener.time))
        dateViewModel.setDates(fromDate, toDate)
        Log.i("oooooooooooooooooo", toDate)
    }

/*    override fun onResume() {
        super.onResume()

        if(!requireArguments().isEmpty)
        {
            var args = RejectedVouchersFragmentArgs.fromBundle(requireArguments())
            fromDate = args.fromDate
            toDate = args.toDate

            if(fromDate == "" && toDate == "")
            {
                val sdf = SimpleDateFormat("dd-MM-yyyy")
                val sdf1 = SimpleDateFormat("yyyy-MM-dd")
                val curtDate = sdf1.format(Date())

                fromDate = curtDate
                toDate = curtDate
                //set current date to textviews of table
                val currtDate = sdf.format(Date())
                binding.button24.text = currtDate
                binding.button25.text = currtDate
            }
            else
            {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
                val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.UK)

                var temp_from = outputFormat.format(inputFormat.parse(fromDate))
                var temp_to = outputFormat.format(inputFormat.parse(toDate))

                binding.button24.text = temp_from
                binding.button25.text = temp_to
            }
        }
        else{
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val sdf1 = SimpleDateFormat("yyyy-MM-dd")
            val curtDate = sdf1.format(Date())

            fromDate = curtDate
            toDate = curtDate
            //set current date to textviews of table
            val currtDate = sdf.format(Date())
            binding.button24.text = currtDate
            binding.button25.text = currtDate
        }

        var i1 = Internet()
        if (i1.checkConnection(requireContext())) {
            getRejectedVouchers(fromDate, toDate)
        } else {
            //binding.notAvailable6.visibility = View.VISIBLE
            Toast.makeText(
                requireContext(),
                "No internet connection!!",
                Toast.LENGTH_LONG
            ).show()
            binding.rejectedVouchersList.visibility = View.INVISIBLE
            binding.notAvailable1.visibility = View.GONE
            binding.noInternet.visibility = View.VISIBLE
        }
    }*/

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val sdfDisplay = SimpleDateFormat("dd-MM-yyyy", Locale.UK)
//        val sdfServer = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
//        val currentDate = sdfServer.format(Date())
//
//        // 1️⃣ Restore from savedInstanceState if available
//        if (savedInstanceState != null) {
//            fromDate = savedInstanceState.getString("fromDate", currentDate)
//            toDate = savedInstanceState.getString("toDate", currentDate)
//        } else {
//            // 2️⃣ If arguments are passed from navigation
//            if (!requireArguments().isEmpty) {
//                val args = PendingVouchersFragmentArgs.fromBundle(requireArguments())
//                fromDate = if (args.fromDate.isNotEmpty()) args.fromDate else currentDate
//                toDate = if (args.toDate.isNotEmpty()) args.toDate else currentDate
//            } else {
//                // 3️⃣ Default to current date
//                fromDate = currentDate
//                toDate = currentDate
//            }
//        }
//
//        // Update the UI buttons
//        binding.button24.text = sdfDisplay.format(sdfServer.parse(fromDate)!!)
//        binding.button25.text = sdfDisplay.format(sdfServer.parse(toDate)!!)
//
//        // Fetch data
//        val i1 = Internet()
//        if (i1.checkConnection(requireContext())) {
//            getRejectedVouchers(fromDate, toDate)
//        } else {
//            Toast.makeText(requireContext(), "No internet connection!!", Toast.LENGTH_LONG).show()
//            binding.rejectedVouchersList.visibility = View.INVISIBLE
//            binding.notAvailable1.visibility = View.GONE
//            binding.noInternet.visibility = View.VISIBLE
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sdfDisplay = SimpleDateFormat("dd-MM-yyyy", Locale.UK)
        val sdfServer = SimpleDateFormat("yyyy-MM-dd", Locale.UK)

        // Observe dateRange to ensure both dates are ready
        dateViewModel.dateRange.observe(viewLifecycleOwner) { (from, to) ->

            // Only fetch if both dates are non-empty
            if (from.isNotEmpty() && to.isNotEmpty()) {
                fromDate = from
                toDate = to

                Log.i("DATE_VM", "Child fragment got dates: $from → $to")

                // Update UI buttons
                binding.button24.text = sdfDisplay.format(sdfServer.parse(from)!!)
                binding.button25.text = sdfDisplay.format(sdfServer.parse(to)!!)

                // Fetch vouchers once
                offset = 0
                vouchersList.clear()
                getRejectedVouchers(fromDate, toDate)
            }
        }
    }


//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//       outState.putString("fromDate", fromDate)
//       outState.putString("toDate", toDate)
//    }

    private fun getCompanies(com_id: String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_ALL_COMPANIES,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            // Toast.makeText(requireContext(),"No companies available!!", Toast.LENGTH_SHORT).show()

                        }
                        else{
                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)

                                //set default company
//                                if(objectArtist.getString("id") == SharedPrefManager.getInstance(requireActivity().applicationContext).user.client_id)
//                                {
//                                    binding.company.setText(objectArtist.getString("title"))
//                                }

                                companies.add(Client_Data(objectArtist.getString("id"),objectArtist.getString("title")))
                                companies_names.add(objectArtist.getString("title"))
                            }
                            val company_adapter = ArrayAdapter(requireContext(),R.layout.pay_to_dropdown_layout,companies)
                            binding.company.setAdapter(company_adapter)

                            binding.tl1.visibility = View.VISIBLE
                            binding.tl2.visibility = View.VISIBLE
                            binding.textView204.visibility = View.VISIBLE
                            binding.textView205.visibility = View.VISIBLE
                            binding.addLeave2.visibility = View.VISIBLE
                        }


                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: JSONException) {
                    

                }

            },
            Response.ErrorListener { error ->
                
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["id"] = com_id

                return params

            }
        }
        stringRequest.tag = "reje_vouchers"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getSites(sites_id: String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_ALL_BRANCHES,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            // Toast.makeText(requireContext(),"No companies available!!", Toast.LENGTH_SHORT).show()

                        }
                        else{
                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)

                                //set default site
//                                if(objectArtist.getString("id") == SharedPrefManager.getInstance(requireActivity().applicationContext).user.site_id)
//                                {
//                                    binding.branch.setText(objectArtist.getString("title"))
//                                }

                                branches.add(Site_Data(objectArtist.getString("id"),objectArtist.getString("title"),objectArtist.getString("client_code")))
                                branches_names.add(objectArtist.getString("title"))
                            }
                            val site_adapter = ArrayAdapter(requireContext(),R.layout.pay_to_dropdown_layout,branches_names)
                            binding.branch.setAdapter(site_adapter)
                        }


                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: JSONException) {
                    

                }

            },
            Response.ErrorListener { error ->
                
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["id"] = sites_id


                return params

            }
        }
        stringRequest.tag = "reje_vouchers"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
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

    override fun onResume() {
        super.onResume()

        if (isFirstLoad) {
            isFirstLoad = false
            return
        }

        if (fromDate.isNotEmpty() && toDate.isNotEmpty()) {
            isLoading = false
            offset = 0
            totalRows = 0
            vouchersList.clear()
            adapter?.notifyDataSetChanged()
            getRejectedVouchers(fromDate, toDate)
        }
    }


}