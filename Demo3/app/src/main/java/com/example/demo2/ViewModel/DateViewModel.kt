package com.example.demo2.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.demo2.Repository.Repository
import com.example.demo2.Models.TimeData
import kotlinx.coroutines.launch

class DateViewModel(application: Application) : AndroidViewModel(application) {
//    private val _timeDifference = MutableLiveData<Long>()
//    val timeDifference: LiveData<Long> get() = _timeDifference
    private val repository: Repository = Repository(application)
    var timedatas: LiveData<List<TimeData>> ? = null


    companion object{
        var cnt: Int = 0 // to track something fishy
    }
    init {
        timedatas = repository.getAll()
        cnt++
        Log.d("DateViewModel", "DateViewModel created $cnt times")
    }

    fun show() {
//        viewModelScope.launch {
//            val timestamps = repository.getLastTwoTimestamps()
//            Log.d("Time difference is" , timestamps.toString())
//            _timeDifference.postValue(
//                if (timestamps.size == 2) timestamps[0] - 2*timestamps[1] else timestamps.firstOrNull() ?: 0L
//            )
//        }
//        viewModelScope.launch {
//            // only show the timestamp attribute
//            timedatas = repository.getAll()
//        }
    }
    fun decreaseTimestamp(id: Int, timestamp: Long) {
        viewModelScope.launch {
            repository.decreaseTimestampById(id, timestamp - 1000)
            Log.d("DecreseTimeStamp", "Decreased timestamp by 1000")
        }

    }
}
