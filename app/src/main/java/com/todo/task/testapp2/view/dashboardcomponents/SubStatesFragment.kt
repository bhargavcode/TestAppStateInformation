package com.todo.task.testapp2.view.dashboardcomponents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.google.android.material.snackbar.Snackbar
import com.todo.task.testapp2.adapters.StateListAdapter
import com.todo.task.testapp2.common.UiState
import com.todo.task.testapp2.databinding.FragmentStatesBinding
import com.todo.task.testapp2.viewmodel.StateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import com.todo.task.testapp2.R

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */@AndroidEntryPoint
class SubStatesFragment : Fragment() {
    private val viewModel: StateViewModel by activityViewModels()
    private lateinit var binding: FragmentStatesBinding
    private lateinit var stateAdapter: StateListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentStatesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateAdapter = StateListAdapter(
            filterResult = {result, keyword->
                binding.tvNoResult?.apply {
                    isVisible = result
                    text = getString(R.string.no_search_result, keyword)
                }
            },
            onStateClick = {position, state ->
            if (position >= 0){
                binding.rvStatesList.smoothScrollToPosition(position)
                viewModel.setSelectedState(state)
            }else{
                Snackbar.make(binding.root, "Selected one, not found in sub list", 0).show()
            }
            //Navigate to next
        })

        binding.rvStatesList.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            adapter = stateAdapter
        }

        viewModel.stateUiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    binding.pbStates?.isVisible = true
                }
                is UiState.Success -> {
                    binding.pbStates?.isVisible = false
                    stateAdapter.setStatesList(uiState.data)
                }
                is UiState.Error -> {
                    binding.pbStates?.isVisible = false
                    /* His block can be used to show some error */
                }

                UiState.Idle -> {
                    //Nothing to do
                }
            }
        }
        viewModel.highlightSelectedState.observe(viewLifecycleOwner) { state ->
            stateAdapter.highlightSelectedState(state)
        }

        viewModel.searchKeyword.observe(viewLifecycleOwner) { state ->
            stateAdapter.filter.filter(state)
        }
    }
}