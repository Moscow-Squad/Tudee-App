package com.moscow.tudee.presentation.task

import kotlinx.datetime.LocalDate

import com.moscow.tudee.domain.entity.Task
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


data class TaskUiState(
    val selectedDate: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val selectedStatus: Task.Status = Task.Status.IN_PROGRESS,
    val allTasksForSelectedDate: List<Task> = emptyList(),
    val tasksForSelectedState: List<Task> = emptyList(),
    val currentMonth: Month =  selectedDate.month,
    val currentYear: Int =selectedDate.year,
    val monthDays: List<LocalDate> = emptyList(),
    val showDeletedTaskNotification: Boolean = false,
    val isTaskDeleted: Boolean? = null
)