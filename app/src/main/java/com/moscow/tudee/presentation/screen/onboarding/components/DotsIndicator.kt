package com.moscow.tudee.presentation.screen.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    dotSpacing: Dp = 10.dp,
    dotHeight: Dp = 5.dp
) {
    val selectedColor = colors.primary
    val unSelectedColor = colors.primaryVariant

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dotSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalDots) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(dotHeight)
                    .clip(RoundedCornerShape(100.dp))
                    .background(if (index == selectedIndex) selectedColor else unSelectedColor)
            )
        }
    }
}


@Preview
@Composable
fun DotsIndicatorPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        DotsIndicator(
            totalDots = 3,
            selectedIndex = 1,
            modifier = Modifier.align(Alignment.Center),
            dotSpacing = 12.dp,
            dotHeight = 6.dp
        )
    }
}