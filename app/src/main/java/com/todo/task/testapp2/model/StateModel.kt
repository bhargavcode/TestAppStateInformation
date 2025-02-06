package com.todo.task.testapp2.model

data class StateModel(
    val state: String?,
    val population: String?,
    val counties: Int?,
    val details: StateDetailsModel?
)
data class StateDetailsModel(
    val state: String?,
    val population: String?,
    val counties: Int?,
    val countiesList: List<CountyModel>?
)

data class StateResponse(val data: List<StateModel>)