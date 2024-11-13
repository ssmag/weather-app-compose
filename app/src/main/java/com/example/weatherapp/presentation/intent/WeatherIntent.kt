package com.example.weatherapp.presentation.intent

sealed class WeatherIntent: Intent() {
    object FetchWeather: WeatherIntent()
    object RefreshWeather: WeatherIntent()

}