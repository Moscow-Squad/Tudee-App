package com.moscow.tudee.presentation.navigation.entry

import kotlinx.serialization.Serializable

@Serializable
object HomeScreen : BottomNavigationType {
    override val isBottomNavigationVisible: Boolean
        get() = true
}
