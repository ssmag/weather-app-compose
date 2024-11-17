package com.example.weatherapp.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Icon
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.text.input.KeyboardType
import com.example.weatherapp.presentation.intent.WeatherIntent
import com.example.weatherapp.presentation.model.WeatherStateModel
import com.example.weatherapp.presentation.state.WeatherState
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel


@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
) {
    val state by viewModel.stateModelFlow.collectAsState()
    val zipCodeInput = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        SearchComponent(
            zipCodeInput = zipCodeInput,
            viewModel = viewModel
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
fun SearchComponent(
    zipCodeInput: MutableState<String>,
    viewModel: WeatherViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = zipCodeInput.value,
            onValueChange = { zipCodeInput.value = it },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .padding(16.dp)
                .onKeyEvent { keyEvent ->
                    if (keyEvent.nativeKeyEvent.keyCode == Key.Enter.nativeKeyCode) {
                        viewModel.emitIntent(
                            WeatherIntent.FetchWeather(zipCodeInput.value)
                        )
                        true
                    } else {
                        false
                    }
                },
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    innerTextField()
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                viewModel.emitIntent(
                                    WeatherIntent.FetchWeather(zipCodeInput.value)
                                )
                            }
                    )
                }
            }
        )
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

