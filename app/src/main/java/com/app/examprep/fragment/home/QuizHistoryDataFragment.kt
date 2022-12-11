package com.app.examprep.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.examprep.R
import com.app.examprep.adapter.QuizResultAdapter
import com.app.examprep.data.TestHistoryData
import com.app.examprep.databinding.FragmentQuizBinding
import com.app.examprep.databinding.FragmentQuizresultBinding
import com.app.examprep.firebase.MainViewModel
import com.app.examprep.others.Constants
import java.util.*

class QuizHistoryDataFragment : Fragment(R.layout.fragment_quizresult) {

    private lateinit var binding : FragmentQuizresultBinding
    lateinit var quizResultAdapter: QuizResultAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentQuizresultBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {

        quizResultAdapter = QuizResultAdapter()
        binding.rvQuestions.adapter = quizResultAdapter
        binding.rvQuestions.layoutManager = LinearLayoutManager(requireContext())

        quizResultAdapter.questionList = Constants.curTest.questions


        binding.tvRightAnswer.text = getCorrectAnswer()
        binding.tvWrongAnswer.text = getWrongAnswer()
        binding.tvMarksCount.text = getMarks()



    }


    fun getCorrectAnswer() : String{

        val correctAnswer = Constants.curTest.questions.filter { it.answer == it.choosenAnswer }.filter { it.answer != 0 }.size

        return "$correctAnswer/${Constants.curTest.questions.size}"


    }

    fun getMarks() : String{

        val correctAnswer = Constants.curTest.questions.filter { it.answer == it.choosenAnswer }.filter { it.answer != 0 }.size

        return "$correctAnswer"


    }

    fun getWrongAnswer() : String{

        val wrongAnswer = Constants.curTest.questions.filter { it.answer != it.choosenAnswer }.filter { it.answer != 0 }.size

        return "$wrongAnswer/${Constants.curTest.questions.size}"


    }


}