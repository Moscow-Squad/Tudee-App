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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import kotlinx.coroutines.delay

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    navButton: Boolean = false,
) {
    val themeColors = Theme.colors
    val backGroundColor: Brush = remember(isEnabled) {
        if (!isEnabled)
            SolidColor(themeColors.disable)
        else {
            if (navButton) SolidColor(themeColors.errorVariant) else themeColors.primaryGradient
        }
    }
    val textColor = remember(isEnabled) {
        if (!isEnabled)
            themeColors.stroke
        else
            if (navButton) themeColors.error else themeColors.onPrimary
    }
    Box(
        modifier = modifier
            .height(56.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(360.dp),
                ambientColor = Color(0x1F000000),
                spotColor = Color(0x1F000000)
            )
            .clip(RoundedCornerShape(360.dp))
            .background(brush = backGroundColor, RoundedCornerShape(360.dp))
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
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
            Crossfade(
                targetState = isLoading,
                animationSpec = tween(durationMillis = 1000), label = "primary button animation"
            ) { isLoading ->
                if (isLoading) {
                    AnimatedLoading(
                        modifier = Modifier.size(48.dp),
                        tintColor = if (navButton) themeColors.error else Theme.colors.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
) {
    val themeColors = Theme.colors
    val textColor = remember(isEnabled) {
        if (!isEnabled)
            themeColors.stroke
        else
            themeColors.primary
    }

    Box(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(360.dp))
            .border(1.dp, themeColors.stroke, RoundedCornerShape(360.dp))
            .background(Color.Transparent, RoundedCornerShape(360.dp))
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
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Crossfade(
                targetState = isLoading,
                animationSpec = tween(durationMillis = 1000), label = "primary button animation"
            ) { isLoading ->
                if (isLoading) {
                    AnimatedLoading(
                        modifier = Modifier.size(48.dp),
                        tintColor = Theme.colors.primary
                    )
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
    navTextButton: Boolean = false,
) {
    val themeColors = Theme.colors
    val textColor = remember(isEnabled) {
        if (!isEnabled)
            themeColors.disable
        else {
            if (navTextButton) themeColors.error else themeColors.primary
        }
    }
    Box(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(360.dp))
            .background(Color.Transparent, RoundedCornerShape(360.dp))
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
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Crossfade(
                targetState = isLoading,
                animationSpec = tween(durationMillis = 1000), label = ""
            ) { loading ->
                if (loading) {
                    AnimatedLoading(
                        modifier = Modifier.size(48.dp),
                        tintColor = if (navTextButton) Theme.colors.error else Theme.colors.primary
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
                PrimaryButton(
                    text = "Submit",
                    onClick = {},
                    isEnabled = false
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
                    navButton = true,
                    isLoading = true
                )
                PrimaryButton(
                    text = "Submit",
                    onClick = {},
                    navButton = true,
                )
                PrimaryButton(
                    text = "Submit",
                    onClick = {},
                    navButton = true,
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
                    isEnabled = false
                )
                SecondaryButton(
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
            )
            CustomTextButton(
                text = "Submit",
                onClick = {},
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
                navTextButton = true,
            )
            CustomTextButton(
                text = "Submit",
                onClick = {},
                navTextButton = true,
                isLoading = true
            )
                CustomTextButton(
                    text = "Submit",
                    onClick = {},
                    navTextButton = true,
                    isEnabled = false
                )
            }
        }
    }
}
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PrimaryButtonAnimatedPreview() {
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1500)
        isLoading = true
    }

    TudeeTheme {
        PrimaryButton(
            onClick = {},
            text = "Submit",
            isLoading = isLoading
        )
    }
}

