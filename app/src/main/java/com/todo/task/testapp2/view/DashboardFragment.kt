package com.todo.task.testapp2.view

import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.todo.task.testapp2.R
import com.todo.task.testapp2.databinding.FragmentDashboardBinding
import com.todo.task.testapp2.view.dashboardcomponents.StatesDetailsFragment
import com.todo.task.testapp2.view.dashboardcomponents.StatesFragment
import com.todo.task.testapp2.view.dashboardcomponents.SubStatesFragment
import com.todo.task.testapp2.viewmodel.StateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel: StateViewModel by activityViewModels()
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_content_states, StatesFragment(), "stateListFragment")
                .replace(R.id.fragment_content_states_sub_list, SubStatesFragment(), "stateSubList")
                .replace(
                    R.id.fragment_content_sub_states_details,
                    StatesDetailsFragment(),
                    "detailsFragment"
                )
                .commit()
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnShowDetails.setOnClickListener {
                if (viewModel.isStateSelected())
                    findNavController().navigate(R.id.StateDetails)
                else Snackbar.make(binding.root, "State not selected!!", 0).show()
            }
            etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    p0: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int
                ) {
                    //Nothing to do
                }

                override fun onTextChanged(
                    p0: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int
                ) {
                    //Nothing to do
                }

                override fun afterTextChanged(p0: Editable?) {
                    viewModel.apply {
                        if (isStateSelected())
                            setSelectedState(null)
                        updateSearchKeyword(p0.toString())
                    }
                }
            })
        }
        viewModel.searchKeyword.observe(viewLifecycleOwner) { keyword ->
            binding.etSearch.apply {
                if (text?.isEmpty() == true && keyword.isNotEmpty()) //to prevent cycle and config change
                    setText(keyword)
            }
        }
    }

}