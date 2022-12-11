package com.app.examprep.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.app.examprep.R
import com.app.examprep.adapter.CourseAdapter
import com.app.examprep.adapter.ViewPagerAdapter
import com.app.examprep.databinding.FragmentNotificationsBinding
import com.app.examprep.databinding.FragmentShopBinding
import com.app.examprep.firebase.MainViewModel
import com.app.examprep.others.MyDialog
import com.bumptech.glide.RequestManager
import com.google.android.material.tabs.TabLayoutMediator

class NotificationsBlockFragment: Fragment(R.layout.fragment_notifications) {
    private lateinit var binding : FragmentNotificationsBinding
    lateinit var viewmodel : MainViewModel

    lateinit var glide: RequestManager
    lateinit var myDialog: MyDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNotificationsBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {


        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter


        TabLayoutMediator(binding.tabLayout, binding.viewPager ) { tab, position ->
            when(position){
                0 -> tab.text = "Getting started"
                1 -> tab.text = "Eligibility Criteria"
                2 -> tab.text = "Notifications"
            }
        }.attach()
    }

}