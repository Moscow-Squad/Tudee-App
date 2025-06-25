package com.moscow.tudee.presentation.navigation.main

import androidx.navigation.NavGraphBuilder
import com.moscow.tudee.presentation.navigation.entry.MainScreen
import com.moscow.tudee.presentation.navigation.extensions.tudeeComposable


fun NavGraphBuilder.mainRoute(){
    tudeeComposable<MainScreen> {
        MainScreen()
    }
}