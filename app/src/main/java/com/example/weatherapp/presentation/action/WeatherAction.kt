package com.example.weatherapp.presentation.action

import com.example.weatherapp.presentation.state.WeatherState

sealed class WeatherAction: Action() {
    object LoadData: WeatherAction()
    object RefreshData: WeatherAction()
}