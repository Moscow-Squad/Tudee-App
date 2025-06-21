package com.moscow.tudee.presentation.navigation.entry

import com.moscow.tudee.R
import kotlinx.serialization.Serializable

@Serializable
object TasksScreen: TudeeAppType, BottomNavigationType {
    override val appBar: TudeeAppBar
        get() = TudeeAppBar.TudeeTopAppBar(
            titleId = R.string.tasks,
        )
    override val isBottomNavigationVisible: Boolean
        get() = true
}
