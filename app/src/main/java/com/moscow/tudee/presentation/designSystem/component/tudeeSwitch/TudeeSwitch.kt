package com.moscow.tudee.presentation.designSystem.component.tudeeSwitch

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TudeeSwitch(
    modifier: Modifier = Modifier,
    isLightTheme: Boolean,
    onToggleState: () -> Unit
) {
    val transition = updateTransition(
        targetState = isLightTheme,
        label = "isDay"
    )

    val transitionFloatAnimationSpec = remember {
        tween<Float>(800, easing = EaseOut)
    }

    val transitionDpAnimationSpec = remember {
        tween<Dp>(800, easing = EaseOut)
    }

//    transition.AnimatedContent(
//        modifier = modifier,
//        transitionSpec = {
//            fadeIn(transitionFloatAnimationSpec)  togetherWith  fadeOut(transitionFloatAnimationSpec)
//        }
//    ) {
//        if (it) {
//            TudeeSwitchLightTheme(
//                transition = transition,
//                transitionDpAnimationSpec = transitionDpAnimationSpec,
//                transitionFloatAnimationSpec = transitionFloatAnimationSpec,
//                isClickable = isLightTheme,
//                isLightTheme = isLightTheme,
//                onToggleState = onToggleState
//            )
//        } else {
//            TudeeSwitchNight(
//                isClickable = !isLightTheme,
//                onToggleState = onToggleState,
//                transitionFloatAnimationSpec = transitionFloatAnimationSpec
//
//            )
//        }
//    }

    Box(modifier) {
        AnimatedVisibility(
            modifier = modifier,
            visible = isLightTheme,
            enter = fadeIn(transitionFloatAnimationSpec),
            exit = fadeOut(transitionFloatAnimationSpec),
            label = "lightTheme"
        ) {
            TudeeSwitchLightTheme(
                transition = transition,
                transitionDpAnimationSpec = transitionDpAnimationSpec,
                transitionFloatAnimationSpec = transitionFloatAnimationSpec,
                isClickable = isLightTheme,
                isLightTheme = isLightTheme,
                onToggleState = onToggleState
            )
        }
        AnimatedVisibility(
            modifier = modifier,
            visible = !isLightTheme,
            enter = fadeIn(transitionFloatAnimationSpec),
            exit = fadeOut(transitionFloatAnimationSpec),
            label = "darkTheme"
        ) {
            TudeeSwitchDarkTheme(
                isClickable = !isLightTheme,
                onToggleState = onToggleState,
                transitionFloatAnimationSpec = transitionFloatAnimationSpec

            )
        }
    }
}


@Preview
@Composable
private fun TudeeSwitchPreview() {
    var isDay by remember { mutableStateOf(true) }

    TudeeSwitch(
        modifier = Modifier.padding(20.dp),
        isLightTheme = isDay,
        onToggleState = { isDay = !isDay }
    )

}