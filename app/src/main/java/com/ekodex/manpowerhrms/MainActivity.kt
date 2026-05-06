package com.ekodex.manpowerhrms

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Login.LoginActivity
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject
import androidx.appcompat.app.AppCompatDelegate
import com.ekodex.manpowerhrms.Others.InternetNew
import com.google.android.play.core.appupdate.AppUpdateManagerFactory


class MainActivity : BaseActivity(),
    VolleySingleton.ServerStatusListener {

    lateinit var binding:ActivityMainBinding
    lateinit var drawerLayout: DrawerLayout
    private var noInternetDialog: Dialog? = null
    private var serverDownDialog: Dialog? = null

//    val handler = Handler(Looper.getMainLooper())
//    val networkRunnable = object : Runnable {
//        override fun run() {
//            val isConnected = InternetNew().isConnected(this@MainActivity)
//            val serverOk = VolleySingleton.serverAvailable
//
//            when {
//                !isConnected -> showNoInternetDialog()
//                !serverOk -> showServerDownDialog()
//                else -> {
//                    dismissNoInternetDialog()
//                    dismissServerDownDialog()
//                }
//            }
//
//            handler.postDelayed(this, 2500)
//        }
//    }

    //switch store
    lateinit var companies:MutableList<Client_Data>
    lateinit var branches:MutableList<Site_Data>
    lateinit var companies_names:MutableList<String>
    lateinit var branches_names:MutableList<String>
    private var branch_auto: AutoCompleteTextView? = null
    var curtDate:String = ""
    var company_id = ""
    var site_id = ""
    var client_code = ""

    private var progressDialog: AlertDialog? = null

    //play store update
    private val PREF_NAME = "update_pref"
    private val KEY_LAST_SHOWN = "last_update_prompt_time"
    private val TEN_DAYS = 10L * 24 * 60 * 60 * 1000 // 10 days in millis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        supportActionBar?.show()
        supportActionBar?.elevation = 0.0f

        checkForUpdate()


        VolleySingleton.serverStatusListener = this

        companies = mutableListOf()
        branches =  mutableListOf()
        companies_names = mutableListOf()
        branches_names =  mutableListOf()


        //-------------------Check if user is logged in-----------------------------------------------------
        if (!SharedPrefManager.getInstance(this).isLoggedIn) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }
        else if(SharedPrefManager.getInstance(this).isLoggedIn)
        {
            drawerLayout = binding.myDrawer
            setActivityNavigationGraph()
        }

        // user name in the navigation drawer
        val headerView = binding.myNavView.getHeaderView(0)
        val username = headerView.findViewById<TextView>(R.id.textView8)
        val rank = headerView.findViewById<TextView>(R.id.textView50)
        val userImg = headerView.findViewById<ImageView>(R.id.imageView11)
        val switch = headerView.findViewById<Button>(R.id.button3)
        var user = SharedPrefManager.getInstance(this).user


        if(user.role.equals("Employee",ignoreCase = true))
        {
            switch.visibility = View.GONE
        }

        if (SharedPrefManager.getInstance(this).isLoggedIn) {

            val user = SharedPrefManager.getInstance(this).user

            // ---------- FULL NAME SAFE ----------
            val fullName = listOf(user.fname, user.lname)
                .map { it.trim() }
                .filter { it.isNotBlank() && !it.equals("null", ignoreCase = true) }
                .joinToString(" ")
                .ifBlank { "No Name" }     // fallback

            username.text = fullName


            // ---------- RANK SAFE ----------
            val safeRank = user.rank
                ?.takeIf { it.isNotBlank() && !it.equals("null", ignoreCase = true) }
                ?: "NA"

            rank.text = "($safeRank)"


            // ---------- PROFILE IMAGE SAFE ----------
            val gender = user.gender
                ?.trim()
                ?.lowercase()
                ?.takeIf { it.isNotBlank() && it != "null" }

            when (gender) {
                "male" -> userImg.setImageResource(R.drawable.man)
                "female" -> userImg.setImageResource(R.drawable.woman)
                else -> userImg.setImageResource(R.drawable.no_gender) // fallback
            }

        }

        switch.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogView: View = inflater.inflate(R.layout.select_client_popup_layout, null)
            dialogBuilder.setView(dialogView)

            val ok = dialogView.findViewById<Button>(R.id.button22)
            val company_auto = dialogView.findViewById<AutoCompleteTextView>(R.id.company)
            branch_auto = dialogView.findViewById<AutoCompleteTextView>(R.id.branch)
            val loading = dialogView.findViewById<ProgressBar>(R.id.progressBar8)

            val company_adapter = ArrayAdapter(this, R.layout.pay_to_dropdown_layout, companies)
            company_auto.setAdapter(company_adapter)

//                val branch_adapter = ArrayAdapter(requireContext(), R.layout.pay_to_dropdown_layout, branches_names)
//                branch_auto.setAdapter(branch_adapter)

            val alertDialog = dialogBuilder.create()
            alertDialog.setCancelable(true)
            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()

            ok.setOnClickListener {
                if (company_auto.text.isNullOrEmpty()) {
                    Toast.makeText(this, "Please select company!!", Toast.LENGTH_LONG).show()
                } else if (branch_auto!!.text.isNullOrEmpty()) {
                    Toast.makeText(this, "Please select branch!!", Toast.LENGTH_LONG).show()
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
                    finish()
                    startActivity(Intent(this, MainActivity::class.java))
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


                Log.i("11111","Position - ${position}")
                Log.i("11111","client - ${selectedCompany.toString()} , id - ${selectedCompany.id}")


                if (selectedCompany != null) {
                    Toast.makeText(this, "Getting Branch!!", Toast.LENGTH_LONG).show()
                    branch_auto!!.text.clear()

                    company_id = selectedCompany.id
                    branches.clear()
                    branches_names.clear()
                    getBranches(company_id)
                }
            }


//                branch_auto.setOnItemClickListener { _, _, position, _ ->
//                    site_id = branches[position].id
//                    client_code = branches[position].client_code
//                }

            branch_auto!!.setOnItemClickListener { parent, _, position, _ ->
                val selectedBranchName = parent.getItemAtPosition(position) as String
                val selectedBranch = branches.find { it.title == selectedBranchName }

                if (selectedBranch != null) {
                    site_id = selectedBranch.id
                    client_code = selectedBranch.client_code
                }
            }


            // Fetch companies
            Toast.makeText(this, "Wait, fetching companies and sites!!", Toast.LENGTH_LONG).show()
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
        //logout button
        binding.myNavView.getMenu().findItem(R.id.logout)
            .setOnMenuItemClickListener { menuItem ->
                SharedPrefManager.getInstance(applicationContext).logout()
                Toast.makeText(this, "Logout successfully!!", Toast.LENGTH_LONG).show()
                finish()
                true
            }

        binding.myNavView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.dashboardFragment -> {
                    findNavController(R.id.hrmsNavHost)
                        .navigate(R.id.dashboardFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.addNewEmployeeFragment -> {
                    findNavController(R.id.hrmsNavHost)
                        .navigate(R.id.addNewEmployeeFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.employeeDirectoryFragment -> {
                    findNavController(R.id.hrmsNavHost)
                        .navigate(R.id.employeeDirectoryFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                //to add new attendance
                R.id.attendanceFragment -> {
                  if(!SharedPrefManager.getInstance(applicationContext).user.role.equals("employee"))
                  {
                      findNavController(R.id.hrmsNavHost)
                          .navigate(R.id.attendanceSupervisorFragment)
                      binding.myDrawer.closeDrawer(GravityCompat.START, true)
                  }
                  else
                  {
                      findNavController(R.id.hrmsNavHost)
                          .navigate(R.id.attendanceFragment)
                      binding.myDrawer.closeDrawer(GravityCompat.START, true)
                  }
                }
                R.id.myProfileFragment -> {
                    findNavController(R.id.hrmsNavHost)
                        .navigate(R.id.myProfileFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.attendanceReportFragment -> {
                    findNavController(R.id.hrmsNavHost)
                        .navigate(R.id.attendanceReportFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                //to view attendance , filter wise
                R.id.supervisorAttendanceFragment -> {
                        findNavController(R.id.hrmsNavHost)
                            .navigate(R.id.supervisorAttendanceFragment)
                        binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.addVoucherFragment -> {
                    findNavController(R.id.hrmsNavHost)
                        .navigate(R.id.addVoucherFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.addEmployeeVoucherFragment -> {
                    findNavController(R.id.hrmsNavHost)
                        .navigate(R.id.addEmployeeVoucherFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.addSiteVoucherFragment -> {
                    findNavController(R.id.hrmsNavHost)
                        .navigate(R.id.addSiteVoucherFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.addSplitVoucherFragment -> {
                    findNavController(R.id.hrmsNavHost)
                        .navigate(R.id.addSplitVoucherFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                        R.id.voucherSummaryFragment -> {
                    findNavController(R.id.hrmsNavHost)
                        .navigate(R.id.voucherSummaryFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.voucherManagementFragment -> {
                    findNavController(R.id.hrmsNavHost)
                        .navigate(R.id.voucherManagementFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.salarySlipFragment -> {
                    findNavController(R.id.hrmsNavHost)
                        .navigate(R.id.salarySlipFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.bulkAttendanceDownloadFragment -> {
                    findNavController(R.id.hrmsNavHost)
                        .navigate(R.id.bulkAttendanceDownloadFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.uploadWorkOrderFragment -> {
                    findNavController(R.id.hrmsNavHost)
                        .navigate(R.id.uploadWorkOrderFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.leavesManagementFragment -> {
                    findNavController(R.id.hrmsNavHost)
                        .navigate(R.id.leavesManagementFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }
                R.id.attendanceErrorReportFragment -> {
                    findNavController(R.id.hrmsNavHost)
                        .navigate(R.id.attendanceErrorReportFragment)
                    binding.myDrawer.closeDrawer(GravityCompat.START, true)
                }

            }
            true
        }


    }

    override fun onServerDown() {
        if (!InternetNew().isConnected(this)) {
            showNoInternetDialog()
        } else {
            showServerDownDialog()
        }
    }

    override fun onServerUp() {
        dismissNoInternetDialog()
        dismissServerDownDialog()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.hrmsNavHost)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    private fun setActivityNavigationGraph() {
        if (SharedPrefManager.getInstance(applicationContext).user.onboarding == "0") {

            supportActionBar?.hide()

            val navHostFragment =
                (supportFragmentManager.findFragmentById(R.id.hrmsNavHost) as NavHostFragment)

            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.hrms_nav_graph)
            graph.setStartDestination(R.id.onboardingFragment)

            navHostFragment.navController.graph = graph
            drawerLayout = binding.myDrawer
            NavigationUI.setupActionBarWithNavController(
                this,
                navHostFragment.navController,
                drawerLayout
            )
            NavigationUI.setupWithNavController(
                binding.myNavView,
                navHostFragment.navController
            )

        }
        else if(SharedPrefManager.getInstance(applicationContext).user.onboarding == "1"){

            supportActionBar?.show()

            val navHostFragment =
                (supportFragmentManager.findFragmentById(R.id.hrmsNavHost) as NavHostFragment)

            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.hrms_nav_graph)
            graph.setStartDestination(R.id.dashboardFragment)

            navHostFragment.navController.graph = graph
            drawerLayout = binding.myDrawer
            NavigationUI.setupActionBarWithNavController(
                this,
                navHostFragment.navController,
                drawerLayout
            )
            NavigationUI.setupWithNavController(
                binding.myNavView,
                navHostFragment.navController
            )

            if(SharedPrefManager.getInstance(this).user.role.equals("Employee", ignoreCase = true))
            {
                binding.myNavView.getMenu().findItem(R.id.addNewEmployeeFragment).isVisible = false
                binding.myNavView.getMenu().findItem(R.id.employeeDirectoryFragment).isVisible = false
                binding.myNavView.getMenu().findItem(R.id.attendanceReportFragment).isVisible = false
            }

        }
    }

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
                        }
                        else{

                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)
                                companies.add(Client_Data(objectArtist.getString("id"),objectArtist.getString("title")))
                                companies_names.add(objectArtist.getString("title"))
                            }
                            if(lastLoop == true)
                            {
                                // loading.visibility = View.GONE
                                hideProgressDialog()
                                Toast.makeText(this,"It's ready!!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        if(lastLoop == true)
                        {
                            //loading.visibility = View.GONE
                            hideProgressDialog()
                            Toast.makeText(this,"It's ready!!", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: JSONException) {
                    
                    if(lastLoop == true)
                    {
                        //loading.visibility = View.GONE
                        hideProgressDialog()
                        Toast.makeText(this,"It's ready!!", Toast.LENGTH_SHORT).show()
                    }

                }
            },
            Response.ErrorListener { error ->
                if(lastLoop == true)
                {
                    //loading.visibility = View.GONE
                    hideProgressDialog()
                    Toast.makeText(this,"It's ready!!", Toast.LENGTH_SHORT).show()
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



        VolleySingleton.getInstance(this)
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
                            Toast.makeText(this,"No Site available for this company!!", Toast.LENGTH_SHORT).show()
                            hideProgressDialog()
                        }
                        else{
                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)
                                branches.add(Site_Data(objectArtist.getString("id"),objectArtist.getString("title"),objectArtist.getString("client_code")))
                                branches_names.add(objectArtist.getString("title"))
                            }
                        }

                        val branch_adapter = ArrayAdapter(
                            this,
                            R.layout.pay_to_dropdown_layout,
                            branches_names
                        )
                        branch_auto?.setAdapter(branch_adapter)
                        hideProgressDialog()

                    } else {
                        Toast.makeText(
                           this,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        hideProgressDialog()
                    }

                } catch (e: JSONException) {
                    
                    hideProgressDialog()

                }

            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    this,
                    error.message,
                    Toast.LENGTH_LONG
                ).show()
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

        VolleySingleton.getInstance(this)
            .addToRequestQueue(stringRequest)
    }

    private fun showProgressDialog() {
        if (progressDialog == null) {
            val progressBar = ProgressBar(this)
            progressBar.isIndeterminate = true
            val padding = 50
            progressBar.setPadding(padding, padding, padding, padding)

            progressDialog = AlertDialog.Builder(this)
                .setTitle("Loading companies")
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

 /*   private fun observeInternet() {
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {

                val isConnected = InternetNew().isConnected(this@MainActivity)

                if (!isConnected) showNoInternetDialog()
                else dismissNoInternetDialog()

                handler.postDelayed(this, 1000) // checks every 1 second
            }
        })
    }*/


    private fun showNoInternetDialog() {
        if (noInternetDialog != null && noInternetDialog!!.isShowing) return

        noInternetDialog = Dialog(this)
        noInternetDialog!!.setContentView(R.layout.no_internet_dialog)
        noInternetDialog!!.setCancelable(false)
        noInternetDialog!!.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val retryBtn = noInternetDialog!!.findViewById<Button>(R.id.btnRetry)
//        retryBtn.setOnClickListener {
//            if (InternetNew().isConnected(this)) dismissNoInternetDialog()
//        }

        retryBtn.setOnClickListener {
            if (InternetNew().isConnected(this)) {
                dismissNoInternetDialog()
            }
        }


        noInternetDialog!!.show()
    }

    private fun dismissNoInternetDialog() {
        if (noInternetDialog != null && noInternetDialog!!.isShowing) {
            noInternetDialog!!.dismiss()
        }
    }

    private fun showServerDownDialog() {
        if (serverDownDialog != null && serverDownDialog!!.isShowing) return

        serverDownDialog = Dialog(this)
        serverDownDialog!!.setContentView(R.layout.no_server_dialog) // create a new layout like no_internet_dialog
        serverDownDialog!!.setCancelable(false)
        serverDownDialog!!.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val retryBtn = serverDownDialog!!.findViewById<Button>(R.id.btnRetry)

        retryBtn.setOnClickListener {
            dismissServerDownDialog()
        }

//        retryBtn.setOnClickListener {
//            // Retry button simply rechecks server availability
//            if (VolleySingleton.serverAvailable) dismissServerDownDialog()
//        }

        serverDownDialog!!.show()
    }

    private fun dismissServerDownDialog() {
        if (serverDownDialog != null && serverDownDialog!!.isShowing) {
            serverDownDialog!!.dismiss()
        }
    }

//    override fun onResume() {
//        super.onResume()
//        handler.post(networkRunnable)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        handler.removeCallbacks(networkRunnable)
//    }

    override fun onDestroy() {
        super.onDestroy()
        if (VolleySingleton.serverStatusListener === this) {
            VolleySingleton.serverStatusListener = null
        }
    }

    private fun checkForUpdate() {

        val prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val lastShown = prefs.getLong(KEY_LAST_SHOWN, 0L)
        val now = System.currentTimeMillis()

        // If dialog was shown within last 10 days → do not show again
        if (now - lastShown < TEN_DAYS) {
            return
        }

        val appUpdateManager = AppUpdateManagerFactory.create(this)

        appUpdateManager.appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->

                if (appUpdateInfo.updateAvailability() ==
                    com.google.android.play.core.install.model.UpdateAvailability.UPDATE_AVAILABLE
                ) {
                    // Show custom dialog
                    showUpdateDialog()
                }

            }
            .addOnFailureListener {
                // Optional: log error silently
                // Log.e("UpdateCheck", "Failed to check update", it)
            }
    }


    private fun showUpdateDialog() {

        val dialogView = layoutInflater.inflate(R.layout.dialog_update, null)

        val btnLater = dialogView.findViewById<TextView>(R.id.btnLater)
        val btnUpdate = dialogView.findViewById<TextView>(R.id.btnUpdate)

        val dialog = android.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)   // ❗ Important
            .create()

        dialog.setCanceledOnTouchOutside(false) // Extra safety

        btnLater.setOnClickListener {

            // Save 10-day reminder time
            getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                .edit()
                .putLong(KEY_LAST_SHOWN, System.currentTimeMillis())
                .apply()

            dialog.dismiss()
        }

        btnUpdate.setOnClickListener {

            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$packageName")
                    )
                )
            } catch (e: Exception) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                    )
                )
            }

            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

}




