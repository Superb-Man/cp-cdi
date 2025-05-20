package com.example.demo2.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time_data")
data class TimeData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    //column
    var timestamp: Long
)
