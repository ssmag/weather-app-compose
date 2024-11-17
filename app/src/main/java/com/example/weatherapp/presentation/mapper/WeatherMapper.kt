package com.example.weatherapp.presentation.mapper

import android.util.Log
import com.example.weatherapp.data.remote.model.response.weather.WeatherResponseModel
import com.example.weatherapp.presentation.model.DayOfForecastModel
import com.example.weatherapp.presentation.model.TemperatureDataModel
import com.example.weatherapp.presentation.model.WeatherStateModel
import com.example.weatherapp.presentation.model.WindModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object WeatherMapper {
    private const val DATE_INPUT_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val DATE_OUTPUT_FORMAT = "EEEE, MMM dd"
    private const val EMPTY_STRING = ""

    fun fromResponseToStateModel(response: WeatherResponseModel): WeatherStateModel {
        return WeatherStateModel(
            city = response.city?.name ?: EMPTY_STRING,
            country = response.city?.country ?: EMPTY_STRING,
            forecastList = response.forecastList?.map { forecastItem ->
                DayOfForecastModel(
                    mainWeather = forecastItem?.weather?.first()?.main ?: EMPTY_STRING,
                    wind = WindModel(
                        deg = forecastItem?.wind?.deg,
                        speed = forecastItem?.wind?.speed,
                        gust = forecastItem?.wind?.gust
                    ),
                    tempData = TemperatureDataModel(
                        temp = forecastItem?.main?.temp?.toFormattedFahrenheit(),
                        feelsLike = forecastItem?.main?.feelsLike?.toFormattedFahrenheit(),
                        tempMin = forecastItem?.main?.tempMin?.toFormattedFahrenheit(),
                        tempMax = forecastItem?.main?.tempMax?.toFormattedFahrenheit()
                    ),
                    date = forecastItem?.dtTxt.toFormattedDate(),
                    humidityPercentage = forecastItem?.main?.humidity ?: 0
                )
            }
        )

    }

    private fun Double?.toFormattedTemperature(): String {
        return if (this == null) EMPTY_STRING else String.format(Locale.getDefault(), "%.0f", this)
    }

    private fun Double?.toFahrenheit(): Double? {
        if (this == null) return null
        return (this - 273.15) * 9 / 5 + 32
    }

    private fun Double.toFormattedFahrenheit(): String {
        return this.toFahrenheit().toFormattedTemperature()
    }
    private fun String?.toFormattedDate(): String {
        if (this == null) return EMPTY_STRING
        val inputFormat = SimpleDateFormat(DATE_INPUT_FORMAT, Locale.getDefault())
        val outputFormat = SimpleDateFormat(DATE_OUTPUT_FORMAT, Locale.getDefault())
        val date: Date? = inputFormat.parse(this)
        return if (date != null) outputFormat.format(date) else EMPTY_STRING
    }

}