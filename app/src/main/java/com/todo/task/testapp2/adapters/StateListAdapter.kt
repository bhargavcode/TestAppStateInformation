package com.todo.task.testapp2.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.todo.task.testapp2.databinding.LayoutNamesListItemBinding
import com.todo.task.testapp2.model.StateModel
import java.util.*

class StateListAdapter(
    private val allowStateClick: Boolean = true,
    private val filterResult: (Boolean, String) -> Unit = {_, _->},
    private val onStateClick: (Int, StateModel) -> Unit = { _, _ -> },
    private val onLongPress: (Int, StateModel) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<StateListAdapter.StateViewHolder>(), Filterable {

    private var previousSelectedPosition: Int = -1
    private var previousSelectedColor: Int = Color.GREEN
    private val states: MutableList<StateModel> = mutableListOf()
    private val filteredStates: MutableList<StateModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        val binding = LayoutNamesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        val state = filteredStates[position]
        holder.bind(position, state)
        holder.cardView.apply {
            if (position == previousSelectedPosition) {
                setCardBackgroundColor(previousSelectedColor)
                postDelayed({
                    previousSelectedPosition = -1
                    notifyItemChanged(position)
                }, 2000)
            } else {
                setCardBackgroundColor(if (allowStateClick) Color.WHITE else Color.LTGRAY)
            }
        }
    }

    override fun getItemCount(): Int = filteredStates.size

    fun setStatesList(stateList: List<StateModel>) {
        states.clear()
        states.addAll(stateList)
        filteredStates.clear()
        filteredStates.addAll(stateList)
        notifyDataSetChanged()
    }

    fun highlightSelectedState(selectedState: StateModel) {
        previousSelectedPosition = filteredStates.indexOf(selectedState)
        onStateClick(previousSelectedPosition, selectedState)
        notifyItemChanged(previousSelectedPosition)
    }

    inner class StateViewHolder(private val binding: LayoutNamesListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val cardView = binding.cvParent
        fun bind(position: Int, state: StateModel) {
            binding.tvName.apply {
                text = state.state
                setOnClickListener {
                    if (allowStateClick)
                        onStateClick(position, state)
                }
                setOnLongClickListener {
                    if (!allowStateClick)
                        onLongPress(position, state)
                    return@setOnLongClickListener true
                }
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase(Locale.getDefault())
                val filterResults = FilterResults()
                filterResults.values = if (query.isNullOrEmpty()) {
                    states
                } else {
                    states.filter { it.state?.lowercase(Locale.getDefault())?.contains(query) == true }
                }
                return filterResults
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredStates.clear()
                filteredStates.addAll(results?.values as List<StateModel>)
                filterResult.invoke(filteredStates.isEmpty(), constraint.toString())
                notifyDataSetChanged()
            }
        }
    }
}
