package com.moscow.tudee.presentation.screen.home

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.entity.Task.Priority
import com.moscow.tudee.presentation.designSystem.theme.Theme

@Composable
fun Priority.getText(): String {
    return when (this) {
        Priority.HIGH -> stringResource(R.string.high)
        Priority.MEDIUM -> stringResource(R.string.medium)
        Priority.LOW -> stringResource(R.string.low)
    }
}

@Composable
@DrawableRes fun Priority.getIcon(): Int {
    return when (this) {
        Priority.HIGH -> R.drawable.ic_flag
        Priority.MEDIUM -> R.drawable.ic_alert
        Priority.LOW -> R.drawable.ic_trade_down
    }
}

@Composable
fun Priority.getBackground(): Color {
    return when (this) {
        Priority.HIGH -> Theme.colors.pinkAccent
        Priority.MEDIUM -> Theme.colors.yellowAccent
        Priority.LOW -> Theme.colors.greenAccent
    }
}

fun HomeState.HomeTask.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        priority = priority,
        categoryId = category!!.id!!,
        status = status,
        date = date
    )
}