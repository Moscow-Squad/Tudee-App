package com.moscow.tudee.presentation.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors
import com.moscow.tudee.presentation.ui.splash.viewmodel.SplashViewModel
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
    val backgroundImage: Painter = if (state.isSystemDark ?: isSystemInDarkTheme()) {
        painterResource(id = R.drawable.background_splash_dark)
    } else {
        painterResource(id = R.drawable.background_splash_light)
    }

    val scale = remember { Animatable(0f) }

    LaunchedEffect(hasSeenOnboarding) {
        if (hasSeenOnboarding != null) {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000)
            )

            delay(2000)
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
            painter = backgroundImage,
            contentDescription = stringResource(R.string.splash_background),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )

        Image(
            painter = painterResource(id = R.drawable.splash_logo_img),
            contentDescription = stringResource(R.string.splash_logo),
            modifier = Modifier
                .align(Alignment.Center)
                .scale(scale.value)
        )
    }
}