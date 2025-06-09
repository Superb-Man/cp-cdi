package com.example.demo2.ViewModel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo2.Models.HealthData
import com.example.demo2.Repository.HealthRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EditViewModel(application: Application) : AndroidViewModel(application) {

    private val healthRepository: HealthRepository

    init {
        healthRepository = HealthRepository(application)
    }

    suspend fun getHealthData(id: Int): HealthData {
        return withContext(Dispatchers.IO) {
            healthRepository.getHealthData(id)
        }

    }

    // Donot Freeze the UI while updating
    fun updateHealthData(
        id: Int,
        date: String,
        time: String,
        sugarLevel: Int,
        sleepDuration: Int,
        activity: String,
        disease1: Boolean,
        disease2: Boolean,
        disease3: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedData = HealthData(
                id = id,
                date = date,
                time = time,
                sugarLevel = sugarLevel,
                sleepDuration = sleepDuration,
                activity = activity,
                disease1 = disease1,
                disease2 = disease2,
                disease3 = disease3
            )

            healthRepository.updateHealthData(updatedData)
        }
    }

    fun updateHealthData(healthData: HealthData) {
        viewModelScope.launch(Dispatchers.IO) {
            healthRepository.updateHealthData(healthData)
        }
    }


    fun loadFromBundle(bundle: Bundle?): HealthData? {
        return bundle?.let {
            HealthData(
                id = it.getInt("id"),
                date = it.getString("date") ?: "",
                time = it.getString("time") ?: "",
                sugarLevel = it.getInt("sugarLevel"),
                sleepDuration = it.getInt("sleepDuration"),
                activity = it.getString("activity") ?: "",
                disease1 = it.getBoolean("disease1"),
                disease2 = it.getBoolean("disease2"),
                disease3 = it.getBoolean("disease3")
            )
        }
    }

    fun saveToBundle(healthData: HealthData): Bundle {
        return Bundle().apply {
            putInt("id", healthData.id)
            putString("date", healthData.date)
            putString("time", healthData.time)
            putInt("sugarLevel", healthData.sugarLevel)
            putInt("sleepDuration", healthData.sleepDuration)
            putString("activity", healthData.activity)
            putBoolean("disease1", healthData.disease1)
            putBoolean("disease2", healthData.disease2)
            putBoolean("disease3", healthData.disease3)
        }
    }



}
