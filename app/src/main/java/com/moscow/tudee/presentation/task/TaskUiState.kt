package com.moscow.tudee.presentation.task

import androidx.compose.ui.graphics.painter.Painter
import java.time.LocalDate


data class TaskUiState(
    val weeks: List<Week> = emptyList(),
    val selectedDayIndex: Int = 0,
    val selectedDate: java.time.LocalDate = LocalDate.now(),
    val allTasks: List<Task> = emptyList()
)
data class CalendarDay(val date: LocalDate, val dayName: String, val dayNumber: Int)

data class Week(val label: String, val days: List<CalendarDay>)

fun List<Week>.flattenDays() = flatMap { it.days }

data class Task(
    val title: String,
    val description: String,
    val iconRes: Painter,
    val date: String?,
    val priority: com.moscow.tudee.domain.entity.Task.Priority
)



