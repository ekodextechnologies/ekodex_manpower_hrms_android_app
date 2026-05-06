package com.ekodex.manpowerhrms.Recruitment_Post_Create

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.databinding.FragmentRecruitmentCreatePostBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.json.JSONException
import org.json.JSONObject


class RecruitmentCreatePostFragment : Fragment() {

    lateinit var binding: FragmentRecruitmentCreatePostBinding
    var emptype = ""
    var workapproach = ""
    var experience = ""
    var compensation = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_recruitment_create_post, container, false)

        var i1 = Internet()

        if(i1.checkConnection(requireContext())) {
            getAllCategories()
        }
        else
        {
            Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
        }


        binding.seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    binding.textView114.text = seekBar?.progress.toString()
                    compensation = seekBar?.progress.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    binding.textView114.text = seekBar?.progress.toString()
                    compensation = seekBar?.progress.toString()
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    binding.textView114.text = seekBar?.progress.toString()
                    compensation = seekBar?.progress.toString()
                }

            })


        //create post
        binding.button6.setOnClickListener {
           if(i1.checkConnection(requireContext())) {
               createPost()
           }
            else
           {
               Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
           }
        }

        //employment type
        binding.empType.setOnCheckedStateChangeListener(ChipGroup.OnCheckedStateChangeListener { group, checkedIds ->
            val ids = group.checkedChipIds
            for (id in ids) {
                val chip = group.findViewById<Chip>(id!!)
                emptype = chip.text.toString()
            }
        })

        //work approach
        binding.workApproach.setOnCheckedStateChangeListener(ChipGroup.OnCheckedStateChangeListener { group, checkedIds ->
            val ids = group.checkedChipIds
            for (id in ids) {
                val chip = group.findViewById<Chip>(id!!)
                workapproach = chip.text.toString()
            }
        })

        //experience
        binding.experince.setOnCheckedStateChangeListener(ChipGroup.OnCheckedStateChangeListener { group, checkedIds ->
            val ids = group.checkedChipIds
            for (id in ids) {
                val chip = group.findViewById<Chip>(id!!)
                experience = chip.text.toString()
            }
        })

        return binding.root

    }

    private fun createPost() {

        var refno = binding.editTextTextPersonName57.text.toString()
        var title = binding.editTextTextPersonName59.text.toString()
        var category = binding.jobCategory.text.toString()
        var location = binding.editTextTextPersonName60.text.toString()
        var total_post = binding.editTextTextPersonName63.text.toString()


        if (TextUtils.isEmpty(refno)) {
            binding.editTextTextPersonName57.error = "Please enter reference no!!"
            binding.editTextTextPersonName57.requestFocus()
            return
        }

        if (TextUtils.isEmpty(title)) {
            binding.editTextTextPersonName59.error = "Please enter title!!"
            binding.editTextTextPersonName59.requestFocus()
            return
        }

        if (TextUtils.isEmpty(category)) {
            binding.jobCategory.error = "Please select category!!"
            binding.jobCategory.requestFocus()
            return
        }


        if (TextUtils.isEmpty(location)) {
            binding.editTextTextPersonName60.error = "Please enter location!!"
            binding.editTextTextPersonName60.requestFocus()
            return
        }

        if (TextUtils.isEmpty(total_post)) {
            binding.editTextTextPersonName63.error = "Please enter number of total posts!!"
            binding.editTextTextPersonName63.requestFocus()
            return
        }


        if (emptype == "")
        {
            Toast.makeText(requireContext(),"Please select employment type!!",Toast.LENGTH_LONG).show()
        }
        else if(workapproach == "")
        {
            Toast.makeText(requireContext(),"Please select work approach!!",Toast.LENGTH_LONG).show()
        }
        else if(experience == "")
        {
            Toast.makeText(requireContext(),"Please select work experience!!",Toast.LENGTH_LONG).show()
        }
        else if(compensation == ""  || compensation == "0")
        {
            Toast.makeText(requireContext(),"Compensation can't be zero!!",Toast.LENGTH_LONG).show()
        }
        else
        {
            val stringRequest = object : StringRequest(
                Request.Method.POST, URLs.URL_CREATE_POST,
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

                            findNavController().navigate(RecruitmentCreatePostFragmentDirections.actionRecruitmentCreatePostFragmentToRecruitmentPostDetailsFragment(obj.getString("lastid")))

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

                    params["refno"] = refno
                    params["category"] = category
                    params["title"] = title
                    params["location"] = location
                    params["emp_type"] = emptype
                    params["work_approach"] = workapproach
                    params["compensation"] = compensation
                    params["experience"] = experience
                    params["total_posts"] = total_post
                    params["created_by"] = SharedPrefManager.getInstance(requireContext()).user.id

                    return params
                }
            }

            VolleySingleton.getInstance(requireActivity().applicationContext)
                .addToRequestQueue(stringRequest)
        }


    }

    private fun getAllCategories() {
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_ALL_CATEGORIES,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        //
                        // empDirectoryList.clear()

                        if (array.length() <= 0) {
                            Toast.makeText(requireContext(),"No categories available!!",Toast.LENGTH_SHORT).show()

                        }
                        else{

                            var names = arrayListOf<String>()

                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)
                                names.add(objectArtist.getString("title"))
                            }
                            val adapter1 = ArrayAdapter(requireContext(),R.layout.pay_to_dropdown_layout,names)
                            binding.jobCategory.setAdapter(adapter1)
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
            { volleyError ->
                Toast.makeText(
                    requireContext(),
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            })

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)
    }

}