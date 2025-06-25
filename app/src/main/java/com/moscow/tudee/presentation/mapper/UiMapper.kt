package com.moscow.tudee.presentation.mapper

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.entity.Task.Priority
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.model.CategoryUi
import com.moscow.tudee.presentation.model.TaskUi
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

fun Task.toTaskUi() = TaskUi(
    id = id,
    title = title,
    description = description,
    date = date,
    priority = priority,
    status = status,
    category = category.toCategoryUi()
)

fun Category.toCategoryUi() = CategoryUi(
    id = id,
    title = title,
    iconUrl = iconUri,
    numberOfTasksInCategory = countOfTasks,
    isPredefined = isPredefined,
    countOfTasks = countOfTasks
)

fun TaskUi.toTask(): Task =
    Task(
        id = id,
        title = title,
        description = description,
        priority = priority,
        category = category.toCategory(),
        status = status,
        date = date
    )

fun CategoryUi.toCategory() =
    Category(
        id = id,
        title = title,
        iconUri = iconUrl,
        isPredefined = isPredefined,
        countOfTasks = countOfTasks,
    )

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
    return this.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}