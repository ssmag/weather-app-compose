package com.example.weatherapp.presentation.ui

import com.example.weatherapp.presentation.action.WeatherAction
import com.example.weatherapp.presentation.intent.WeatherIntent
import com.example.weatherapp.presentation.state.WeatherState
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel
import javax.inject.Inject

class WeatherScreen @Inject constructor(
    override val viewModel: WeatherViewModel
) : IScreen<WeatherIntent, WeatherState, WeatherAction> {
    override val state: WeatherState = viewModel.stateModelFlow.value
}