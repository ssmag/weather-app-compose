package com.example.weatherapp.presentation.ui

import androidx.compose.foundation.border
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        SearchComponent(
            viewModel = viewModel
        )
        Spacer(modifier = Modifier.height(16.dp))
        when (val currentState = state) {
            is WeatherState.Loading -> {
                LoadingComponent()
            }

            is WeatherState.Success -> {
                ForecastComponent(
                    viewModel = viewModel,
                    weatherModel = currentState.data
                )
            }

            is WeatherState.Error -> {
                ErrorComponent(currentState.error.localizedMessage)
            }

            is WeatherState.Empty -> {
                // Show empty screen
                EmptyComponent()
            }
        }
    }
}

@Composable
fun SearchComponent(
    viewModel: WeatherViewModel
) {
    val zipCodeInput = rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        TextField(
            value = zipCodeInput.value,
            onValueChange = { zipCodeInput.value = it },
            maxLines = 1,
            placeholder = {
                Text(text = "Enter a Zip Code")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .padding(16.dp)
                .border(1.dp, Color.Black)
                .onKeyEvent { keyEvent ->
                    if (keyEvent.nativeKeyEvent.keyCode == Key.Enter.nativeKeyCode) {
                        searchForZipAndHideKeyboard(
                            viewModel = viewModel,
                            keyboardController = keyboardController,
                            zipCode = zipCodeInput.value,
                        )
                        true
                    } else {
                        false
                    }
                })
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Search",
            tint = Color.Black,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterVertically)
                .clickable {
                    searchForZipAndHideKeyboard(
                        viewModel = viewModel,
                        keyboardController = keyboardController,
                        zipCode = zipCodeInput.value,
                    )
                }
        )
    }
}

@Composable
fun EmptyComponent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No data available",
            style = MaterialTheme.typography.labelSmall
        )
    }
}
@Composable
fun ErrorComponent(errorMessage: String?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Error: ${errorMessage ?: "Unknown error"}",
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun ForecastComponent(
    // would rather pass an event emitter rather than the whole ViewModel.
    // Would need to do some rearchitecting
    viewModel: WeatherViewModel,
    weatherModel: WeatherStateModel,
) {
    Column {
        Text(
            text = "${weatherModel.city}, ${weatherModel.country}",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            weatherModel.forecastList?.forEach { dayOfForecast ->
                item {
                    Column(
                        modifier = Modifier
                            .clickable {
                                viewModel.emitIntent(
                                    WeatherIntent.GoToWeatherDetails(dayOfForecast.date)
                                )
                            }
                    ) {
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
}

@Composable
fun LoadingComponent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

private fun searchForZipAndHideKeyboard(
    viewModel: WeatherViewModel,
    keyboardController: SoftwareKeyboardController?,
    zipCode: String,
) {
    viewModel.emitIntent(
        WeatherIntent.FetchWeather(zipCode)
    )
    keyboardController?.hide()
}
