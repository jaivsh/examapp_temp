package com.app.examprep.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.examprep.R
import com.app.examprep.data.TestHistoryData
import com.app.examprep.databinding.RvContentBinding
import java.text.SimpleDateFormat


class TestHistoryAdapter  : RecyclerView.Adapter<TestHistoryAdapter.CourseViewHolder>()  {
    
    class CourseViewHolder(val binding : RvContentBinding) : RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((TestHistoryData) -> Unit)? = null

    fun setOnItemClickListener(position: (TestHistoryData) -> Unit) {
        onItemClickListener = position
    }

    private var shareListener: ((TestHistoryData) -> Unit)? = null

    fun setOnItemClickListenerForShare(position: (TestHistoryData) -> Unit) {
        shareListener = position
    }

    private var warningListener: ((TestHistoryData) -> Unit)? = null

    fun setOnItemClickListenerForWarning(position: (TestHistoryData) -> Unit) {
        warningListener = position
    }

    private var chartListener: ((TestHistoryData) -> Unit)? = null

    fun setOnItemClickListenerForChart(position: (TestHistoryData) -> Unit) {
        chartListener = position
    }






    private val diffCallback = object : DiffUtil.ItemCallback<TestHistoryData>() {

        override fun areContentsTheSame(oldItem: TestHistoryData, newItem: TestHistoryData): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: TestHistoryData, newItem: TestHistoryData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }


    private val differ = AsyncListDiffer(this, diffCallback)

    var contentList : List<TestHistoryData>
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
                
                binding.cdCount.setImageResource(R.drawable.test)
                binding.tvLessonname.text = data.name

                binding.ivLockimg.visibility = View.INVISIBLE

                binding.tvCategory.text = getDate(data.date.toLong())
                

            }

            setOnClickListener {

                onItemClickListener?.let {
                        click ->
                    click(data)
                }
            }
        }
    }


    fun getDate(ms : Long) : String{
        val format = SimpleDateFormat("dd, MMM yyyy")
        return format.format(ms)
    }

}