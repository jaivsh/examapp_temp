package com.app.examprep.fragment.home.course

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.examprep.R
import com.app.examprep.adapter.LessonAdapter
import com.app.examprep.databinding.FragmentLessonsBinding
import com.app.examprep.firebase.MainViewModel
import com.app.examprep.others.Constants
import com.app.examprep.others.MyDialog
import com.app.examprep.payment.CheckoutActivity
import com.bulltradeintraday.app.others.SharedPref

class LessonsFragment : Fragment(R.layout.fragment_lessons) {

    private lateinit var binding : FragmentLessonsBinding
    lateinit var lessonAdapter: LessonAdapter
    lateinit var viewmodel : MainViewModel
    lateinit var myDialog : MyDialog
    lateinit var sharePref : SharedPref
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentLessonsBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {

        viewmodel =
            ViewModelProvider(this).get(MainViewModel::class.java)

        myDialog = MyDialog(requireContext())

        sharePref = SharedPref(requireContext())

        binding.tvTitle.text = Constants.curCourse.courseName

        lessonAdapter = LessonAdapter()
        binding.rvLessons.adapter = lessonAdapter
        binding.rvLessons.layoutManager = LinearLayoutManager(requireContext())

        if(Constants.curUserData.myMaterials.contains(Constants.curCourse.courseId)){
            binding.btBuy.visibility = View.INVISIBLE
        }else{
            binding.btBuy.visibility = View.VISIBLE
        }

        lessonAdapter.lessonsList = Constants.curCourse.lessons



        lessonAdapter.setOnItemClickListener {
            if(it.status == Constants.LOCKED){
                if(Constants.curUserData.myMaterials.contains(Constants.curCourse.courseId)){
                    Constants.curLesson = it
                    Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_lessonsFragment_to_contentFragment)
                }else{
                    Toast.makeText(requireContext(), "Please buy this course to unlock this lesson", Toast.LENGTH_SHORT).show()
                }
            }else{
                Constants.curLesson = it
                Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_lessonsFragment_to_contentFragment)
            }
         }

        binding.btBuy.setOnClickListener {

            sharePref.setCourseData(Constants.curCourse)
            startActivity(Intent(requireContext(),CheckoutActivity::class.java))

        }



        setCallbacks()


    }

    private fun setCallbacks() {

        viewmodel.updatePurchaseLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            myDialog.dismissProgressDialog()

            Toast.makeText(requireContext(), "You bought it , Please check your materials", Toast.LENGTH_SHORT).show()

            binding.btBuy.visibility = View.INVISIBLE

        })

        viewmodel.errorupdatePurchaseLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            myDialog.dismissProgressDialog()

            myDialog.showErrorAlertDialog(it,null)

        })
    }

}