package com.moscow.tudee.presentation.ui.main

import com.moscow.tudee.presentation.navigation.entry.TudeeAppBar

data class MainScreenState(
    val appBar: TudeeAppBar = TudeeAppBar.NoAppBar,
    val isBottomNavigationVisible: Boolean = true,
)