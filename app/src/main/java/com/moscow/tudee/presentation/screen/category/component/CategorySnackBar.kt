package com.moscow.tudee.presentation.screen.category.component

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.screen.category.CategoriesScreenState
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

@Composable
private fun SnackBarContent(
    icon: Painter,
    message: String,
    iconBackground: Color,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .statusBarsPadding()
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Theme.colors.surfaceHigh)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = stringResource(R.string.status_icon),
            tint = iconTint,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconBackground)
                .padding(8.dp)
                .size(24.dp)
        )

        Text(
            text = message,
            style = Theme.textStyle.body.medium,
            color = Theme.colors.body
        )
    }
}

@Composable
private fun SuccessSnackBar(
    message: String,
    modifier: Modifier = Modifier
) {
    SnackBarContent(
        icon = painterResource(id = R.drawable.ic_checkmark_badge),
        message = message,
        iconBackground = Theme.colors.greenVariant,
        iconTint = Theme.colors.greenAccent,
        modifier = modifier
    )
}

@Composable
private fun ErrorSnackBar(
    message: String,
    modifier: Modifier = Modifier
) {
    SnackBarContent(
        icon = painterResource(id = R.drawable.ic_information_diamond),
        message = message,
        iconBackground = Theme.colors.errorVariant,
        iconTint = Theme.colors.error,
        modifier = modifier
    )
}