package com.ekodex.manpowerhrms.Events

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ekodex.manpowerhrms.Birthdays.BirthdaysFragment
import com.ekodex.manpowerhrms.Dashboard.DashboardFragment


class EventsManagementAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EventsFragment()
            1 -> BirthdaysFragment()
            else -> DashboardFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}