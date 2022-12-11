package com.app.examprep.fragment.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.app.examprep.R
import com.app.examprep.databinding.FragmentSplashscreenBinding
import com.bulltradeintraday.app.others.SharedPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment(R.layout.fragment_splashscreen) {

    private lateinit var binding : FragmentSplashscreenBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentSplashscreenBinding.bind(view)

        CoroutineScope(Dispatchers.Main).launch {

            delay(2500)

            val sharedPref = SharedPref(requireContext())

//            if (sharedPref.getUserAuthStatus()) {
                Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_splashScreenFragment_to_landingFragment)
//            } else {
//                Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_splashScreenFragment_to_mobileLoginFragment)
//            }


        }

    }

}
