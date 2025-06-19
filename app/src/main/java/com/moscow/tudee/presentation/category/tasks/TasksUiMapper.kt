package com.moscow.tudee.presentation.category.tasks

import com.moscow.tudee.domain.entity.Task
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

@OptIn(FormatStringsInDatetimeFormats::class)
private val dateFormat = LocalDate.Format {
    byUnicodePattern("dd-MM-yyyy")
}

fun Task.toTaskUi() = TasksUiState.TaskUi(
    icon = TODO(),
    title = title,
    description = description,
    date = date.date.toDateUi(),
    priority = priority.toString(),
    status = status,
)

private fun LocalDate.toDateUi() = TasksUiState.TaskUi.DateUi(
    date = this,
    formattedDate = format(dateFormat)
)