package com.moscow.tudee.presentation.screen.onboarding.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.CustomFAB
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors
import com.moscow.tudee.presentation.screen.onboarding.OnboardingData

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalAnimationApi::class)
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
        BoxWithConstraints(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Surface(
                shape = RoundedCornerShape(32.dp),
                color = colors.onPrimaryCard,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = colors.onPrimaryStroke,
                        shape = RoundedCornerShape(32.dp)
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .padding(top = 24.dp, bottom = 48.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                ) {
                    AnimatedContent(
                        targetState = data.title,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "TitleAnimation"
                    ) { title ->
                        TudeeText(
                            text = title,
                            style = Theme.textStyle.title.medium,
                            color = colors.title,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            minLines = 2
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    AnimatedContent(
                        targetState = data.description,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "DescriptionAnimation"
                    ) { description ->
                        TudeeText(
                            text = description,
                            style = Theme.textStyle.body.medium,
                            color = colors.body,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            minLines = 3
                        )
                    }
                }
            }
        }

        val layoutDirection = LocalLayoutDirection.current
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = 28.dp)
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
