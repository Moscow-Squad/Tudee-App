package com.moscow.tudee.presentation.task

import androidx.compose.ui.graphics.painter.Painter
import kotlinx.datetime.LocalDate

import com.moscow.tudee.domain.entity.Task
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class TaskUiState(
    val selectedDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val selectedStatus: Task.Status = Task.Status.IN_PROGRESS,
    val allTasksForSelectedDate: List<Task> = emptyList(),
    val tasksForSelectedState: List<Task> = emptyList()
)





