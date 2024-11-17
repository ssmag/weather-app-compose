package com.example.weatherapp.presentation.state

import androidx.compose.runtime.Immutable
import com.example.weatherapp.data.remote.model.response.weather.WeatherModel

@Immutable
sealed class WeatherState: UIState {
    object Empty: WeatherState()
    object Loading: WeatherState()
    data class Success(val data: WeatherModel): WeatherState()
    data class Error(val error: Throwable): WeatherState()
}