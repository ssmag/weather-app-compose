package com.example.weatherapp.data.remote.ktor

import com.example.weatherapp.data.model.WeatherModel
import com.example.weatherapp.data.remote.IRemoteDataSource
import io.ktor.client.HttpClient
import javax.inject.Inject

class KtorRemoteDataSource @Inject constructor(
    private val client: HttpClient
) : IRemoteDataSource<WeatherModel>