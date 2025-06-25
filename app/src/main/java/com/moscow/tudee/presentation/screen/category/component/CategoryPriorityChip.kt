package com.moscow.tudee.presentation.screen.category.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.designSystem.theme.Theme

@Composable
fun CategoryPriorityChip(
    modifier: Modifier = Modifier,
    priority: Task.Priority = Task.Priority.LOW,
    selected: Boolean = true,
    unselectedBackgroundColor: Color = Theme.colors.surfaceLow,
    selectedContentColor: Color = Theme.colors.onPrimary,
    unselectedContentColor: Color = Theme.colors.hint
) {
    val (labelText, iconResId, baseBackgroundColor) = when (priority) {
        Task.Priority.HIGH -> Triple(
            stringResource(R.string.priority_high),
            R.drawable.ic_flag,
            Theme.colors.pinkAccent
        )
        Task.Priority.MEDIUM -> Triple(
            stringResource(R.string.priority_medium),
            R.drawable.ic_alert,
            Theme.colors.yellowAccent
        )
        Task.Priority.LOW -> Triple(
            stringResource(R.string.priority_low),
            R.drawable.ic_trade_down,
            Theme.colors.greenAccent
        )
    }

    val backgroundColor by animateColorAsState(
        targetValue = if (selected) baseBackgroundColor else unselectedBackgroundColor,
        animationSpec = tween(durationMillis = 300)
    )
    val contentColor by animateColorAsState(
        targetValue = if (selected) selectedContentColor else unselectedContentColor,
        animationSpec = tween(durationMillis = 300)
    )

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = "priority icon",
            tint = contentColor,
            modifier = Modifier.size(14.dp)
        )
        Text(
            text = labelText,
            color = contentColor,
            style = Theme.textStyle.label.small
        )
    }
}
