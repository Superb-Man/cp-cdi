package com.example.demo2.Repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.demo2.DB.AppDatabase
import com.example.demo2.Models.HealthData

class HealthRepository(application: Application){
    private val healthDao = AppDatabase.getInstance(application).healthDataDAO


    suspend fun insert(healthData: HealthData) {
        healthDao.insert(healthData)
    }

    suspend fun getAllHealthData(): List<HealthData>{
        return healthDao.getAllHealthData()
    }

    suspend fun getHealthData(id: Int): HealthData {
        return healthDao.getHealthData(id)
    }

    suspend fun deleteHealthData(id: Int) {
        healthDao.deleteHealthData(id)
    }

    suspend fun updateHealthData(healthData: HealthData) {
        healthDao.updateHealthData(healthData)
    }
}