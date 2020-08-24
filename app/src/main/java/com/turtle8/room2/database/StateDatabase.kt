package com.turtle8.room2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FragmentState::class],version=1, exportSchema = false)
abstract class StateDatabase:RoomDatabase() {
    abstract val fragmentStateDao:FragmentStateDao

    companion object{
        @Volatile
        private var INSTANCE: StateDatabase? = null

        fun getInstance(context: Context):StateDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StateDatabase::class.java,
                        "State_Database"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}