package com.moscow.tudee.presentation.task

import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task.Priority
import com.moscow.tudee.domain.entity.Task.Status
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class AddTaskBottomSheetUiState(
    val isLoading: Boolean = false,
    val title: String = "",
    val description: String = "",
    val priority: Priority? = null,
    val status: Status = Status.TODO,
    val date: LocalDateTime = Clock.System.now()
        .toLocalDateTime(timeZone = TimeZone.currentSystemDefault()),
    val category: Category? = null,
    val availableCategories: List<Category> = emptyList(),
    val showAddTaskBottomSheet: Boolean = false,
    val errorMessage: String = "",
)