package com.moscow.tudee.presentation.ui.main

import com.moscow.tudee.presentation.navigation.entry.TudeeAppBar

sealed interface MainScreenEvents {

    data class UpdateAppBar(val type: TudeeAppBar) : MainScreenEvents

    data class UpdateBottomBarVisibility(val visible: Boolean) : MainScreenEvents
}