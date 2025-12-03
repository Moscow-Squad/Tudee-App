package com.moscow.tudee.presentation.component.tudeeSwitch

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.shadow.innerShadow
import com.moscow.tudee.presentation.designSystem.theme.Theme

@Stable
@Composable
fun TudeeSwitchDarkTheme(
    onToggleState: () -> Unit,
    isClickable: Boolean,
    movingCloudySize: Dp,
    moonAlignment: BiasAlignment,
) {
    Box(
        modifier = Modifier
            .requiredSize(width = 64.dp, height = 36.dp)
            .background(
                color = Color(0xFF151535),
                shape = CircleShape

            )
            .border(
                width = 1.dp,
                color = Theme.colors.stroke.copy(0.12f),
                shape = CircleShape
            )
            .clip(CircleShape)

    ) {
        Box(
            modifier = Modifier
                .size(98.dp)
                .blur(24.dp, edgeTreatment = BlurredEdgeTreatment(CircleShape))
                .background(Color(0xFF222257))
                .offset(y = (-18).dp, x = (-42).dp)
        )

        Box(
            modifier = Modifier
                .size(104.dp)
                .blur(24.dp, edgeTreatment = BlurredEdgeTreatment(CircleShape))
                .background(Color(0xFF1A1A44))
                .offset(y = (-20).dp, x = (-18).dp)
        )

        Image(
            modifier = Modifier
                .padding(start = 6.dp)
                .height(27.dp)
                .align(Alignment.CenterStart),
            painter = painterResource(id = R.drawable.ic_stars),
            contentDescription = null,
            contentScale = ContentScale.FillHeight
        )
        Box(
            modifier = Modifier
                .padding(end = 2.dp)
                .size(32.dp)
                .clip(CircleShape)
                .clickable(
                    enabled = isClickable,
                    onClick = onToggleState
                )
                .align(moonAlignment)
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color(0xFFE9F0FF),
                                Color(0xFFE0E9FE)
                            )
                        ),
                        shape = CircleShape
                    )
                    .blur(12.dp, edgeTreatment = BlurredEdgeTreatment(CircleShape))
            )
            Box(
                modifier = Modifier
                    .padding(top = 2.dp, start = 9.dp)
                    .size(movingCloudySize)
                    .animateContentSize(
                        animationSpec = tween(800),
                        alignment = Alignment.TopEnd
                    )
                    .background(
                        color = Color(0xFFE9EFFF),
                        shape = CircleShape
                    )
                    .innerShadow(
                        color = Color(0xFFBFD2FF),
                        offsetY = 1.dp,
                        offsetX = 1.dp,
                        blur = 4.dp,
                        cornersRadius = 8.dp
                    )
            )

            Box(
                modifier = Modifier
                    .padding(top = 12.dp, start = 4.dp)
                    .size(14.dp)
                    .background(
                        color = Color(0xFFE9EFFF),
                        shape = CircleShape
                    )
                    .innerShadow(
                        color = Color(0xFFBFD2FF),
                        offsetY = 1.dp,
                        offsetX = 1.dp,
                        blur = 4.dp,
                        cornersRadius = 14.dp
                    )
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 2.dp, end = 9.dp)
                    .size(4.dp)
                    .background(
                        color = Color(0xFFE9EFFF),
                        shape = CircleShape
                    )
                    .innerShadow(
                        color = Color(0xFFBFD2FF),
                        offsetY = 1.dp,
                        offsetX = 1.dp,
                        blur = 4.dp,
                        cornersRadius = 4.dp
                    )
            )
        }
    }
}