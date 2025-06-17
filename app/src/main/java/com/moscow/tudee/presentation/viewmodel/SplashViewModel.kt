package com.moscow.tudee.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moscow.tudee.domain.service.SplashService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val splashService: SplashService
) : ViewModel() {

    private val _hasSeenOnboarding = MutableStateFlow<Boolean?>(null)
    val hasSeenOnboarding: StateFlow<Boolean?> = _hasSeenOnboarding

    fun checkIfUserHasSeenOnboarding() {
        viewModelScope.launch {
            _hasSeenOnboarding.value = splashService.hasSeenOnboarding()
        }
    }

    fun setOnboardingSeen(shown: Boolean) {
        viewModelScope.launch {
            splashService.markOnboardingAsShown(shown)
        }
    }
}