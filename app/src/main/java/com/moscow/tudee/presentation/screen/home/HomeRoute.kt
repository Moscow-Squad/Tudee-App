package com.moscow.tudee.presentation.screen.home

import androidx.navigation.NavGraphBuilder
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.navigation.entry.HomeScreen
import com.moscow.tudee.presentation.navigation.extensions.tudeeComposable

fun NavGraphBuilder.homeRoute(
    isBottomNavigationVisible: (Boolean) -> Unit,
    navigateToTaskScreen: (status: Task.Status) -> Unit
) {
    isBottomNavigationVisible(HomeScreen.isBottomNavigationVisible)

    tudeeComposable<HomeScreen> {
        HomeScreen(
            navigateToTaskScreen = navigateToTaskScreen
        )
    }
}