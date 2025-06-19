package com.moscow.tudee.presentation.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moscow.tudee.presentation.navigation.MainNavGraph
import com.moscow.tudee.presentation.designSystem.component.BottomNavBar
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.model.BottomNavigationDestination

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
){

    Scaffold(
        modifier = Modifier.fillMaxSize().navigationBarsPadding(),
        containerColor = Theme.colors.surface,
        bottomBar = {
            BottomNavBar(
                bottomNavigationItems = BottomNavigationDestination.entries,
                navController = navController
            )
        },
    ) { innerPadding ->

        MainNavGraph(
            navController = navController,
            paddingValues = innerPadding,
        )

    }
}

