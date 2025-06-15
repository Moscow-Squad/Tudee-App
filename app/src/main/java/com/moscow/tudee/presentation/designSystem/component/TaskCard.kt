package com.moscow.tudee.presentation.designSystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme

@Composable
fun TaskCard(
    icon: Painter,
    title: String,
    description: String,
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
            priorityChip()
        }
    )
}


@Preview(showBackground = true, widthDp = 330)
@Composable
fun TaskCardPreview() {
    TudeeTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            val icon = painterResource(id = R.drawable.quran_01)

            TaskCard(
                icon = icon,
                title = "Organize Study Desk",
                description = "Review cell structure and functions for tomorrow...",
                iconTint = Theme.colors.primary,
                priorityChip = { MediumPriorityChip() }
            )

            TaskCard(
                icon = icon,
                title = "Review Flashcards",
                description = "Study biology flashcards for 15 minutes",
                iconTint = Theme.colors.secondary,
                priorityChip = { HighPriorityChip() }
            )

            TaskCard(
                icon = icon,
                title = "Take a break",
                description = "Go for a walk outside",
                iconTint = Theme.colors.yellowAccent,
                priorityChip = { LowPriorityChip() }
            )
        }
    }
}

