package com.example.weatherapp.repository

import com.example.weatherapp.data.local.ILocalDataSource
import com.example.weatherapp.data.model.WeatherModel
import com.example.weatherapp.data.remote.IRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Would be interesting to implement a strategy pattern, such that depending on the data type
// or the use case we can implement different caching strategies
class WeatherRepository @Inject constructor(
    private val localDS: ILocalDataSource<WeatherModel>,
    private val remoteDS: IRemoteDataSource<WeatherModel>,
) : IRepository<List<WeatherModel>> {

    suspend fun getWeatherData(): List<WeatherModel> {
        val resultList = mutableListOf<WeatherModel>()
        localDS.read().also { localWeatherDataItem ->
            if (localWeatherDataItem.isNotEmpty() && localDS.isCacheStale().not()) {
                return localWeatherDataItem
            } else {
                resultList.addAll(getCurrentWeatherData())
            }
        }

        return resultList
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getCurrentWeatherData(): List<WeatherModel> {
        val resultList = mutableListOf<WeatherModel>()
        remoteDS.readFlow()
            .flatMapConcat { it.asFlow() }
            .map { remoteWeatherDataItem ->
                remoteWeatherDataItem?.let {
                    localDS.create(remoteWeatherDataItem)
                }
                remoteWeatherDataItem
            }
            .collect { item ->
                item?.let {
                    resultList.add(item)
                }
            }
        return resultList
    }

}
