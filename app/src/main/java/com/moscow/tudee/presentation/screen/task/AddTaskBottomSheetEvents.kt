package com.moscow.tudee.presentation.screen.task

import com.moscow.tudee.R
import kotlinx.datetime.LocalDate

sealed class AddTaskBottomSheetEvents(val snackBarUi: SnackBarUi) {
    data class NotifyTaskAdded(val date: LocalDate): AddTaskBottomSheetEvents(
        SnackBarUi(
            type = SnackBarType.SUCCESS,
            messageId = R.string.add_task_successfully
        )
    )
    data object NotifyTaskNotAdded: AddTaskBottomSheetEvents(
        SnackBarUi(
            type = SnackBarType.ERROR,
            messageId = R.string.some_error_happened
        )
    )
}