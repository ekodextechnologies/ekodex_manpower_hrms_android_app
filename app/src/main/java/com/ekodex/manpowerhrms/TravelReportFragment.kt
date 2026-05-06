package com.ekodex.manpowerhrms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ekodex.manpowerhrms.databinding.FragmentTravelReportBinding

class TravelReportFragment : Fragment() {

    lateinit var binding: FragmentTravelReportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_travel_report, container, false)




        return binding.root

    }


}