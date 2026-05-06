package com.ekodex.manpowerhrms

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentRecruitmentPostDetailsBinding
import org.json.JSONException
import org.json.JSONObject

class RecruitmentPostDetailsFragment : Fragment() {

    lateinit var binding: FragmentRecruitmentPostDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recruitment_post_details, container, false)

        var i1 = Internet()

       binding.button6.setOnClickListener {
           if(i1.checkConnection(requireContext())) {
               addPostDetails()
           }
           else
           {
               Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
           }
       }

        return binding.root

    }

    private fun addPostDetails() {

        var desc = binding.editTextTextPersonName57.text.toString()
        var responsibilities = binding.editTextTextPersonName61.text.toString()
        var skills = binding.editTextTextPersonName62.text.toString()


        if (TextUtils.isEmpty(desc)) {
            binding.editTextTextPersonName57.error = "Please enter job description!!"
            binding.editTextTextPersonName57.requestFocus()
            return
        }

        if (TextUtils.isEmpty(responsibilities)) {
            binding.editTextTextPersonName61.error = "Please enter job responsibilities!!"
            binding.editTextTextPersonName61.requestFocus()
            return
        }

        if (TextUtils.isEmpty(skills)) {
            binding.editTextTextPersonName62.error = "Please enter job skills!!"
            binding.editTextTextPersonName62.requestFocus()
            return
        }
            val stringRequest = object : StringRequest(
                Request.Method.POST, URLs.URL_ADD_POST_DETAILS,
                Response.Listener
                { response ->

                    try {
                        //converting response to json object
                        val obj = JSONObject(response)

                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            Toast.makeText(
                                requireContext(),
                                obj.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()

                            findNavController().navigate(RecruitmentPostDetailsFragmentDirections.actionRecruitmentPostDetailsFragmentToRecruitmentCreatePostFragment())

                        }
                        else {
                            Toast.makeText(
                                requireContext(),
                                obj.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        
                    }
                },
                Response.ErrorListener { error ->
                    if (error.message != null) {
                        Toast.makeText(
                            requireContext(),
                            error.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()

                    var args = RecruitmentPostDetailsFragmentArgs.fromBundle(requireArguments())
                    params["id"] = args.lastid
                    params["desc"] = desc
                    params["resp"] = responsibilities
                    params["skill"] = skills

                    return params
                }
            }

            VolleySingleton.getInstance(requireActivity().applicationContext)
                .addToRequestQueue(stringRequest)
    }

}