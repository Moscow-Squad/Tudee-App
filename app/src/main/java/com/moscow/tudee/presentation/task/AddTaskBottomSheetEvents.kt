package com.moscow.tudee.presentation.task

import com.moscow.tudee.R

sealed class AddTaskBottomSheetEvents(val snackBarUi: SnackBarUi) {
    data object NotifyTaskAdded: AddTaskBottomSheetEvents(
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