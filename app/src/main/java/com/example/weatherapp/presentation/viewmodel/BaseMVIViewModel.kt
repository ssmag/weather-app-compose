package com.example.weatherapp.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.weatherapp.presentation.action.Action
import com.example.weatherapp.presentation.intent.Intent
import com.example.weatherapp.presentation.state.UIState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseMVIViewModel<I: Intent, S: UIState, A: Action>(
    initialState: S
): ViewModel() {
    private val _stateModelFlow: MutableStateFlow<S> = MutableStateFlow(initialState)
    val stateModelFlow: StateFlow<S> = _stateModelFlow

    private val _intentFlow: MutableStateFlow<I?> = MutableStateFlow(null)
    val intentFlow: StateFlow<I?> = _intentFlow

    // would not want to couple flow with the intent necessarily, but taking this flow heavy
    // approach currently
    abstract fun processIntent(intent: StateFlow<I?>)

    protected fun updateState(state: S) {
        _stateModelFlow.value = state
    }

    fun emitIntent(intent: I) {
        _intentFlow.value = intent
    }

    protected abstract fun handleAction(uiAction: A)


}