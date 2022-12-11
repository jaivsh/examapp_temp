package com.app.examprep.fragment.home.course

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.examprep.R
import com.app.examprep.adapter.QuestionNumAdapter
import com.app.examprep.data.TestHistoryData
import com.app.examprep.databinding.FragmentLessonsBinding
import com.app.examprep.databinding.FragmentPrimaryBinding
import com.app.examprep.databinding.FragmentQuizBinding
import com.app.examprep.firebase.MainViewModel
import com.app.examprep.others.Constants
import java.util.*

class QuizFragment : Fragment(R.layout.fragment_quiz) {

    private lateinit var binding : FragmentQuizBinding
    var curQuestion = 1
    lateinit var questionNumAdapter : QuestionNumAdapter
    lateinit var countDownTimer: CountDownTimer
    var seconds = 0L
    var isSubmitClicked = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentQuizBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {


        questionNumAdapter = QuestionNumAdapter(Constants.curTest.questions.size)
        binding.rvQuestionNumbers.adapter = questionNumAdapter
        binding.rvQuestionNumbers.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        displayQuestion()

        binding.tvTotalquestions.text = "Total Questions = ${Constants.curTest.questions.size}"

        questionNumAdapter.setOnItemClickListener {
            if (isSubmitClicked){
                binding.cdQuestioncontainer.visibility = View.VISIBLE
                binding.cdQuestiondetailsfinal.visibility = View.GONE
                binding.fabFinalsubmit.visibility = View.GONE
                binding.fabSubmit.visibility = View.VISIBLE
                isSubmitClicked = false
            }

            curQuestion = it
            displayQuestion()
        }

        binding.fabPrevious.setOnClickListener {
            curQuestion--
            displayQuestion()
        }

        binding.fabNext.setOnClickListener {
            curQuestion++
            displayQuestion()
        }

        binding.fabFinalsubmit.setOnClickListener {

            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_quizFragment_to_quizResultFragment)
        }

        binding.fabSubmit.setOnClickListener {
            isSubmitClicked = true
            binding.fabSubmit.visibility = View.GONE
            binding.cdQuestioncontainer.visibility = View.GONE
            binding.cdQuestiondetailsfinal.visibility = View.VISIBLE
            binding.fabFinalsubmit.visibility = View.VISIBLE
            binding.fabPrevious.visibility = View.INVISIBLE
            binding.fabNext.visibility = View.INVISIBLE

            curQuestion = Constants.curTest.questions.size + 1

            val attemptedQuestions = Constants.curTest.questions.filter { it.answer != 0 }
            val notAttemptedQuestions = Constants.curTest.questions.size - attemptedQuestions.size

            binding.tvQuestiondetails.text = "Attempted Questions - ${attemptedQuestions.size}" +
                    "\n Not attempted Questions - ${notAttemptedQuestions}"


        }

        timer()


    }

    private fun setOptionsColor(layout : ConstraintLayout,choosenAnswer : Int){

        val previousAnswer =  Constants.curTest.questions.filter { it.questionNo == curQuestion }[0].choosenAnswer

          setAllColorsWhite()

            if(previousAnswer != choosenAnswer){
                Constants.curTest.questions.filter { it.questionNo == curQuestion }[0].apply {
                    this.choosenAnswer = choosenAnswer
                }
               // questionNumAdapter.attendedQuestions.add(curQuestion-1)
                layout.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.purple_200))

            }else{

            //    questionNumAdapter.attendedQuestions.remove(curQuestion-1)
                Constants.curTest.questions.filter { it.questionNo == curQuestion }[0].apply {
                    this.choosenAnswer = 0
                }

            }

        questionNumAdapter.notifyDataSetChanged()


    }

    private fun setAllColorsWhite(){

        binding.ctOptionA.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
        binding.ctOptionB.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
        binding.ctOptionC.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
        binding.ctOptionD.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))

    }

    private fun displayQuestion() {

        val question = Constants.curTest.questions.filter { it.questionNo == curQuestion }

        if(question.isNotEmpty()){

            binding.tvQuestiion.text = question[0].question
            binding.tvOptionA.text = question[0].option1
            binding.tvOptionB.text = question[0].option2
            binding.tvOptionC.text = question[0].option3
            binding.tvOptionD.text = question[0].option4



            if(curQuestion == Constants.curTest.questions.size){
                binding.fabNext.visibility = View.INVISIBLE
            }else{
                binding.fabNext.visibility = View.VISIBLE
            }

            binding.cdOptionA.setOnClickListener {
                setOptionsColor(binding.ctOptionA,1)
            }

            binding.cdOptionB.setOnClickListener {
                setOptionsColor(binding.ctOptionB,2)
            }

            binding.cdOptionC.setOnClickListener {
                setOptionsColor(binding.ctOptionC,3)
            }

            binding.cdOptionD.setOnClickListener {
                setOptionsColor(binding.ctOptionD,4)
            }

            questionNumAdapter.curQuestion = curQuestion-1
            questionNumAdapter.notifyDataSetChanged()

            if(curQuestion == 1){
                binding.fabPrevious.visibility = View.INVISIBLE
            }else{
                binding.fabPrevious.visibility = View.VISIBLE
            }

            setAllColorsWhite()

            when(question[0].choosenAnswer){
                1 ->  binding.ctOptionA.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.purple_200))
                2 ->  binding.ctOptionB.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.purple_200))
                3 ->  binding.ctOptionC.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.purple_200))
                4 ->  binding.ctOptionD.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.purple_200))
            }

        }

    }



    fun timer(){
        val time = ((20 * 60) * 1000).toLong()
        countDownTimer = object : CountDownTimer(time, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                seconds = time - millisUntilFinished
                val finaltime = timefinal(millisUntilFinished)
                binding.tvTime.text = finaltime
            }

            override fun onFinish() {
                Toast.makeText(requireContext(),"Time finished",Toast.LENGTH_SHORT).show()
            }
        };
        countDownTimer.start()
    }

    private fun timefinal(millisUntilFinished: Long): String {
        val min = ((millisUntilFinished / 1000) / 60).toInt()
        val sec = ((millisUntilFinished / 1000) % 60).toInt()
        val finaltime = finaltime(min, sec)
        return finaltime
    }

    private fun finaltime(min: Int, sec: Int): String {
        val minFormat = getMin(min)
        val secFormat = getSec(sec)
        val finaltime = "$minFormat:$secFormat"
        return finaltime
    }

    private fun getMin(min: Int): String {
        val minFormat = getSec(min)
        return minFormat
    }

    private fun getSec(sec: Int): String {
        val secFormat = if (sec == 0) {
            "00"
        } else if (sec < 10) {
            "0$sec"
        } else {
            "$sec"
        }
        return secFormat
    }


}