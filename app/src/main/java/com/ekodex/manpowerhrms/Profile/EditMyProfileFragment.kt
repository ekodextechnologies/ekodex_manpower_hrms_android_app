package com.ekodex.manpowerhrms.Profile

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.databinding.FragmentEditMyProfileBinding
import org.json.JSONException
import org.json.JSONObject

lateinit var binding: FragmentEditMyProfileBinding

class EditMyProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_edit_my_profile,container,false)


        var i1 = Internet()
        if(i1.checkConnection(requireActivity().applicationContext))
        {
            getMyProfile()
        }
        else
        {
            Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
        }


        binding.button2.setOnClickListener {
          if(i1.checkConnection(requireActivity().applicationContext))
          {
              editMyProfile()
          }
          else
          {
              Toast.makeText(requireActivity().applicationContext,"No internet connection!!", Toast.LENGTH_LONG).show()
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

                        binding.editTextTextPersonName11.setText(userJson.getString("first_name"))
                        binding.editTextTextPersonName6.setText(userJson.getString("middle_name"))
                        binding.editTextTextPersonName13.setText(userJson.getString("last_name"))


                        binding.editTextTextPersonName4.setText(userJson.getString("address"))
                        binding.editTextTextPersonName5.setText(userJson.getString("dob"))
                        binding.editTextTextPersonName55.setText(userJson.getString("gender"))
                        binding.editTextTextPersonName7.setText(userJson.getString("email_id"))
                        binding.editTextTextPersonName8.setText(userJson.getString("contact_person"))
                        binding.editTextTextPersonName9.setText(userJson.getString("city"))
                        binding.editTextTextPersonName10.setText(userJson.getString("state"))

                        binding.editTextTextPersonName12.setText(userJson.getString("pincode"))
                        binding.editTextTextPersonName15.setText(userJson.getString("contact_mobile"))
                        binding.editTextTextPersonName16.setText(userJson.getString("p_address"))
                        binding.editTextTextPersonName17.setText(userJson.getString("phone1"))
                        binding.editTextTextPersonName18.setText(userJson.getString("phone2"))
                        binding.editTextTextPersonName19.setText(userJson.getString("p_state"))
                        binding.editTextTextPersonName20.setText(userJson.getString("p_city"))

                        binding.editTextTextPersonName21.setText(userJson.getString("p_pincode"))
                        binding.editTextTextPersonName22.setText(userJson.getString("p_phone1"))
                        binding.editTextTextPersonName23.setText(userJson.getString("p_phone2"))
                        binding.editTextTextPersonName24.setText(userJson.getString("contact_relation"))
                        binding.editTextTextPersonName25.setText(userJson.getString("contact_email"))
                        binding.editTextTextPersonName26.setText(userJson.getString("marital_status"))
                        binding.editTextTextPersonName27.setText(userJson.getString("mrg_date"))
                        binding.editTextTextPersonName28.setText(userJson.getString("cast"))
                        binding.editTextTextPersonName29.setText(userJson.getString("category"))
                        binding.editTextTextPersonName30.setText(userJson.getString("native_place"))

                        binding.editTextTextPersonName31.setText(userJson.getString("blood_group"))
                        binding.editTextTextPersonName32.setText(userJson.getString("driving_license"))
                        binding.editTextTextPersonName33.setText(userJson.getString("pancard_no"))
                        binding.editTextTextPersonName34.setText(userJson.getString("aadhar_no"))
                        binding.editTextTextPersonName35.setText(userJson.getString("passport_no"))
                        binding.editTextTextPersonName36.setText(userJson.getString("passport_valid_date"))
                        binding.editTextTextPersonName37.setText(userJson.getString("uan_no"))
                        binding.editTextTextPersonName38.setText(userJson.getString("lang1"))
                        binding.editTextTextPersonName39.setText(userJson.getString("lang2"))
                        binding.editTextTextPersonName40.setText(userJson.getString("lang3"))

                        binding.editTextTextPersonName41.setText(userJson.getString("lang4"))
                        binding.editTextTextPersonName42.setText(userJson.getString("lang5"))
                        binding.editTextTextPersonName43.setText(userJson.getString("hobb1"))
                        binding.editTextTextPersonName44.setText(userJson.getString("hobby2"))
                        binding.editTextTextPersonName45.setText(userJson.getString("hobby3"))
                        binding.editTextTextPersonName46.setText(userJson.getString("hobby4"))
                        binding.editTextTextPersonName47.setText(userJson.getString("bank_name"))
                        binding.editTextTextPersonName48.setText(userJson.getString("account_no"))
                        binding.editTextTextPersonName49.setText(userJson.getString("bank_address"))
                        binding.editTextTextPersonName50.setText(userJson.getString("bank_state"))

                        binding.editTextTextPersonName51.setText(userJson.getString("bank_city"))
                        binding.editTextTextPersonName52.setText(userJson.getString("bank_ifsc"))
                        binding.editTextTextPersonName53.setText(userJson.getString("acc_holder_name"))
                        binding.editTextTextPersonName54.setText(userJson.getString("card_no"))

                        //userJson.getString("totalamt")


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

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun editMyProfile() {
        val stringRequest = @RequiresApi(Build.VERSION_CODES.M)
        object : StringRequest(
            Request.Method.POST, URLs.URL_EDIT_MY_PROFILE,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()

                        findNavController().navigate(EditMyProfileFragmentDirections.actionEditMyProfileFragmentToDashboardFragment())

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
                                Toast.LENGTH_LONG
                            ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()

                params["id"] = SharedPrefManager.getInstance(requireActivity().applicationContext).user.id
                params["fname"] = binding.editTextTextPersonName11.text.toString()
                params["mname"] = binding.editTextTextPersonName6.text.toString()
                params["lname"] = binding.editTextTextPersonName13.text.toString()
                params["gender"] = binding.editTextTextPersonName55.text.toString()

                params["address"] = binding.editTextTextPersonName4.text.toString()
                params["state"] = binding.editTextTextPersonName10.text.toString()
                params["city"] = binding.editTextTextPersonName9.text.toString()
                params["pincode"] = binding.editTextTextPersonName12.text.toString()

                params["phone1"] = binding.editTextTextPersonName17.text.toString()
                params["phone2"] = binding.editTextTextPersonName18.text.toString()

                params["email_id"] = binding.editTextTextPersonName7.text.toString()
                params["dob"] = binding.editTextTextPersonName5.text.toString()

                params["contact_person"] = binding.editTextTextPersonName8.text.toString()
                params["contact_mobile"] = binding.editTextTextPersonName15.text.toString()

                params["p_address"] = binding.editTextTextPersonName16.text.toString()
                params["p_state"] = binding.editTextTextPersonName19.text.toString()
                params["p_city"] = binding.editTextTextPersonName20.text.toString()
                params["p_pincode"] = binding.editTextTextPersonName21.text.toString()
                params["p_phone1"] = binding.editTextTextPersonName22.text.toString()
                params["p_phone2"] = binding.editTextTextPersonName23.text.toString()

                params["contact_relation"] = binding.editTextTextPersonName24.text.toString()
                params["contact_email"] = binding.editTextTextPersonName25.text.toString()
                params["marital_status"] = binding.editTextTextPersonName26.text.toString()
                params["mrg_date"] = binding.editTextTextPersonName27.text.toString()
                params["cast"] = binding.editTextTextPersonName28.text.toString()
                params["category"] = binding.editTextTextPersonName29.text.toString()
                params["native_place"] = binding.editTextTextPersonName30.text.toString()
                params["blood_group"] = binding.editTextTextPersonName31.text.toString()
                params["driving_license"] = binding.editTextTextPersonName32.text.toString()
                params["pancard_no"] = binding.editTextTextPersonName33.text.toString()
                params["aadhar_no"] = binding.editTextTextPersonName34.text.toString()
                params["passport_no"] = binding.editTextTextPersonName35.text.toString()
                params["passport_valid_date"] = binding.editTextTextPersonName36.text.toString()
                params["uan_no"] = binding.editTextTextPersonName37.text.toString()

                params["lang1"] = binding.editTextTextPersonName38.text.toString()
                params["lang2"] = binding.editTextTextPersonName39.text.toString()
                params["lang3"] = binding.editTextTextPersonName40.text.toString()
                params["lang4"] = binding.editTextTextPersonName41.text.toString()
                params["lang5"] = binding.editTextTextPersonName42.text.toString()
                params["hobby1"] = binding.editTextTextPersonName43.text.toString()
                params["hobby2"] = binding.editTextTextPersonName44.text.toString()
                params["hobby3"] = binding.editTextTextPersonName45.text.toString()
                params["hobby4"] = binding.editTextTextPersonName46.text.toString()

                params["bank_name"] = binding.editTextTextPersonName47.text.toString()
                params["account_no"] = binding.editTextTextPersonName48.text.toString()
                params["bank_address"] = binding.editTextTextPersonName49.text.toString()
                params["bank_state"] = binding.editTextTextPersonName50.text.toString()
                params["bank_city"] = binding.editTextTextPersonName51.text.toString()
                params["bank_ifsc"] = binding.editTextTextPersonName52.text.toString()
                params["ac_holder_name"] = binding.editTextTextPersonName53.text.toString()
                params["card_no"] = binding.editTextTextPersonName54.text.toString()

                return params
            }
        }

        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

}


