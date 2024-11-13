package com.example.weatherapp.presentation.state

import com.example.weatherapp.data.model.WeatherModel

sealed class WeatherState: UIState {
    object Empty: WeatherState()
    object Loading: WeatherState()
    data class Success(val data: List<WeatherModel>): WeatherState()
    data class Error(val error: Throwable): WeatherState()
}