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
    protected val _stateModelFlow: MutableStateFlow<S> = MutableStateFlow(initialState)
    val stateModelFlow: StateFlow<S> = _stateModelFlow

    // would not want to couple flow with the intent necessarily, but taking this flow heavy
    // approach currently
    abstract fun processIntent(intent: Flow<I>)

    protected fun updateState(state: S) {
        _stateModelFlow.value = state
    }

    protected abstract fun handleAction(uiAction: A)


}