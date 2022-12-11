package com.app.examprep.fragment.home

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
import com.app.examprep.databinding.FragmentShopBinding
import com.app.examprep.firebase.MainViewModel
import com.app.examprep.others.Constants
import com.app.examprep.others.MyDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

class MyMaterialFragment : Fragment(R.layout.fragment_list) {


    private lateinit var binding : FragmentListBinding
    lateinit var viewmodel : MainViewModel

    lateinit var glide: RequestManager
    lateinit var myDialog: MyDialog
    lateinit var courseAdapter: CourseAdapter
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


        binding.rvItems.adapter = courseAdapter
        binding.rvItems.layoutManager = GridLayoutManager(requireContext(),2)

        viewmodel.getCoursesDataByID(Constants.curUserData.myMaterials)

        courseAdapter.setOnItemClickListener {
            Constants.curCourse = it
            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_courseFragment_to_lessonsFragment)
        }

        setCallbacks()


    }

    private fun setCallbacks() {

        viewmodel.coursesLive.observe(viewLifecycleOwner, Observer {

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

        viewmodel.errorCoursesLive.observe(viewLifecycleOwner, Observer {

            binding.progressbar.visibility = View.GONE
            myDialog.showErrorAlertDialog(it)

        })

    }


}