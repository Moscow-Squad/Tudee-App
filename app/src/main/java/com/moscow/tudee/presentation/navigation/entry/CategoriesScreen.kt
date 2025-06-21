package com.moscow.tudee.presentation.navigation.entry

import com.moscow.tudee.R
import kotlinx.serialization.Serializable

@Serializable
object CategoriesScreen:TudeeAppType, BottomNavigationType {
    override val appBar: TudeeAppBar
        get() = TudeeAppBar.TudeeTopAppBar(
            titleId = R.string.categories,
        )
    override val isBottomNavigationVisible: Boolean
        get() = true
}