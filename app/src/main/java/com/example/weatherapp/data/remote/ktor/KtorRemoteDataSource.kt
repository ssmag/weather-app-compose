package com.example.weatherapp.data.remote.ktor

import com.example.weatherapp.data.model.WeatherModel
import com.example.weatherapp.data.remote.IRemoteDataSource
import io.ktor.client.HttpClient
import javax.inject.Inject

class KtorRemoteDataSource @Inject constructor(
    private val client: HttpClient
) : IRemoteDataSource<WeatherModel> {
    override suspend fun create(item: WeatherModel) {
        TODO("Not yet implemented")
    }

    override suspend fun read(): List<WeatherModel> {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Long): WeatherModel {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: WeatherModel) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: WeatherModel) {
        TODO("Not yet implemented")
    }
}