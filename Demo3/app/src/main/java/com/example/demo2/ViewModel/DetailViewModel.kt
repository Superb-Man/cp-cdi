package com.example.demo2.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.demo2.Models.HealthData
import com.example.demo2.Repository.HealthRepository
import kotlinx.coroutines.launch



class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: HealthRepository = HealthRepository(application)


    private val _healthData = MutableLiveData<HealthData>()
    val healthData: LiveData<HealthData> get() = _healthData

    fun getHealthData(id: Int) {
        viewModelScope.launch {
            val fetchedData = repository.getHealthData(id)
            _healthData.postValue(fetchedData)
        }
    }
}