package com.example.weatherapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val placeholder: String
)
