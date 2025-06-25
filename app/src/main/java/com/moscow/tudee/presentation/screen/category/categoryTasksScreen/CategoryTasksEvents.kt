package com.moscow.tudee.presentation.screen.category.categoryTasksScreen

sealed class CategoryTasksEvents {
    data object NavigateBack : CategoryTasksEvents()
    data class NavigateBackWithResult(val messageID: Int) : CategoryTasksEvents()
}