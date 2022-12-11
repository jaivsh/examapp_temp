package com.app.examprep.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.examprep.R
import com.app.examprep.data.PurchasHistoryData
import com.app.examprep.databinding.RvContentBinding
import com.app.examprep.databinding.RvPurchasehistoryBinding
import com.bumptech.glide.RequestManager


class PurchaseHistoryAdapter(val glide : RequestManager)  : RecyclerView.Adapter<PurchaseHistoryAdapter.CourseViewHolder>()  {
    
    class CourseViewHolder(val binding : RvPurchasehistoryBinding) : RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((PurchasHistoryData) -> Unit)? = null

    fun setOnItemClickListener(position: (PurchasHistoryData) -> Unit) {
        onItemClickListener = position
    }

    private var shareListener: ((PurchasHistoryData) -> Unit)? = null

    fun setOnItemClickListenerForShare(position: (PurchasHistoryData) -> Unit) {
        shareListener = position
    }

    private var warningListener: ((PurchasHistoryData) -> Unit)? = null

    fun setOnItemClickListenerForWarning(position: (PurchasHistoryData) -> Unit) {
        warningListener = position
    }

    private var chartListener: ((PurchasHistoryData) -> Unit)? = null

    fun setOnItemClickListenerForChart(position: (PurchasHistoryData) -> Unit) {
        chartListener = position
    }






    private val diffCallback = object : DiffUtil.ItemCallback<PurchasHistoryData>() {

        override fun areContentsTheSame(oldItem: PurchasHistoryData, newItem: PurchasHistoryData): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: PurchasHistoryData, newItem: PurchasHistoryData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }


    private val differ = AsyncListDiffer(this, diffCallback)

    var contentList : List<PurchasHistoryData>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    var visibleItemPositions = mutableListOf<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  CourseViewHolder {
        val binding = RvPurchasehistoryBinding.inflate(
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

                glide.load(data.courseData.courseImg).into(binding.ivCourse)

                binding.tvCoursename.text = data.courseData.courseName
                binding.tvDate.text = data.date

                binding.tvOrderid.text = "Order ID : " + data.orderId
                binding.tvAmount.text = "Amount : ₹" + data.money

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