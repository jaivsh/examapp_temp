package com.app.examprep.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.app.examprep.R
import com.app.examprep.databinding.FragmentTncBinding

class AboutUsFragment : Fragment(R.layout.fragment_tnc) {


    lateinit var binding : FragmentTncBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentTncBinding.bind(view)


        setUI(view)


    }

    private fun setUI(view: View) {


        binding.ivLogo.visibility = View.VISIBLE

        binding.ivLogo.setOnClickListener {
            openBrowser("https://vidhyasoft.com/")
        }

        binding.tvTitle.text = "About Us"
        binding.tvNotes.text = "Our mission is to embrace the pursuit of excellence. We\n" +
                "encourage critical thinking and emphasize on practice and\n" +
                "creative learning process over rote memorization.\n" +
                "VidhyaSoft offers a variety of high-quality courses designed to\n" +
                "prepare you for competitive exams. We also offer placement\n" +
                "tests to help match you to your skill level and more!"

    }

}

fun Fragment.openBrowser(url : String){
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
}