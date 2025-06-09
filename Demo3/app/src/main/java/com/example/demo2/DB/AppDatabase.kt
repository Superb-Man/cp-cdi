package com.example.demo2.DB

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.demo2.DAO.HealthDataDAO
import com.example.demo2.DAO.TimeDataDao
import com.example.demo2.Models.HealthData
import com.example.demo2.Models.TimeData

@Database(entities = [TimeData::class, HealthData::class], version = 100, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val timeDataDao: TimeDataDao
    abstract val healthDataDAO: HealthDataDAO
    /*
    This works like a singletone pattern.everytime i donot need to restart
    the database
     */
    companion object {
        /*
        for updating anywhere and have effect on the all threads and DB
         */
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            Log.d("AppDatabase", "Getting instance...")
            synchronized(this) {

                var instance = INSTANCE
                // If instance is `null` make a new database instance.
                if (instance == null) {
                    Log.d("AppDatabase", "Creating new instance")
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "HealthDB"
                    ).fallbackToDestructiveMigration().build()
                    Log.d("AppDatabase", "Made new instance")
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }
                return instance
            }


        }
    }
}
