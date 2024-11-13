package com.example.weatherapp.domain.usecase.abs

interface ISimpleUseCase<I, O>: IUseCase<I, O> {
    fun execute(p: I): O
}