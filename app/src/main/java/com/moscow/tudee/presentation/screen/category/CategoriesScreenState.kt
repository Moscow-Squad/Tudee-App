package com.moscow.tudee.presentation.screen.category

import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.entity.Task.Status


data class CategoriesScreenState(
    val isLoading: Boolean = false,
    val errorMessage: Int? = null,
    val successMessage: Int? = null,
    val categories: List<CategoryUi> = listOf(),
    val tasks: List<TaskUi> = listOf(),
    val category: CategoryUi = CategoryUi(),
    val selectedStatus: Status= Status.TODO,
    val isEditCategoryBottomSheetShow: Boolean = false,
    val isAddCategoryBottomSheetShow: Boolean = false,
    val isDeleteCategoryBottomSheetShow: Boolean = false,
    val isSnackBarShow: Boolean = false,

    ) {
    data class CategoryUi(
        val id: Long = 0L,
        val title: String = "",
        val isPredefined: Boolean = false,
        val numberOfTasksInCategory: Int = 0,
        val iconUrl: String = "",
    )

    data class TaskUi(
        val title: String = "",
        val description: String = "",
        val date: String = "",
        val priority: Task.Priority,
        val status: Status = Status.TODO,
        val category: CategoryUi = CategoryUi(),
    )
}