package com.moscow.tudee.presentation.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme

@Composable
fun SelectedCategory(
    icon: Painter,
    label: String,
    iconTint: Color = Theme.colors.greenAccent,
    modifier: Modifier = Modifier
) {
    BaseCategory(
        icon = icon,
        label = label,
        iconTint = iconTint,
        modifier = modifier,
        badge = {
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(RoundedCornerShape(100))
                    .background(Theme.colors.greenAccent)
                    .size(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.tick_double_02),
                    contentDescription = "selected tick",
                    tint = Theme.colors.onPrimary,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun SelectedCategoryPreview() {
    val icon = painterResource(id = R.drawable.shopping_cart_02)
    TudeeTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(16.dp)
        ) {
            SelectedCategory(
                icon = icon,
                label = "Shopping",
                iconTint = Theme.colors.secondary
            )

            SelectedCategory(
                icon = icon,
                label = "Study",
                iconTint = Theme.colors.purpleAccent
            )

            SelectedCategory(
                icon = icon,
                label = "Health",
                iconTint = Theme.colors.greenAccent
            )
        }
    }

}
