package com.moscow.tudee.presentation.navigation.entry

import com.moscow.tudee.R
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen : TudeeAppType, BottomNavigationType {
    override val appBar: TudeeAppBar
        get() = TudeeAppBar.TudeeHomeTopAppBar(
            titleId = R.string.tudee,
            subTitleId = R.string.your_cute_helper_for_every_task
        )
    override val isBottomNavigationVisible: Boolean
        get() = true
}
