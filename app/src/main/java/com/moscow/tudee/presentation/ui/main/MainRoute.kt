package com.moscow.tudee.presentation.ui.main

import androidx.navigation.NavGraphBuilder
import com.moscow.tudee.navigation.entry.MainScreen
import com.moscow.tudee.navigation.extensions.tudeeComposable


fun NavGraphBuilder.mainRoute(){
    tudeeComposable<MainScreen> {
        MainScreen()
    }
}