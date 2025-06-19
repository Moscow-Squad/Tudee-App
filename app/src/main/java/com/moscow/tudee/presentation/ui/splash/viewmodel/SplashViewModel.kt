package com.moscow.tudee.presentation.ui.splash.viewmodel

import com.moscow.tudee.domain.service.SplashService
import com.moscow.tudee.presentation.BaseViewModel

class SplashViewModel(
    private val splashService: SplashService
) : BaseViewModel<SplashState, SplashEvent>(SplashState()) {

    fun checkIfUserHasSeenOnboarding() {
        launchWithResult(
            action = { splashService.hasSeenOnboarding() },
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
            action = { splashService.markOnboardingAsShown(shown) },
            onSuccess = {
                updateState { it.copy(hasSeenOnboarding = shown) }
            },
            onError = {
                sendEvent(SplashEvent.ShowError("Failed to save onboarding state"))
            }
        )
    }
}
