package com.moscow.tudee.presentation.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.CustomFAB
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors

@Composable
fun OnboardingItem(
    data: OnboardingData,
    onNext: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = data.imageRes),
            contentDescription = "Tudee onboarding image",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(37.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(bottom = 27.dp)
                    .fillMaxWidth()
                    .background(color = colors.onPrimaryCard, shape = RoundedCornerShape(32.dp))
                    .border(1.dp, colors.onPrimaryStroke, RoundedCornerShape(32.dp))
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 24.dp,
                        bottom = 48.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TudeeText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    text = data.title,
                    style = Theme.textStyle.title.medium,
                    color = colors.title,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
                TudeeText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    text = data.description,
                    style = Theme.textStyle.body.medium,
                    color = colors.body,
                    textAlign = TextAlign.Center,
                    maxLines = 3
                )
            }
            val layoutDirection = LocalLayoutDirection.current
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .graphicsLayer {
                        if (layoutDirection == LayoutDirection.Rtl) {
                            scaleX = -1f
                        }
                    }
            ) {
                CustomFAB(
                    onClick = onNext,
                    isLoading = false,
                    isEnabled = true,
                    icon = R.drawable.arrow_right_double
                )
            }
        }
    }
}

@Preview
@Composable
private fun OnboardingItemPreview() {
    OnboardingItem(
        data = OnboardingData(
            imageRes = R.drawable.image_container_1,
            title = "Welcome to Tudee",
            description = "Your personal assistant for managing tasks and staying organized."
        ),
        onNext = {}
    )
}
