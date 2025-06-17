package com.moscow.tudee.tudeeSwitch

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TudeeSwitch(
    modifier: Modifier = Modifier,
    isDay: Boolean,
    onToggleState: () -> Unit,
) {
    val trans = updateTransition(
        targetState = isDay,
        label = "isDay"
    )

    val transitionFloatAnimationSpec = remember {
        tween<Float>(800, easing = EaseOut)
    }

    val transitionDpAnimationSpec = remember {
        tween<Dp>(800, easing = EaseOut)
    }

    val sunBiasAlignment by remember {
        derivedStateOf { (if (isDay) Alignment.CenterStart else Alignment.CenterEnd) as BiasAlignment }
    }
    val sunHorizontalBias by trans.animateFloat(
        transitionSpec = {
            transitionFloatAnimationSpec
        },
        label = "horizontalBias"
    ) {
        if (it) sunBiasAlignment.horizontalBias
        else -sunBiasAlignment.horizontalBias
    }

    val sunAlignment by remember(isDay) {
        derivedStateOf {
            if (!isDay) BiasAlignment(sunHorizontalBias, sunBiasAlignment.verticalBias)
            else Alignment.CenterStart
        }
    }

    val moonBiasAlignment by remember {
        derivedStateOf { (if (isDay) Alignment.CenterStart else Alignment.CenterEnd) as BiasAlignment }
    }
    val moonHorizontalBias by trans.animateFloat(
        transitionSpec = {
            transitionFloatAnimationSpec
        },
        label = "horizontalBias"
    ) {
        if (it) sunBiasAlignment.horizontalBias
        else -sunBiasAlignment.horizontalBias
    }

    val moonAlignment by remember(isDay) {
        derivedStateOf {
            if (!isDay) Alignment.CenterEnd
            else BiasAlignment(sunHorizontalBias, sunBiasAlignment.verticalBias)
        }
    }

    val circleBiasAlignment by remember {
        derivedStateOf { (if (isDay) Alignment.BottomEnd else Alignment.BottomCenter) as BiasAlignment }
    }

    val circleHorizontalBias by trans.animateFloat(
        transitionSpec = {
            transitionFloatAnimationSpec
        },
        label = "horizontalBias"
    ) {
        if (it) circleBiasAlignment.horizontalBias
        else - circleBiasAlignment.horizontalBias
    }
    val circleAlignment by remember {
        derivedStateOf {
            BiasAlignment(circleHorizontalBias, circleBiasAlignment.verticalBias)
        }
    }

    val circleSize by trans.animateDp(
        transitionSpec = {
            transitionDpAnimationSpec
        },
        label = "circleSize"
    ) {
        if (it) 0.dp else 16.dp
    }

    //400
    val circlePositionX by trans.animateDp(
        transitionSpec = {
            transitionDpAnimationSpec
        },
        label = "circleSize"
    ) {
        if (it) (-14.5).dp else 0.dp
    }

    val circlePositionY by trans.animateDp(
        transitionSpec = {
            transitionDpAnimationSpec
        },
        label = "circleSize"
    ) {
        if (it) 4.dp else 26.dp
    }
//300
    val cloudyOutlierTopDp by trans.animateDp(
        transitionSpec = {
            transitionDpAnimationSpec
        },
        label = "cloudyOutlierTopDp"
    ) {
        if (it) 16.dp
        else 0.dp
    }
    //300
    val cloudyOutlierBottomDp by trans.animateDp(
        transitionSpec = {
            transitionDpAnimationSpec
        },
        label = "cloudyOutlierBottomDp"
    ) {
        if (it) 0.dp else -(8).dp
    }

    val movingCloudyPadding by trans.animateDp(
        transitionSpec = {
            transitionDpAnimationSpec
        },
        label = "movingCloudyPadding"
    ) {
        if (it) 14.5.dp else 4.dp
    }

    val movingCloudyPositionX by trans.animateDp(
        transitionSpec = {
            transitionDpAnimationSpec
        },
        label = "movingCloudyPositionX"
    ) {
        if (it) 13.5.dp else (-20.5).dp
    }

    val movingCloudyPositionY by trans.animateDp(
        transitionSpec = {
            transitionDpAnimationSpec
        },
        label = "movingCloudyPositionY"
    ) {
        if (it) 0.dp else 4.dp
    }


    val movingSmallCloudyPadding by trans.animateDp(
        transitionSpec = {
            transitionDpAnimationSpec
        },
        label = "movingSmallCloudyPadding"
    ) {
        if (it) 8.dp else 0.dp
    }


    trans.Crossfade(
        modifier = modifier,
        animationSpec = transitionFloatAnimationSpec
    ) {
        if (it) {
            TudeeSwitchDay(
                onToggleState = onToggleState,
                isClickable = isDay,
                sunAlignment = sunAlignment,
                circleAlignment = circleAlignment,
                circlePositionX = circlePositionX,
                circlePositionY = circlePositionY,
                cloudyOutlierTopDp = cloudyOutlierTopDp,
                cloudyOutlierBottomDp = cloudyOutlierBottomDp,
                movingCloudyPadding = movingCloudyPadding,
                movingCloudyPositionX = movingCloudyPositionX,
                movingCloudyPositionY = movingCloudyPositionY,
                movingSmallCloudyPadding = movingSmallCloudyPadding
            )
        } else {
            TudeeSwitchNight(
                onToggleState = onToggleState,
                moonAlignment = moonAlignment,
                isClickable = !isDay
            )
        }
    }
}


@Preview
@Composable
private fun TudeeSwitchPreview() {
    var isDay by remember { mutableStateOf(false) }

    TudeeSwitch(
        modifier = Modifier,
        isDay = isDay,
        onToggleState = { isDay = !isDay }
    )

}