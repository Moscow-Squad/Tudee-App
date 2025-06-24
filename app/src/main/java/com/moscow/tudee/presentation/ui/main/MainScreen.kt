package com.moscow.tudee.presentation.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moscow.tudee.presentation.designSystem.component.BottomNavBar
import com.moscow.tudee.presentation.designSystem.component.topbar.TudeeAppBar
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.model.BottomNavigationDestination
import com.moscow.tudee.presentation.navigation.MainNavGraph

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel = MainViewModel(),
){
    val state = mainViewModel.state.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        containerColor = Theme.colors.surface,
        topBar = {

            TudeeAppBar(
                appBar = state.appBar
            )

        },
        bottomBar = {
            if (state.isBottomNavigationVisible){
                BottomNavBar(
                    bottomNavigationItems = BottomNavigationDestination.entries,
                    navController = navController
                )
            }
        },
    ) { innerPadding ->

        MainNavGraph(
            navController = navController,
            paddingValues = innerPadding,
            isBottomNavigationVisible = {
                mainViewModel.onEvent(MainScreenEvents.UpdateBottomBarVisibility(it))
            }
        )

    }
}

