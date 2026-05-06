package com.ekodex.manpowerhrms

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class KycSummaryManagementAdapter(
    fragmentActivity: FragmentActivity,
    var type: String
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        Log.i("11111", "log2 - ${type}")  // ✅ Adapter scope (correct value)

        return when (position) {
            0 -> KycDoneFragment().apply {
                // ✅ Explicitly reference adapter's type to avoid shadowing
                Log.i("11111", "log1 - ${this@KycSummaryManagementAdapter.type}")
                arguments = Bundle().apply {
                    putString("type", this@KycSummaryManagementAdapter.type)
                }
            }

            1 -> KycNotDoneFragment().apply {
                arguments = Bundle().apply {
                    putString("type", this@KycSummaryManagementAdapter.type)
                }
            }

            else -> Fragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}
