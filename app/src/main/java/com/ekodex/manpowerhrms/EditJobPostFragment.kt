package com.ekodex.manpowerhrms

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentEditJobPostBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.json.JSONException
import org.json.JSONObject

class EditJobPostFragment : Fragment() {

    lateinit var binding: FragmentEditJobPostBinding
    var emptype = ""
    var workapproach = ""
    var experience = ""
    var compensation = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_job_post,container,false)

        var i1 = Internet()
        if(i1.checkConnection(requireActivity().applicationContext))
        {
            getMyJob()
            getAllCategories()
        }
        else
        {
            Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
        }

        binding.button6.setOnClickListener {
            if(i1.checkConnection(requireActivity().applicationContext))
            {
                editMyJob()
            }
            else
            {
                Toast.makeText(requireActivity().applicationContext,"No internet connection!!", Toast.LENGTH_LONG).show()
            }
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

    private fun getMyJob() {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_POST_DETAILS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {
//                        Toast.makeText(
//                            requireActivity().applicationContext,
//                            obj.getString("message"),
//                            Toast.LENGTH_SHORT
//                        ).show()
                        //getting the user from the response
                        val userJson = obj.getJSONObject("user")

                        binding.editTextTextPersonName57.setText(userJson.getString("ref"))
                        binding.editTextTextPersonName59.setText(userJson.getString("title"))
                        binding.jobCategory.setText(userJson.getString("cat"))
                        binding.editTextTextPersonName60.setText(userJson.getString("loc"))
                        binding.editTextTextPersonName70.setText(userJson.getString("desc"))
                        binding.editTextTextPersonName71.setText(userJson.getString("resp"))
                        binding.editTextTextPersonName72.setText(userJson.getString("skill"))
                        binding.editTextTextPersonName63.setText(userJson.getString("totalpost"))

                        //-------------------------------------------------------------------------------------------
                        val empChips = mutableListOf<Chip>()

                        for (i in 0 until binding.empType.childCount) {
                            val childView = binding.empType.getChildAt(i)
                            if (childView is Chip) {
                                empChips.add(childView)
                            }
                        }

                        empChips.forEach {
                            if(userJson.getString("emtype") == it.text.toString())
                            {
                                it.isChecked = true
                            }
                        }
                        //-------------------------------------------------------------------------------------------
                        val workChips = mutableListOf<Chip>()

                        for (i in 0 until binding.workApproach.childCount) {
                            val childView = binding.workApproach.getChildAt(i)
                            if (childView is Chip) {
                                workChips.add(childView)
                            }
                        }

                        workChips.forEach {
                          //  Toast.makeText(requireContext(),it.text.toString(),Toast.LENGTH_SHORT).show()
                            if(userJson.getString("woapp") == it.text.toString())
                            {
                                it.isChecked = true
                                workapproach = it.text.toString()
                               // Toast.makeText(requireContext(),workapproach,Toast.LENGTH_SHORT).show()
                            }
                        }
                        //-------------------------------------------------------------------------------------------
                        val expChips = mutableListOf<Chip>()

                        for (i in 0 until binding.experince.childCount) {
                            val childView = binding.experince.getChildAt(i)
                            if (childView is Chip) {
                                expChips.add(childView)
                            }
                        }

                        expChips.forEach {
                            if(userJson.getString("exp") == it.text.toString())
                            {
                                it.isChecked = true
                                experience = it.text.toString()
                                //Toast.makeText(requireContext(),experience,Toast.LENGTH_SHORT).show()
                            }
                        }
                        //-------------------------------------------------------------------------------------------


                        binding.seekBar.max = userJson.getString("comp").toInt()
                        binding.textView114.text = userJson.getString("comp")

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

                var args = EditJobPostFragmentArgs.fromBundle(requireArguments())
                params["id"] = args.postId

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun editMyJob() {
        var refno = binding.editTextTextPersonName57.text.toString()
        var title = binding.editTextTextPersonName59.text.toString()
        var category = binding.jobCategory.text.toString()
        var location = binding.editTextTextPersonName60.text.toString()
        var total_post = binding.editTextTextPersonName63.text.toString()
        var desc = binding.editTextTextPersonName70.text.toString()
        var responsibilities = binding.editTextTextPersonName71.text.toString()
        var skills = binding.editTextTextPersonName72.text.toString()

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

        if (TextUtils.isEmpty(desc)) {
            binding.editTextTextPersonName70.error = "Please enter job description!!"
            binding.editTextTextPersonName70.requestFocus()
            return
        }

        if (TextUtils.isEmpty(responsibilities)) {
            binding.editTextTextPersonName71.error = "Please enter job responsibilities!!"
            binding.editTextTextPersonName71.requestFocus()
            return
        }

        if (TextUtils.isEmpty(skills)) {
            binding.editTextTextPersonName72.error = "Please enter job skills!!"
            binding.editTextTextPersonName72.requestFocus()
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
            val stringRequest = @RequiresApi(Build.VERSION_CODES.M)
            object : StringRequest(
                Request.Method.POST, URLs.URL_UPDATE_POST,
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

                            findNavController().navigate(EditJobPostFragmentDirections.actionEditJobPostFragmentToRecruitmentFragment())

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

                    var args = EditJobPostFragmentArgs.fromBundle(requireArguments())
                    params["id"] = args.postId
                    params["title"] = title
                    params["cat"] = category
                    params["loc"] = location
                    params["emp"] = emptype
                    params["work"] = workapproach
                    params["exp"] = experience
                    params["comp"] = compensation
                    params["desc"] = desc
                    params["resp"] = responsibilities
                    params["skill"] = skills
                    params["total"] = total_post

                    return params
                }
            }

            VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
        }
    }

}