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
        return WeatherStateModel(
            city = response.city?.name ?: "",
            country = response.city?.country ?: "",
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
                    date = forecastItem?.dtTxt.toFormattedDate(),
                    humidityPercentage = forecastItem?.main?.humidity ?: 0
                )
            }
        )

    }

    private fun String?.toFormattedDate(): String {
        if (this == null) return ""
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEEE, MMM dd", Locale.getDefault())
        val date: Date? = inputFormat.parse(this)
        return if (date != null) outputFormat.format(date) else ""
    }
}