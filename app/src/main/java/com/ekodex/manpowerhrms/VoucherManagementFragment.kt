package com.ekodex.manpowerhrms

import VoucherDateViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ekodex.manpowerhrms.databinding.FragmentVoucherManagementBinding
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class VoucherManagementFragment : Fragment() {

    private lateinit var binding: FragmentVoucherManagementBinding
    private lateinit var adapter: VoucherManagementAdapter

    // Shared ViewModel (SOURCE OF TRUTH)
    private val dateViewModel: VoucherDateViewModel by activityViewModels()

    // ✅ Class-level dates (so they are accessible everywhere)
    private var fromDate: String = ""
    private var toDate: String = ""

    private var openTab = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_voucher_management,
            container,
            false
        )

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
        val currentDate = sdf.format(Date())

        // Read initial dates from navigation args
        arguments?.let {
            try {
                val navArgs = VoucherManagementFragmentArgs.fromBundle(it)
                fromDate = navArgs.fromDate.ifEmpty { currentDate }
                toDate = navArgs.toDate.ifEmpty { currentDate }
                openTab = navArgs.openTab
            } catch (e: Exception) {
                fromDate = currentDate
                toDate = currentDate
            }
        } ?: run {
            fromDate = currentDate
            toDate = currentDate
        }

        // Push initial dates to ViewModel
        dateViewModel.setDates(fromDate, toDate)

        // Setup adapter
        adapter = VoucherManagementAdapter(requireActivity())
        binding.viewPager.adapter = adapter

        if (openTab !in 0..3) openTab = 0
        binding.viewPager.post {
            binding.viewPager.setCurrentItem(openTab, false)
        }

        // Tabs → ViewPager
        binding.tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        // ViewPager → Tabs
        binding.viewPager.registerOnPageChangeCallback(
            object : androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.tabLayout.getTabAt(position)?.select()
                }
            }
        )

        return binding.root
    }

    // Called when user taps APPLY button
    fun onDateChanged(newFromDate: String, newToDate: String) {
        fromDate = newFromDate
        toDate = newToDate

        // Update ViewModel
        dateViewModel.setDates(newFromDate, newToDate)
    }
}
