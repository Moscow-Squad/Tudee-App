package com.moscow.tudee.presentation.navigation.extensions

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable

fun <T : Any> NavController?.navigateSafe(
    route: T,
    builder: NavOptionsBuilder.() -> Unit,
) {
    if (this == null) return
    try {
        navigate(route, builder)
    } catch (e: Exception) {
        Log.e("Navigation Error",e.message.toString(),e)
    }
}

const val ANIMATION_DURATION = 800

inline fun <reified T : Any> NavGraphBuilder.tudeeComposable(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable<T>(
        enterTransition = { fadeIn(tween(ANIMATION_DURATION)) },
        exitTransition = { fadeOut(tween(ANIMATION_DURATION)) },
        popEnterTransition = { fadeIn(tween(ANIMATION_DURATION)) },
        popExitTransition = { fadeOut(tween(ANIMATION_DURATION)) },
        content = content,
    )
}
