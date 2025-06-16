package com.moscow.tudee.presentation.designSystem.component
import androidx.compose.runtime.Composable
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.LottieDynamicProperties
import com.airbnb.lottie.compose.LottieDynamicProperty
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.model.KeyPath
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.moscow.tudee.R

@Composable
fun AnimatedLoading(
    modifier: Modifier = Modifier,
    tintColor: Color
) {
    val preloaderLottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))

    val progress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )
    val dynamicProperties = remember(tintColor) {
        LottieDynamicProperties(
            listOf(
                LottieDynamicProperty(
                    value = tintColor.toArgb(),
                    property = LottieProperty.COLOR,
                    keyPath = KeyPath("**")
                )
            )
        )
    }
    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = { progress },
        modifier = modifier,
        dynamicProperties = dynamicProperties
    )
}