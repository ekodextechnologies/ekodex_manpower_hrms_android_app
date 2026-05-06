package com.ekodex.manpowerhrms.Travel_Management

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ekodex.manpowerhrms.Dashboard.DashboardFragment
import com.ekodex.manpowerhrms.TravelReportFragment
import com.ekodex.manpowerhrms.Travel_Requests.TravelRequestFragment


class TravelManagementAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TravelRequestFragment()
            1 -> TravelReportFragment()
            else -> DashboardFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}