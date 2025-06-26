package com.moscow.tudee.presentation.screen.task

import kotlinx.datetime.LocalDate

sealed class AddTaskBottomSheetEvents {
    data class NotifyTaskAdded(val date: LocalDate) : AddTaskBottomSheetEvents()
    data object NotifyTaskNotAdded : AddTaskBottomSheetEvents()
}