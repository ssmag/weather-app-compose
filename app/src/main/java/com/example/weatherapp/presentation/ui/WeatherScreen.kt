package com.example.weatherapp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.weatherapp.presentation.action.WeatherAction
import com.example.weatherapp.presentation.intent.WeatherIntent
import com.example.weatherapp.presentation.state.WeatherState
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel
import javax.inject.Inject


// Another example of something highly abstracted.
// Allows for reusability and endofrces using viewmodels and states in our composables
// Would normally not write code like this, because variables of a composable function should
// not be global to a class.
// Nonetheless, this can be a good example of how to abstract things
class WeatherScreen @Inject constructor(
    override val viewModel: WeatherViewModel
) : IScreen<WeatherIntent, WeatherState, WeatherAction> {

    @Composable
    override fun Render() {
        val state by viewModel.stateModelFlow.collectAsState()

        when (state) {
            is WeatherState.Loading -> {
                // Show loading screen
            }

            is WeatherState.Success -> {
                // Show success screen
            }

            is WeatherState.Error -> {
                // Show error screen
            }

            is WeatherState.Empty -> {
                // Show empty screen
            }
        }
    }

}