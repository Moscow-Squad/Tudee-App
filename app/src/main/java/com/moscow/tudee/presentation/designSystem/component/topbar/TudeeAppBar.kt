package com.moscow.tudee.presentation.designSystem.component.topbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.moscow.tudee.presentation.navigation.entry.TudeeAppBar


@Composable
fun TudeeAppBar(
    appBar: TudeeAppBar,
){
    when(appBar){
        is TudeeAppBar.TudeeHomeTopAppBar -> {
            HomeTopAppBar(
                title = stringResource(id = appBar.titleId),
                subTitle = stringResource(id = appBar.subTitleId),
            )
        }
        is TudeeAppBar.TudeeTopAppBar -> {
            TudeeTopAppBar(
                title = stringResource(id = appBar.titleId),
            )
        }
        TudeeAppBar.NoAppBar -> {

        }
    }

}