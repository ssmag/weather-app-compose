package com.example.weatherapp.presentation.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.weatherapp.presentation.state.WeatherState
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel


@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
) {
    val state by viewModel.stateModelFlow.collectAsState()

    when (state) {
        WeatherState.Loading -> {
            // Show loading screen
        }

        is WeatherState.Success -> {
            // render data inside text composable
            Text(text = (state as WeatherState.Success).data.toString())
        }

        is WeatherState.Error -> {
            // Show error screen
        }

        is WeatherState.Empty -> {
            // Show empty screen
        }
    }
}