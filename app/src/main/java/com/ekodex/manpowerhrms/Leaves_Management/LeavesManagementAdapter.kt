package com.ekodex.manpowerhrms.Leaves_Management

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ekodex.manpowerhrms.Dashboard.DashboardFragment
import com.ekodex.manpowerhrms.Leaves_Approved.ApprovedLeavesFragment
import com.ekodex.manpowerhrms.Leaves_Pending.PendingLeaveFragment
import com.ekodex.manpowerhrms.Leaves_Rejected.RejectedLeavesFragment


class LeavesManagementAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PendingLeaveFragment()
            1 -> ApprovedLeavesFragment()
            2 -> RejectedLeavesFragment()
            else -> DashboardFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}