package com.moscow.tudee.presentation.designSystem.component.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.designSystem.component.tudeeSwitch.TudeeSwitch
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme


typealias ComposableCallback = @Composable () -> Unit

@Composable
fun TudeeScaffold(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    bottomBar: ComposableCallback?,
    topBar: ComposableCallback?,
    floatingActionButton: ComposableCallback?
) {
    Box(modifier.systemBarsPadding()) {
        Column {
            topBar?.invoke()
            Box(Modifier.weight(1f)) {
                content()
                Box(Modifier.align(Alignment.BottomEnd)) {
                    floatingActionButton?.invoke()
                }
            }
            bottomBar?.invoke()
        }
    }
}

@Preview
@Composable
private fun TudeeScaffoldPreview() {
    TudeeTheme {
        TudeeScaffold(
            content = {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Red)
                ) { TudeeSwitch(isLightTheme = true) { }}
            },
            bottomBar = {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.Green)
                ) {
                    TudeeSwitch(isLightTheme = true) { }
                }
            },
            topBar = {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.Blue)
                ) {
                    TudeeSwitch(isLightTheme = true) { }
                }
            },
            floatingActionButton = {
                Column(
                    Modifier
                        .size(50.dp)
                        .background(Color.Yellow)
                ) {
                    TudeeSwitch(isLightTheme = true) { }
                }
            }
        )
    }
}