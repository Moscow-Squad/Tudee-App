package com.moscow.tudee.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.moscow.tudee.presentation.navigation.entry.CategoryTasks
import com.moscow.tudee.presentation.navigation.entry.HomeScreen
import com.moscow.tudee.presentation.navigation.entry.MainScreen
import com.moscow.tudee.presentation.navigation.entry.TasksScreen
import com.moscow.tudee.presentation.navigation.entry.TudeeAppBar
import com.moscow.tudee.presentation.navigation.extensions.navigateSafe
import com.moscow.tudee.presentation.ui.categories.categoriesRoute
import com.moscow.tudee.presentation.ui.home.homeRoute
import com.moscow.tudee.presentation.ui.tasks.tasksRoute

@Composable
fun MainNavGraph(
    navController: NavHostController = rememberNavController(),
    isBottomNavigationVisible: (Boolean) -> Unit,
    paddingValues: PaddingValues,
) {
    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        route = MainScreen::class,
        startDestination = HomeScreen
    )
    {
        homeRoute(
            isBottomNavigationVisible = isBottomNavigationVisible,
            navigateToTaskScreen = {
                navController.navigateSafe(
                    route = TasksScreen,
                    builder = {}
                )
            }
        )

        tasksRoute(
            isBottomNavigationVisible = isBottomNavigationVisible
        )

        categoriesRoute(
            isBottomNavigationVisible = isBottomNavigationVisible,
            navigateToCategoryTasks = {categoryID->
                navController.navigateSafe(
                    route =  CategoryTasks(categoryID),
                    builder = {}
                )
            }
        )

//        categoriesTasksRoute()
    }
}

