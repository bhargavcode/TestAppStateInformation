package com.todo.task.testapp2.view.dashboardcomponents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.todo.task.testapp2.R
import com.todo.task.testapp2.adapters.StateListAdapter
import com.todo.task.testapp2.common.UiState
import com.todo.task.testapp2.databinding.FragmentStatesBinding
import com.todo.task.testapp2.viewmodel.StateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class StatesFragment : Fragment() {

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
        stateAdapter = StateListAdapter(false){ position, state ->
            viewModel.highlightSelection(state)
        }

        binding.rvStatesList.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            adapter = stateAdapter
        }

        // Observe state list from ViewModel
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
        // Fetch states
        viewModel.fetchStates()
    }
}