package com.moscow.tudee.presentation.navigation.entry

import com.moscow.tudee.R
import kotlinx.serialization.Serializable

@Serializable
object CategoriesScreen:BottomNavigationType {
    override val isBottomNavigationVisible: Boolean
        get() = true
}