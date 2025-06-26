package com.moscow.tudee.presentation.screen.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.screen.onboarding.OnboardingData
import kotlinx.coroutines.launch

@Composable
fun OnboardingPager(
    pages: List<OnboardingData>,
    pagerState: PagerState,
    onComplete: () -> Unit
) {
    val scope = rememberCoroutineScope()

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            OnBoardingImage(
                page = pages[index],
            )
        }

        OnboardingItem(
            data = pages[pagerState.currentPage],
            onNext = {
                scope.launch {
                    if (pagerState.currentPage < pages.lastIndex){
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                    else {
                        onComplete()
                    }
                }
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            DotsIndicator(
                totalDots = pages.size,
                selectedIndex = pagerState.currentPage
            )
        }
    }
}
