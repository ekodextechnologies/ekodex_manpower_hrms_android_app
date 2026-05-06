package com.ekodex.manpowerhrms.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Forgot_Password.ForgotPasswordActivity
import com.ekodex.manpowerhrms.MainActivity
import com.ekodex.manpowerhrms.Others.*
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.databinding.ActivityLoginBinding
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    //initially password is hidden
    var passwordShown = false
    private var progressDialog: AlertDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        supportActionBar?.hide()

        binding.button2.setOnClickListener {
            userLogin()
        }

        //show/hide password
        binding.imageView12.setOnClickListener {
            if (passwordShown == false) {
                binding.imageView12.setImageResource(R.drawable.show)
                binding.editTextTextPersonName2.inputType =
                    InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                binding.editTextTextPersonName2.setSelection(binding.editTextTextPersonName2.getText().length);
                passwordShown = true
            } else if (passwordShown == true) {
                binding.imageView12.setImageResource(R.drawable.hide)
                binding.editTextTextPersonName2.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.editTextTextPersonName2.setSelection(binding.editTextTextPersonName2.getText().length);
                passwordShown = false
            }
        }

        //forgot password
        binding.textView2.setOnClickListener {
            startActivity(Intent(applicationContext, ForgotPasswordActivity::class.java))
        }
    }

    private fun userLogin() {

        val email = binding.editTextTextPersonName.text.toString()
        val password = binding.editTextTextPersonName2.text.toString()

        if (TextUtils.isEmpty(email)) {
            binding.editTextTextPersonName.error = "Please enter your email id"
            binding.editTextTextPersonName.requestFocus()
            return
        }

        if (TextUtils.getTrimmedLength(email) > 0 && !android.util.Patterns.EMAIL_ADDRESS.matcher(
                email
            ).matches()
        ) {
            binding.editTextTextPersonName.error = "Enter a valid email id"
            binding.editTextTextPersonName.requestFocus()
            return
        }

        if (TextUtils.isEmpty(password)) {
            binding.editTextTextPersonName2.error = "Please enter your password"
            binding.editTextTextPersonName2.requestFocus()
            return
        }

        var i1 = Internet()
        if (i1.checkConnection(this)) {

            showProgressDialog()
           // binding.progressBar3.visibility = View.VISIBLE

            val stringRequest = object : StringRequest(
                Request.Method.POST, URLs.URL_LOGIN,
                Response.Listener { response ->

                    try {
                        //converting response to json object
                        val obj = JSONObject(response)

                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            Toast.makeText(
                                applicationContext,
                                obj.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()
                            //getting the user from the response
                            val userJson = obj.getJSONObject("user")
                            if (userJson != null) {

                                    //creating a new user object
                                    val user = User(
                                        userJson.getString("id"),
                                        userJson.getString("emp_code"),
                                        userJson.getString("fname"),
                                        userJson.getString("lname"),
                                        userJson.getString("gender"),
                                        userJson.getString("rank"),
                                        userJson.getString("email"),
                                        userJson.getString("phone"),
                                        userJson.getString("address"),
                                        userJson.getString("state"),
                                        userJson.getString("city"),
                                        userJson.getString("pincode"),
                                        userJson.getString("onboarding"),
                                        userJson.getString("role"),
                                        userJson.getString("client_id"),
                                        userJson.getString("site_id"),
                                        userJson.getString("client_code"),
                                        0,
                                        userJson.getString("client_id"),
                                        userJson.getString("site_id"),
                                        userJson.getString("bank_name"),
                                        userJson.getString("account_no"),
                                        userJson.getString("bank_ifsc"),
                                        userJson.getString("ac_holder_name"),
                                        userJson.getString("bank_address"),
                                        userJson.getString("bank_state"),
                                        userJson.getString("bank_city"),
                                        userJson.getString("bank_micr"),
                                        userJson.getString("card_no"),

                                        )

                                    //storing the user in shared preferences
                                    SharedPrefManager.getInstance(applicationContext).userLogin(user)

                             //   binding.progressBar3.visibility = View.GONE

                                hideProgressDialog()
                                    //starting the MainActivity
                                    finish()

                                    startActivity(Intent(applicationContext, MainActivity::class.java))
                            }
                            else{
                                hideProgressDialog()
                            }
                        }
                        else {
                            Toast.makeText(
                                applicationContext,
                                obj.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()
                          //  binding.progressBar3.visibility = View.GONE
                            hideProgressDialog()

                            //----------   go to not register activity -------------
//                            finish()
//                            startActivity(Intent(applicationContext, NotRegisteredActivity::class.java))
                            //----------------------------------------------------
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(
                            applicationContext,
                            "Some issue ongoing , please try again!!",
                            Toast.LENGTH_SHORT
                        ).show()
                     //   binding.progressBar3.visibility = View.GONE
                     //   hideProgressDialog()

                    }
                },
                Response.ErrorListener { error ->
                    if (error.message != null) {
                        Toast.makeText(
                            applicationContext,
                            "Some issue ongoing , please try again!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()

                    params["email"] = email
                    params["password"] = password

                    return params
                }
            }

            VolleySingleton.getInstance(applicationContext)
                .addToRequestQueue(stringRequest)

        } else {
            Toast.makeText(this, "No internet available!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgressDialog() {
        if (progressDialog == null) {
            val progressBar = ProgressBar(this)
            progressBar.isIndeterminate = true
            val padding = 50
            progressBar.setPadding(padding, padding, padding, padding)

            progressDialog = AlertDialog.Builder(this)
                .setTitle("Processing")
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