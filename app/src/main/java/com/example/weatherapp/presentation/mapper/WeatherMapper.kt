package com.example.weatherapp.presentation.mapper

import com.example.weatherapp.data.remote.model.response.weather.WeatherResponseModel
import com.example.weatherapp.presentation.model.DayOfForecastModel
import com.example.weatherapp.presentation.model.TemperatureDataModel
import com.example.weatherapp.presentation.model.WeatherStateModel
import com.example.weatherapp.presentation.model.WindModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object WeatherMapper {

    fun fromResponseToStateModel(response: WeatherResponseModel): WeatherStateModel {
        val city = response.city?.name
        return WeatherStateModel(
            city = response.city?.name ?: "",
            forecastList = response.forecastList?.map { forecastItem ->
                DayOfForecastModel(
                    mainWeather = forecastItem?.weather?.first()?.main ?: "",
                    wind = WindModel(
                        deg = forecastItem?.wind?.deg,
                        speed = forecastItem?.wind?.speed,
                        gust = forecastItem?.wind?.gust
                    ),
                    tempData = TemperatureDataModel(
                        temp = forecastItem?.main?.temp,
                        feelsLike = forecastItem?.main?.feelsLike,
                        tempMin = forecastItem?.main?.tempMin,
                        tempMax = forecastItem?.main?.tempMax
                    ),
                    date = forecastItem?.dt?.toDateString() ?: "",
                    humidityPercentage = forecastItem?.main?.humidity ?: 0
                )
            }
        )

    }

    // Converts to a human readable date string
    private fun Int.toDateString(): String {
        val date = Date(this.toLong())
        val format = SimpleDateFormat("EEEE, MMM dd", Locale.getDefault())
        return format.format(date)
    }
}