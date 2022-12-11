package com.app.examprep.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
         return when (position) {
            0 -> com.app.examprep.fragment.GettingStartedFragment()
            1 -> com.app.examprep.fragment.EligibilityCriteriaFragment()
             else -> {
                 com.app.examprep.fragment.NotificationsFragment()
             }
         }
    }

}