package com.moscow.tudee.presentation.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(): ViewModel() {
    private val _state: MutableStateFlow<MainScreenState> = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> get() = _state


    fun onEvent(event: MainScreenEvents) {
        when (event) {
            is MainScreenEvents.UpdateAppBar -> {
                _state.update { it.copy(appBar = event.type) }
            }

            is MainScreenEvents.UpdateBottomBarVisibility -> {
                _state.update { it.copy(isBottomNavigationVisible = event.visible) }
            }
        }
    }

}