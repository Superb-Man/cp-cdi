package com.example.demo2.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.demo2.Models.HealthData

@Dao
interface HealthDataDAO {
    @Insert
    suspend fun insert(healthData: HealthData)

    // select all from table health_data
    @Query("SELECT * FROM health_data ORDER BY id DESC")
    suspend fun getAllHealthData(): List<HealthData>

    // select health data of specific id
    @Query("SELECT * FROM health_data WHERE id = :id")
    suspend fun getHealthData(id: Int): HealthData

    // Delete specific health data
    @Query("DELETE FROM health_data WHERE id = :id")
    suspend fun deleteHealthData(id: Int)

    //update specific health data
    @Update
    suspend fun updateHealthData(healthData: HealthData)

}