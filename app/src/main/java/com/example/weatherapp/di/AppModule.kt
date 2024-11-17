package com.example.weatherapp.di

import com.example.weatherapp.data.remote.ktor.KtorRemoteDataSource
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.presentation.state.WeatherState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.gson.gson
import javax.inject.Singleton


// Thinking about creating separate modules for the db and network
// but keeping it simple for simplicity and time
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                gson()
            }

            install(Logging) {
                logger = Logger.DEFAULT
            }
        }
    }

    @Provides
    fun provideRemoteDatasource(
        client: HttpClient
    ): KtorRemoteDataSource {
        return KtorRemoteDataSource(
            client = client
        )
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        remoteDS: KtorRemoteDataSource,
    ): WeatherRepository {
        return WeatherRepository(remoteDS)
    }

    @Provides
    @Singleton
    fun provideWeatherScreenState(): WeatherState = WeatherState.Empty
}