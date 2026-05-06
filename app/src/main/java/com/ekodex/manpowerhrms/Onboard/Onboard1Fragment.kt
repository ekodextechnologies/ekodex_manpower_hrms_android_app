package com.ekodex.manpowerhrms.Onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.databinding.FragmentOnboard1Binding

class Onboard1Fragment : Fragment() {

    lateinit var binding: FragmentOnboard1Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_onboard1,container,false)

        val viewPager =  activity?.findViewById<ViewPager2>(R.id.pager)

        binding.textView80.setOnClickListener {
            viewPager?.currentItem = 1
        }

        return binding.root

    }

}