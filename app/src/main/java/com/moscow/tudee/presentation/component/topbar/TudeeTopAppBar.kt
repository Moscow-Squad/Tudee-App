package com.moscow.tudee.presentation.component.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.theme.Theme

@Composable
fun TudeeTopAppBar(
    title: String,
    modifier: Modifier = Modifier
){

    Box(
        modifier = modifier.fillMaxWidth()
        .background(Theme.colors.surfaceHigh)
        .statusBarsPadding()
        .padding(vertical = 20.dp)
        .padding(horizontal = 16.dp),
    ) {

        TudeeText(
            text = title,
            style = Theme.textStyle.title.large,
            color = Theme.colors.title.copy(alpha = .87f)
        )

    }
}