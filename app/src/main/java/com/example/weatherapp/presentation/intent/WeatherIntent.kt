package com.example.weatherapp.presentation.intent

sealed class WeatherIntent: Intent() {
    class FetchWeather(val zipCode: String): WeatherIntent()
    object RefreshWeather: WeatherIntent()

}