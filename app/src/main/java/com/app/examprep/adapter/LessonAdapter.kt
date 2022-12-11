package com.app.examprep.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.examprep.R
import com.app.examprep.data.LessonData
import com.app.examprep.databinding.RvLessonBinding
import com.app.examprep.others.Constants


class LessonAdapter  : RecyclerView.Adapter<LessonAdapter.CourseViewHolder>()  {
    
    class CourseViewHolder(val binding : RvLessonBinding) : RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((LessonData) -> Unit)? = null

    fun setOnItemClickListener(position: (LessonData) -> Unit) {
        onItemClickListener = position
    }

    private var shareListener: ((LessonData) -> Unit)? = null

    fun setOnItemClickListenerForShare(position: (LessonData) -> Unit) {
        shareListener = position
    }

    private var warningListener: ((LessonData) -> Unit)? = null

    fun setOnItemClickListenerForWarning(position: (LessonData) -> Unit) {
        warningListener = position
    }

    private var chartListener: ((LessonData) -> Unit)? = null

    fun setOnItemClickListenerForChart(position: (LessonData) -> Unit) {
        chartListener = position
    }






    private val diffCallback = object : DiffUtil.ItemCallback<LessonData>() {

        override fun areContentsTheSame(oldItem: LessonData, newItem: LessonData): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: LessonData, newItem: LessonData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }


    private val differ = AsyncListDiffer(this, diffCallback)

    var lessonsList : List<LessonData>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    var visibleItemPositions = mutableListOf<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  CourseViewHolder {
        val binding = RvLessonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return  CourseViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return lessonsList.size
    }
    

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val data = lessonsList[position]
        holder.itemView.apply {

            with(holder) {

                binding.tvCount.text = data.lessonCount.toString()

                binding.tvTeachername.text = data.teacher

                binding.tvLessonname.text = data.name
                binding.tvCategory.text = data.category



               if(data.status == Constants.LOCKED){
                    binding.tvFree.visibility = View.GONE
                    if(Constants.curUserData.myMaterials.contains(Constants.curCourse.courseId)){
                        binding.ivLockimg.setImageResource(R.drawable.unlock)
                    }else{
                        binding.ivLockimg.setImageResource(R.drawable.lock)
                    }
                }else{
                    binding.ivLockimg.visibility = View.INVISIBLE
                    binding.tvFree.visibility = View.VISIBLE
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