package com.moscow.tudee.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import com.moscow.tudee.presentation.screen.onboarding.OnboardingScreen
import com.moscow.tudee.presentation.screen.splash.SplashScreen

@Composable
fun TudeeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    TudeeTheme {
        Column(modifier = modifier.fillMaxSize()) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.SplashScreen.route,
                    modifier = modifier.fillMaxSize()
                ) {
                    composable(Screen.SplashScreen.route) {
                        SplashScreen(
                            onNavigateToHome = {
                                navController.navigate(Screen.HomeScreen.route) {
                                    popUpTo(Screen.SplashScreen.route) { inclusive = true }
                                }
                            },
                            onNavigateToOnboarding = {
                                navController.navigate(Screen.OnboardingScreen.route) {
                                    popUpTo(Screen.SplashScreen.route) { inclusive = true }
                                }
                            }
                        )
                    }

                    composable(Screen.OnboardingScreen.route) {
                        OnboardingScreen(
                            onFinish = {
                                navController.navigate(Screen.HomeScreen.route) {
                                    popUpTo(Screen.OnboardingScreen.route) { inclusive = true }
                                }
                            }
                        )
                    }

                    composable(Screen.HomeScreen.route) {
                        //HomeScreen()
                    }

                    composable(Screen.TaskScreen.route) {
                        //TaskScreen()
                    }

                    composable(Screen.CategoriesScreen.route) {
                        //CategoriesScreen()
                    }
                }
            }
        }
    }
}