package com.example.weatherapp.presentation.state

sealed class WeatherState: State() {
    object Empty: WeatherState()
}