package com.moscow.tudee.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.moscow.tudee.presentation.designSystem.color.LocalTudeeColors
import com.moscow.tudee.presentation.designSystem.color.TudeeColors
import com.moscow.tudee.presentation.designSystem.color.tudeeDarkTheme
import com.moscow.tudee.presentation.designSystem.color.tudeeLightColor
import com.moscow.tudee.presentation.designSystem.typograghy.LocalTudeeTextStyle
import com.moscow.tudee.presentation.designSystem.typograghy.TudeeTextStyle
import com.moscow.tudee.presentation.designSystem.typograghy.defaultTextStyle


@Composable
fun TudeeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> tudeeDarkTheme
        else -> tudeeLightColor
    }
    CompositionLocalProvider(
        LocalTudeeColors provides colorScheme,
        LocalTudeeTextStyle provides defaultTextStyle
    ) {
        content()
    }
}

object Theme {
    val colors: TudeeColors
        @Composable @ReadOnlyComposable get() = LocalTudeeColors.current

    val textStyle:TudeeTextStyle
        @Composable @ReadOnlyComposable get() = LocalTudeeTextStyle.current
}