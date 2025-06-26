package com.moscow.tudee.presentation.component.topbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.component.tudeeSwitch.TudeeSwitch
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.typography.CherryBomb
import kotlinx.coroutines.delay

@Composable
fun HomeTopAppBar(
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier,
    showSnackbar: Boolean = false,
    snackbarMessage: String = "",
    snackbarIcon: Painter? = null,
    onSnackbarDismiss: () -> Unit = {},
) {
    val snackbarVisible = remember { mutableStateOf(false) }

    LaunchedEffect(showSnackbar, snackbarMessage) {
        if (showSnackbar && snackbarMessage.isNotEmpty()) {
            snackbarVisible.value = true

            delay(3000)
            snackbarVisible.value = false

            delay(300)
            onSnackbarDismiss()
        } else {
            snackbarVisible.value = false
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_header),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 8.dp)
            )

            Column(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                TudeeText(
                    text = title,
                    fontFamily = CherryBomb,
                    fontSize = 18.sp,
                    lineHeight = 18.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight(400),
                    color = Theme.colors.onPrimary.copy(.87f),
                )
                TudeeText(
                    text = subTitle,
                    color = Theme.colors.onPrimaryCaption.copy(.70f),
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight(500),
                    style = Theme.textStyle.label.small,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            TudeeSwitch()
        }

        AnimatedVisibility(
            visible = snackbarVisible.value,
            enter = slideInVertically(
                initialOffsetY = { -it - 50 },
                animationSpec = tween(400, easing = FastOutSlowInEasing)
            ) + fadeIn(
                animationSpec = tween(400, easing = FastOutSlowInEasing)
            ),
            exit = slideOutVertically(
                targetOffsetY = { -it - 50 },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeOut(
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(1f)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Theme.colors.surface
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (snackbarIcon != null) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    color = Theme.colors.greenVariant,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = snackbarIcon,
                                contentDescription = null,
                                tint = Theme.colors.greenAccent,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                    }

                    TudeeText(
                        text = snackbarMessage,
                        style = Theme.textStyle.body.medium,
                        color = Theme.colors.title,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}