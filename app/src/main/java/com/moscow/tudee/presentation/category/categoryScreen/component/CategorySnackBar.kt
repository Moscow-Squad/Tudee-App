package com.moscow.tudee.presentation.category.categoryScreen.component

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.moscow.tudee.presentation.category.CategoriesScreenState
import com.moscow.tudee.presentation.designSystem.component.ErrorSnackBar
import com.moscow.tudee.presentation.designSystem.component.SuccessSnackBar
import kotlinx.coroutines.delay
@Composable
fun CategorySnackBar(
    uiState: CategoriesScreenState,
    onHide: () -> Unit
) {
    val messageId = uiState.successMessage ?: uiState.errorMessage
    val message = messageId?.let { stringResource(id = it) } ?: return
    var localVisible by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isSnackBarShow) {
        if (uiState.isSnackBarShow) {
            localVisible = true
            delay(500 + 3000)
            localVisible = false
            delay(300)
            onHide()
        } else localVisible = false
    }

    StartAnimationFromTopToBottomWithBounce(localVisible, uiState, message)
}

@Composable
private fun StartAnimationFromTopToBottomWithBounce(
    localVisible: Boolean,
    uiState: CategoriesScreenState,
    message: String
) {
    AnimatedVisibility(
        visible = localVisible,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        ),
        exit = fadeOut(tween(300))
    ) {
        if (uiState.successMessage != null) {
            SuccessSnackBar(message = message)
        } else {
            ErrorSnackBar(message = message)
        }
    }
}
