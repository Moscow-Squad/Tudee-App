package com.moscow.tudee.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<T>(
    initialState: T
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<T> = _uiState.asStateFlow()

    protected fun updateState(transform: (T) -> T) {
        _uiState.update { transform(it) }
    }

    protected fun <R> launchWithResult(
        action: suspend () -> R,
        onSuccess: (R) -> Unit,
        onError: (Throwable) -> Unit,
        onStart: () -> Unit = {},
        onFinally: () -> Unit = {}
    ) {
        viewModelScope.launch {
            onStart()
            runCatching { action() }
                .onSuccess(onSuccess)
                .onFailure(onError)
            onFinally()
        }
    }
}