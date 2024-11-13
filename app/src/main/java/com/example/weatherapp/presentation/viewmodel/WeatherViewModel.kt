package com.example.weatherapp.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.WeatherModel
import com.example.weatherapp.di.AppModule.Companion.CURRENT_WEATHER
import com.example.weatherapp.di.AppModule.Companion.GET_WEATHER
import com.example.weatherapp.domain.usecase.abs.ISuspendUseCase
import com.example.weatherapp.presentation.action.WeatherAction
import com.example.weatherapp.presentation.intent.WeatherIntent
import com.example.weatherapp.presentation.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class WeatherViewModel @Inject constructor(
    @Named(GET_WEATHER) private val getWeatherUseCase: ISuspendUseCase<Unit, List<WeatherModel>>,
    @Named(CURRENT_WEATHER) private val getCurrentWeatherUseCase: ISuspendUseCase<Unit, List<WeatherModel>>,
    initialState: WeatherState
): BaseMVIViewModel<WeatherIntent, WeatherState, WeatherAction>(initialState = initialState) {

    init {
        viewModelScope.launch {
            getWeatherData()
        }
    }

    private fun getWeatherData() = viewModelScope.launch(Dispatchers.IO) {
        _stateModelFlow.value = WeatherState.Loading
        try {
            val weatherData = getWeatherUseCase.execute(Unit)
            _stateModelFlow.value = WeatherState.Success(weatherData)
        } catch (e: Exception) {
            _stateModelFlow.value = WeatherState.Error(e)
        }
    }

    private fun refreshWeatherData() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val weatherData = getWeatherUseCase.execute(Unit)
            _stateModelFlow.value = WeatherState.Success(weatherData)
        } catch (e: Exception) {
            _stateModelFlow.value = WeatherState.Error(e)
        }
    }


    override fun processIntent(intent: Flow<WeatherIntent>) {
        viewModelScope.launch(Dispatchers.IO) {
            intent.collect {
                when (it) {
                    is WeatherIntent.FetchWeather -> {
                        handleAction(WeatherAction.LoadData)
                    }
                    is WeatherIntent.RefreshWeather -> {
                        handleAction(WeatherAction.RefreshData)
                    }
                }
            }
        }
    }

    override fun handleAction(uiAction: WeatherAction) {
        viewModelScope.launch {
            when (uiAction) {
                WeatherAction.LoadData -> {
                    getWeatherData()
                }
                WeatherAction.RefreshData -> {
                    refreshWeatherData()
                }
            }
        }
    }

}