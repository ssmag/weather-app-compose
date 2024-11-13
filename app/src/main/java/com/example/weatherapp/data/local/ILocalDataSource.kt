package com.example.weatherapp.data.local

import com.example.weatherapp.data.IDataSource

// Stub to indicate that a specific class is a data source that is local.
// In case we need to add specific methods to remote data sources
// that don't belong in the RemoteDataSource, or any other IDataSource for that matter
interface ILocalDataSource<T>: IDataSource<T> {
    fun isCacheStale(): Boolean = true
}