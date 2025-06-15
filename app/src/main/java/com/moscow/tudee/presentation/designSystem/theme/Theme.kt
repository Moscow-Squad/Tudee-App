package com.moscow.tudee.presentation.designSystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.moscow.tudee.presentation.designSystem.color.LocalTudeeColors
import com.moscow.tudee.presentation.designSystem.color.TudeeColors
import com.moscow.tudee.presentation.designSystem.color.darkThemeColors
import com.moscow.tudee.presentation.designSystem.color.lightThemeColors
import com.moscow.tudee.presentation.designSystem.typography.LocalTudeeTextStyle
import com.moscow.tudee.presentation.designSystem.typography.TudeeTextStyle
import com.moscow.tudee.presentation.designSystem.typography.DefaultTextStyle


@Composable
fun TudeeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkThemeColors
        else -> lightThemeColors
    }
    CompositionLocalProvider(
        LocalTudeeColors provides colorScheme,
        LocalTudeeTextStyle provides DefaultTextStyle
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