package com.moscow.tudee.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.theme.Theme


@Composable
fun TaskStatus(
    modifier: Modifier = Modifier,
    status: String,
    statusColor: Color,
    backgroundColor: Color,
    dotColor: Color
) {

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .padding(
                vertical = 6.dp,
                horizontal = 12.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(5.dp)
                    .background(dotColor)
            )

            TudeeText(
                text = status,
                style = Theme.textStyle.label.small,
                color = statusColor
            )
        }
    }
}

@Preview
@Composable
private fun TaskStatusComponentPreview() {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        TaskStatus(
            modifier = Modifier,
            status = "In progress",
            backgroundColor = Theme.colors.purpleVariant,
            statusColor = Theme.colors.purpleAccent,
            dotColor = Theme.colors.purpleAccent
        )

        TaskStatus(
            modifier = Modifier,
            status = "Done",
            backgroundColor = Theme.colors.greenVariant,
            statusColor = Theme.colors.greenAccent,
            dotColor = Theme.colors.greenAccent
        )

        TaskStatus(
            modifier = Modifier,
            status = "To do",
            backgroundColor = Theme.colors.yellowVariant,
            statusColor = Theme.colors.yellowAccent,
            dotColor = Theme.colors.yellowAccent
        )

    }
}