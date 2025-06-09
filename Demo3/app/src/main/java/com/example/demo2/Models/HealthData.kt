package com.example.demo2.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health_data")
data class HealthData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var sleepDuration: Int,          // in hours
    var sugarLevel: Int,              // in beats per minute (bpm)
    var date: String,               // e.g., "2025-05-22"
    var time: String,               // e.g., "14:30"
    var activity: String,           // e.g., "Walking"
    var disease1: Boolean,           // e.g., "Diabetes"
    var disease2: Boolean,           // optional second disease
    var disease3: Boolean            // optional third disease
)
