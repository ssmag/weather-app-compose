package com.example.weatherapp.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class WeatherStateModel(
    val city: String,
    val country: String,
    val searchQuery: String = "",
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
    val temp: String? = null,
    val feelsLike: String? = null,
    val tempMin: String? = null,
    val tempMax: String? = null,
)