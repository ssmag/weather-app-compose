package com.example.weatherapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.presentation.action.WeatherAction.*
import com.example.weatherapp.presentation.action.WeatherAction
import com.example.weatherapp.presentation.intent.WeatherIntent
import com.example.weatherapp.presentation.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "WeatherViewModel"

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repo: WeatherRepository,
    initialState: WeatherState = WeatherState.Empty
): BaseMVIViewModel<WeatherIntent, WeatherState, WeatherAction>(initialState = initialState) {

    private val myZip = "80201"
    init {
        viewModelScope.launch {
            getWeatherData(myZip)
        }
    }

    private fun getWeatherData(zipCode: String) = viewModelScope.launch(Dispatchers.IO) {
        updateState(WeatherState.Loading)
        try {
            val weatherData = repo.getWeatherForecastForZip(zipCode)
            updateState(WeatherState.Success(weatherData))
            Log.d(TAG, ":: Successful weatherdata: $weatherData")
        } catch (e: Exception) {
            Log.e(TAG, ":: Error: $e")
            updateState(WeatherState.Error(e))
        }
    }

    override fun processIntent(intent: Flow<WeatherIntent>) {
        viewModelScope.launch(Dispatchers.IO) {
            intent.collect {
                when (it) {
                    is WeatherIntent.FetchWeather -> {
                        handleAction(LoadData(it.zipCode))
                    }
                    is WeatherIntent.RefreshWeather -> {
                        handleAction(RefreshData)
                    }
                }
            }
        }
    }

    override fun handleAction(uiAction: WeatherAction) {
        viewModelScope.launch {
            when (uiAction) {
                is LoadData -> {
                    getWeatherData(uiAction.zipCode)
                }
                RefreshData -> {
                    // do something
                }
            }
        }
    }

}