package com.todo.task.testapp2.repository

import com.todo.task.testapp2.model.CountyModel
import com.todo.task.testapp2.model.StateModel

interface StateRepository {
    suspend fun getStateList(): List<StateModel>  // Fetch list of states
    suspend fun getCountiesForState(state: StateModel): List<CountyModel>  // Fetch counties for a selected state
    suspend fun getStateDetails(state: StateModel): StateModel  // Fetch detailed info for a state
}