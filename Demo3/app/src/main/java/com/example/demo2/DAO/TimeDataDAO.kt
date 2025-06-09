package com.example.demo2.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.lifecycle.LiveData
import com.example.demo2.Models.TimeData

@Dao
interface TimeDataDao {
    @Insert
    suspend fun insertTime(timeData: TimeData)

    @Query("SELECT timestamp FROM time_data ORDER BY id DESC LIMIT 2")
    suspend fun getLastTwoTimestamps(): List<Long>

    // get all timestamps
    @Query("SELECT * FROM time_data ORDER BY id DESC")
    fun getAllLive(): LiveData<List<TimeData>>

    // delete everything from table
    @Query("DELETE  FROM time_data")
    suspend fun deleteAll()

    @Query("UPDATE time_data SET timestamp = :timestamp WHERE id = :id")
    suspend fun updateTimestamp(id: Int, timestamp: Long)  // update specific row by id

}
