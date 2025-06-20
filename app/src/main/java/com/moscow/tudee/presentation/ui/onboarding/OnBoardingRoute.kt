package com.moscow.tudee.presentation.ui.onboarding

import androidx.navigation.NavGraphBuilder
import com.moscow.tudee.presentation.navigation.entry.OnBoardingScreen
import com.moscow.tudee.presentation.navigation.extensions.tudeeComposable

fun NavGraphBuilder.onBoardingRoute(
    navigateToHome: () -> Unit
) {
    tudeeComposable<OnBoardingScreen> {

        OnboardingScreen(
            onFinish = navigateToHome
        )
    }
}