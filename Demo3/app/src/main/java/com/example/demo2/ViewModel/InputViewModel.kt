package com.example.demo2.ViewModel

import SharedPrefManager
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo2.Models.HealthData
import com.example.demo2.Repository.HealthRepository
import com.example.demo2.Repository.Repository
import kotlinx.coroutines.launch
import android.util.Log


open class InputViewModel(application: Application) : AndroidViewModel(application) {
    private val healthRepository: HealthRepository
    private val sharedPrefManager: SharedPrefManager

    init {
        healthRepository = HealthRepository(application)
        sharedPrefManager = SharedPrefManager(application.applicationContext)
    }
    fun saveData(key: String, value: String) {
        sharedPrefManager.save(key, value)
    }

    fun getData(key: String): String? {
        return sharedPrefManager.get(key)
    }


    fun insert(date: String,
               time: String,
               sugarLevel: Int,
               activity: String,
               sleepDuration: Int,
               disease1: Boolean,
               disease2: Boolean,
               disease3: Boolean,
               ) {
        viewModelScope.launch {
            // create a healthdata
            val data = HealthData(
                date = date,
                time = time, sugarLevel = sugarLevel,
                activity = activity,
                sleepDuration = sleepDuration,
                disease1 = disease1,
                disease2 = disease2,
                disease3 = disease3
            )
            Log.d("in HealthViewModel", data.toString())

            healthRepository.insert(healthData = data)
            sharedPrefManager.clear()

        }
    }

    fun insert(healthData: HealthData) {
        viewModelScope.launch {
            Log.d("HealthViewModel", healthData.toString())
            healthRepository.insert(healthData)
            sharedPrefManager.clear()
        }
    }
}
