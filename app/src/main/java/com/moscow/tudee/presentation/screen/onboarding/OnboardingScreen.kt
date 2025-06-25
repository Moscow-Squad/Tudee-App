package com.moscow.tudee.presentation.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors
import com.moscow.tudee.presentation.screen.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun OnboardingScreen(
    viewModel: SplashViewModel = koinViewModel(),
    onFinish: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val pages = provideOnboardingPages()

    val pagerState = rememberPagerState(pageCount = { pages.size })

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    WindowInsets.navigationBars.asPaddingValues()
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.surface)
                    .background(colors.overlay)
            ) {
                if (pagerState.currentPage != pages.lastIndex) {
                    TextButton(
                        modifier = Modifier
                            .padding(top = 56.dp)
                            .align(Alignment.Start),
                        onClick = onFinish
                    ) {
                        TudeeText(
                            text = stringResource(R.string.skip),
                            style = Theme.textStyle.label.large,
                            color = colors.primary
                        )
                    }
                }

                OnboardingPager(
                    pages = pages,
                    onComplete = {
                        scope.launch {
                            viewModel.setOnboardingSeen(true)
                            onFinish()
                        }
                    }
                )
            }
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
        }
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    val bg = painterResource(id = R.drawable.background_splash_light)
    OnboardingScreen(onFinish = {})
}