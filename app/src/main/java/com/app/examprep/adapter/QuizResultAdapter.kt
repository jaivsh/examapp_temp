package com.app.examprep.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.examprep.R
import com.app.examprep.data.CourseData
import com.app.examprep.data.LessonData
import com.app.examprep.data.QuestionData
import com.app.examprep.databinding.RvMaterialBinding
import com.app.examprep.databinding.RvQuizBinding
import com.app.examprep.databinding.RvQuizResultBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager


class QuizResultAdapter  : RecyclerView.Adapter<QuizResultAdapter.CourseViewHolder>()  {

    class CourseViewHolder(val binding : RvQuizResultBinding) : RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((QuestionData) -> Unit)? = null

    fun setOnItemClickListener(position: (QuestionData) -> Unit) {
        onItemClickListener = position
    }



    private val diffCallback = object : DiffUtil.ItemCallback<QuestionData>() {

        override fun areContentsTheSame(oldItem: QuestionData, newItem: QuestionData): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: QuestionData, newItem: QuestionData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }


    private val differ = AsyncListDiffer(this, diffCallback)

    var questionList : List<QuestionData>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  CourseViewHolder {
        val binding = RvQuizResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return  CourseViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return questionList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val data = questionList[position]
        holder.itemView.apply {

            with(holder) {

                binding.tvQuestiion.text = "Q-${position+1} : ${data.question}"

                binding.tvOptionA.text = data.option1
                binding.tvOptionB.text = data.option2
                binding.tvOptionC.text = data.option3
                binding.tvOptionD.text = data.option4

                when(data.choosenAnswer){
                    1 ->  binding.ctOptionA.setBackgroundColor(ContextCompat.getColor(context,R.color.red_200))
                    2 ->  binding.ctOptionB.setBackgroundColor(ContextCompat.getColor(context,R.color.red_200))
                    3 ->  binding.ctOptionC.setBackgroundColor(ContextCompat.getColor(context,R.color.red_200))
                    4 ->  binding.ctOptionD.setBackgroundColor(ContextCompat.getColor(context,R.color.red_200))
                }

                when(data.answer){
                    1 ->  binding.ctOptionA.setBackgroundColor(ContextCompat.getColor(context,R.color.green_200))
                    2 ->  binding.ctOptionB.setBackgroundColor(ContextCompat.getColor(context,R.color.green_200))
                    3 ->  binding.ctOptionC.setBackgroundColor(ContextCompat.getColor(context,R.color.green_200))
                    4 ->  binding.ctOptionD.setBackgroundColor(ContextCompat.getColor(context,R.color.green_200))
                }

           }

            setOnClickListener {

                onItemClickListener?.let {
                        click ->
                    click(data)
                }
            }
        }
    }





}