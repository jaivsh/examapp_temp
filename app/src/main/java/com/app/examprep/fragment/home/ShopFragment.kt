package com.app.examprep.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.GridLayoutManager
import com.app.examprep.R
import com.app.examprep.adapter.CourseAdapter
import com.app.examprep.adapter.ViewPagerAdapter
import com.app.examprep.databinding.FragmentHomeBinding
import com.app.examprep.databinding.FragmentShopBinding
import com.app.examprep.firebase.MainViewModel
import com.app.examprep.others.Constants
import com.app.examprep.others.MyDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.android.material.tabs.TabLayoutMediator

class ShopFragment : Fragment(R.layout.fragment_shop) {


    private lateinit var binding : FragmentShopBinding
    lateinit var viewmodel : MainViewModel

    lateinit var glide: RequestManager
    lateinit var myDialog: MyDialog
    lateinit var courseAdapter: CourseAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding = FragmentShopBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {


        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter


        TabLayoutMediator(binding.tabLayout, binding.viewPager ) { tab, position ->
            when(position){
                0 -> tab.text = "Courses"
                1 -> tab.text = "Tests"
                2 -> tab.text = "Materials"
            }
        }.attach()




    }



}