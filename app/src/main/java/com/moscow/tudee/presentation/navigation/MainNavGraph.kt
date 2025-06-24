package com.moscow.tudee.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.moscow.tudee.presentation.category.CategoryTasks
import com.moscow.tudee.presentation.category.categoriesRoute
import com.moscow.tudee.presentation.category.categoryTasksRoute
import com.moscow.tudee.presentation.navigation.entry.HomeScreen
import com.moscow.tudee.presentation.navigation.entry.MainScreen
import com.moscow.tudee.presentation.navigation.entry.TasksScreen
import com.moscow.tudee.presentation.navigation.entry.TudeeAppBar
import com.moscow.tudee.presentation.navigation.extensions.navigateSafe
import com.moscow.tudee.presentation.ui.home.homeRoute
import com.moscow.tudee.presentation.ui.tasks.tasksRoute

@Composable
fun MainNavGraph(
    navController: NavHostController = rememberNavController(),
    appBar: (TudeeAppBar) -> Unit,
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
            appBar = appBar,
            isBottomNavigationVisible = isBottomNavigationVisible,
            navigateToTaskScreen = {
                navController.navigateSafe(
                    route = TasksScreen,
                    builder = {}
                )
            }
        )

        tasksRoute(
            appBar = appBar,
            isBottomNavigationVisible = isBottomNavigationVisible
        )

        categoriesRoute(
            navigateToCategoryTasks = { categoryID ->
                navController.navigateSafe(
                    route = CategoryTasks(categoryID),
                    builder = {}
                )
            }
        )

        categoryTasksRoute(
            isBottomNavigationType=isBottomNavigationVisible,
            navigateBackToCategoryScreen = { messageId ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("result_message", messageId)

                navController.popBackStack()
            }
            , navigateBack ={
                navController.navigateSafe(
                    route=navController.popBackStack(),
                    builder = {}
                )
            }
        )
    }

}


