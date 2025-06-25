package com.moscow.tudee.presentation.screen.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors
import com.moscow.tudee.presentation.designSystem.typography.CherryBomb
import com.moscow.tudee.presentation.screen.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
    viewModel: SplashViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val hasSeenOnboarding = state.hasSeenOnboarding

    val scale = remember { Animatable(0f) }

    LaunchedEffect(hasSeenOnboarding) {
        if (hasSeenOnboarding != null) {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000)
            )

            delay(3000)
            if (hasSeenOnboarding == true) {
                onNavigateToHome()
            } else {
                onNavigateToOnboarding()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.checkIfUserHasSeenOnboarding()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.surface)
            .background(colors.overlay)
    ) {

        Image(
            painter = painterResource(
                id = if (state.isSystemDark ?: isSystemInDarkTheme()) {
                    R.drawable.background_splash_dark
                } else {
                    R.drawable.background_splash_light
                }
            ),
            contentDescription = stringResource(R.string.splash_background),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .scale(scale.value)
            ) {
                TudeeText(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 48.sp,
                    fontFamily = CherryBomb,
                    color = colors.primary,
                    style = TextStyle(
                        drawStyle = Stroke(
                            width = 12f,
                            miter = 10f,
                            join = StrokeJoin.Round
                        )
                    )
                )
                TudeeText(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 48.sp,
                    fontFamily = CherryBomb,
                    color = colors.onPrimary
                )
            }
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(
        onNavigateToHome = {},
        onNavigateToOnboarding = {}
    )
}