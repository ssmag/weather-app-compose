package com.example.weatherapp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import com.example.weatherapp.presentation.action.Action
import com.example.weatherapp.presentation.intent.Intent
import com.example.weatherapp.presentation.state.UIState
import com.example.weatherapp.presentation.viewmodel.BaseMVIViewModel

interface IScreen<I: Intent, S: UIState, A: Action> {
    val viewModel: BaseMVIViewModel<I, S, A>
    @Composable
    fun Render()
}