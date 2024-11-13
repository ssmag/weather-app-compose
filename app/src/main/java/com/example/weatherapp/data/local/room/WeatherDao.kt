package com.example.weatherapp.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherapp.data.model.WeatherModel

// Would normally be implementiong IRemoteDataSource, but room does not like that
@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(item: WeatherModel)

    @Query("SELECT * FROM WeatherModel")
    suspend fun read(): List<WeatherModel>

    @Query("SELECT * FROM WeatherModel WHERE id = :id")
    suspend fun read(id: Long): WeatherModel

    @Update
    suspend fun update(item: WeatherModel)

    @Delete
    suspend fun delete(item: WeatherModel)
}