package com.example.weatherapp.domain.usecase.abs

import com.example.weatherapp.data.model.WeatherModel
import com.example.weatherapp.data.remote.model.GetWeatherRequest
import com.example.weatherapp.repository.IRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    override val repository: IRepository<WeatherModel>
): ISuspendUseCase<GetWeatherRequest, WeatherModel> {
    override suspend fun execute(params: GetWeatherRequest): WeatherModel {
        TODO("Not yet implemented")
    }
}