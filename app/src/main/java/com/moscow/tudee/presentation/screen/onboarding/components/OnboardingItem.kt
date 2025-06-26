package com.moscow.tudee.presentation.screen.onboarding.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.CustomFAB
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors
import com.moscow.tudee.presentation.screen.onboarding.OnboardingData

@Composable
fun OnboardingItem(
    modifier: Modifier = Modifier,
    data: OnboardingData,
    onNext: () -> Unit
) {

    Box(
        modifier = modifier
            .padding(
                top = 32.dp,
                bottom = 60.dp
            )
    ) {
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = colors.onPrimaryCard,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .border(
                    width = 1.dp,
                    color = colors.onPrimaryStroke,
                    shape = RoundedCornerShape(32.dp)
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(
                    top = 24.dp,
                    bottom = 48.dp,
                    start = 16.dp,
                    end = 16.dp,
                )
            ) {
                TudeeText(
                    text = data.title,
                    style = Theme.textStyle.title.medium,
                    color = colors.title,
                    modifier = Modifier.padding(bottom = 16.dp),
                    textAlign = TextAlign.Center,
                )
                TudeeText(
                    text = data.description,
                    style = Theme.textStyle.body.medium,
                    color = colors.body,
                    textAlign = TextAlign.Center,
                )
            }
        }
            val layoutDirection = LocalLayoutDirection.current
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (28).dp)
                    .graphicsLayer {
                        if (layoutDirection == LayoutDirection.Rtl) {
                            scaleX = -1f
                        }
                    }
            ) {
                CustomFAB(
                    modifier = Modifier.size(64.dp),
                    onClick = onNext,
                    isLoading = false,
                    isEnabled = true,
                    icon = R.drawable.arrow_right_double
                )
            }
    }
}

@Preview
@Composable
fun OnboardingItemPreview() {
    OnboardingItem(
        data = OnboardingData(
            imageRes = R.drawable.splash_logo_img,
            title = "Welcome to Tudee",
            description = "Your personal task manager to help you stay organized and productive."
        ),
        onNext = {}
    )
}

