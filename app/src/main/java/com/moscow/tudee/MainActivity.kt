package com.moscow.tudee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.designSystem.component.scaffold.TudeeScaffold
import com.moscow.tudee.presentation.designSystem.component.tudeeSwitch.TudeeSwitch
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.ThemeState
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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

            var isDay by remember {
                mutableStateOf(false)
            }

            TudeeTheme(state = themeState) {
                val localState = Theme.state
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        TudeeSwitch(
                            isLightTheme = localState.isDark,
                            onToggleState = {
                                localState.onThemeChanged(!localState.isDark)
                            }
                        )
                    }

                }

//                TudeeScaffold(
//                    content = {
//                        Column(
//                            Modifier
//                                .fillMaxSize()
//                                .background(Color.Red)
//                        ) { TudeeSwitch(isLightTheme = true) { }}
//                    },
//                    bottomBar = {
//                        Column(
//                            Modifier
//                                .fillMaxWidth()
//                                .height(100.dp)
//                                .background(Color.Green)
//                        ) {
//                            TudeeSwitch(isLightTheme = true) { }
//                        }
//                    },
//                    topBar = {
//                        Column(
//                            Modifier
//                                .fillMaxWidth()
//                                .background(Color.Blue)
//                        ) {
//                            TudeeSwitch(isLightTheme = true) { }
//                        }
//                    },
//                    floatingActionButton = {
//                        Column(
//                            Modifier
//                                .size(50.dp)
//                                .background(Color.Yellow)
//                        ) {
//                            TudeeSwitch(isLightTheme = true) { }
//                        }
//                    }
//                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TudeeTheme {
        Greeting("Android")
    }
}