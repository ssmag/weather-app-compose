package com.example.weatherapp.domain.usecase

import com.example.weatherapp.data.model.WeatherModel
import com.example.weatherapp.domain.usecase.abs.ISuspendUseCase
import com.example.weatherapp.repository.IRepository
import com.example.weatherapp.repository.WeatherRepository
import javax.inject.Inject

// This approach of injecting an IRepository rather than an explicit WeatherRepository
// is designed to pidgeonhole development into a specific architecture
// It's benefits are interchangeability and modularity making it testable.
// It's cons are that it's over-engineered and can be difficult to understand
// It adds a lot of abstraction overhead and can add difficulty to support and maintain

// This is purely for demonstrative purposes and not a reflection of my personal
// coding style
class GetCurrentWeatherUseCase @Inject constructor(
    override val repository: IRepository<List<WeatherModel>>
): ISuspendUseCase<Unit, List<WeatherModel>> {
    override suspend fun execute(params: Unit): List<WeatherModel> {
        (
            repository as? WeatherRepository ?:
            throw IllegalStateException(REPO_CAST_ERROR)
        ).let { repo -> return repo.getCurrentWeatherData() }
    }

    companion object {
        private const val REPO_CAST_ERROR = "Repository is not of type WeatherRepository"
    }
}