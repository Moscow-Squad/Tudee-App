package com.moscow.tudee.presentation.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
fun Category(
    icon: Painter,
    label: String,
    count: Int,
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
                    .clip(RoundedCornerShape(100))
                    .background(Theme.colors.surfaceLow)
                    .width(36.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (count > 100) "+100" else count.toString(),
                    style = Theme.textStyle.label.small,
                    color = Theme.colors.hint,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun CategoryPreview() {
    val icon = painterResource(id = R.drawable.shopping_cart_02)
    TudeeTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(16.dp)
        ) {
            Category(
                icon = icon,
                label = "Shopping",
                count = 23,
                iconTint = Theme.colors.secondary
            )

            Category(
                icon = icon,
                label = "Study",
                count = 122,
                iconTint = Theme.colors.purpleAccent
            )

            Category(
                icon = icon,
                label = "Health",
                count = 5,
                iconTint = Theme.colors.greenAccent
            )
        }
    }

}
