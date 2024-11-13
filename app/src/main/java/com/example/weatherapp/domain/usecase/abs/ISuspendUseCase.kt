package com.example.weatherapp.domain.usecase.abs

interface ISuspendUseCase<I, O>: IUseCase<I, O> {
    suspend fun execute(params: I): O
}