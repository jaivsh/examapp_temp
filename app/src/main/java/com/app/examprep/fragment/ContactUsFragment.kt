package com.app.examprep.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.app.examprep.R
import com.app.examprep.databinding.FragmentContactusBinding
import com.app.examprep.databinding.FragmentTncBinding

class ContactUsFragment : Fragment(R.layout.fragment_contactus) {

    lateinit var binding : FragmentContactusBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentContactusBinding.bind(view)

        binding.ivProfile.setOnClickListener {
            openBrowser("https://vidhyasoft.com/")
        }

    }


}