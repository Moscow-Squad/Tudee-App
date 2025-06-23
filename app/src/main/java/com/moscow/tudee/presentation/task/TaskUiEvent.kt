package com.moscow.tudee.presentation.task

sealed interface TaskUiEvent {
    object ShowDatePicker : TaskUiEvent
    object DismissDatePicker : TaskUiEvent
    data class ShowDeleteResult(val success: Boolean) : TaskUiEvent
}
