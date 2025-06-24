package com.moscow.tudee.presentation.task

sealed class AddTaskBottomSheetEvents {
    data object NotifyTaskAdded: AddTaskBottomSheetEvents()
    data object NotifyTaskNotAdded: AddTaskBottomSheetEvents()
}