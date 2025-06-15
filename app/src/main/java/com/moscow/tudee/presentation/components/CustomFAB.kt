package com.moscow.tudee.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme

@Composable
fun CustomFAB(
    onClick: () -> Unit,
    isLoading: Boolean = false,
    isEnabled: Boolean = true,
    size: Dp = 64.dp,
) {
    val iconTint = if (!isEnabled)
         Theme.colors.stroke
        else  Theme.colors.onPrimary

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .then(
                if (isEnabled) {
                    Modifier
                        .shadow(
                            elevation = 4.dp,
                            shape = CircleShape,
                            ambientColor = Color(0x1F000000),
                            spotColor = Color(0x1F000000)
                        )
                        .background(brush = Theme.colors.primaryGradient, shape = CircleShape)
                } else {
                    Modifier.background(color = Theme.colors.disable, shape = CircleShape)
                }
            )
            .clickable(enabled = isEnabled && !isLoading, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            AnimatedLoading(
                modifier = Modifier
                    .padding(15.dp),
                Theme.colors.onPrimary
            )
        } else {
            Icon(
                painter = painterResource(R.drawable.download),
                contentDescription = null,
                tint = iconTint
            )
        }
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CustomFABPreview() {
    TudeeTheme {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomFAB(
                onClick = {}
            )
            CustomFAB(
                onClick = {},
                isLoading = true
            )
            CustomFAB(
                onClick = {},
                isEnabled = false
            )
        }
    }
}
