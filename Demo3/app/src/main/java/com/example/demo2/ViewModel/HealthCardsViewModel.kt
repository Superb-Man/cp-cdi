package com.example.demo2.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo2.Models.HealthData
import com.example.demo2.Repository.HealthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HealthCardsViewModel(application: Application) : AndroidViewModel(application) {


    private val repository: HealthRepository = HealthRepository(application)
    var healthDataList: ArrayList<HealthData> = ArrayList<HealthData>()

    suspend fun getAllHealthData(): ArrayList<HealthData> {
        withContext(Dispatchers.IO) {
            healthDataList = repository.getAllHealthData() as ArrayList<HealthData>
        }
        return healthDataList
    }
    fun deleteHealthData(id: Int) {
        viewModelScope.launch {
            repository.deleteHealthData(id)
        }
    }


}
