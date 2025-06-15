package com.moscow.tudee.presentation.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme


@Composable
fun SnackBar(
    icon: Painter,
    message: String,
    iconBackground: Color,
    iconTint: Color = Theme.colors.onPrimary,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(start = 8.dp)
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = "status icon",
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = message,
            style = Theme.textStyle.body.medium,
            color = Theme.colors.body
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomSnackBarPreview() {
    TudeeTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            SnackBar(
                icon = painterResource(id = R.drawable.information_diamond),
                message = "Some error happened",
                iconBackground = Theme.colors.errorVariant,
                iconTint = Theme.colors.error
            )

            SnackBar(
                icon = painterResource(id = R.drawable.checkmark_badge_01),
                message = "Successfully.",
                iconBackground = Theme.colors.greenVariant,
                iconTint = Theme.colors.greenAccent
            )
        }
    }
}


