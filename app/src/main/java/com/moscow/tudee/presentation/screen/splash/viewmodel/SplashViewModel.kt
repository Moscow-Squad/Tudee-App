package com.moscow.tudee.presentation.screen.splash.viewmodel

import com.moscow.tudee.domain.service.LocalService
import com.moscow.tudee.presentation.base.BaseViewModel

class SplashViewModel(
    private val localService: LocalService
) : BaseViewModel<SplashState, SplashEvent>(SplashState()) {

    init {
        isSystemDark()
    }
    fun checkIfUserHasSeenOnboarding() {
        launchWithResult(
            action = { localService.hasSeenOnboarding() },
            onStart = {
                updateState { it.copy(isLoading = true) }
            },
            onSuccess = { hasSeen ->
                updateState { it.copy(hasSeenOnboarding = hasSeen, isLoading = false) }
                if (hasSeen) {
                    sendEvent(SplashEvent.NavigateToHome)
                } else {
                    sendEvent(SplashEvent.NavigateToOnboarding)
                }
            },
            onError = { error ->
                updateState { it.copy(isLoading = false) }
                sendEvent(SplashEvent.ShowError(error.message ?: "Unexpected error in splash screen"))
            }
        )
    }

    fun setOnboardingSeen(shown: Boolean) {
        launchWithResult(
            action = { localService.markOnboardingAsShown(shown) },
            onSuccess = {
                updateState { it.copy(hasSeenOnboarding = shown) }
            },
            onError = {
                sendEvent(SplashEvent.ShowError("Failed to save onboarding state"))
            }
        )
    }

    private fun isSystemDark(){
        launchWithResult(
            action = { localService.isSystemThemeDark() },
            onSuccess = {isDark->
                updateState { it.copy(isSystemDark = isDark) }
            },
            onError = {
                updateState { it.copy(isSystemDark = false) }
            }
        )
    }
}
