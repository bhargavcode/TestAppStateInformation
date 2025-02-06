package com.todo.task.testapp2.view.dashboardcomponents

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.todo.task.testapp2.databinding.FragmentStateDetailsBinding
import com.todo.task.testapp2.model.StateModel
import com.todo.task.testapp2.viewmodel.StateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import com.todo.task.testapp2.R

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class StatesDetailsFragment : Fragment() {

    private lateinit var binding: FragmentStateDetailsBinding
    private val viewModel: StateViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentStateDetailsBinding.inflate(inflater, container, false)
        //Set views initially hidden
        binding.root.isVisible = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            selectedState.observe(viewLifecycleOwner) { state ->
                updateStateDataOnUi(state)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateStateDataOnUi(state: StateModel?) {
        binding.root.isVisible = state != null
        state?.let {
            binding.apply {
                (activity as? AppCompatActivity)?.supportActionBar?.title =
                    getString(R.string.state_details_label, it.state)
                tvName.text = it.state
                tvStateOverAllPopulation.text = it.population
                tvNumberOfCounties.text = it.counties.toString()
                tvNameOfCounties.text =
                    it.details?.countiesList?.map {s-> s.name }?.joinToString("  |  ")
            }
        }
    }
}