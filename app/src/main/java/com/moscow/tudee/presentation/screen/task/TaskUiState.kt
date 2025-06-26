package com.moscow.tudee.presentation.screen.task

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import com.moscow.tudee.domain.entity.Task
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Stable
data class TaskUiState(
    val selectedDate: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val selectedStatus: Task.Status = Task.Status.IN_PROGRESS,
    val allTasksForSelectedDate: List<Task> = emptyList(),
    val tasksForSelectedState: List<Task> = emptyList(),
    val currentMonth: Month = selectedDate.month,
    val currentYear: Int = selectedDate.year,
    val monthDays: List<LocalDate> = emptyList(),
    val showDeletedTaskNotification: Boolean = false,
    val isTaskDeleted: Boolean? = null,
    val isDatePickerVisible: Boolean = false,
    val isSnackBarVisible: Boolean = false,
    val snackBarUi: SnackBarUi? = null,
    val snackBarTextId: Int? = null,
    val selectedTaskToDelete: Task? = null,
    val isLoading: Boolean = false
) {
    @Stable
    data class SnackBarUi(
        val type: SnackBarType,
        @StringRes val messageId: Int,
        @DrawableRes val iconId: Int
    ) {
        @Stable
        enum class SnackBarType { SUCCESS, ERROR }
    }

}