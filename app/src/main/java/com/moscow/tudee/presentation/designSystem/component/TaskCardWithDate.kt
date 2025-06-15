package com.moscow.tudee.presentation.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun TaskCardWithDate(
    icon: Painter,
    title: String,
    description: String,
    date: String,
    iconTint: Color = Theme.colors.purpleAccent,
    modifier: Modifier = Modifier,
    priorityChip: @Composable () -> Unit
) {
    BaseTaskCardContent(
        icon = icon,
        iconTint = iconTint,
        title = title,
        description = description,
        modifier = modifier,
        headerContent = {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .background(Theme.colors.surface)
                    .padding(vertical = 6.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.calendar_favorite_01),
                    contentDescription = "calendar icon",
                    tint = Theme.colors.body,
                    modifier = Modifier
                        .size(12.dp)
                )
                Text(
                    text = date,
                    style = Theme.textStyle.label.small,
                    color = Theme.colors.body,
                )
            }
            priorityChip()
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TaskCardPreviews() {
    TudeeTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            TaskCardWithDate(
                icon = painterResource(id = R.drawable.quran_01),
                title = "Organize Study Desk",
                description = "Review cell structure and functions for tomorrow...",
                date = "12-03-2025",
                iconTint = Theme.colors.pinkAccent,
                priorityChip = { MediumPriorityChip() }
            )
        }
    }
}
