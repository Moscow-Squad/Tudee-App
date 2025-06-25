package com.moscow.tudee

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.moscow.tudee.data.service.LocalServiceImpl
import com.moscow.tudee.presentation.designSystem.theme.ThemeState
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import com.moscow.tudee.presentation.navigation.TudeeGraph
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val localService = LocalServiceImpl(this@MainActivity)
            val isSystemInDarkTheme = isSystemInDarkTheme()

            var isDark by remember { mutableStateOf<Boolean>(isSystemInDarkTheme) }

            val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(Unit) {
                try {
                    val dataStoreIsDark = localService.isSystemThemeDark()

                    if (dataStoreIsDark == null) {
                        isDark = isSystemInDarkTheme
                    } else {
                        isDark = dataStoreIsDark
                    }
                } catch (e: Exception) {
                    isSystemInDarkTheme.also { systemTheme ->
                        localService.setSystemThemeDark(systemTheme)
                    }
                }
            }

            val isDarkThemeState = remember(isDark) { mutableStateOf(isDark) }
            val themeState by remember(isDarkThemeState) {
                derivedStateOf {
                    ThemeState(
                        isDark = isDarkThemeState.value,
                        onThemeChanged = { isDark ->

                            isDarkThemeState.value = isDark
                            coroutineScope.launch {
                                localService.setSystemThemeDark(isDark)
                            }
                        }
                    )
                }
            }
            TudeeTheme(state = themeState) {
                TudeeGraph()
            }

        }
    }
}

