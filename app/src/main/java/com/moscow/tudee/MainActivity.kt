package com.moscow.tudee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.moscow.tudee.presentation.designSystem.theme.ThemeState
import com.moscow.tudee.presentation.navigation.TudeeGraph
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import com.moscow.tudee.presentation.navigation.TudeeGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TudeeTheme {
                TudeeGraph()
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
        }
    }
}
