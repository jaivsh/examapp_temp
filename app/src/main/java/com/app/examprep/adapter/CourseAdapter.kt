package com.app.examprep.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.provider.SyncStateContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.examprep.R
import com.app.examprep.data.CourseData
import com.app.examprep.databinding.RvMaterialBinding
import com.app.examprep.others.Constants
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager


class CourseAdapter(val glide : RequestManager)  : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>()  {

    class CourseViewHolder(val binding : RvMaterialBinding) : RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((CourseData) -> Unit)? = null

    fun setOnItemClickListener(position: (CourseData) -> Unit) {
        onItemClickListener = position
    }

    private var shareListener: ((CourseData) -> Unit)? = null

    fun setOnItemClickListenerForShare(position: (CourseData) -> Unit) {
        shareListener = position
    }

    private var warningListener: ((CourseData) -> Unit)? = null

    fun setOnItemClickListenerForWarning(position: (CourseData) -> Unit) {
        warningListener = position
    }

    private var chartListener: ((CourseData) -> Unit)? = null

    fun setOnItemClickListenerForChart(position: (CourseData) -> Unit) {
        chartListener = position
    }






    private val diffCallback = object : DiffUtil.ItemCallback<CourseData>() {

        override fun areContentsTheSame(oldItem: CourseData, newItem: CourseData): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: CourseData, newItem: CourseData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }


    private val differ = AsyncListDiffer(this, diffCallback)

    var courseList : List<CourseData>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    var visibleItemPositions = mutableListOf<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  CourseViewHolder {
        val binding = RvMaterialBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return  CourseViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return courseList.size
    }

    inline var TextView.strike: Boolean
        set(visible) {
            paintFlags = if (visible) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
        get() = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG == Paint.STRIKE_THRU_TEXT_FLAG



    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val data = courseList[position]
        holder.itemView.apply {

            with(holder) {

                glide.load(data.courseImg).into(binding.ivCourse)

                binding.tvActualprice.strike = true

                binding.tvCoursename.text = data.courseName

                if(data.status == Constants.LOCKED){
                    if(Constants.curUserData.myMaterials.contains(data.courseId)){
                        binding.tvPrice.text = "PAID"
                        binding.tvPrice.setTextColor(ContextCompat.getColor(context, R.color.green_700))
                        binding.tvActualprice.visibility = View.INVISIBLE
                    }else{
                        binding.tvPrice.text = "₹${data.price}"
                        binding.tvActualprice.text = "₹${data.actualPrice}"
                    }
                }else{

                    binding.tvPrice.text = "Free"
                    binding.tvPrice.setTextColor(ContextCompat.getColor(context, R.color.green_700))
                    binding.tvActualprice.visibility = View.INVISIBLE

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