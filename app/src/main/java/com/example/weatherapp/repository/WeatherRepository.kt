package com.example.weatherapp.repository

import com.example.weatherapp.data.local.ILocalDataSource
import com.example.weatherapp.data.model.WeatherModel
import com.example.weatherapp.data.remote.IRemoteDataSource
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val localDS: ILocalDataSource<WeatherModel>,
    private val remoteDS: IRemoteDataSource<WeatherModel>
): IRepository<WeatherModel>