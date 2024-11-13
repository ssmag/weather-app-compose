package com.example.weatherapp.presentation.ui

import com.example.weatherapp.presentation.action.Action
import com.example.weatherapp.presentation.intent.Intent
import com.example.weatherapp.presentation.state.State
import com.example.weatherapp.presentation.viewmodel.BaseMVIViewModel

interface IScreen<I: Intent, S: State, A: Action> {
    val viewModel: BaseMVIViewModel<I, S, A>
    val state: S
}