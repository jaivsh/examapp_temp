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
import com.app.examprep.databinding.RvMaterialBinding
import com.app.examprep.databinding.RvQuizBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager


class QuestionNumAdapter(val size : Int)  : RecyclerView.Adapter<QuestionNumAdapter.CourseViewHolder>()  {

    class CourseViewHolder(val binding : RvQuizBinding) : RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(position: (Int) -> Unit) {
        onItemClickListener = position
    }


    var attendedQuestions = mutableListOf<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  CourseViewHolder {
        val binding = RvQuizBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return  CourseViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return size
    }

    var curQuestion = 0



    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.itemView.apply {

            with(holder) {

                binding.tvQuestionno.text = (position + 1).toString()

                if(curQuestion == position){
                    binding.tvQuestionno.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_500))
                }else{
                    binding.tvQuestionno.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                }

                if(attendedQuestions.contains(position)){
                    binding.tvQuestionno.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_200))
                }

           }

            setOnClickListener {

                onItemClickListener?.let {
                        click ->
                    click(position+1)
                }
            }
        }
    }





}