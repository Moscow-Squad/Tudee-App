package com.moscow.tudee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.moscow.tudee.presentation.designSystem.theme.ThemeState
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import com.moscow.tudee.presentation.navigation.TudeeGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.auto(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb()
            )
        )
        setContent {
            val isDark = isSystemInDarkTheme()
            val (isDarkThemeState, onThemeStateChanged) = remember { mutableStateOf(isDark) }
            val themeState by remember(isDarkThemeState) {
                derivedStateOf {
                    ThemeState(
                        isDark = isDarkThemeState,
                        onThemeChanged = onThemeStateChanged
                    )
                }
            }
            TudeeTheme(state = themeState) {
                TudeeGraph()
            }
        }
    }
}

