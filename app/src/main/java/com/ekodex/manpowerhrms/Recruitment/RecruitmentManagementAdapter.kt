package com.ekodex.manpowerhrms.Recruitment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ekodex.manpowerhrms.Dashboard.DashboardFragment
import com.ekodex.manpowerhrms.Recruitment_Applicants.RecruitmentApplicantsFragment
import com.ekodex.manpowerhrms.Recruitment_Posts.RecruitmentPostsFragment


class RecruitmentManagementAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RecruitmentPostsFragment()
            1 -> RecruitmentApplicantsFragment()
            else -> DashboardFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}