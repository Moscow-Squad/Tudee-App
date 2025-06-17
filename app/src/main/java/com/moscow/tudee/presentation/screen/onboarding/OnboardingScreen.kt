package com.moscow.tudee.presentation.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.CustomFAB
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors
import com.moscow.tudee.presentation.viewmodel.SplashViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingScreen(
    viewModel: SplashViewModel = koinViewModel(),
    onFinish: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val isDarkMode = isSystemInDarkTheme()
    val backgroundImage = if (isDarkMode) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                ) { page ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = pages[page].imageRes),
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(bottom = 27.dp)
                                    .fillMaxWidth()
                                    .background(
                                        color = colors.onPrimaryCard,
                                        shape = RoundedCornerShape(32.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = colors.onPrimaryStroke,
                                        shape = RoundedCornerShape(32.dp)
                                    )
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 24.dp,
                                        bottom = 48.dp
                                    ),
                                verticalArrangement = Arrangement.spacedBy(40.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    text = pages[page].title,
                                    style = Theme.textStyle.title.medium,
                                    color = colors.title,
                                    textAlign = TextAlign.Center,
                                    maxLines = 2
                                )

                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    text = pages[page].description,
                                    style = Theme.textStyle.body.medium,
                                    color = colors.body,
                                    textAlign = TextAlign.Center,
                                    maxLines = 3
                                )
                            }
                            CustomFAB(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter),
                                onClick = {
                                    scope.launch {
                                        if (pagerState.currentPage < pages.lastIndex) {
                                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                        } else {
                                            viewModel.setOnboardingSeen(true)
                                            onFinish()
                                        }
                                    }
                                },
                                isLoading = false,
                                isEnabled = true,
                                icon = R.drawable.arrow_right_double
                            )
                        }
                    }
                }


            }
            DotsIndicator(
                totalDots = pages.size,
                selectedIndex = pagerState.currentPage,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp, bottom = 24.dp)
            )

        }
    }
}

data class OnboardingData(
    val imageRes: Int,
    val title: String,
    val description: String
)

@Preview
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(
        onFinish = {}
    )
}