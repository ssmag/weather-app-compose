package com.example.weatherapp.presentation.viewmodel

import com.example.weatherapp.data.model.WeatherModel
import com.example.weatherapp.data.remote.model.GetWeatherRequest
import com.example.weatherapp.domain.usecase.abs.ISuspendUseCase
import com.example.weatherapp.presentation.action.WeatherAction
import com.example.weatherapp.presentation.intent.WeatherIntent
import com.example.weatherapp.presentation.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: ISuspendUseCase<GetWeatherRequest, WeatherModel>,
    initialState: WeatherState = WeatherState.Empty
): BaseMVIViewModel<WeatherIntent, WeatherState, WeatherAction>(initialState = initialState) {

    override fun processIntent(intent: WeatherIntent) {
        TODO("Not yet implemented")
    }

    override fun handleAction(uiAction: WeatherAction) {
        TODO("Not yet implemented")
    }

}