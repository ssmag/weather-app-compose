package com.example.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.data.local.ILocalDataSource
import com.example.weatherapp.data.local.room.WeatherRoomDataSource
import com.example.weatherapp.data.local.room.WeatherDao
import com.example.weatherapp.data.local.room.WeatherDatabase
import com.example.weatherapp.data.model.WeatherModel
import com.example.weatherapp.data.remote.IRemoteDataSource
import com.example.weatherapp.data.remote.ktor.KtorRemoteDataSource
import com.example.weatherapp.data.remote.model.GetWeatherRequest
import com.example.weatherapp.domain.usecase.abs.GetWeatherUseCase
import com.example.weatherapp.domain.usecase.abs.ISuspendUseCase
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel
import com.example.weatherapp.repository.IRepository
import com.example.weatherapp.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.engine.android.Android
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
        }
    }

    @Provides
    @Singleton
    fun provideRemoteDatasource(
        client: HttpClient
    ): IRemoteDataSource<WeatherModel> {
        return KtorRemoteDataSource(client)
    }

    @Provides
    @Singleton
    fun provideLocalDatasource(): ILocalDataSource<WeatherModel> {
        return WeatherRoomDataSource()
    }

    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            WeatherDatabase.NAME
        ).build()
    }

    @Provides
    fun provideWeatherDao(database: WeatherDatabase): WeatherDao {
        return database.weatherDao()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        localDS: ILocalDataSource<WeatherModel>,
        remoteDS: IRemoteDataSource<WeatherModel>
    ): IRepository<WeatherModel> {
        return WeatherRepository(localDS, remoteDS)
    }

    @Provides
    fun provideWeatherViewModel(
        getWeatherUseCase: ISuspendUseCase<GetWeatherRequest, WeatherModel>
    ): WeatherViewModel {
        return WeatherViewModel(
            getWeatherUseCase = getWeatherUseCase
        )
    }

    @Provides
    fun provideWeatherUseCase(
        repository: IRepository<WeatherModel>
    ): ISuspendUseCase<GetWeatherRequest, WeatherModel> {
        return GetWeatherUseCase(repository)
    }
}