package com.todo.task.testapp2.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.todo.task.testapp2.model.CountyModel
import com.todo.task.testapp2.model.StateModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import com.todo.task.testapp2.R
import com.todo.task.testapp2.model.StateResponse

class StateRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : StateRepository {

    override suspend fun getStateList(): List<StateModel> {
        return try {
            val jsonString = loadJSONFromAsset()
            val type = object : TypeToken<StateResponse>() {}.type
            val states = Gson().fromJson<StateResponse>(jsonString, type)
            states.data
        } catch (e: Exception) {
            emptyList()  // Return an empty list in case of error
        }
    }

    override suspend fun getCountiesForState(state: StateModel): List<CountyModel> {
        return try {
            val jsonString = loadJSONFromAsset()
            val type = object : TypeToken<List<CountyModel>>() {}.type
            val counties = Gson().fromJson<List<CountyModel>>(jsonString, type)
            counties
        } catch (e: Exception) {
            emptyList()  // Return empty list in case of error
        }
    }

    override suspend fun getStateDetails(state: StateModel): StateModel {
        return try {
            val jsonString = loadJSONFromAsset()
            val type = object : TypeToken<StateResponse>() {}.type
            val states = Gson().fromJson<StateResponse>(jsonString, type)
            states.data[1]
        } catch (e: Exception) {
            StateModel(
                state = null,
                population = null,
                counties = null,
                details = null
            )
        }
    }

    // Utility function to read JSON data from assets folder
    private fun loadJSONFromAsset(): String {
        val json: String
        try {
            json = context.resources.openRawResource(R.raw.mock__state_data)
                .bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
        return json
    }
}
