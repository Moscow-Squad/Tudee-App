package com.moscow.tudee.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.moscow.tudee.navigation.entry.CategoriesScreen
import com.moscow.tudee.navigation.entry.HomeScreen
import com.moscow.tudee.navigation.entry.MainScreen
import com.moscow.tudee.navigation.entry.TasksScreen
import com.moscow.tudee.navigation.extensions.tudeeComposable
import com.moscow.tudee.presentation.ui.categories.categoriesRoute
import com.moscow.tudee.presentation.ui.home.homeRoute
import com.moscow.tudee.presentation.ui.tasks.tasksRoute

@Composable
fun MainNavGraph(
    navController: NavHostController = rememberNavController(),
    paddingValues: PaddingValues,
) {
    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        route = MainScreen::class,
        startDestination = HomeScreen
    )
    {
        homeRoute()

        tasksRoute()

        categoriesRoute()

    }
}