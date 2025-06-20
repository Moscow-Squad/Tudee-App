package com.moscow.tudee.presentation.navigation.entry

import androidx.annotation.StringRes

sealed class TudeeAppBar {
    data object NoAppBar : TudeeAppBar()

    data class TudeeTopAppBar(
        @StringRes val titleId: Int
    ): TudeeAppBar()

//    data class TudeeNavigationAppBar(
//        @StringRes val titleId: Int
//    ):TudeeAppBar()

    data class TudeeHomeTopAppBar(
        @StringRes val titleId: Int,
        @StringRes val subTitleId: Int,
    ):TudeeAppBar()
}
