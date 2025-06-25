package com.moscow.tudee.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.moscow.tudee.presentation.navigation.entry.MainScreen
import com.moscow.tudee.presentation.navigation.entry.OnBoardingScreen
import com.moscow.tudee.presentation.navigation.entry.SplashScreen
import com.moscow.tudee.presentation.navigation.extensions.navigateSafe
import com.moscow.tudee.presentation.navigation.main.mainRoute
import com.moscow.tudee.presentation.screen.onboarding.onBoardingRoute
import com.moscow.tudee.presentation.screen.splash.splashRoute

@Composable
fun TudeeGraph(
    navController: NavHostController = rememberNavController(),
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