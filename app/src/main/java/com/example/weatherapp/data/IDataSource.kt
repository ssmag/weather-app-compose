package com.example.weatherapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface IDataSource<T> {

    suspend fun create(item: T)

    suspend fun read(): List<T>

    suspend fun read(id: Long): T?

    fun readFlow(id: Long): Flow<T?> = flow { read(id) }

    fun readFlow(): Flow<List<T?>> = flow { read() }

    suspend fun update(item: T)

    suspend fun delete(item: T)

}
