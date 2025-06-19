package com.moscow.tudee.presentation.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors
import com.moscow.tudee.presentation.ui.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingScreen(
    viewModel: SplashViewModel = koinViewModel(),
    onFinish: () -> Unit
) {
    val scope = rememberCoroutineScope()

    val backgroundImage: Painter = if (isSystemInDarkTheme()) {
        painterResource(id = R.drawable.background_splash_dark)
    } else {
        painterResource(id = R.drawable.background_splash_light)
    }

    val pages = listOf(
        OnboardingData(
            imageRes = R.drawable.image_container_1,
            title = stringResource(R.string.overwhelmed_with_tasks),
            description = stringResource(R.string.let_s_bring_some_order_to_the_chaos_tudee_is_here_to_help_you_sort_plan_and_breathe_easier)
        ),
        OnboardingData(
            imageRes = R.drawable.image_container_2,
            title = stringResource(R.string.uh_oh_procrastinating_again),
            description = stringResource(R.string.tudee_not_mad_just_a_little_disappointed)
        ),
        OnboardingData(
            imageRes = R.drawable.image_container_3,
            title = stringResource(R.string.let_s_complete_tasks_and_celebrate_together),
            description = stringResource(R.string.tudee_will_celebrate_you_on_every_win)
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })

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

        if (pagerState.currentPage < pages.lastIndex) {
            Text(
                text = stringResource(R.string.skip),
                modifier = Modifier
                    .padding(start = 16.dp, top = 56.dp)
                    .align(Alignment.TopStart)
                    .clickable {
                        scope.launch {
                            viewModel.setOnboardingSeen(true)
                            onFinish()
                        }
                    },
                style = Theme.textStyle.label.large,
                color = colors.primary
            )
        }


        OnboardingPager(
            pages = pages,
            pagerState = pagerState,
            onComplete = {
                scope.launch {
                    viewModel.setOnboardingSeen(true)
                    onFinish()
                }
            }
        )
    }
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    val bg = painterResource(id = R.drawable.background_splash_light)
    OnboardingScreen(onFinish = {})
}
