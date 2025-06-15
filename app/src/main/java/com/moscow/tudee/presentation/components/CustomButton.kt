package com.moscow.tudee.presentation.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme

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
                fontWeight = FontWeight.Medium
            )
            if (isLoading) {
                AnimatedLoading(
                    modifier = Modifier,
                    tintColor = if (navButton) themeColors.error else Theme.colors.onPrimary
                )
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
            if (isLoading) {
                AnimatedLoading(
                    modifier = Modifier,
                    tintColor = Theme.colors.primary
                )
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
            if (isLoading) {
                AnimatedLoading(
                    modifier = Modifier,
                    tintColor = if (navTextButton) Theme.colors.error else Theme.colors.primary
                )
            }
        }
    }
}

@Preview
@Composable
private fun NormalButtonPreview() {
    TudeeTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(3.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
            PrimaryButton(
                text = "Submit",
                onClick = {},
                navButton = true
            )
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
                isEnabled = false
            )
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
            CustomTextButton(
                text = "Submit",
                onClick = {},
            )
            CustomTextButton(
                text = "Submit",
                onClick = {},
                navTextButton = true
            )
            CustomTextButton(
                text = "Submit",
                onClick = {},
                navTextButton = true,
                isEnabled = true
            )
            CustomTextButton(
                text = "Submit",
                onClick = {},
                navTextButton = true,
                isLoading = true
            )
        }
    }
}

