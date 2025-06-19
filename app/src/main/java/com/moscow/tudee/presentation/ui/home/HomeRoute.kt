package com.moscow.tudee.presentation.ui.home

import androidx.navigation.NavGraphBuilder
import com.moscow.tudee.presentation.navigation.entry.HomeScreen
import com.moscow.tudee.presentation.navigation.extensions.tudeeComposable
import com.moscow.tudee.presentation.screen.home.HomeScreen

fun NavGraphBuilder.homeRoute() {

    tudeeComposable<HomeScreen> {
        HomeScreen(
            navigateToTaskScreen = { /* TODO */ }
        )
    }
}