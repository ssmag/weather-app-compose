package com.example.weatherapp.data.remote

import com.example.weatherapp.data.IDataSource

// Stub to indicate that a specific class is a data source that is remote.
// In case we need to add specific methods to remote data sources
// that don't belong in the LocalDataSource, or any other IDataSource for that matter
interface IRemoteDataSource<T>: IDataSource<T>