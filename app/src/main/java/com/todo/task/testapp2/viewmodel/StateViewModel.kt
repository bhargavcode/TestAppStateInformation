package com.todo.task.testapp2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todo.task.testapp2.common.UiState
import com.todo.task.testapp2.model.StateModel
import com.todo.task.testapp2.repository.StateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StateViewModel @Inject constructor(
    private val repository: StateRepository
) : ViewModel() {

    private val _stateUiState = MutableLiveData<UiState<List<StateModel>>>()
    val stateUiState: LiveData<UiState<List<StateModel>>> = _stateUiState

    private val _selectedState = MutableLiveData<StateModel?>()
    val selectedState: LiveData<StateModel?> = _selectedState

    private val _highlightSelectedState = MutableLiveData<StateModel>()
    val highlightSelectedState: LiveData<StateModel> = _highlightSelectedState

    private val _searchKeyword = MutableLiveData<String>()
    val searchKeyword: LiveData<String> = _searchKeyword

    fun highlightSelection(selectedState: StateModel){
        _highlightSelectedState.value = selectedState
    }

    fun setSelectedState(selectedState: StateModel?){
        _selectedState.value = selectedState
    }
    fun updateSearchKeyword(keyword: String){
        _searchKeyword.value = keyword
    }
    fun isStateSelected() = _selectedState.value != null

    // Fetch the list of states
    fun fetchStates() {
        _stateUiState.value = UiState.Loading
        viewModelScope.launch{
            try {
                val states = repository.getStateList() // Fetch from the repository
                _stateUiState.value = UiState.Success(states)
            } catch (e: Exception) {
                _stateUiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
