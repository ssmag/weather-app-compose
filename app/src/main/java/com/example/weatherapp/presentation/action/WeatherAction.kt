package com.example.weatherapp.presentation.action

sealed class WeatherAction: Action() {
    class LoadData(val zipCode: String): WeatherAction()
    object RefreshData: WeatherAction()
}