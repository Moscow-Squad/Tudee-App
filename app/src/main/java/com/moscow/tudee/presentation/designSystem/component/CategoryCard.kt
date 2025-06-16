package com.moscow.tudee.presentation.designSystem.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme

@Composable
fun CategoryCard(
    icon: Painter,
    label: String,
    count: Int? = null,
    selected: Boolean = false,
    iconTint: androidx.compose.ui.graphics.Color = Theme.colors.greenAccent,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .animateContentSize(),
            contentAlignment = Alignment.TopEnd
        ) {
            Icon(
                painter = icon,
                contentDescription = stringResource(R.string.category_icon),
                tint = iconTint,
                modifier = Modifier
                    .size(78.dp)
                    .clip(RoundedCornerShape(88.dp))
                    .background(Theme.colors.surfaceHigh)
                    .padding(23.dp)
                    .size(32.dp)
            )

            androidx.compose.animation.AnimatedVisibility(
                visible = selected,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_tick_double),
                    contentDescription = stringResource(R.string.selected_icon),
                    tint = Theme.colors.onPrimary,
                    modifier = Modifier
                        .padding(2.dp)
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Theme.colors.greenAccent)
                        .padding(4.dp)
                        .size(12.dp)
                )
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = !selected && (count ?: 0) > 0,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = if ((count ?: 0) > 100) stringResource(R.string.greater_than_100) else (count ?: 0).toString(),
                    style = Theme.textStyle.label.small,
                    color = Theme.colors.hint,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(36.dp)
                        .clip(RoundedCornerShape(100))
                        .background(Theme.colors.surfaceLow)
                        .padding(vertical = 2.dp)
                )
            }
        }

        Text(
            text = label,
            style = Theme.textStyle.label.small,
            color = Theme.colors.body
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CategoryItem_NoBadge_Preview() {
    TudeeTheme {
        CategoryCard(
            icon = painterResource(id = R.drawable.ic_shopping_cart),
            label = "Shopping",
            count = null,
            selected = false,
            iconTint = Theme.colors.secondary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryItem_Count_Preview() {
    TudeeTheme {
        CategoryCard(
            icon = painterResource(id = R.drawable.ic_book_open),
            label = "Study",
            count = 23,
            selected = false,
            iconTint = Theme.colors.purpleAccent
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryItem_Selected_Preview() {
    TudeeTheme {
        CategoryCard(
            icon = painterResource(id = R.drawable.ic_chef),
            label = "Health",
            count = 5,
            selected = true,
            iconTint = Theme.colors.greenAccent
        )
    }
}
