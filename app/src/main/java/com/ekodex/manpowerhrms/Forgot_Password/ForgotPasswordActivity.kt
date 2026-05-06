package com.ekodex.manpowerhrms.Forgot_Password

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Login.LoginActivity
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.databinding.ActivityForgotPasswordBinding
import org.json.JSONException
import org.json.JSONObject

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityForgotPasswordBinding
    private var isEmailVerified = false  // track verification step
    private var progressDialog: AlertDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        supportActionBar?.hide()

        // Initial UI
        showEmailStep()

        // Button click listener
        binding.buttonVerifyEmail.setOnClickListener {
            if (!isEmailVerified) {
                sendRecoveryCode()
            } else {
                resetPassword()
            }
        }

        // Sign in click
        binding.textViewSignIn.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }
    }

    private fun showEmailStep() {
        binding.editTextEmail.visibility = View.VISIBLE
        binding.buttonVerifyEmail.text = "SEND OTP"
        binding.editTextRecoveryKey.visibility = View.GONE
        binding.editTextNewPassword.visibility = View.GONE
        binding.editTextConfirmPassword.visibility = View.GONE
        binding.buttonResetPassword.visibility = View.GONE
    }

    private fun showResetStep() {
        binding.editTextRecoveryKey.visibility = View.VISIBLE
        binding.editTextNewPassword.visibility = View.VISIBLE
        binding.editTextConfirmPassword.visibility = View.VISIBLE
        binding.buttonVerifyEmail.text = "RESET PASSWORD"
        isEmailVerified = true
    }

    // Step 1: Send Recovery Key
    private fun sendRecoveryCode() {
        val email = binding.editTextEmail.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            binding.editTextEmail.error = "Please enter your registered email"
            binding.editTextEmail.requestFocus()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.error = "Enter a valid email"
            binding.editTextEmail.requestFocus()
            return
        }

        showProgressDialog()
        // Volley request to send OTP
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_SEND_PASSWORD_RECOVERY,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(this, obj.getString("message"), Toast.LENGTH_SHORT).show()
                        showResetStep() // show OTP + password fields
                        hideProgressDialog()
                    } else {
                        Toast.makeText(this, obj.getString("message"), Toast.LENGTH_SHORT).show()
                 hideProgressDialog()
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error parsing response", Toast.LENGTH_SHORT).show()
             hideProgressDialog()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.message ?: "Something went wrong", Toast.LENGTH_SHORT).show()
           hideProgressDialog()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                return hashMapOf("email" to email)
            }
        }

        VolleySingleton.getInstance(applicationContext).addToRequestQueue(stringRequest)
    }

    // Step 2: Reset Password
    private fun resetPassword() {
        val email = binding.editTextEmail.text.toString().trim()
        val otp = binding.editTextRecoveryKey.text.toString().trim()
        val newPassword = binding.editTextNewPassword.text.toString().trim()
        val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()

        if (TextUtils.isEmpty(otp)) {
            binding.editTextRecoveryKey.error = "Enter OTP sent to your email"
            binding.editTextRecoveryKey.requestFocus()
            return
        }
        if (TextUtils.isEmpty(newPassword)) {
            binding.editTextNewPassword.error = "Enter new password"
            binding.editTextNewPassword.requestFocus()
            return
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            binding.editTextConfirmPassword.error = "Confirm your password"
            binding.editTextConfirmPassword.requestFocus()
            return
        }
        if (newPassword != confirmPassword) {
            binding.editTextConfirmPassword.error = "Passwords do not match"
            binding.editTextConfirmPassword.requestFocus()
            return
        }

        showProgressDialog()
        // Volley request to reset password
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_RESET_PASSWORD,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(this, obj.getString("message"), Toast.LENGTH_SHORT).show()
                        hideProgressDialog()
                        startActivity(Intent(applicationContext, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, obj.getString("message"), Toast.LENGTH_SHORT).show()
                        if (obj.getString("message").contains("expired", true)) {
                            // Clear old OTP
                            binding.editTextRecoveryKey.text?.clear()

                            // Send new OTP silently
                            sendRecoveryCode()
                        }
                        hideProgressDialog()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error parsing response", Toast.LENGTH_SHORT).show()
                    hideProgressDialog()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.message ?: "Something went wrong", Toast.LENGTH_SHORT).show()
                hideProgressDialog()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                return hashMapOf(
                    "email" to email,
                    "recovery_key" to otp,
                    "new_password" to newPassword
                )
            }
        }

        VolleySingleton.getInstance(applicationContext).addToRequestQueue(stringRequest)
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
