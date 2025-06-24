package com.moscow.tudee.presentation.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun OnboardingPager(
    pages: List<OnboardingData>,
    onComplete: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0) { pages.size }

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
                .padding(bottom = 24.dp),
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
