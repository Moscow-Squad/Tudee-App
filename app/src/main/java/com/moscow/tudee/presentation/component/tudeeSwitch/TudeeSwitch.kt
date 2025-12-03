package com.moscow.tudee.presentation.component.tudeeSwitch

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.ThemeState
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TudeeSwitch(
    modifier: Modifier = Modifier,
) {
    val themeState = Theme.state
    val isLightTheme = !themeState.isDark
    var isClickable by remember { mutableStateOf(true) }

    val transition = updateTransition(
        targetState = isLightTheme,
        label = "switchButtonTransition"
    )

    val transitionFloatAnimationSpec = remember {
        tween<Float>(600, easing = EaseOut)
    }

    val transitionDpAnimationSpec = remember {
        tween<Dp>(600, easing = EaseOut)
    }


    val movingCloudySize by transition.animateDp(
        transitionSpec = { transitionDpAnimationSpec },
        label = "movingCloudySize"
    ) {
        if (it) 29.dp else 8.dp
    }


    val outerTopCloudSize by transition.animateDp(
        transitionSpec = { transitionDpAnimationSpec },
        label = "cloudyOutlierTopDp"
    ) {
        if (it) 32.dp else 0.dp
    }

    val cloudyOutlierBottomDp by transition.animateDp(
        transitionSpec = { transitionDpAnimationSpec },
        label = "cloudyOutlierBottomDp"
    ) {
        if (it) 0.dp else -(8).dp
    }

    val movingCloudyPositionX by transition.animateDp(
        transitionSpec = { transitionDpAnimationSpec },
        label = "movingCloudyPositionX"
    ) {
        if (it) 13.5.dp else (-17).dp
    }

    val movingCloudyPositionY by transition.animateDp(
        transitionSpec = { transitionDpAnimationSpec },
        label = "movingCloudyPositionY"
    ) {
        if (it) 0.dp else 4.dp
    }


    val movingSmallCloudySize by transition.animateDp(
        transitionSpec = { transitionDpAnimationSpec },
        label = "movingSmallCloudySize"
    ) {
        if (it) 16.dp else 0.dp
    }

    val circleHorizontalBias by transition.animateFloat(
        transitionSpec = { transitionFloatAnimationSpec },
        label = "horizontalBias"
    ) {
        if (it) 1f else -1f
    }

    val circleAlignment by remember {
        derivedStateOf {
            BiasAlignment(circleHorizontalBias, 1f)
        }
    }

    val circlePositionX by transition.animateDp(
        transitionSpec = { transitionDpAnimationSpec },
        label = "circleSizeX"
    ) {
        if (it) -(14.5).dp else -(14.5).dp
    }

    val circlePositionY by transition.animateDp(
        transitionSpec = { transitionDpAnimationSpec },
        label = "circleSizeY"
    ) {
        if (it) 4.dp else 26.dp
    }

    val sunBiasAlignment = remember {
        Animatable(if (isLightTheme) -1f else 1f)
    }

    val moonBiasAlignment = remember {
        Animatable(if (isLightTheme) 1f else -1f)
    }


    LaunchedEffect(isLightTheme) {
        val startSunBiasAlignment = -1f
        val startMoonBiasAlignment = 1f
        isClickable = false

        if (!isLightTheme) {
            moonBiasAlignment.snapTo(startMoonBiasAlignment)
            sunBiasAlignment.animateTo(
                targetValue = -startSunBiasAlignment,
                animationSpec = transitionFloatAnimationSpec
            )
        } else {
            sunBiasAlignment.snapTo(startSunBiasAlignment)
            moonBiasAlignment.animateTo(
                targetValue = -startMoonBiasAlignment,
                animationSpec = transitionFloatAnimationSpec
            )
        }
        isClickable = true
    }



    Box(modifier) {
        transition.Crossfade(
            modifier = Modifier,
            animationSpec = transitionFloatAnimationSpec
        ) {
            if (it) {
                TudeeSwitchLightTheme(
                    isClickable = isClickable,
                    onToggleState = { themeState.onThemeChanged(isLightTheme) },
                    movingCloudySize = movingCloudySize,
                    outerTopCloudSize = outerTopCloudSize,
                    cloudyOutlierBottomDp = cloudyOutlierBottomDp,
                    movingCloudyPositionX = movingCloudyPositionX,
                    movingCloudyPositionY = movingCloudyPositionY,
                    movingSmallCloudySize = movingSmallCloudySize,
                    circleAlignment = circleAlignment,
                    circlePositionX = circlePositionX,
                    circlePositionY = circlePositionY,
                    sunAlignment = BiasAlignment(sunBiasAlignment.value, 0f)
                )
            } else {
                TudeeSwitchDarkTheme(
                    moonAlignment = BiasAlignment(moonBiasAlignment.value, 0f),
                    isClickable = isClickable,
                    onToggleState = { themeState.onThemeChanged(isLightTheme) },
                    movingCloudySize = movingCloudySize
                )
            }
        }
    }
}


@Preview
@Composable
private fun TudeeSwitchPreview() {
    val isDark = isSystemInDarkTheme()
    val (isDarkThemeState, onThemeStateChanged) = remember { mutableStateOf(isDark) }
    val themeState by remember(isDarkThemeState) {
        derivedStateOf {
            ThemeState(
                isDark = isDarkThemeState,
                onThemeChanged = onThemeStateChanged
            )
        }
    }

    TudeeTheme(themeState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.surface),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TudeeSwitch()
        }
    }
}