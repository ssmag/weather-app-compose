package com.example.weatherapp.domain.usecase.abs

import com.example.weatherapp.repository.IRepository

interface IUseCase<in I, out O> {
    val repository: IRepository<O>
}