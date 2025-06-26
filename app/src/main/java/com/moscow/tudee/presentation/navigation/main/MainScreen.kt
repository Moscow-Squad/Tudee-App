package com.moscow.tudee.presentation.navigation.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.moscow.tudee.presentation.component.BottomNavBar
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.model.BottomNavigationDestination
import com.moscow.tudee.presentation.navigation.MainNavGraph

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel = MainViewModel(),
) {
    val state = mainViewModel.state.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Theme.colors.surface,
        bottomBar = {

            if (state.isBottomNavigationVisible) {
                BottomNavBar(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .background(Theme.colors.surface),
                    bottomNavigationItems = BottomNavigationDestination.entries,
                    navController = navController
                )
            }
        },
    ) { innerPadding ->

        MainNavGraph(
            navController = navController,
            paddingValues = innerPadding,
            modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
            isBottomNavigationVisible = {
                mainViewModel.onEvent(MainScreenEvents.UpdateBottomBarVisibility(it))
            })

    }
}