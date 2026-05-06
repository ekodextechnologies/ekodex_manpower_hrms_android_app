package com.ekodex.manpowerhrms

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VoucherManagementAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PendingVouchersFragment()
            1 -> ApprovedVouchersFragment()
            2 -> RejectedVouchersFragment()
            3 -> PaidVouchersFragment()
            else -> PendingVouchersFragment()
        }
    }
}
