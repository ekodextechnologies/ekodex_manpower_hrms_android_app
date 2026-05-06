package com.ekodex.manpowerhrms

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentTravelScheduleBinding
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class TravelScheduleFragment : Fragment() {

    lateinit var binding: FragmentTravelScheduleBinding
    //date filter
    lateinit var fromDate: String
    lateinit var toDate: String
    lateinit var from_flag: String
    lateinit var to_flag: String
    var grp_size = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_travel_schedule, container, false)

        from_flag = "false"
        to_flag = "false"
        fromDate = ""
        toDate = ""

        var i1 = Internet()

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


        binding.button4.setOnClickListener {
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
        binding.button5.setOnClickListener {
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

        binding.button16.setOnClickListener {
            if(i1.checkConnection(requireContext())) {
                addTravelSchedule()
            }
            else
            {
                Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
            }
        }

        binding.groupSize.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            var radioButton = view?.findViewById<RadioButton>(checkedId)
            grp_size = radioButton?.getText().toString()
        }
        )

        return binding.root

    }

    private fun addTravelSchedule() {

        var traveler1 = binding.editTextTextPersonName60.text.toString()
        var destination = binding.editTextTextPersonName66.text.toString()
        var modeofop = binding.editTextTextPersonName67.text.toString()


//        if (TextUtils.isEmpty(traveler1)) {
//            binding.editTextTextPersonName60.error = "Please enter job description!!"
//            binding.editTextTextPersonName60.requestFocus()
//            return
//        }

        if (TextUtils.isEmpty(destination)) {
            binding.editTextTextPersonName66.error = "Please enter job responsibilities!!"
            binding.editTextTextPersonName66.requestFocus()
            return
        }

//
//        if (TextUtils.isEmpty(modeofop)) {
//            binding.editTextTextPersonName67.error = "Please enter job skills!!"
//            binding.editTextTextPersonName67.requestFocus()
//            return
//        }

        if (fromDate == "") {
            Toast.makeText(requireContext(), "Please select from travel date!!", Toast.LENGTH_LONG).show()
        } else if (toDate == "") {
            Toast.makeText(requireContext(), "Please select to travel date!!", Toast.LENGTH_LONG)
                .show()
        }  else
        {
            val stringRequest = object : StringRequest(
                Request.Method.POST, URLs.URL_ADD_TRAVEL_SCHEDULE,
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

                            var args = TravelScheduleFragmentArgs.fromBundle(requireArguments())
                            findNavController().navigate(TravelScheduleFragmentDirections.actionTravelScheduleFragmentToTravelAccomodationFragment(args.id))

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

                    var args = TravelScheduleFragmentArgs.fromBundle(requireArguments())
                    params["id"] = args.id
                    params["group_size"] = grp_size
                    params["travel_from_date"] = fromDate
                    params["travel_to_date"] = toDate
                    params["traveler1_name"] = traveler1
                    params["destination"] = destination
                    params["mode_of_transportation"] = modeofop

                    return params
                }
            }

            VolleySingleton.getInstance(requireActivity().applicationContext)
                .addToRequestQueue(stringRequest)
        }

    }

    //date filter functions
    private fun updateFromDateLable(calener: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
// Here we can use calendar selected date
        binding.button4.setText((sdf.format(calener.time)))
        val myFormat2 = "yyyy-MM-dd"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.UK)
        fromDate = (sdf2.format(calener.time))
        //Log.i("oooooooooooooooooo", fromDate)
    }

    private fun updateToDateLable(calener: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
// Here we can use calendar selected date
        binding.button5.setText((sdf.format(calener.time)))

        val myFormat2 = "yyyy-MM-dd"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.UK)
        toDate = (sdf2.format(calener.time))

        // Log.i("oooooooooooooooooo", toDate)
    }

}