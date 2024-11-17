package com.example.weatherapp.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherapp.presentation.model.WeatherStateModel
import com.example.weatherapp.presentation.state.WeatherState
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel


@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
) {
    val state by viewModel.stateModelFlow.collectAsState()
    val inputText = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        BasicTextField(
            value = inputText.value,
            onValueChange = { inputText.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (val currentState = state) {
            is WeatherState.Loading -> {
                LoadingUI()
            }

            is WeatherState.Success -> {
                ForecastUI(currentState.data)
            }

            is WeatherState.Error -> {
                ErrorUI(currentState.error.localizedMessage)
            }

            is WeatherState.Empty -> {
                // Show empty screen
                Text(text = "No data available", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
fun ErrorUI(errorMessage: String?) {
    Text(
        text = "Error: ${errorMessage ?: "Unknown error"}",
        style = MaterialTheme.typography.labelSmall
    )
}

@Composable
fun ForecastUI(weatherModel: WeatherStateModel) {
    Column {
        Text(
            text = weatherModel.city,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            weatherModel.forecastList?.forEach { dayOfForecast ->
                item {
                    Text(
                        text = dayOfForecast.date,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = dayOfForecast.mainWeather,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Wind: ${dayOfForecast.wind.speed} mph",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Temp: ${dayOfForecast.tempData.temp}°F",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Feels like: ${dayOfForecast.tempData.feelsLike}°F",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Humidity: ${dayOfForecast.humidityPercentage}%",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }


        }
    }
}

@Composable
fun LoadingUI() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

