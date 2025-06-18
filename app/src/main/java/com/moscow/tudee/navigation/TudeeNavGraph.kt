package com.moscow.tudee.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.moscow.tudee.navigation.entry.MainScreen
import com.moscow.tudee.navigation.entry.OnBoardingScreen
import com.moscow.tudee.navigation.entry.SplashScreen
import com.moscow.tudee.navigation.extensions.navigateSafe
import com.moscow.tudee.presentation.ui.main.mainRoute
import com.moscow.tudee.presentation.ui.onboarding.onBoardingRoute
import com.moscow.tudee.presentation.ui.splash.splashRoute

@Composable
fun TudeeGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = SplashScreen
    ) {

        splashRoute(
            navigateToOnBoarding = {
                navController.navigateSafe(
                    OnBoardingScreen,
                    builder = {
                        popUpTo<SplashScreen> { inclusive = true }
                    }
                )
            },
            navigateToHome = {
                navController.navigateSafe(
                    MainScreen,
                    builder = {
                        popUpTo<SplashScreen> { inclusive = true }
                    }
                )
            }
        )

        onBoardingRoute(
            navigateToHome = {
                navController.navigateSafe(
                    MainScreen,
                    builder = {
                        popUpTo<OnBoardingScreen> { inclusive = true }
                    }
                )
            }
        )

        mainRoute()

    }
}