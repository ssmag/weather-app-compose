package com.example.weatherapp.presentation.model

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

@Immutable
data class WeatherStateModel(
    val city: String,
    val searchQuery: String= "",
    val forecastList: List<DayOfForecastModel>? = emptyList()
)

@Immutable
data class DayOfForecastModel(
    val date: String,
    val mainWeather: String,
    val wind: WindModel,
    val tempData: TemperatureDataModel,
    val humidityPercentage: Int,
)

data class WindModel(
    val deg: Int? = null,
    val speed: Double? = null,
    val gust: Double? = null
)

data class TemperatureDataModel(
    val temp: Double? = null,
    val feelsLike: Double? = null,
    val tempMin: Double? = null,
    val tempMax: Double? = null,
)