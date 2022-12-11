package com.app.examprep.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.app.examprep.R
import com.app.examprep.adapter.CourseAdapter
import com.app.examprep.databinding.FragmentListBinding
import com.app.examprep.firebase.MainViewModel
import com.app.examprep.others.Constants
import com.app.examprep.others.MyDialog
import com.bulltradeintraday.app.others.SharedPref
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

class CourseFragment() : Fragment(R.layout.fragment_list) {


    private lateinit var binding : FragmentListBinding
    lateinit var viewmodel : MainViewModel

    lateinit var glide: RequestManager
    lateinit var myDialog: MyDialog
    lateinit var courseAdapter: CourseAdapter
    lateinit var sharedPref: SharedPref
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentListBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {

        viewmodel =
            ViewModelProvider(this).get(MainViewModel::class.java)

        glide = Glide.with(requireContext())


        courseAdapter = CourseAdapter(glide)


        myDialog = MyDialog(requireContext())

        sharedPref = SharedPref(requireContext())

        viewmodel.getUserData(sharedPref.getMobileNumber())

        viewmodel.getCoursesData(Constants.COURSES)

        binding.rvItems.adapter = courseAdapter
        binding.rvItems.layoutManager = GridLayoutManager(requireContext(),2)

        binding.btTesthistory.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_homeFragment_to_testHistoryFragment)
        }


        courseAdapter.setOnItemClickListener {
            Constants.curCourse = it
            Navigation.findNavController(requireActivity(), R.id.main_fragment)
                .navigate(R.id.action_courseFragment_to_lessonsFragment)
        }

        setCallbacks()


    }

    private fun setCallbacks() {

        viewmodel.courseLive.observe(viewLifecycleOwner, Observer {

            if(it.isEmpty()){
                binding.layoutEmpty.ivMaterialsempty.visibility = View.VISIBLE
                binding.layoutEmpty.tvNothingFound.visibility = View.VISIBLE
            }else{
                binding.layoutEmpty.ivMaterialsempty.visibility = View.GONE
                binding.layoutEmpty.tvNothingFound.visibility = View.GONE
            }

            binding.progressbar.visibility = View.GONE
            courseAdapter.courseList = it

        })

        viewmodel.errorCourseLive.observe(viewLifecycleOwner, Observer {

            binding.progressbar.visibility = View.GONE
            myDialog.showErrorAlertDialog(it)

        })


    }


}