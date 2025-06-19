package com.moscow.tudee.presentation.designSystem.component.tudeeSwitch

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.designSystem.theme.Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Stable
@Composable
fun TudeeSwitchLightTheme(
    onToggleState: () -> Unit,
    isClickable: Boolean,
    transitionFloatAnimationSpec: TweenSpec<Float>,
    movingCloudySize: Dp,
    outerTopCloudSize: Dp,
    cloudyOutlierBottomDp: Dp,
    movingCloudyPositionX: Dp,
    movingCloudyPositionY: Dp,
    movingSmallCloudySize: Dp,
    circleAlignment: BiasAlignment,
    circlePositionX: Dp,
    circlePositionY: Dp,
    surfaceLow: Color = Color(0xFFF0F0F0)
) {

    val coroutineScope = rememberCoroutineScope()

    val sunBiasAlignment = remember {
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
                .background(surfaceLow, CircleShape)
                .size(outerTopCloudSize)
                .animateContentSize()
                .clip(CircleShape)
                .align(Alignment.TopEnd)
        )
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        y = (8.dp - cloudyOutlierBottomDp).roundToPx(),
                        x = ((-8).dp - cloudyOutlierBottomDp).roundToPx()
                    )
                }
                .background(surfaceLow, CircleShape)
                .size(24.dp)
                .clip(CircleShape)
                .align(Alignment.BottomEnd)

        )
        Box(
            modifier = Modifier
                .align(circleAlignment)
                .offset {
                    IntOffset(x = circlePositionX.roundToPx(), y = circlePositionY.roundToPx())
                }
                .background(Theme.colors.surfaceHigh, CircleShape)
                .size(
                    width = 14.dp,
                    height = 16.dp
                )
                .clip(CircleShape)
        )
        Box(
            modifier = Modifier
                .offset(y = 5.dp, x = -(1).dp)
                .background(Color.White, CircleShape)
                .size(movingSmallCloudySize)
                .animateContentSize()
                .clip(CircleShape)
                .align(Alignment.BottomEnd)
        )

        Box(
            Modifier
                .padding(start = 2.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFFF2C849),
                            Color(0xFFF49061)
                        )
                    ),
                    shape = CircleShape
                )
                .size(32.dp)
                .blur(
                    radius = 12.dp,
                    edgeTreatment = BlurredEdgeTreatment(CircleShape)
                )
                .clip(CircleShape)
                .clickable(isClickable) {
                    coroutineScope.launch(Dispatchers.Main.immediate) {
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
                    IntOffset(
                        x = movingCloudyPositionX.roundToPx(),
                        y = movingCloudyPositionY.roundToPx()
                    )
                }
                .background(Color.White, CircleShape)
                .size(movingCloudySize)
                .animateContentSize(
                    animationSpec = tween(800),
                    alignment = Alignment.TopEnd
                )
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