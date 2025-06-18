package com.moscow.tudee.presentation.designSystem.component.scaffold

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.component.BottomNavBar
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme


typealias ComposableCallback = @Composable () -> Unit

@Composable
fun TudeeScaffold(
    modifier: Modifier = Modifier,
    bottomBar: ComposableCallback? = null,
    topBar: ComposableCallback? = null,
    floatingActionButton: ComposableCallback? = null,
    content: ComposableCallback
) {
    Box(modifier.systemBarsPadding()) {
        Column {
            AnimatedVisibility(
                visible = topBar != null,
                enter = slideInVertically(tween(800)) { it },
                exit = slideOutVertically { -it }
            ) {
                topBar?.invoke()
            }

            Box(Modifier.weight(1f)) {
                content()
                androidx.compose.animation.AnimatedVisibility(
                    visible = floatingActionButton != null,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    Box(Modifier.align(Alignment.BottomEnd)) {
                        floatingActionButton?.invoke()
                    }
                }
            }

            AnimatedVisibility(
                visible = bottomBar != null
            ) {
                bottomBar?.invoke()
            }
        }
    }
}

@Preview
@Composable
 fun TudeeScaffoldPreview() {
    TudeeTheme {
        TudeeScaffold(
            content = {
                TudeeScaffold {
                    TudeeScaffold(
                        topBar = {
                            BottomNavBar(
                                selectedIndex = 1,
                                onItemSelected = {  },
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    ) {
                        Column(
                            Modifier
                                .fillMaxSize()
                                .background(Color.White.copy(0.41f))
                        ) { TudeeText("Top") }
                    }
                }
            },
            bottomBar = {
                BottomNavBar(
                    selectedIndex = 0,
                    onItemSelected = {  },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            topBar = {

            },
            floatingActionButton = {

            }
        )
    }
}