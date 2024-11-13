package com.example.weatherapp.data.local.room

import com.example.weatherapp.data.local.ILocalDataSource
import com.example.weatherapp.data.model.WeatherModel

class WeatherRoomDataSource: ILocalDataSource<WeatherModel> {

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