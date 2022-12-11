package com.app.examprep.fragment.home.course

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.examprep.R
import com.app.examprep.adapter.ContentAdapter
import com.app.examprep.adapter.LessonAdapter
import com.app.examprep.databinding.FragmentLessonsBinding
import com.app.examprep.databinding.FragmentShopBinding
import com.app.examprep.others.Constants

class ContentFragment : Fragment(R.layout.fragment_lessons) {

    private lateinit var binding : FragmentLessonsBinding
    lateinit var contentAdapter : ContentAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentLessonsBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {

        binding.tvTitle.text = Constants.curLesson.name

        contentAdapter = ContentAdapter()
        binding.rvLessons.adapter = contentAdapter
        binding.rvLessons.layoutManager = LinearLayoutManager(requireContext())

        contentAdapter.contentList = Constants.curLesson.content

        binding.btBuy.visibility = View.GONE


        contentAdapter.setOnItemClickListener {

   /*         if(it.status == Constants.LOCKED){
                if(!Constants.curUserData.myMaterials.contains(Constants.curCourse.courseId)){
                    Toast.makeText(requireContext(), "Please buy this course to unlock this lesson", Toast.LENGTH_SHORT).show()
                    return@setOnItemClickListener
                }
            }*/

            Constants.curUrl = it.url

            if(it.type == "PDF"){
               Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_contentFragment_to_pdfViewFragment)
            }

            if(it.type == "Video") {
                Navigation.findNavController(requireActivity(), R.id.main_fragment).navigate(R.id.action_contentFragment_to_videoViewFragment)
            }

            if(it.type == "Test"){
                Constants.curContentData = it
                Constants.curTest = it.testData
                Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_contentFragment_to_quizFragment)
            }


        }






    }

}