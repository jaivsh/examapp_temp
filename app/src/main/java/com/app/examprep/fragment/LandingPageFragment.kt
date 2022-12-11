package com.app.examprep.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.app.examprep.R
import com.app.examprep.databinding.FragmentLandingPageBinding
import com.bulltradeintraday.app.others.SharedPref

class LandingPageFragment: Fragment(R.layout.fragment_landing_page) {
    private lateinit var binding: FragmentLandingPageBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLandingPageBinding.bind(view)
        setUi(view)
    }
    private fun setUi(view: View) {
//        val fragment = this.childFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
//        val navController = fragment.findNavController()
//
//        binding.navView.setupWithNavController(navController)
        val sharePref = SharedPref(requireContext())
        val userName:TextView = binding.loggedUser
        if (sharePref.getUserName() !== null) {
            userName.text = sharePref.getUserName();
        }
        binding.notificationsPage.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_landing_to_notificationFragment)
        }
        binding.CoursesPage.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_landing_to_coursesFragment)
        }
        binding.TestsPage.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_landing_to_testsFragment)
        }
    }
}