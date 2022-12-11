package com.app.examprep.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.examprep.R
import com.app.examprep.adapter.ContentAdapter
import com.app.examprep.databinding.FragmentListBinding
import com.app.examprep.firebase.MainViewModel
import com.app.examprep.others.Constants
import com.app.examprep.others.MyDialog
import com.bulltradeintraday.app.others.SharedPref
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

class TestsFragment() : Fragment(R.layout.fragment_list) {


    private lateinit var binding : FragmentListBinding
    lateinit var viewmodel : MainViewModel

    lateinit var glide: RequestManager
    lateinit var myDialog: MyDialog
    lateinit var testsAdapter: ContentAdapter
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

        testsAdapter = ContentAdapter()

        myDialog = MyDialog(requireContext())

        sharedPref = SharedPref(requireContext())

        viewmodel.getUserData(sharedPref.getMobileNumber())

        viewmodel.getTestData()

        binding.rvItems.adapter = testsAdapter
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())


        binding.btTesthistory.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_homeFragment_to_testHistoryFragment)
        }

        testsAdapter.setOnItemClickListener {

            //     Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()

            if(it.type == "PDF"){
                Constants.curUrl = it.url
                Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_testsFragment_to_pdfViewFragment)
            }

            if(it.type == "Test"){
                Constants.curContentData = it
                Constants.curTest = it.testData
                Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_testsFragment_to_quizFragment)
            }

        }

        setCallbacks()


    }

    private fun setCallbacks() {


        viewmodel.testLive.observe(viewLifecycleOwner, Observer {

            if(it.isEmpty()){
                binding.layoutEmpty.ivMaterialsempty.visibility = View.VISIBLE
                binding.layoutEmpty.tvNothingFound.visibility = View.VISIBLE
            }else{
                binding.layoutEmpty.ivMaterialsempty.visibility = View.GONE
                binding.layoutEmpty.tvNothingFound.visibility = View.GONE
            }

            binding.progressbar.visibility = View.GONE
            testsAdapter.contentList = it

        })

        viewmodel.errorTestLive.observe(viewLifecycleOwner, Observer {

            binding.progressbar.visibility = View.GONE
            myDialog.showErrorAlertDialog(it)

        })


        viewmodel.materialsLive.observe(viewLifecycleOwner, Observer {

            if(it.isEmpty()){
                binding.layoutEmpty.ivMaterialsempty.visibility = View.VISIBLE
                binding.layoutEmpty.tvNothingFound.visibility = View.VISIBLE
            }else{
                binding.layoutEmpty.ivMaterialsempty.visibility = View.GONE
                binding.layoutEmpty.tvNothingFound.visibility = View.GONE
            }

            binding.progressbar.visibility = View.GONE
            testsAdapter.contentList = it

        })

        viewmodel.errorMaterialsLive.observe(viewLifecycleOwner, Observer {

            binding.progressbar.visibility = View.GONE
            myDialog.showErrorAlertDialog(it)

        })


    }


}