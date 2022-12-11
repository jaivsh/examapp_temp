package com.app.examprep.fragment

import android.os.Bundle
import android.provider.SyncStateContract
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.examprep.R
import com.app.examprep.adapter.PurchaseHistoryAdapter
import com.app.examprep.databinding.FragmentListBinding
import com.app.examprep.databinding.FragmentTncBinding
import com.app.examprep.others.Constants
import com.bumptech.glide.Glide

class PurchaseHistoryFragment : Fragment(R.layout.fragment_list) {

    lateinit var binding : FragmentListBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentListBinding.bind(view)


        setUI(view)


    }

    private fun setUI(view: View) {

        binding.progressbar.visibility = View.GONE

        if(Constants.curUserData.purchaseHistory.isEmpty()){
                binding.layoutEmpty.ivMaterialsempty.visibility = View.VISIBLE
                binding.layoutEmpty.tvNothingFound.visibility = View.VISIBLE
            }else{
                binding.layoutEmpty.ivMaterialsempty.visibility = View.GONE
                binding.layoutEmpty.tvNothingFound.visibility = View.GONE
            }

        val glide = Glide.with(requireContext())

        val adapter = PurchaseHistoryAdapter(glide)

        binding.rvItems.adapter = adapter
        binding.rvItems.layoutManager  = LinearLayoutManager(requireContext())

        adapter.contentList = Constants.curUserData.purchaseHistory



    }

}