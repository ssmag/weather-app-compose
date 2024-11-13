package com.example.weatherapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.data.model.WeatherModel

@Database(entities = [WeatherModel::class], version = 1)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        const val NAME = "weather_database"
    }
}