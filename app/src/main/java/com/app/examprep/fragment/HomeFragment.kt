package com.app.examprep.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.app.examprep.R
import com.app.examprep.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {



    private lateinit var binding : FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        setUi(view)

    }

    private fun setUi(view: View) {

        val fragment = this.childFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        val navController = fragment.findNavController()

        binding.navView.setupWithNavController(navController)

    }

}