package com.example.demo2.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.demo2.DB.AppDatabase
import com.example.demo2.DB.TimeData
import com.example.demo2.DB.TimeDataDao

class Repository(application: Application) {

    private val timeDao: TimeDataDao = AppDatabase.getInstance(application).timeDataDao

    suspend fun getLastTwoTimestamps(): List<Long> {
        return timeDao.getLastTwoTimestamps()
    }

    fun getAll(): LiveData<List<TimeData>> {
        return timeDao.getAllLive()
    }


    suspend fun insertTime(timeData: TimeData) {
        timeDao.insertTime(timeData)
    }

    suspend fun deleteAll() {
        timeDao.deleteAll()
    }

    suspend fun decreaseTimestampById(id: Int, timestamp: Long) {
        timeDao.updateTimestamp(id, timestamp)
    }
}
