package com.app.examprep.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.examprep.R
import com.app.examprep.data.ContentData
import com.app.examprep.databinding.RvContentBinding


class ContentAdapter  : RecyclerView.Adapter<ContentAdapter.CourseViewHolder>()  {
    
    class CourseViewHolder(val binding : RvContentBinding) : RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((ContentData) -> Unit)? = null

    fun setOnItemClickListener(position: (ContentData) -> Unit) {
        onItemClickListener = position
    }

    private var shareListener: ((ContentData) -> Unit)? = null

    fun setOnItemClickListenerForShare(position: (ContentData) -> Unit) {
        shareListener = position
    }

    private var warningListener: ((ContentData) -> Unit)? = null

    fun setOnItemClickListenerForWarning(position: (ContentData) -> Unit) {
        warningListener = position
    }

    private var chartListener: ((ContentData) -> Unit)? = null

    fun setOnItemClickListenerForChart(position: (ContentData) -> Unit) {
        chartListener = position
    }






    private val diffCallback = object : DiffUtil.ItemCallback<ContentData>() {

        override fun areContentsTheSame(oldItem: ContentData, newItem: ContentData): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: ContentData, newItem: ContentData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }


    private val differ = AsyncListDiffer(this, diffCallback)

    var contentList : List<ContentData>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    var visibleItemPositions = mutableListOf<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  CourseViewHolder {
        val binding = RvContentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return  CourseViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return contentList.size
    }
    

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val data = contentList[position]
        holder.itemView.apply {

            with(holder) {

                binding.tvLessonname.text = data.name
                binding.tvCategory.text = data.type

                binding.tvFree.visibility = View.GONE
                binding.ivLockimg.visibility = View.GONE

   /*             if(data.status == Constants.LOCKED){
                    binding.tvFree.visibility = View.GONE
                    if(Constants.curUserData.myMaterials.contains(Constants.curCourse.courseId)){
                        binding.ivLockimg.setImageResource(R.drawable.unlock)
                    }else{
                        binding.ivLockimg.setImageResource(R.drawable.lock)
                    }
                }else{
                    binding.ivLockimg.visibility = View.INVISIBLE
                    binding.tvFree.visibility = View.VISIBLE
                }*/


                if(data.type == "PDF"){
                    binding.cdCount.setImageResource(R.drawable.pdfimg)
                }

                if(data.type == "Video"){
                    binding.cdCount.setImageResource(R.drawable.video)
                }

                if(data.type == "Test"){
                    binding.cdCount.setImageResource(R.drawable.test)
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