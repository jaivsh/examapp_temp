package com.app.examprep.fragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.app.examprep.R
import com.app.examprep.databinding.FragmentEligibilityAgeBinding

class EligibilityAge: Fragment(R.layout.fragment_eligibility_age) {

    lateinit var binding : FragmentEligibilityAgeBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentEligibilityAgeBinding.bind(view)


        setUI(view)


    }

    private fun setUI(view: View) {


    }

}