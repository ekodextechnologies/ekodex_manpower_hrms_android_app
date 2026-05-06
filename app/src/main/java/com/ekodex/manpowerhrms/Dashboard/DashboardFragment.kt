package com.ekodex.manpowerhrms.Dashboard

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.*
import com.ekodex.manpowerhrms.Employee_Directory.EmployeeAttendanceSummaryData
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.User
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentDashboardBinding
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.messaging.FirebaseMessaging
import org.json.JSONArray
import java.io.File
import java.io.FileOutputStream


class DashboardFragment : Fragment(), DateFilterDialog.DateFilterCallback {

    lateinit var binding: FragmentDashboardBinding
    lateinit var companies: MutableList<Client_Data>
    lateinit var branches: MutableList<Site_Data>
    lateinit var companies_display: MutableList<Client_Data>
    lateinit var branches_display: MutableList<Site_Data>
    lateinit var companies_names: MutableList<String>
    lateinit var branches_names: MutableList<String>
    private var branch_auto: AutoCompleteTextView? = null
    private var company_auto: AutoCompleteTextView? = null
    private var progressDialog: AlertDialog? = null

    var empadapter: EmployeeAttendanceSummaryAdapter? = null
    var attendanceList = mutableListOf<EmployeeAttendanceSummaryData>()

    //download button
    var attendanceListForDownload = mutableListOf<EmployeeAttendanceSummaryData>()
    lateinit var csvDataResponse: JSONArray



    // private var company_auto: AutoCompleteTextView? = null

    var from_date = ""
    var to_date = ""

    var curtDate: String = ""
    var company_id = ""
    var site_id = ""
    var client_code = ""

    lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)

        binding.rvNames.isNestedScrollingEnabled = false
        binding.emplist.isNestedScrollingEnabled = false

        companies = mutableListOf()
        branches = mutableListOf()
        companies_display = mutableListOf()
        branches_display = mutableListOf()
        companies_names = mutableListOf()
        branches_names = mutableListOf()

        if (SharedPrefManager.getInstance(requireContext()).isLoggedIn) {
            user = SharedPrefManager.getInstance(requireActivity().applicationContext).user
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.POST_NOTIFICATIONS
                    )
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                        101
                    )
                }
            }

        }

        // Toast.makeText(requireContext(),"site_id : ${user.site_id}  ,  Client_id :  ${user.client_id}  ,  Client_code  :  ${user.client_code}", Toast.LENGTH_LONG).show()


        if (user.role != "Employee") {
//-----------------------------------------------------------------------------------------------------------------
            binding.cardView.visibility = View.GONE

            //attendance and
            binding.cardView3.visibility = View.GONE
            binding.cardView6.visibility = View.GONE

            binding.cardView4.visibility = View.GONE
            binding.cardView5.visibility = View.GONE
            binding.cardView7.visibility = View.GONE
            binding.cardView8.visibility = View.GONE
            binding.cardView9.visibility = View.GONE
            binding.cardView10.visibility = View.GONE
            binding.cardView11.visibility = View.GONE
            binding.cardView12.visibility = View.GONE
            binding.cardView13.visibility = View.GONE
            binding.cardView14.visibility = View.GONE
            binding.cardview20.visibility = View.GONE

            val constraintSet = ConstraintSet().apply {
                clone(binding.dashLayout)
                connect(
                    binding.cardView6.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START,
                    80
                )
                connect(
                    binding.cardView6.id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END,
                    80
                )
            }

            val constraintSet2 = ConstraintSet().apply {
                clone(binding.dashLayout)
                connect(
                    binding.cardView3.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START,
                    80
                )
                connect(
                    binding.cardView3.id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END,
                    80
                )
            }

            constraintSet.applyTo(binding.dashLayout)
            constraintSet2.applyTo(binding.dashLayout)
        }


        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("Firebase", "Fetching FCM token failed", task.exception)
                    return@addOnCompleteListener
                }

                // Get the token
                val token = task.result
                Log.i("11111", "FCM Token: $token")

                // Send token to server if internet is available
                val internetCheck = Internet()
                if (internetCheck.checkConnection(requireContext())) {
                    updateDeviceToken(token)
                }
            }


// If user is Employee
        if (user.role.equals("Employee", ignoreCase = true)) {

            binding.textView337.visibility = View.GONE
            binding.textView325.visibility = View.GONE
            binding.textView326.visibility = View.GONE
            binding.textView327.visibility = View.GONE
            binding.textView328.visibility = View.GONE
            binding.textView329.visibility = View.GONE
            binding.textView330.visibility = View.GONE

            binding.view6.visibility = View.GONE
            binding.textView338.visibility = View.GONE
            binding.textView331.visibility = View.GONE
            binding.textView332.visibility = View.GONE
            binding.textView333.visibility = View.GONE

            binding.textView334.visibility = View.GONE
            binding.textView335.visibility = View.GONE
            binding.textView336.visibility = View.GONE
            binding.textView337.visibility = View.GONE

            binding.textView419.visibility = View.GONE
            binding.textView420.visibility = View.GONE
            binding.textView421.visibility = View.GONE
            binding.textView423.visibility = View.GONE
            binding.textView416.visibility = View.GONE
            binding.textView417.visibility = View.GONE
            binding.textView418.visibility = View.GONE
            binding.textView422.visibility = View.GONE
            binding.view7.visibility = View.GONE

            binding.view9.visibility = View.GONE
            binding.textView236.visibility = View.GONE

            binding.textView380.visibility = View.GONE
            binding.textView381.visibility = View.GONE
            binding.textView382.visibility = View.GONE

            binding.textView404.visibility = View.GONE
            binding.textView405.visibility = View.GONE
            binding.textView406.visibility = View.GONE

            binding.textView410.visibility = View.GONE
            binding.textView411.visibility = View.GONE
            binding.textView412.visibility = View.GONE

            binding.textView401.visibility = View.GONE
            binding.textView402.visibility = View.GONE
            binding.textView403.visibility = View.GONE


            binding.textView407.visibility = View.GONE
            binding.textView408.visibility = View.GONE
            binding.textView409.visibility = View.GONE

            binding.textView413.visibility = View.GONE
            binding.textView414.visibility = View.GONE
            binding.textView415.visibility = View.GONE

            // If site not already selected, set it automatically
            if (user.site_selected == 0) {
                val sharedPreferences = SharedPrefManager.ctx?.getSharedPreferences(
                    SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE
                )
                val editor = sharedPreferences?.edit()
                editor?.putInt(SharedPrefManager.KEY_SITE_SELECTED, 1)
                editor?.commit()
            }

            Log.i("11111", "Company id - ${user.client_id}")
            Log.i("11111", "siye id - ${user.site_id}")
            Log.i("11111", "client id - ${user.client_code}")
            Log.i(
                "11111",
                "site selected - ${SharedPrefManager.getInstance(requireContext()).user.site_selected}"
            )

            // Directly call data functions
            val sdf1 = SimpleDateFormat("yyyy-MM-dd")
            val curtDate = sdf1.format(Date())
            from_date = curtDate
            to_date = curtDate
            val internet = Internet()

//            if (internet.checkConnection(requireContext())) {
            getAttendanceCountsSupervisor(curtDate, curtDate)
            getSupervisorVoucherCount(curtDate, curtDate)
            getSupervisorNetDaysCount(curtDate, curtDate)
            getEmployeesCountsSupervisor(curtDate, curtDate)
            getKycCountsForDashboard()
            getAttendanceSupervisor(curtDate, curtDate)
//            } else {
//                Toast.makeText(requireContext(), "No internet connection!!", Toast.LENGTH_LONG).show()
//            }
        }

// If user is Admin, Supervisor, etc.
        else {

            // If site is not selected yet
            if (user.site_selected == 0) {

                val dialogBuilder = AlertDialog.Builder(requireContext())
                val inflater = layoutInflater
                val dialogView: View = inflater.inflate(R.layout.select_client_popup_layout, null)
                dialogBuilder.setView(dialogView)

                val ok = dialogView.findViewById<Button>(R.id.button22)
                val company_auto = dialogView.findViewById<AutoCompleteTextView>(R.id.company)
                branch_auto = dialogView.findViewById<AutoCompleteTextView>(R.id.branch)
                val loading = dialogView.findViewById<ProgressBar>(R.id.progressBar8)

                val company_adapter =
                    ArrayAdapter(requireContext(), R.layout.pay_to_dropdown_layout, companies)
                company_auto.setAdapter(company_adapter)

//                val branch_adapter = ArrayAdapter(requireContext(), R.layout.pay_to_dropdown_layout, branches_names)
//                branch_auto.setAdapter(branch_adapter)

                val alertDialog = dialogBuilder.create()
                alertDialog.setCancelable(true)
                alertDialog.setCanceledOnTouchOutside(false)
                alertDialog.show()

                ok.setOnClickListener {
                    if (company_auto.text.isNullOrEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            "Please select company!!",
                            Toast.LENGTH_LONG
                        ).show()
                    } else if (branch_auto!!.text.isNullOrEmpty()) {
                        Toast.makeText(
                            requireContext(),
                            "Please select branch!!",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val sharedPreferences = SharedPrefManager.ctx?.getSharedPreferences(
                            SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE
                        )
                        val editor = sharedPreferences?.edit()
                        editor?.putString(SharedPrefManager.KEY_CLIENT_ID, company_id)
                        editor?.putString(SharedPrefManager.KEY_SITE_ID, site_id)
                        editor?.putString(SharedPrefManager.KEY_CLIENT_CODE, client_code)
                        editor?.putInt(SharedPrefManager.KEY_SITE_SELECTED, 1)
                        editor?.commit()

                        alertDialog.dismiss()
                        requireActivity().finish()
                        startActivity(Intent(requireActivity(), MainActivity::class.java))
                    }
                }

//                company_auto.setOnItemClickListener { _, _, position, _ ->
//                    Toast.makeText(requireContext(), "Getting Branch!!", Toast.LENGTH_LONG).show()
//                    branch_auto.text.clear()
//                    company_id = companies[position].id
//                    branches.clear()
//                    branches_names.clear()
//                    getBranches(company_id)
//                }

                company_auto.setOnItemClickListener { parent, _, position, _ ->
                    //  val selectedCompanyName = parent.getItemAtPosition(position) as String
                    // val selectedCompany = companies.find { it.title == selectedCompanyName }
                    val selectedCompany = parent.getItemAtPosition(position) as Client_Data


                    Log.i("11111", "Position - ${position}")
                    Log.i(
                        "11111",
                        "client - ${selectedCompany.toString()} , id - ${selectedCompany.id}"
                    )


                    if (selectedCompany != null) {
                        Toast.makeText(requireContext(), "Getting Branch!!", Toast.LENGTH_LONG)
                            .show()
                        branch_auto!!.text.clear()

                        company_id = selectedCompany.id
                        branches.clear()
                        branches_names.clear()
                        getBranches(company_id)
                    }
                }




                branch_auto!!.setOnItemClickListener { parent, _, position, _ ->
                    val selectedBranchName = parent.getItemAtPosition(position) as String
                    val selectedBranch = branches.find { it.title == selectedBranchName }

                    if (selectedBranch != null) {
                        site_id = selectedBranch.id
                        client_code = selectedBranch.client_code
                    }
                }


                //                branch_auto.setOnItemClickListener { _, _, position, _ ->
//                    site_id = branches[position].id
//                    client_code = branches[position].client_code
//                }

                // Fetch companies
                Toast.makeText(
                    requireContext(),
                    "Wait, fetching companies and sites!!",
                    Toast.LENGTH_LONG
                ).show()
                var lastLoop = false


                if (user.copy_client_id.contains(",")) {
                    val list = user.copy_client_id.split(",")
                    list.forEachIndexed { index, s ->
                        if (index == list.size - 1) lastLoop = true
                        getCompanies(s, lastLoop, loading)
                    }
                } else {
                    lastLoop = true
                    getCompanies(user.client_id, lastLoop, loading)
                }
            }

            // If site already selected, call data functions directly
            else {
                val sdf1 = SimpleDateFormat("yyyy-MM-dd")
                val curtDate = sdf1.format(Date())
                from_date = curtDate
                to_date = curtDate
                val internet = Internet()

                //   if (internet.checkConnection(requireContext())) {
                getAttendanceCountsSupervisor(curtDate, curtDate)
                getSupervisorVoucherCount(curtDate, curtDate)
                getSupervisorNetDaysCount(curtDate, curtDate)
                getEmployeesCountsSupervisor(curtDate, curtDate)
                getKycCountsForDashboard()
                getAttendanceSupervisor(curtDate, curtDate)
//                } else {
//                    Toast.makeText(requireContext(), "No internet connection!!", Toast.LENGTH_LONG).show()
//                }
            }
        }


        /* if(SharedPrefManager.getInstance(requireActivity().applicationContext).user.site_selected == 0)
         {

             val dialogBuilder = AlertDialog.Builder(requireActivity())

             val inflater = requireActivity().layoutInflater
             val dialogView: View = inflater.inflate(R.layout.select_client_popup_layout, null)
             dialogBuilder.setView(dialogView)

             var ok = dialogView.findViewById<Button>(R.id.button22)
             var company_auto = dialogView.findViewById<AutoCompleteTextView>(R.id.company)
             var branch_auto = dialogView.findViewById<AutoCompleteTextView>(R.id.branch)
             var loading = dialogView.findViewById<ProgressBar>(R.id.progressBar8)


             val company_adapter = ArrayAdapter(requireContext(),R.layout.pay_to_dropdown_layout,companies_names)
             company_auto.setAdapter(company_adapter)

             val branch_adapter = ArrayAdapter(requireContext(),R.layout.pay_to_dropdown_layout,branches_names)
             branch_auto.setAdapter(branch_adapter)

             val alertDialog = dialogBuilder.create()
             alertDialog.setCancelable(false)
             alertDialog.setCanceledOnTouchOutside(false)
             alertDialog.show()

             ok.setOnClickListener {
                 if (company_auto.text.toString().length == 0) {
                     Toast.makeText(requireContext(),"Please select company!!", Toast.LENGTH_LONG).show()
                 }
                 else if (branch_auto.text.toString().length == 0) {
                     Toast.makeText(requireContext(),"Please select branch!!", Toast.LENGTH_LONG).show()
                 }
                 else
                 {
                     val sharedPreferences = SharedPrefManager.ctx?.getSharedPreferences(
                         SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE)
                     val editor = sharedPreferences?.edit()

                     editor?.putString(SharedPrefManager.KEY_CLIENT_ID,company_id)
                     editor?.putString(SharedPrefManager.KEY_SITE_ID,site_id)
                     editor?.putString(SharedPrefManager.KEY_CLIENT_CODE,client_code)
                     editor?.putInt(SharedPrefManager.KEY_SITE_SELECTED,1)

                     editor?.apply()
                     alertDialog.dismiss()
                     requireActivity().finish()
                     startActivity(Intent(requireActivity(), MainActivity::class.java))
                 }
             }

             company_auto.setOnItemClickListener(object : AdapterView.OnItemClickListener {
                 override fun onItemClick(
                     parent: AdapterView<*>?,
                     view: View?,
                     position: Int,
                     id: Long
                 ) {
                     Toast.makeText(requireContext(),"Getting Branch!!",Toast.LENGTH_LONG).show()
                     branch_auto.text.clear()
                     company_id = companies.get(position).id

                     branches.clear()
                     branches_names.clear()

                     getBranches(company_id)
                 }

             })

             branch_auto.setOnItemClickListener(object : AdapterView.OnItemClickListener {
                 override fun onItemClick(
                     parent: AdapterView<*>?,
                     view: View?,
                     position: Int,
                     id: Long
                 ) {
                     site_id = branches.get(position).id
                     client_code = branches.get(position).client_code
                     //Toast.makeText(requireContext(),site_id,Toast.LENGTH_LONG).show()
                 }

             })

             var lastLoop = false
             Toast.makeText(requireContext(),"Wait , fetching companies and sites!!",Toast.LENGTH_LONG).show()

             if(user.client_id.contains(","))
             {
                 val list = (user.client_id.split(",").toTypedArray())
                 list.forEachIndexed { index, s ->
                     if(index == list.size - 1)
                     {
                         lastLoop = true
                     }
                     getCompanies(s,lastLoop,loading)
                 }
             }
             else
             {
                 lastLoop = true
                 getCompanies(user.client_id, lastLoop,loading)
             }
         }
         else
         {
             val sdf1 = SimpleDateFormat("yyyy-MM-dd")
             curtDate = sdf1.format(Date())
             var i1 = Internet()
             if (i1.checkConnection(requireContext())) {
                 getAttendanceCountsSupervisor(curtDate, curtDate)
                 getSupervisorVoucherCount(curtDate,curtDate)
                 //getSupervisorNetDaysCount(curtDate,curtDate)
                 getEmployeesCountsSupervisor()
             } else {
                 //binding.notAvailable6.visibility = View.VISIBLE
                 Toast.makeText(requireContext(), "No internet connection!!", Toast.LENGTH_LONG).show()
             }
         }*/


        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            var i1 = Internet()
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            val dateInMillis = selectedDate.timeInMillis
            val sdf1 = SimpleDateFormat("yyyy-MM-dd")
            curtDate = sdf1.format(Date(dateInMillis))
            from_date = curtDate
            to_date = curtDate
            //Toast.makeText(requireContext(), "Start Date: ${sdf1.format(Date(dateInMillis))}", Toast.LENGTH_LONG).show()
            // if (i1.checkConnection(requireContext())) {
            getAttendanceCountsSupervisor(curtDate, curtDate)
            getSupervisorVoucherCount(curtDate, curtDate)
            getSupervisorNetDaysCount(curtDate, curtDate)
            getEmployeesCountsSupervisor(curtDate, curtDate)
            getKycCountsForDashboard()
            getAttendanceSupervisor(curtDate, curtDate)
            //findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToSupervisorAttendanceFragment(curtDate,curtDate))
//            } else {
//                //binding.notAvailable6.visibility = View.VISIBLE
//                Toast.makeText(requireContext(), "No internet connection!!", Toast.LENGTH_LONG).show()
//            }

        }


        //Employee search
        binding.cardView3.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToEmployeeDirectoryFragment(
                    "",
                    "",
                    ""
                )
            )
        }

        //Attendance
        binding.cardView6.setOnClickListener {
            if (SharedPrefManager.getInstance(requireActivity().applicationContext).user.role != "Employee") {
                it.findNavController()
                    .navigate(DashboardFragmentDirections.actionDashboardFragmentToAttendanceSupervisorFragment())
            } else {
                it.findNavController()
                    .navigate(DashboardFragmentDirections.actionDashboardFragmentToAttendanceFragment())
            }
        }

        //Holiday's list
        binding.cardView4.setOnClickListener {
            it.findNavController()
                .navigate(DashboardFragmentDirections.actionDashboardFragmentToHolidaysFragment())
        }

        //Leaves management
//        binding.cardView.setOnClickListener {
//            it.findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToLeavesManagementFragment())
//        }

        //birthday
        binding.cardView14.setOnClickListener {
            it.findNavController()
                .navigate(DashboardFragmentDirections.actionDashboardFragmentToBirthdaysFragment())
        }

        //events
        binding.cardview20.setOnClickListener {
            it.findNavController()
                .navigate(DashboardFragmentDirections.actionDashboardFragmentToEventsFragment())
        }

        //news
        binding.cardView5.setOnClickListener {
            it.findNavController()
                .navigate(DashboardFragmentDirections.actionDashboardFragmentToNewsFragment())
        }

        //expenses
        binding.cardView10.setOnClickListener {
            it.findNavController()
                .navigate(DashboardFragmentDirections.actionDashboardFragmentToExpensesFragment())
        }

        //recruitment
        binding.cardView11.setOnClickListener {
            it.findNavController()
                .navigate(DashboardFragmentDirections.actionDashboardFragmentToRecruitmentFragment())
        }

        //travel
        binding.cardView9.setOnClickListener {
            it.findNavController()
                .navigate(DashboardFragmentDirections.actionDashboardFragmentToTravelManagementFragment())
        }

        //voucher counts
        binding.textView357.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToVoucherManagementFragment(
                    from_date,
                    to_date
                )
            )
        }

        //pending
        binding.textView443.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToVoucherManagementFragment(
                    from_date,
                    to_date
                ).setOpenTab(0)
            )
        }

        //approved
        binding.textView445.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToVoucherManagementFragment(
                    from_date,
                    to_date
                ).setOpenTab(1)
            )
        }

        //rejected
        binding.textView446.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToVoucherManagementFragment(
                    from_date,
                    to_date
                ).setOpenTab(2)
            )
        }

        //paid count
        binding.textView452.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToVoucherManagementFragment(
                    from_date,
                    to_date
                ).setOpenTab(3)
            )
        }

        //aadhar kyc
        binding.textView401.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToKycSummaryManagementFragment("aadhar_no")
            )
        }

        //pan kyc
        binding.textView402.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToKycSummaryManagementFragment("pancard_no")
            )
        }

        //uan kyc
        binding.textView403.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToKycSummaryManagementFragment("uan_no")
            )
        }

        //esis kyc
        binding.textView408.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToKycSummaryManagementFragment("esis_no")
            )
        }

        //pf kyc
        binding.textView407.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToKycSummaryManagementFragment("pf_no")
            )
        }

        //passport kyc
        binding.textView409.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToKycSummaryManagementFragment("passport_no")
            )
        }

        //bank kyc
        binding.textView413.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToKycSummaryManagementFragment("account_no")
            )
        }

        //doj kyc
        binding.textView414.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToKycSummaryManagementFragment("date_of_joining")
            )
        }

        //dob kyc
        binding.textView415.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToKycSummaryManagementFragment("dob")
            )
        }

        //voucher counts
//        binding.textView358.setOnClickListener {
//            it.findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToVoucherManagementFragment())
//        }
        //voucher counts
//        binding.textView359.setOnClickListener {
//            it.findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToVoucherManagementFragment())
//        }

        //helpdesk
        binding.cardView12.setOnClickListener {
            val selectorIntent = Intent(Intent.ACTION_SENDTO)
            selectorIntent.data = Uri.parse("mailto:")

            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("hello@qikbill.com"))
            emailIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                " - Concern/Feedback"
            )

            emailIntent.selector = selectorIntent
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }


        binding.textView328.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToEmployeeDirectoryFragment(
                    "Working",
                    from_date,
                    to_date
                )
            )
        }

        binding.textView329.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToEmployeeDirectoryFragment(
                    "Total",
                    from_date,
                    to_date
                )
            )
        }

        binding.textView330.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToEmployeeDirectoryFragment(
                    "Left",
                    from_date,
                    to_date
                )
            )
        }

        binding.textView334.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToSupervisorAttendanceFragment(
                    "",
                    from_date,
                    to_date
                )
            )
        }

        binding.textView335.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToSupervisorAttendanceFragment(
                    "P",
                    from_date,
                    to_date
                )
            )
        }

        binding.textView336.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToSupervisorAttendanceFragment(
                    "A",
                    from_date,
                    to_date
                )
            )
        }

        binding.textView419.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToSupervisorAttendanceFragment(
                    "SL",
                    from_date,
                    to_date
                )
            )
        }

        binding.textView420.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToSupervisorAttendanceFragment(
                    "CL",
                    from_date,
                    to_date
                )
            )
        }

        binding.textView421.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToSupervisorAttendanceFragment(
                    "PL",
                    from_date,
                    to_date
                )
            )
        }

        binding.textView423.setOnClickListener {
            it.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToSupervisorAttendanceFragment(
                    "H",
                    from_date,
                    to_date
                )
            )
        }
        //date filter
        binding.button22.setOnClickListener {
            val dateFilterDialog = DateFilterDialog()
            dateFilterDialog.callback = this
            dateFilterDialog.show(requireActivity().supportFragmentManager, "DateFilterDialog")
        }


        binding.downloadReport.setOnClickListener {
            var i1 = Internet()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (requireContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && requireContext().checkSelfPermission(android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED
                    && requireContext().checkSelfPermission(android.Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED
                    && requireContext().checkSelfPermission(android.Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED
                    && requireContext().checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermission()
                } else {
                    if (i1.checkConnection(requireContext())) {
                        if (csvDataResponse.length() <= 0) {
                            Toast.makeText(
                                requireContext(),
                                "No report data to download",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            splitdata(csvDataResponse)
                        }

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "No internet connection!!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }
        }


        return binding.root
    }

    /*    private fun getCompanies(com_id: String, lastLoop: Boolean, loading: ProgressBar) {
            val stringRequest = object : StringRequest(
                Request.Method.POST, URLs.URL_GET_ALL_COMPANIES,
                Response.Listener { response ->
            showProgressDialog()
                    try {
                        //converting response to json object
                        val obj = JSONObject(response)
                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            val array = obj.getJSONArray("user")

                            if (array.length() <= 0) {
                              //  Toast.makeText(requireContext(),"No companies available!!", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                for (i in (array.length() - 1) downTo 0) {
                                    val objectArtist = array.getJSONObject(i)
                                    companies.add(Client_Data(objectArtist.getString("id"),objectArtist.getString("title")))
                                    companies_names.add(objectArtist.getString("title"))
                                }
                                companies_display = companies.toMutableList()

                                if(lastLoop == true)
                                {
                                    //loading.visibility = View.GONE
                                    hideProgressDialog()
                                    Toast.makeText(requireContext(),"It's ready!!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
    //                        Toast.makeText(
    //                            requireActivity().applicationContext,
    //                            obj.getString("message"),
    //                            Toast.LENGTH_LONG
    //                        ).show()
                            if(lastLoop == true)
                            {
                                //loading.visibility = View.GONE
                                hideProgressDialog()
                                Toast.makeText(requireContext(),"It's ready!!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: JSONException) {

                        if(lastLoop == true)
                        {
                            //loading.visibility = View.GONE
                            hideProgressDialog()
                            Toast.makeText(requireContext(),"It's ready!!", Toast.LENGTH_SHORT).show()
                        }

                    }
                },
                Response.ErrorListener { error ->
    //                Toast.makeText(
    //                    requireActivity().applicationContext,
    //                    error.message,
    //                    Toast.LENGTH_LONG
    //                ).show()
    //                if(lastLoop == true)
    //                {
    //                    //loading.visibility = View.GONE
    //                    hideProgressDialog()
    //                    Toast.makeText(requireContext(),"It's ready!!", Toast.LENGTH_SHORT).show()
    //                }
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = java.util.HashMap<String, String>()

                    params["id"] = com_id

                    return params

                }
            }

            VolleySingleton.getInstance(requireActivity().applicationContext)
                .addToRequestQueue(stringRequest)
        }

        private fun getBranches(sites_id: String) {
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
                               // Toast.makeText(requireContext(),"No Site available for this company!!", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                for (i in (array.length() - 1) downTo 0) {
                                    val objectArtist = array.getJSONObject(i)
                                    branches.add(Site_Data(objectArtist.getString("id"),objectArtist.getString("title"),objectArtist.getString("client_code")))
                                    branches_display.add(Site_Data(objectArtist.getString("id"),objectArtist.getString("title"),objectArtist.getString("client_code")))
                                    branches_names.add(objectArtist.getString("title"))
                                }
                            }

                            branch_auto!!.setAdapter(
                                ArrayAdapter(
                                    requireContext(),
                                    R.layout.pay_to_dropdown_layout,
                                    branches_display.map { it.title }
                                )
                            )

                            branch_auto!!.addTextChangedListener(object : TextWatcher {
                                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                                    branches_display.clear()

                                    branches.forEach {
                                        if (it.title.contains(s.toString(), ignoreCase = true)) {
                                            branches_display.add(it)
                                        }
                                    }

                                    // 🔥 MUST UPDATE ADAPTER
                                    branch_auto!!.setAdapter(
                                        ArrayAdapter(
                                            requireContext(),
                                            R.layout.pay_to_dropdown_layout,
                                            branches_display.map { it.title }
                                        )
                                    )
                                }

                                override fun afterTextChanged(s: Editable?) {}
                                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                            })


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

            VolleySingleton.getInstance(requireActivity().applicationContext)
                .addToRequestQueue(stringRequest)
        }*/

    private fun getCompanies(com_id: String, lastLoop: Boolean, loading: ProgressBar) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_ALL_COMPANIES,
            Response.Listener { response ->
                showProgressDialog()
                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        if (array.length() <= 0) {
                            //  Toast.makeText(this,"No companies available!!", Toast.LENGTH_SHORT).show()
                        } else {

                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)
                                companies.add(
                                    Client_Data(
                                        objectArtist.getString("id"),
                                        objectArtist.getString("title")
                                    )
                                )
                                companies_names.add(objectArtist.getString("title"))
                            }
                            if (lastLoop == true) {
                                // loading.visibility = View.GONE
                                hideProgressDialog()
                                Toast.makeText(requireContext(), "It's ready!!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    } else {
                        if (lastLoop == true) {
                            //loading.visibility = View.GONE
                            hideProgressDialog()
                            Toast.makeText(requireContext(), "It's ready!!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } catch (e: JSONException) {
                    //
//                    if(lastLoop == true)
//                    {
//                        //loading.visibility = View.GONE
//                        hideProgressDialog()
//                        Toast.makeText(requireContext(),"It's ready!!", Toast.LENGTH_SHORT).show()
//                    }

                }
            },
            Response.ErrorListener {
                if (lastLoop == true) {
                    //loading.visibility = View.GONE
                    hideProgressDialog()
                    Toast.makeText(requireContext(), "It's ready!!", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["id"] = com_id

                return params

            }
        }


        stringRequest.tag = "DASHBOARD"
        VolleySingleton.getInstance(requireContext())
            .addToRequestQueue(stringRequest)
    }

    private fun getBranches(sites_id: String) {
        showProgressDialog()
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
                            Toast.makeText(
                                requireContext(),
                                "No Site available for this company!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            hideProgressDialog()
                        } else {
                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)
                                branches.add(
                                    Site_Data(
                                        objectArtist.getString("id"),
                                        objectArtist.getString("title"),
                                        objectArtist.getString("client_code")
                                    )
                                )
                                branches_names.add(objectArtist.getString("title"))
                            }
                        }

                        val branch_adapter = ArrayAdapter(
                            requireContext(),
                            R.layout.pay_to_dropdown_layout,
                            branches_names
                        )
                        branch_auto?.setAdapter(branch_adapter)
                        hideProgressDialog()

                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        hideProgressDialog()
                    }

                } catch (e: JSONException) {
                    //
                    hideProgressDialog()

                }
            },
            Response.ErrorListener {
//                Toast.makeText(
//                    requireContext(),
//                    error.message,
//                    Toast.LENGTH_LONG
//                ).show()
                hideProgressDialog()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["id"] = sites_id


                return params

            }
        }
        stringRequest.tag = "DASHBOARD"
        VolleySingleton.getInstance(requireContext())
            .addToRequestQueue(stringRequest)
    }


    private fun getAttendanceSupervisor(fr: String, too: String) {
        attendanceList.clear()
        csvDataResponse = JSONArray()
        showProgressDialog()

        Log.i(
            "testing",
            "site id: ${SharedPrefManager.getInstance(requireActivity()).user.site_id}"
        )
        Log.i(
            "testing",
            "client id: ${SharedPrefManager.getInstance(requireActivity()).user.client_id}"
        )
        Log.i(
            "testing",
            "client code: ${SharedPrefManager.getInstance(requireActivity()).user.client_code}"
        )
        Log.i("testing", "from date: ${fr}")
        Log.i("testing", "to date: ${too}")

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            URLs.URL_GET_EMPLOYEE_ATTENDANCE_REPORT,

            Response.Listener { response ->


                try {
                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {

                        val array = obj.getJSONArray("data")

                        if (array.length() > 0) {

                            attendanceListForDownload.clear()
                            csvDataResponse = array

                            Log.i("test","function response - ${csvDataResponse.toString()}")


                            for (i in 0 until array.length()) {

                                val item = array.getJSONObject(i)

                                val empCode =
                                    item.optString("emp_code").takeIf { it != "null" } ?: ""
                                val empName =
                                    item.optString("emp_name").takeIf { it != "null" } ?: "N/A"

                                val total = item.optString("total", "0")
                                val present = item.optString("present", "0")
                                val absent = item.optString("absent", "0")
                                val halfday = item.optString("halfday", "0")
                                val emp_code = item.optString("emp_code", "")
                                val sl = item.optString("sl", "0")
                                val pl = item.optString("pl", "0")
                                val cl = item.optString("cl", "0")
                                val ml = item.optString("ml", "0")
                                val totalLeave = item.optString("total_leave", "0")

                                val data = EmployeeAttendanceSummaryData(
                                    empCode,
                                    empName,
                                    total,
                                    present,
                                    absent,
                                    halfday,
                                    emp_code,
                                    totalLeave,
                                    sl,
                                    ml,
                                    pl,
                                    cl
                                )

                                attendanceList.add(data)
                            }

                            empadapter = EmployeeAttendanceSummaryAdapter(attendanceList, this)
                            binding.emplist.adapter = empadapter


                            val nameAdapter = EmployeeNameAdapter(attendanceList)
                            binding.rvNames.adapter = nameAdapter

                            binding.emplist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                    binding.rvNames.scrollBy(0, dy)
                                }
                            })

//                            binding.attendanceReportList.visibility = View.VISIBLE
//                            binding.notAvailable1.visibility = View.GONE

                        } else {
//                            binding.notAvailable1.visibility = View.VISIBLE
//                            binding.attendanceReportList.visibility = View.GONE
                        }
                        hideProgressDialog()

                    } else {
                        progressDialog
//                        binding.notAvailable1.visibility = View.VISIBLE
//                        binding.attendanceReportList.visibility = View.GONE
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                    hideProgressDialog()
//                    binding.notAvailable1.visibility = View.VISIBLE
//                    binding.attendanceReportList.visibility = View.GONE
                }

//                isLoading = false
//                hideProgressDialog()
            },

            Response.ErrorListener { error ->
                error.printStackTrace()
                hideProgressDialog()
//                binding.notAvailable1.visibility = View.VISIBLE
//                binding.attendanceReportList.visibility = View.GONE
//                isLoading = false
//                hideProgressDialog()
            }

        ) {

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {

                val params = HashMap<String, String>()

                params["from_date"] = fr
                params["to_date"] = too
                params["site_id"] =
                    SharedPrefManager.getInstance(requireActivity()).user.site_id
                params["client_id"] =
                    SharedPrefManager.getInstance(requireActivity()).user.client_id
//                params["limit"] = limit.toString()
//                params["offset"] = offset.toString()

                return params
            }
        }

        stringRequest.tag = "DASHBOARD"
        VolleySingleton.getInstance(requireActivity())
            .addToRequestQueue(stringRequest)
    }


    private fun getAttendanceCountsSupervisor(fr: String, too: String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_SUPERVISOR_ATTENDANCE_COUNT_FOR_DASHBOARD,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")


                        if (array.length() <= 0) {
                            Toast.makeText(
                                requireContext(),
                                "No attendance for this dates!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)
                                if (objectArtist.optString("expected")
                                        .isNotBlank() && objectArtist.optString("expected") != "null"
                                ) {
                                    binding.textView334.text = objectArtist.optString("expected")
                                } else {
                                    binding.textView334.text = "0"
                                    binding.textView334.visibility = View.INVISIBLE
                                }

                                if (objectArtist.optString("count")
                                        .isNotBlank() && objectArtist.optString("count") != "null"
                                ) {
                                    binding.textView335.text = objectArtist.optString("count")
                                } else {
                                    binding.textView335.text = "0"
                                }

                                if (objectArtist.optString("absent")
                                        .isNotBlank() && objectArtist.optString("absent") != "null"
                                ) {
                                    binding.textView336.text = objectArtist.optString("absent")
                                } else {
                                    binding.textView336.text = "0"
                                }


                                if (objectArtist.optString("sl")
                                        .isNotBlank() && objectArtist.optString("sl") != "null"
                                ) {
                                    binding.textView419.text = objectArtist.optString("sl")
                                } else {
                                    binding.textView419.text = "0"
                                }


                                if (objectArtist.optString("cl")
                                        .isNotBlank() && objectArtist.optString("cl") != "null"
                                ) {
                                    binding.textView420.text = objectArtist.optString("cl")
                                } else {
                                    binding.textView420.text = "0"
                                }

                                if (objectArtist.optString("pl")
                                        .isNotBlank() && objectArtist.optString("pl") != "null"
                                ) {
                                    binding.textView421.text = objectArtist.optString("pl")
                                } else {
                                    binding.textView421.text = "0"
                                }

                                if (objectArtist.optString("h")
                                        .isNotBlank() && objectArtist.optString("h") != "null"
                                ) {
                                    binding.textView423.text = objectArtist.optString("h")
                                } else {
                                    binding.textView423.text = "0"
                                }

                            }
                        }
                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: JSONException) {
                    //
                }

            },
            Response.ErrorListener {
//                Toast.makeText(
//                    requireActivity().applicationContext,
//                    error.message,
//                    Toast.LENGTH_LONG
//                ).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                val user = SharedPrefManager.getInstance(requireActivity().applicationContext).user
                params["site_id"] = user.site_id
                params["client_code"] = user.client_code
                params["from_date"] = fr
                params["to_date"] = too
                return params

            }
        }

        stringRequest.tag = "DASHBOARD"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getSupervisorVoucherCount(fr: String, too: String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_SUPERVISOR_VOUCHER_COUNT_FOR_DASHBOARD,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        val userJson = obj.getJSONObject("user")

                        if (userJson.length() <= 0) {
                            Toast.makeText(
                                requireContext(),
                                "No attendance for this dates!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (userJson.optString("total")
                                    .isNotBlank() && userJson.optString("total") != "null"
                            ) {
                                binding.textView357.text = userJson.optString("total")
                            } else {
                                binding.textView357.text = "0"
                            }

                            if (userJson.optString("sum")
                                    .isNotBlank() && userJson.optString("sum") != "null"
                            ) {
                                binding.textView358.text = userJson.optString("sum")
                            } else {
                                binding.textView358.text = "0"
                            }

                            if (userJson.optString("paid")
                                    .isNotBlank() && userJson.optString("paid") != "null"
                            ) {
                                binding.textView359.text = userJson.optString("paid")
                            } else {
                                binding.textView359.text = "0"
                            }
                            //--------------------------------------------------

                            if (userJson.optString("approved")
                                    .isNotBlank() && userJson.optString("approved") != "null"
                            ) {
                                binding.textView445.text = userJson.optString("approved")
                            } else {
                                binding.textView445.text = "0"
                            }

                            if (userJson.optString("paid_count").isNotBlank() && userJson.optString(
                                    "paid_count"
                                ) != "null"
                            ) {
                                binding.textView452.text = userJson.optString("paid_count")
                            } else {
                                binding.textView452.text = "0"
                            }

                            if (userJson.optString("cancelled")
                                    .isNotBlank() && userJson.optString("cancelled") != "null"
                            ) {
                                binding.textView446.text = userJson.optString("cancelled")
                            } else {
                                binding.textView446.text = "0"
                            }

                            if (userJson.optString("pending")
                                    .isNotBlank() && userJson.optString("pending") != "null"
                            ) {
                                binding.textView443.text = userJson.optString("pending")
                            } else {
                                binding.textView443.text = "0"
                            }


                        }
                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: JSONException) {
                    //
                }

            },
            Response.ErrorListener {
//                Toast.makeText(
//                    requireActivity().applicationContext,
//                    error.message,
//                    Toast.LENGTH_LONG
//                ).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                val user = SharedPrefManager.getInstance(requireActivity().applicationContext).user

                // Access user fields safely
                params["created_by"] = user.id
                params["site_id"] = user.site_id
                params["client_id"] = user.client_id
                params["role"] = user.role
                params["from_date"] = fr
                params["to_date"] = too

                return params

            }
        }
        stringRequest.tag = "DASHBOARD"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getSupervisorNetDaysCount(fr: String, too: String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_SUPERVISOR_NET_DAYS_COUNT_FOR_DASHBOARD,
            Response.Listener { response ->

                try {
                    // converting response to json object
                    val obj = JSONObject(response)

                    // if no error in response
                    if (!obj.getBoolean("error")) {

                        val user = obj.getJSONObject("user")

                        if (user == null) {
                            // Toast.makeText(requireContext(),"No attendance for this dates!!",Toast.LENGTH_SHORT).show()
                        } else {

                            if (user.optString("total")
                                    .isNotBlank() && user.optString("total") != "null"
                            ) {
                                binding.textView362.text = user.optString("total")
                            } else {
                                binding.textView362.text = "0"
                            }

                            if (user.optString("ot_days")
                                    .isNotBlank() && user.optString("ot_days") != "null"
                            ) {
                                binding.textView363.text = user.optString("ot_days")
                            } else {
                                binding.textView363.text = "0"
                            }

                            if (user.optString("ot_hours")
                                    .isNotBlank() && user.optString("ot_hours") != "null"
                            ) {
                                binding.textView364.text = user.optString("ot_hours")
                            } else {
                                binding.textView364.text = "0"
                            }

                            if (user.optString("week_off")
                                    .isNotBlank() && user.optString("week_off") != "null"
                            ) {
                                binding.textView370.text = user.optString("week_off")
                            } else {
                                binding.textView370.text = "0"
                            }

                            if (user.optString("net_days")
                                    .isNotBlank() && user.optString("net_days") != "null"
                            ) {
                                binding.textView369.text = user.optString("net_days")
                            } else {
                                binding.textView369.text = "0"
                            }
                        }
                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: JSONException) {
//                    
                }

            },
            Response.ErrorListener {
//                Toast.makeText(
//                    requireActivity().applicationContext,
//                    error.message,
//                    Toast.LENGTH_LONG
//                ).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                val user = SharedPrefManager.getInstance(requireActivity().applicationContext).user
                params["site_id"] = user.site_id
                params["client_code"] = user.client_code
                params["role"] = user.role
                params["emp_code"] = user.emp_code
                params["from_date"] = fr
                params["to_date"] = too
                return params
            }
        }
        stringRequest.tag = "DASHBOARD"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getEmployeesCountsSupervisor(fr: String, too: String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_SUPERVISOR_EMPLOYEES_COUNT,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val userJson = obj.getJSONObject("user")

                        if (userJson.optString("total")
                                .isNotBlank() && userJson.optString("total") != "null"
                        ) {
                            binding.textView329.text = userJson.optString("total")
                        } else {
                            binding.textView329.text = "0"
                        }

                        if (userJson.optString("working")
                                .isNotBlank() && userJson.optString("working") != "null"
                        ) {
                            binding.textView328.text = userJson.optString("working")
                        } else {
                            binding.textView328.text = "0"
                        }

                        if (userJson.optString("left")
                                .isNotBlank() && userJson.optString("left") != "null"
                        ) {
                            binding.textView330.text = userJson.optString("left")
                        } else {
                            binding.textView330.text = "0"
                        }
                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: JSONException) {
                    //
                }

            },
            Response.ErrorListener {
//                Toast.makeText(
//                    requireActivity().applicationContext,
//                    error.message,
//                    Toast.LENGTH_LONG
//                ).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                val user = SharedPrefManager.getInstance(requireActivity().applicationContext).user
                params["site_id"] = user.site_id
                params["client_id"] = user.client_id
                params["from_date"] = fr
                params["to_date"] = too
                return params

            }
        }
        stringRequest.tag = "DASHBOARD"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getKycCountsForDashboard() {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_KYC_SUMMARY_COUNTS_FOR_DASHBOARD,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        val userJson = obj.getJSONObject("user")

                        binding.textView401.text =
                            "${userJson.optString("aadhar_done")}/${userJson.optString("aadhar_not")}"
                        binding.textView402.text =
                            "${userJson.optString("pancard_done")}/${userJson.optString("pancard_not")}"
                        binding.textView403.text =
                            "${userJson.optString("uan_done")}/${userJson.optString("uan_not")}"

                        binding.textView407.text =
                            "${userJson.optString("pf_done")}/${userJson.optString("pf_not")}"
                        binding.textView408.text =
                            "${userJson.optString("esis_done")}/${userJson.optString("esis_not")}"
                        binding.textView409.text =
                            "${userJson.optString("passport_done")}/${userJson.optString("passport_not")}"

                        binding.textView413.text =
                            "${userJson.optString("bank_done")}/${userJson.optString("bank_not")}"
                        binding.textView414.text =
                            "${userJson.optString("doj_done")}/${userJson.optString("doj_not")}"
                        binding.textView415.text =
                            "${userJson.optString("dob_done")}/${userJson.optString("dob_not")}"

                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    //
                }
            },
            Response.ErrorListener {
//                Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                val user = SharedPrefManager.getInstance(requireContext()).user
                params["site_id"] = user.site_id
                params["client_id"] = user.client_id
                return params
            }
        }

        stringRequest.tag = "DASHBOARD"
        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }


    override fun onDateFilterSelected(label: String, startDate: String, endDate: String) {
        var i1 = Internet()
// Update UI or call methods in the fragment
        binding.button22.text = label

        from_date = startDate
        to_date = endDate

        Log.i("11111", "$startDate  -  $endDate")
        //Toast.makeText(requireContext(),"$startDate  -  $endDate",Toast.LENGTH_LONG).show()
        // if (i1.checkConnection(requireContext())) {
        getAttendanceCountsSupervisor(startDate, endDate)
        getSupervisorVoucherCount(startDate, endDate)
        getSupervisorNetDaysCount(startDate, endDate)
        getEmployeesCountsSupervisor(startDate, endDate)
        getAttendanceSupervisor(startDate, endDate)
//        } else {
//            Toast.makeText(requireContext(), "No internet connection!!", Toast.LENGTH_LONG).show()
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        VolleySingleton.getInstance(requireActivity()).requestQueue.cancelAll("DASHBOARD")
    }

    private fun showProgressDialog() {
        if (progressDialog == null) {
            val progressBar = ProgressBar(requireContext())
            progressBar.isIndeterminate = true
            val padding = 50
            progressBar.setPadding(padding, padding, padding, padding)

            progressDialog = AlertDialog.Builder(requireContext())
                .setTitle("Loading Data..")
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

    private fun updateDeviceToken(token: String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_ADD_DEVICE_TOKEN,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
//                        Toast.makeText(
//                           applicationContext,
//                            obj.getString("message"),
//                            Toast.LENGTH_SHORT
//                        ).show()
                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    //
                }
            },
            Response.ErrorListener {
//                Toast.makeText(
//                    requireActivity().applicationContext,
//                    error.message,
//                    Toast.LENGTH_SHORT
//                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                val user = SharedPrefManager.getInstance(requireActivity().applicationContext).user
                params["id"] = user.id
                params["token"] = token

                return params
            }
        }
        stringRequest.tag = "DASHBOARD"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun splitdata(csvDataResponse: JSONArray) {
        //StringBuilder  to store the data
        val data = StringBuilder()
        var array: JSONArray


        data.append("Employee Name,Total,Total Present,Total Absent,Total HF,Total CL,Total ML,Total PL,Total SL")
        array = csvDataResponse


        if (array.length() == 0) {
            Toast.makeText(requireContext(), "No data to download", Toast.LENGTH_SHORT).show()
        } else {
            for (i in 0 until array.length()) {
                val item = array.getJSONObject(i)

                val empName = item.optString("emp_name", "N/A")
                val total = item.optString("total", "0")
                val present = item.optString("present", "0")
                val absent = item.optString("absent", "0")
                val halfday = item.optString("halfday", "0")
                val cl = item.optString("cl", "0")
                val ml = item.optString("ml", "0")
                val pl = item.optString("pl", "0")
                val sl = item.optString("sl", "0")

                data.append(
                    "\n" +
                            empName + "," +
                            total + "," +
                            present + "," +
                            absent + "," +
                            halfday + "," +
                            cl + "," +
                            ml + "," +
                            pl + "," +
                            sl
                )
            }

            //before i gave below cust name as ->    this.csvDataResponse.getJSONObject(0).optString("custname")

            CreateCSV(data)

        }
    }

/*    private fun CreateCSV(data: java.lang.StringBuilder) {

        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val dateTime = sdf.format(Date())

        val random = (1000..9999).random()

        try {

            val newFile = File(
                requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                "HRMS Report Files"
            )

            if (!newFile.exists()) {
                newFile.mkdirs()
            }

            try {
                var csvName =
                    requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/HRMS Report Files/" + "HRMS_ATTENDANCE_REPORT_${time}.csv"
                val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                val dateTime = sdf.format(Date())

                val random = (1000..9999).random()

                val file = File(newFile, "HRMS_ATTENDANCE_REPORT_${dateTime}_$random.csv")
                var csvPath = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/HRMS Report Files/"

                Log.i("CSV_PATH", file.absolutePath)
                val fout = FileOutputStream(file)
                fout.write(data.toString().toByteArray())
                fout.close()

                Toast.makeText(requireContext(), "file saved at: $csvPath", Toast.LENGTH_LONG)
                    .show()
                createNotificationChannel()
                showNotification(csvName)

            } catch (e: Exception) {
                // Toast.makeText(requireContext(),e.message,Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {

            Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }*/

    private fun CreateCSV(data: StringBuilder) {

        try {

            val folder = File(
                requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                "HRMS Report Files"
            )

            if (!folder.exists()) {
                folder.mkdirs()
            }

            val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            val dateTime = sdf.format(Date())
            val random = (1000..9999).random()

            val fileName = "HRMS_ATTENDANCE_REPORT_${dateTime}_$random.csv"
            val file = File(folder, fileName)

            Log.i("CSV_PATH", file.absolutePath)

            val fout = FileOutputStream(file)
            fout.write(data.toString().toByteArray())
            fout.close()

            Toast.makeText(
                requireContext(),
                "File saved: $fileName",
                Toast.LENGTH_LONG
            ).show()

            createNotificationChannel()
            showNotification(file.absolutePath)

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), e.message ?: "Error saving file", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_MEDIA_IMAGES,
                    android.Manifest.permission.READ_MEDIA_VIDEO,
                    android.Manifest.permission.READ_MEDIA_AUDIO,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ), 100
            )
        }
    }


    // new notification code
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MyChannel"
            val descriptionText = "Channel for notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(csvName: String) {
        val notificationManager =
            requireActivity().getSystemService(NotificationManager::class.java)
        val notificationBuilder = NotificationCompat.Builder(requireActivity(), "CHANNEL_ID")
            .setSmallIcon(R.drawable.tps_logo) // Your notification icon
            .setContentTitle("Open CSV File")
            .setContentText("Click to open your CSV file.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(getPendingIntent(csvName)) // Set pending intent
            .setAutoCancel(true)

        //notificationManager.notify(1, notificationBuilder.build())

        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }

/*    private fun getPendingIntent(csvName: String): PendingIntent {
        val intent = Intent(requireActivity(), OpenCsvReceiver::class.java)
        intent.putExtra("FILE_URI", csvName) // Adjust file name as necessary
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        return PendingIntent.getBroadcast(
            requireActivity(),
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }*/

    private fun getPendingIntent(csvName: String): PendingIntent {

        val intent = Intent(requireActivity(), OpenCsvReceiver::class.java)
        intent.putExtra("FILE_URI", csvName)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        return PendingIntent.getBroadcast(
            requireActivity(),
            System.currentTimeMillis().toInt(),   // unique request code
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            100 -> if (grantResults.size > 0) {
                val p1 = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val p2 = grantResults[1] == PackageManager.PERMISSION_GRANTED
                val p3 = grantResults[2] == PackageManager.PERMISSION_GRANTED
                val p4 = grantResults[3] == PackageManager.PERMISSION_GRANTED
                val p5 = grantResults[4] == PackageManager.PERMISSION_GRANTED

                if (p1 && p2 && p3 && p4 && p4 && p5) {
                    Toast.makeText(requireContext(), "granted all", Toast.LENGTH_SHORT).show()
                } else {
                }
            }
        }

    }
}

