package com.example.weatherapp.presentation.intent

sealed class WeatherIntent: Intent {
    class FetchWeather(val zipCode: String): WeatherIntent()
    class GoToWeatherDetails(val date: String): WeatherIntent()

}