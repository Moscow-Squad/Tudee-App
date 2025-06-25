package com.moscow.tudee.presentation.screen.home

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.entity.Task.Priority
import com.moscow.tudee.presentation.designSystem.theme.Theme
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant

@Composable
fun Priority.getText(): String {
    return when (this) {
        Priority.HIGH -> stringResource(R.string.high)
        Priority.MEDIUM -> stringResource(R.string.medium)
        Priority.LOW -> stringResource(R.string.low)
    }
}

@Composable
@DrawableRes
fun Priority.getIcon(): Int {
    return when (this) {
        Priority.HIGH -> R.drawable.ic_flag
        Priority.MEDIUM -> R.drawable.ic_alert
        Priority.LOW -> R.drawable.ic_trade_down
    }
}

@Composable
fun Priority.getColor(): Color {
    return when (this) {
        Priority.HIGH -> Theme.colors.pinkAccent
        Priority.MEDIUM -> Theme.colors.yellowAccent
        Priority.LOW -> Theme.colors.greenAccent
    }
}

fun HomeState.HomeTask.toTask(): Task {
    val category = category
        ?: throw IllegalStateException("Cannot map HomeTask(id=$id) → Task: category was null")
    return Task(
        id = id,
        title = title,
        description = description,
        priority = priority ?: Priority.LOW,
        category = category,
        status = status,
        date = date
    )
}

@Composable
fun Task.Status.getText(): String {
    return when (this) {
        Task.Status.TODO -> stringResource(R.string.to_do)
        Task.Status.IN_PROGRESS -> stringResource(R.string.in_progress_status)
        Task.Status.DONE -> stringResource(R.string.done)
    }
}

@Composable
fun Task.Status.getBackgroundColor(): Color {
    return when (this) {
        Task.Status.TODO -> Theme.colors.yellowVariant
        Task.Status.IN_PROGRESS -> Theme.colors.purpleVariant
        Task.Status.DONE -> Theme.colors.greenVariant
    }
}

@Composable
fun Task.Status.getColor(): Color {
    return when (this) {
        Task.Status.TODO -> Theme.colors.yellowAccent
        Task.Status.IN_PROGRESS -> Theme.colors.purpleAccent
        Task.Status.DONE -> Theme.colors.greenAccent
    }
}
fun LocalDateTime.asLong(): Long {
    return this.toInstant(kotlinx.datetime.TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

fun Task.toTaskItem(): HomeState.HomeTask {
    return HomeState.HomeTask(
        id = id,
        title = title,
        description = description,
        priority = priority,
        status = status,
        category = category,
        date = date
    )
}