package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.remote.ktor.KtorRemoteDataSource
import com.example.weatherapp.data.remote.model.response.weather.WeatherModel
import javax.inject.Inject

// Would be interesting to implement a strategy pattern, such that depending on the data type
// or the use case we can implement different caching strategies
class WeatherRepository @Inject constructor(
    private val remoteDS: KtorRemoteDataSource,
) {

    suspend fun getWeatherForecastForZip(zipCode: String): WeatherModel {
        return remoteDS.getForecast(zipCode)
    }


}
