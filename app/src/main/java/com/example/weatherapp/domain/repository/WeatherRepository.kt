package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.remote.ktor.KtorRemoteDataSource
import com.example.weatherapp.data.remote.model.response.weather.WeatherResponseModel
import javax.inject.Inject

// Would be interesting to implement a strategy pattern, such that depending on the data type
// or the use case we can implement different caching strategies
class WeatherRepository @Inject constructor(
    private val remoteDS: KtorRemoteDataSource,
) {

    // Would like to wrap the response in a success/failure wrapper
    // this way when we get an error for invalid input,
    // or a network error we can propagate it in a clean way
    suspend fun getWeatherForecastForZip(zipCode: String): WeatherResponseModel {
        return remoteDS.getForecast(zipCode)
    }


}
