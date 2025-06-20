package com.moscow.tudee.presentation.ui.home

import androidx.navigation.NavGraphBuilder
import com.moscow.tudee.presentation.navigation.entry.HomeScreen
import com.moscow.tudee.presentation.navigation.entry.TudeeAppBar
import com.moscow.tudee.presentation.navigation.extensions.tudeeComposable
import com.moscow.tudee.presentation.screen.home.HomeScreen

fun NavGraphBuilder.homeRoute(
    appBar: (TudeeAppBar) -> Unit,
    isBottomNavigationVisible: (Boolean) -> Unit,
    navigateToTaskScreen: () -> Unit

) {
    appBar(HomeScreen.appBar)
    isBottomNavigationVisible(HomeScreen.isBottomNavigationVisible)

    tudeeComposable<HomeScreen> {
        HomeScreen(
            navigateToTaskScreen = navigateToTaskScreen
        )
    }
}