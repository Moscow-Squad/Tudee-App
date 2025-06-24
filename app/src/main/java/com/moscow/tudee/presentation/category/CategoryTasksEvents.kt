package com.moscow.tudee.presentation.category

sealed class CategoryTasksEvents {
    data object NavigateBack : CategoryTasksEvents()
    data class NavigateBackWithResult(val messageID: Int) : CategoryTasksEvents()
}