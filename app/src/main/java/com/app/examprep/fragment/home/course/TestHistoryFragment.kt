package com.app.examprep.fragment.home.course

import android.os.Bundle
import android.provider.SyncStateContract
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.examprep.R
import com.app.examprep.adapter.QuizResultAdapter
import com.app.examprep.adapter.TestHistoryAdapter
import com.app.examprep.data.TestData
import com.app.examprep.databinding.FragmentQuizresultBinding
import com.app.examprep.databinding.FragmentTeamhistoryBinding
import com.app.examprep.firebase.MainViewModel
import com.app.examprep.others.Constants

class TestHistoryFragment : Fragment(R.layout.fragment_teamhistory) {
    
    private lateinit var binding : FragmentTeamhistoryBinding
    lateinit var testHistoryAdapter: TestHistoryAdapter
    lateinit var viewModel: MainViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTeamhistoryBinding.bind(view)

        setUI(view)

    }

    private fun setUI(view: View) {

        testHistoryAdapter = TestHistoryAdapter()

        binding.rvItems.adapter = testHistoryAdapter
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())

        testHistoryAdapter.contentList = Constants.curUserData.testHistory

        testHistoryAdapter.setOnItemClickListener {
            Constants.curTest = TestData(it.id,it.questions)
            Navigation.findNavController(requireActivity(),R.id.main_fragment).navigate(R.id.action_testHistoryFragment_to_quizHistoryDataFragment)
        }

       if(Constants.curUserData.testHistory.isNotEmpty()){
           binding.progressbar.visibility = View.INVISIBLE
           binding.layoutEmpty.ivMaterialsempty.visibility = View.GONE
           binding.layoutEmpty.tvNothingFound.visibility = View.GONE
           binding.rvItems.visibility = View.VISIBLE
       }else{
           binding.progressbar.visibility = View.INVISIBLE
           binding.layoutEmpty.ivMaterialsempty.visibility = View.VISIBLE
           binding.layoutEmpty.tvNothingFound.visibility = View.VISIBLE
           binding.rvItems.visibility = View.GONE
       }


    }


}