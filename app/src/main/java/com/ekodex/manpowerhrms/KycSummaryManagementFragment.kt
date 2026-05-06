package com.ekodex.manpowerhrms

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.ekodex.manpowerhrms.databinding.FragmentKycSummaryManagementBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class KycSummaryManagementFragment : Fragment() {

    lateinit var binding: FragmentKycSummaryManagementBinding
    var type = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_kyc_summary_management, container, false)

        if (arguments != null && !requireArguments().isEmpty) {
            try {
                val navArgs = KycSummaryManagementFragmentArgs.fromBundle(requireArguments())
                type = navArgs.type
            } catch (e: Exception) {
                // If args parsing fails, use current date as fallback
                //type = ""
            }
        }
Log.i("11111","managemet - ${type}")
        var adapter = KycSummaryManagementAdapter(requireActivity(),type)

        binding.viewPager.adapter = adapter

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.getTabAt(position)!!.select()
            }
        })

        return binding.root
    }


}