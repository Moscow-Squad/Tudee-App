package com.moscow.tudee.presentation.screen.splash

import androidx.navigation.NavGraphBuilder
import com.moscow.tudee.presentation.navigation.entry.SplashScreen
import com.moscow.tudee.presentation.navigation.extensions.tudeeComposable


fun NavGraphBuilder.splashRoute(
    navigateToOnBoarding: () -> Unit,
    navigateToHome: () -> Unit,
) {


    tudeeComposable<SplashScreen> {

        SplashScreen(
            onNavigateToOnboarding = navigateToOnBoarding,
            onNavigateToHome = navigateToHome
        )
    }
}