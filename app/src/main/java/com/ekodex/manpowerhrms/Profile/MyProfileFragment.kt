package com.ekodex.manpowerhrms.Profile

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.databinding.FragmentMyProfileBinding
import org.json.JSONException
import org.json.JSONObject

class MyProfileFragment : Fragment() {

    lateinit var binding: FragmentMyProfileBinding
    var passport_no = ""
    var passport_valid_date = ""
    var date_of_birth = ""
    var client_name = ""
    var site_name = ""

    var aadhar_no = ""
    var pan_no = ""
    var uan_no = ""
    var esis_no = ""
    var pf_no = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile,container,false)

        //------------------------------------------------------------------------------------------------

        val pref = SharedPrefManager.getInstance(requireContext())


        if (pref.isLoggedIn) {

            if(!pref.user.role.equals("employee",ignoreCase = true))
            {
                binding.cardView7.visibility = View.GONE
                binding.cardView8.visibility = View.GONE
                binding.cardView9.visibility = View.GONE
            }

            getMyProfile()

            val user = pref.user
            // Safely build full name
            val fullName = listOf(user.fname, user.lname)
                .map { value ->
                    value?.takeIf {
                        it.isNotBlank() && !it.equals("null", ignoreCase = true)
                    }
                }
                .filterNotNull()
                .joinToString(" ")
                .ifBlank { "No Name" }

            binding.textView13.text = fullName


            // Safely get empCode and rank
            val empCode = user.emp_code
                ?.takeIf { it.isNotBlank() && !it.equals("null", ignoreCase = true) }
                ?: "NA"

            val rank = user.rank
                ?.takeIf { it.isNotBlank() && !it.equals("null", ignoreCase = true) }
                ?: "NA"


            binding.textView17.text = "(Employee code : $empCode | Designation : $rank)"

            //------------------------ setting profile image ----------------------------------
            val gender = user.gender
                ?.trim()
                ?.lowercase()
                ?.takeIf { it.isNotBlank() && it != "null" }

            when (gender) {
                "male" -> binding.imageView21.setImageResource(R.drawable.man)
                "female" -> binding.imageView21.setImageResource(R.drawable.woman)
                else -> binding.imageView21.setImageResource(R.drawable.no_gender) // fallback
            }

        } else {
            // Not logged in - show defaults
            binding.textView13.text = "Name not available"
            binding.textView17.text = "(Employee code : NA | Designation : NA)"
            binding.imageView21.setImageResource(R.drawable.no_gender)
        }

        //-------------------------------------------------------------------------------------------------


        //contact details
        binding.cardView10.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireActivity())

            val inflater = requireActivity().layoutInflater
            val dialogView: View = inflater.inflate(R.layout.contact_details_dialog_layout, null)
            dialogBuilder.setView(dialogView)

            val phone = dialogView.findViewById<TextView>(R.id.textView70)
            val email = dialogView.findViewById<TextView>(R.id.textView67)
            val address = dialogView.findViewById<TextView>(R.id.textView43)
            val close = dialogView.findViewById<Button>(R.id.update)

            val user = SharedPrefManager.getInstance(requireActivity()).user

            // Safe fallback values with null + blank + "null" checks
            val safePhone = user.phone
                ?.takeIf { it.isNotBlank() && !it.equals("null", ignoreCase = true) }
                ?: "NA"

            val safeEmail = user.email
                ?.takeIf { it.isNotBlank() && !it.equals("null", ignoreCase = true) }
                ?: "NA"

// Address fields combined safely
            val safeAddress = listOf(
                user.address,
                user.state,
                user.city,
                user.pincode
            )
                .map { it?.takeIf { v -> v.isNotBlank() && !v.equals("null", ignoreCase = true) } }
                .filterNotNull()
                .joinToString(", ")
                .ifBlank { "NA" }

            phone.text = safePhone
            email.text = safeEmail
            address.text = safeAddress

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            close.setOnClickListener {
                alertDialog.dismiss()
            }
        }


        //bank details
        binding.cardView11.setOnClickListener {
            findNavController().navigate(MyProfileFragmentDirections.actionMyProfileFragmentToMyBanksFragment())
//            val dialogBuilder = AlertDialog.Builder(requireActivity())
//
//            val inflater = requireActivity().layoutInflater
//            val dialogView: View = inflater.inflate(R.layout.bank_details_dialog_layout, null)
//            dialogBuilder.setView(dialogView)
//
//            val phone = dialogView.findViewById<TextView>(R.id.textView70)
//            val email = dialogView.findViewById<TextView>(R.id.textView67)
//            val address = dialogView.findViewById<TextView>(R.id.textView43)
//            val close = dialogView.findViewById<Button>(R.id.update)
//            val alertDialog = dialogBuilder.create()
//            alertDialog.show()
//
//            close.setOnClickListener {
//                alertDialog.dismiss()
//            }
        }

        //passport details
        binding.cardView9.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireActivity())

            val inflater = requireActivity().layoutInflater
            val dialogView: View = inflater.inflate(R.layout.passport_details_dialog_layout, null)
            dialogBuilder.setView(dialogView)

            val name = dialogView.findViewById<TextView>(R.id.textView70)
            val phone = dialogView.findViewById<TextView>(R.id.textView67)
            val pass_no = dialogView.findViewById<TextView>(R.id.textView43)
            val gender = dialogView.findViewById<TextView>(R.id.textView44)
            val pass_valid_date = dialogView.findViewById<TextView>(R.id.textView47)
            val close = dialogView.findViewById<Button>(R.id.update)

            val first = pref.user.fname.safe()
            val last = pref.user.lname.safe()

            name.text = when {
                first == "NA" && last == "NA" -> "NA"           // both missing
                first == "NA" -> last                           // only last available
                last == "NA" -> first                           // only first available
                else -> "$first $last"                          // both available
            }


            phone.text = pref.user.phone.safe()
            gender.text = pref.user.gender.safe()

            pass_no.setText(passport_no)
            pass_valid_date.setText(passport_valid_date)

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            close.setOnClickListener {
                alertDialog.dismiss()
            }
        }

        // personal details
        binding.cardView6.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireActivity())

            val inflater = requireActivity().layoutInflater
            val dialogView: View = inflater.inflate(R.layout.personal_details_dialog_layout, null)
            dialogBuilder.setView(dialogView)

            val name = dialogView.findViewById<TextView>(R.id.textView70)
            val phone = dialogView.findViewById<TextView>(R.id.textView67)
            val email = dialogView.findViewById<TextView>(R.id.textView43)
            val gender = dialogView.findViewById<TextView>(R.id.textView44)
            val dob = dialogView.findViewById<TextView>(R.id.textView47)
            val dob_icon = dialogView.findViewById<ImageView>(R.id.imageView33)
            val address = dialogView.findViewById<TextView>(R.id.textView45)
            val close = dialogView.findViewById<Button>(R.id.update)

            val user = pref.user

// Apply safe() to each address field before combining
            val safeAddress = listOf(
                user.address.safe(),
                user.state.safe(),
                user.city.safe(),
                user.pincode.safe()
            )
                .filter { it != "NA" }     // remove empty/NA values
                .joinToString(", ")
                .ifBlank { "NA" }

// Use safe() directly for all fields

            val first = pref.user.fname.safe()
            val last = pref.user.lname.safe()

            name.text = when {
                first == "NA" && last == "NA" -> "NA"           // both missing
                first == "NA" -> last                           // only last available
                last == "NA" -> first                           // only first available
                else -> "$first $last"                          // both available
            }


            phone.text = user.phone.safe()
            email.text = user.email.safe()
            gender.text = user.gender.safe()
            address.text = safeAddress


            //non employee
            if(pref.user.role.equals("employee",ignoreCase = true))
            {
                dob.setText(date_of_birth)
            }
            else
            {
                dob.visibility = View.GONE
                dob_icon.visibility = View.GONE
            }




            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            close.setOnClickListener {
                alertDialog.dismiss()
            }
        }

        // employee details
        binding.cardView7.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireActivity())

            val inflater = requireActivity().layoutInflater
            val dialogView: View = inflater.inflate(R.layout.employee_details_dialog_layout, null)
            dialogBuilder.setView(dialogView)

            val name = dialogView.findViewById<TextView>(R.id.textView70)
            val rank = dialogView.findViewById<TextView>(R.id.textView67)
            val client = dialogView.findViewById<TextView>(R.id.textView43)
            val site = dialogView.findViewById<TextView>(R.id.textView44)
            val empcode = dialogView.findViewById<TextView>(R.id.textView47)
            val close = dialogView.findViewById<Button>(R.id.update)

            val first = pref.user.fname.safe()
            val last = pref.user.lname.safe()

            name.text = when {
                first == "NA" && last == "NA" -> "NA"           // both missing
                first == "NA" -> last                           // only last available
                last == "NA" -> first                           // only first available
                else -> "$first $last"                          // both available
            }


            rank.text = "Rank : ${pref.user.rank.safe()}"
            empcode.text = "Employee Code : ${pref.user.emp_code.safe()}"
            client.setText("Client : " + client_name)
            site.setText("Site : " + site_name)


            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            close.setOnClickListener {
                alertDialog.dismiss()
            }
        }

        // document details details
        binding.cardView8.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireActivity())

            val inflater = requireActivity().layoutInflater
            val dialogView: View = inflater.inflate(R.layout.employee_document_details_layout, null)
            dialogBuilder.setView(dialogView)

            val name = dialogView.findViewById<TextView>(R.id.textView70)
            val aadhar = dialogView.findViewById<TextView>(R.id.textView67)
            val pan = dialogView.findViewById<TextView>(R.id.textView43)
            val uan = dialogView.findViewById<TextView>(R.id.textView44)
            val pf = dialogView.findViewById<TextView>(R.id.textView47)
            val esis = dialogView.findViewById<TextView>(R.id.textView397)
            val passport = dialogView.findViewById<TextView>(R.id.textView468)
            val close = dialogView.findViewById<Button>(R.id.update)

            val first = pref.user.fname.safe()
            val last = pref.user.lname.safe()

            name.text = when {
                first == "NA" && last == "NA" -> "NA"           // both missing
                first == "NA" -> last                           // only last available
                last == "NA" -> first                           // only first available
                else -> "$first $last"                          // both available
            }


            aadhar.setText("Aadhar No : " + aadhar_no)
            pan.setText("Pancard No : " + pan_no)
            uan.setText("UAN No : " + uan_no)
            pf.setText("PF No : " + pf_no)
            esis.setText("ESIS No : " + esis_no)
            passport.setText("Passport No : " + passport_no)

            val alertDialog = dialogBuilder.create()
            alertDialog.show()

            close.setOnClickListener {
                alertDialog.dismiss()
            }
        }

        return binding.root

    }

    private fun getMyProfile() {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_MY_PROFILE,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()

                        //getting the user from the response
                        val userJson = obj.getJSONObject("user")
                     //   binding.editTextTextPersonName26.setText(userJson.getString("marital_status"))
                        passport_no = userJson.getString("passport_no").safe()
                        passport_valid_date = userJson.getString("passport_valid_date").safe()
                        date_of_birth = userJson.getString("dob").safe()
                        client_name = userJson.getString("client_name").safe()
                        site_name = userJson.getString("site_name").safe()
                        aadhar_no = userJson.getString("aadhar_no").safe()
                        pan_no = userJson.getString("pancard_no").safe()
                        uan_no = userJson.getString("uan_no").safe()
                        esis_no = userJson.getString("esis_no").safe()
                        pf_no = userJson.getString("pf_no").safe()

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
                Toast.makeText(
                    requireActivity().applicationContext,
                    error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["id"] =
                    SharedPrefManager.getInstance(requireActivity().applicationContext).user.id

                return params
            }
        }
        stringRequest.tag = "get_profile"
        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        VolleySingleton.getInstance(requireActivity()).requestQueue.cancelAll("get_profile")
    }

    fun String?.safe() = this?.takeIf { it.isNotBlank() && !it.equals("null", true) } ?: "NA"

}