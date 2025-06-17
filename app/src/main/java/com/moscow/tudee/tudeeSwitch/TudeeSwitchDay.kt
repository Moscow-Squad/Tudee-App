package com.moscow.tudee.tudeeSwitch

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.designSystem.theme.Theme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TudeeSwitchDay(
    modifier: Modifier = Modifier,
    onToggleState: () -> Unit,
    sunAlignment: Alignment,
    cloudyOutlierTopDp: Dp,
    cloudyOutlierBottomDp: Dp,
    circleAlignment: BiasAlignment,
    movingCloudyPadding: Dp,
    movingCloudyPositionX: Dp,
    movingCloudyPositionY: Dp,
    movingSmallCloudyPadding: Dp,
    circlePositionX: Dp,
    circlePositionY: Dp,
    isClickable: Boolean,
) {

    Box(
        modifier = Modifier
            .requiredSize(width = 64.dp, height = 36.dp)
            .background(
                color = Color(0xFF548EFE),
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
                .background(Theme.colors.primary, CircleShape)
                .offset(y = -(17).dp, x = 24.dp)
        )

        Box(
            modifier = Modifier
                .offset(y = -(3).dp, x = 12.33.dp)
                .background(Theme.colors.surfaceLow, CircleShape)
                .padding(cloudyOutlierTopDp)
                .clip(CircleShape)
                .align(Alignment.TopEnd)
        )
        Box(
            modifier = Modifier
                .offset(y = (8.dp - cloudyOutlierBottomDp), x = ((-8).dp - cloudyOutlierBottomDp))
                .background(Theme.colors.surfaceLow, CircleShape)
                .padding(12.dp)
                .clip(CircleShape)
                .align(Alignment.BottomEnd)

        )
        Box(
            modifier = modifier
                .offset(x = movingCloudyPositionX, y = movingCloudyPositionY)
                .background(Theme.colors.surfaceHigh, CircleShape)
                .padding(movingCloudyPadding)
                .clip(CircleShape)
                .align(Alignment.TopEnd)
        )
        Box(
            modifier = Modifier
                .offset(x = circlePositionX, y = circlePositionY)
                .background(Theme.colors.surfaceHigh, CircleShape)
                .padding(
                    horizontal = 7.dp,
                    vertical = 8.dp
                )
                .clip(CircleShape)
                .align(circleAlignment)
        )
        Box(
            modifier = Modifier
                .offset(y = 5.dp, x = -(1).dp)
                .background(Theme.colors.surfaceHigh, CircleShape)
                .padding(movingSmallCloudyPadding)
                .clip(CircleShape)
                .align(Alignment.BottomEnd)
        )

        Box(
            Modifier
                .padding(start = 2.dp)
                .size(32.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFFF2C849),
                            Color(0xFFF49061)
                        )
                    ),
                    shape = CircleShape
                )
                .blur(
                    radius = 12.dp,
                    edgeTreatment = BlurredEdgeTreatment(CircleShape)
                )
                .clip(CircleShape)
                .clickable(isClickable) { onToggleState() }
                .align(sunAlignment)
        )

        Box(
            Modifier
                .shadow(
                    elevation = 1.dp,
                    shape = CircleShape,
                    spotColor = Color(0xFFA3C2FF)
                )
                .blur(
                    radius = 2.dp,
                    edgeTreatment = BlurredEdgeTreatment(CircleShape)
                )
                .shadow(
                    elevation = 1.dp,
                    shape = CircleShape,
                    spotColor = Color(0xFF79A4FD)
                )
                .blur(
                    radius = 3.dp,
                    edgeTreatment = BlurredEdgeTreatment(CircleShape)
                )
        )
    }
}

@Preview
@Composable
private fun TudeeSwitchDayPreview() {
//    TudeeSwitchDay(
//        onToggleState = {},
////        moonAlpha = 0f,
//        isDay = false,
//        sunAlignment = Alignment.CenterStart,
//        alpha = 1f,
//        animateVisibilityScope = this
//    )

}