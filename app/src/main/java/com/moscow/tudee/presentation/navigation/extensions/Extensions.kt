package com.moscow.tudee.presentation.navigation.extensions

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
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

fun NavController.selectNavigationItem(route: Any) {
    navigateSafe(route) {
        popUpTo(graph.findStartDestination().id) {

            /**
             *  Preserves the state of the screen you're leaving.
             *  For example, if you're on a list screen and scroll down,
             *  then switch tabs and come back, you'll return to the same scroll position
             */

            saveState = true
        }

        /**
         * Prevents creating duplicate instances of the same destination.
         * If you're already on the "Home" tab and tap "Home" again,
         * it won't create a new Home screen instance
         */
        launchSingleTop = true

        /**
         * When returning to a previously visited tab,
         * it restores the saved state (scroll position, form data, etc.)
         */
        restoreState = true
    }
}
