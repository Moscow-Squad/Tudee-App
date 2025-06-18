package com.moscow.tudee.presentation.designSystem.component.tudeeSwitch

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.designSystem.theme.Theme
import kotlinx.coroutines.launch

@Stable
@Composable
fun TudeeSwitchLightTheme(
    modifier: Modifier = Modifier,
    onToggleState: () -> Unit,
    isClickable: Boolean,
    isLightTheme: Boolean,
    transition: Transition<Boolean>,
    transitionDpAnimationSpec: TweenSpec<Dp>,
    transitionFloatAnimationSpec: TweenSpec<Float>
) {
    val cloudyOutlierTopDp by transition.animateDp(
        transitionSpec = {
            transitionDpAnimationSpec
        },
        label = "cloudyOutlierTopDp"
    ) {
        if (it) 16.dp
        else 0.dp
    }

    val cloudyOutlierBottomDp by transition.animateDp(
        transitionSpec = {
            transitionDpAnimationSpec
        },
        label = "cloudyOutlierBottomDp"
    ) {
        if (it) 0.dp else -(8).dp
    }

    val movingCloudyPadding by transition.animateDp(
        transitionSpec = {
            transitionDpAnimationSpec
        },
        label = "movingCloudyPadding"
    ) {
        if (it) 14.5.dp else 4.dp
    }

    val movingCloudyPositionX by transition.animateDp(
        transitionSpec = {
            transitionDpAnimationSpec
        },
        label = "movingCloudyPositionX"
    ) {
        if (it) 13.5.dp else (-17).dp
    }

    val movingCloudyPositionY by transition.animateDp(
        transitionSpec = {
            transitionDpAnimationSpec
        },
        label = "movingCloudyPositionY"
    ) {
        if (it) 0.dp else 4.dp
    }


    val movingSmallCloudyPadding by transition.animateDp(
        transitionSpec = {
            transitionDpAnimationSpec
        },
        label = "movingSmallCloudyPadding"
    ) {
        if (it) 8.dp else 0.dp
    }

    val circleBiasAlignment by remember {
        derivedStateOf { (if (isLightTheme) Alignment.BottomEnd else Alignment.BottomCenter) as BiasAlignment }
    }

    val circleHorizontalBias by transition.animateFloat(
        transitionSpec = {
            transitionFloatAnimationSpec
        },
        label = "horizontalBias"
    ) {
        if (it) circleBiasAlignment.horizontalBias
        else -circleBiasAlignment.horizontalBias
    }
    val circleAlignment by remember {
        derivedStateOf {
            BiasAlignment(circleHorizontalBias, circleBiasAlignment.verticalBias)
        }
    }

    //400
    val circlePositionX by transition.animateDp(
        transitionSpec = {
            transitionDpAnimationSpec
        },
        label = "circleSize"
    ) {
        if (it) (-14.5).dp else 0.dp
    }

    val circlePositionY by transition.animateDp(
        transitionSpec = {
            transitionDpAnimationSpec
        },
        label = "circleSize"
    ) {
        if (it) 4.dp else 26.dp
    }

    val coroutineScope = rememberCoroutineScope()

    val sunBiasAlignment = remember(isLightTheme) {
        Animatable(-1f)
    }

    val sunAlignment by remember {
        derivedStateOf {
            BiasAlignment(sunBiasAlignment.value, 0f)
        }
    }

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
                .offset(y = -(17).dp, x = -(24).dp)
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
                .offset {
                    IntOffset(y = (8.dp - cloudyOutlierBottomDp).roundToPx(), x = ((-8).dp - cloudyOutlierBottomDp).roundToPx())
                }
                .background(Theme.colors.surfaceLow, CircleShape)
                .padding(12.dp)
                .clip(CircleShape)
                .align(Alignment.BottomEnd)

        )
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(x = circlePositionX.roundToPx(), y = circlePositionY.roundToPx())
                }
//                .offset(x = circlePositionX, y = circlePositionY)
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
                .clickable(isClickable) {
                    coroutineScope.launch {
                        sunBiasAlignment.animateTo(
                            targetValue = 1f,
                            animationSpec = transitionFloatAnimationSpec
                        )
                    }
                    onToggleState()
                }
                .align(sunAlignment)
        )

        Box(
            modifier = Modifier
                .offset {
                    IntOffset(x = movingCloudyPositionX.roundToPx(), y = movingCloudyPositionY.roundToPx())
                }
//                .offset(x = movingCloudyPositionX, y = movingCloudyPositionY)
                .background(Theme.colors.surfaceHigh, CircleShape)
                .padding(movingCloudyPadding)
                .clip(CircleShape)
                .align(Alignment.TopEnd)

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