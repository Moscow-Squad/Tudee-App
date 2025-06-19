package com.moscow.tudee.presentation.designSystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import com.moscow.tudee.presentation.designSystem.color.LocalTudeeColors
import com.moscow.tudee.presentation.designSystem.color.TudeeColors
import com.moscow.tudee.presentation.designSystem.color.darkThemeColors
import com.moscow.tudee.presentation.designSystem.color.lightThemeColors
import com.moscow.tudee.presentation.designSystem.typography.LocalTudeeTextStyle
import com.moscow.tudee.presentation.designSystem.typography.TudeeTextStyle
import com.moscow.tudee.presentation.designSystem.typography.DefaultTextStyle


@Composable
fun TudeeTheme(
    state: ThemeState = ThemeState(isDark = isSystemInDarkTheme(), onThemeChanged = {}),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        state.isDark -> darkThemeColors
        else -> lightThemeColors
    }
    CompositionLocalProvider(
        LocalThemeState provides state,
        LocalTudeeColors provides colorScheme,
        LocalTudeeTextStyle provides DefaultTextStyle,
    ) {
        content()
    }
}

object Theme {
    val colors: TudeeColors
        @Composable @ReadOnlyComposable get() = LocalTudeeColors.current

    val textStyle: TudeeTextStyle
        @Composable @ReadOnlyComposable get() = LocalTudeeTextStyle.current

    val state: ThemeState
        @Composable get() = LocalThemeState.current
}

val LocalThemeState = compositionLocalOf { ThemeState(false, {}) }