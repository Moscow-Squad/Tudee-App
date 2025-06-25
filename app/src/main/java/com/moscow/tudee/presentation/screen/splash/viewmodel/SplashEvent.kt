package com.moscow.tudee.presentation.screen.splash.viewmodel

sealed class SplashEvent {
    object NavigateToHome : SplashEvent()
    object NavigateToOnboarding : SplashEvent()
    class ShowError(val message: String) : SplashEvent()
}
