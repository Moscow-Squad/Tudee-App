package com.moscow.tudee.presentation.component

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    backgroundColor : Brush = Theme.colors.primaryGradient,
    textColor : Color = Theme.colors.onPrimary,
    tintColor : Color = Theme.colors.onPrimary,
) {
    Box(
        modifier = modifier
            .height(56.dp)
            .shadow(
                elevation = 4.dp,
                shape = CircleShape,
                ambientColor = Color(0x1F000000),
                spotColor = Color(0x1F000000)
            )
            .clip(CircleShape)
            .background(brush = backgroundColor, CircleShape)
            .clickable(
                enabled = isEnabled && !isLoading,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        ) {
            Text(
                text,
                style = Theme.textStyle.label.large,
                color = textColor,
            )
            Crossfade(
                targetState = isLoading,
                animationSpec = tween(durationMillis = 1000), label = "primary button animation"
            ) { isLoading ->
                if (isLoading) {
                    AnimatedLoading(
                        modifier = Modifier.size(48.dp),
                        tintColor = tintColor
                    )
                }
            }
        }
    }
}

@Composable
fun SecondaryButton(
    text: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    icon: Int? = null,
    textColor : Color = Theme.colors.primary,
    tintColor : Color = Theme.colors.primary,
) {
    val themeColors = Theme.colors
    Box(
        modifier = modifier
            .height(56.dp)
            .clip(CircleShape)
            .border(1.dp, themeColors.stroke, CircleShape)
            .background(Color.Transparent, CircleShape)
            .clickable(
                enabled = isEnabled && !isLoading,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)

        ) {
            if (icon != null) {
                Icon(
                    painterResource(id =icon),
                    contentDescription = "icon",
                    tint = themeColors.primary,
                    modifier = modifier
                )
            } else {
                Text(
                    text ?: "",
                    style = Theme.textStyle.label.large,
                    color = textColor,
                )
                Crossfade(
                    targetState = isLoading,
                    animationSpec = tween(durationMillis = 1000), label = "primary button animation"
                ) { isLoading ->
                    if (isLoading) {
                        AnimatedLoading(
                            modifier = Modifier.size(48.dp),
                            tintColor = tintColor
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun CustomTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    textColor : Color = Theme.colors.primary,
    tintColor : Color = Theme.colors.primary,
) {
    Box(
        modifier = modifier
            .height(56.dp)
            .clip(CircleShape)
            .background(Color.Transparent, CircleShape)
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .clickable(
                enabled = isEnabled && !isLoading,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text,
                style = Theme.textStyle.label.large,
                color = textColor,
            )
            Crossfade(
                targetState = isLoading,
                animationSpec = tween(durationMillis = 1000), label = ""
            ) { loading ->
                if (loading) {
                    AnimatedLoading(
                        modifier = Modifier.size(48.dp),
                        tintColor = tintColor
                    )
                }
            }
        }
    }
}
@Preview(apiLevel = 33, showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Preview(apiLevel = 33, showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NormalButtonPreview() {
    TudeeTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.surface),
            verticalArrangement = Arrangement.spacedBy(3.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                PrimaryButton(
                    text = "Submit",
                    onClick = {},
                )
                PrimaryButton(
                    text = "Submit",
                    onClick = {},
                    isLoading = true
                )
            }
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                PrimaryButton(
                    text = "Submit",
                    onClick = {},
                    textColor = Theme.colors.error,
                    tintColor = Theme.colors.error,
                    backgroundColor = SolidColor(Theme.colors.errorVariant)
                )
                PrimaryButton(
                    text = "Submit",
                    onClick = {},
                    textColor = Theme.colors.error,
                    tintColor = Theme.colors.error,
                    backgroundColor = SolidColor(Theme.colors.errorVariant),
                    isLoading = true
                )

            }
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
            PrimaryButton(
                text = "Submit",
                onClick = {},
                textColor = Theme.colors.stroke,
                backgroundColor = SolidColor(Theme.colors.disable),
                isEnabled = false
            )
            }
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SecondaryButton(
                    text = "Submit",
                    onClick = {},
                )
                SecondaryButton(
                    text = "Submit",
                    onClick = {},
                    isEnabled = false,
                    textColor = Theme.colors.stroke,
                )
                SecondaryButton(
                    text = "Submit",
                    onClick = {},
                    isLoading = true,
                )
            }
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
            CustomTextButton(
                text = "Submit",
                onClick = {},
            )
            CustomTextButton(
                text = "Submit",
                onClick = {},
                textColor = Theme.colors.stroke,
                isEnabled = false
            )
            CustomTextButton(
                text = "Submit",
                onClick = {},
                isLoading = true
            )
            }
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
            CustomTextButton(
                text = "Submit",
                onClick = {},
                isLoading = true,
                textColor = Theme.colors.error,
                tintColor = Theme.colors.error,
            )
            CustomTextButton(
                text = "Submit",
                onClick = {},
                isEnabled = false,
                textColor = Theme.colors.error,
                tintColor = Theme.colors.error,
            )
                CustomTextButton(
                    text = "Submit",
                    onClick = {},
                    textColor = Theme.colors.stroke,
                    isEnabled = false
                )
            }
            SecondaryButton(
                text = null,
                onClick = {},
                isEnabled = false,
                icon = R.drawable.download
            )
        }
    }
}


