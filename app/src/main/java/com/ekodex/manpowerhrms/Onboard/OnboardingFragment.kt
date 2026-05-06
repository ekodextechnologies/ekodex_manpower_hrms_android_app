package com.ekodex.manpowerhrms.Onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    lateinit var binding: FragmentOnboardingBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_onboarding,container,false)

        val list = arrayListOf<Fragment>(
           Onboard1Fragment(),
           Onboard2Fragment(),
           Onboard3Fragment()

        )

        val adapter = ViewPagerAdapter(
            list,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.pager.adapter = adapter
        //set circle indicator
        binding.indicator.setViewPager(binding.pager);


        return binding.root
    }

}