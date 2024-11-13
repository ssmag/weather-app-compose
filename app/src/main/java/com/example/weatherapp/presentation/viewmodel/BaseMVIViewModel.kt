package com.example.weatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weatherapp.presentation.action.Action
import com.example.weatherapp.presentation.intent.Intent
import com.example.weatherapp.presentation.state.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseMVIViewModel<I: Intent, S: State, A: Action>(
    initialState: S
): ViewModel() {
    private val _stateModelFlow: MutableStateFlow<S> = MutableStateFlow(initialState)
    val stateModelFlow: StateFlow<S> = _stateModelFlow

    abstract fun processIntent(intent: I)

    protected fun updateState(state: S) {
        _stateModelFlow.value = state
    }

    protected abstract fun handleAction(uiAction: A)


}