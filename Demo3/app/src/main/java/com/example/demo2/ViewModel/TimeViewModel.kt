//package com.example.demo2
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//
//class TimeViewModel : ViewModel() {
//    private val _time = MutableLiveData<Long>()
//    val time: LiveData<Long> get() = _time
//
//    fun decreaseTime() {
//        _time.value = _time.value?.minus(1000)
//    }
//
//    fun updateTime(savedTime: Long?) {
//        _time.value = savedTime ?: System.currentTimeMillis()
//    }
//}

package com.example.demo2.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.demo2.Repository.Repository
import com.example.demo2.Models.TimeData
import kotlinx.coroutines.launch

class TimeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository = Repository(application)

    private val _time = MutableLiveData<Long>()
    val time: LiveData<Long> get() = _time

    fun decreaseTime() {
        _time.value = _time.value?.minus(1000)
    }

    fun updateTime(savedTime: Long?) {
        _time.value = savedTime ?: System.currentTimeMillis()
    }

    fun storeCurrentTime() {
        viewModelScope.launch {
            repository.insertTime(TimeData(timestamp = _time.value?: 0L))
        }
    }

    fun clearDB() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}
