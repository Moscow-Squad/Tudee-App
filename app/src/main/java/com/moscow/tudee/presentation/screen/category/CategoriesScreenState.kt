package com.moscow.tudee.presentation.screen.category

import com.moscow.tudee.domain.entity.Task.Status
import com.moscow.tudee.presentation.model.CategoryUi
import com.moscow.tudee.presentation.model.TaskUi


data class CategoriesScreenState(
    val isLoading: Boolean = false,
    val errorMessage: Int? = null,
    val successMessage: Int? = null,
    val categories: List<CategoryUi> = listOf(),
    val tasks: List<TaskUi> = listOf(),
    val category: CategoryUi = CategoryUi(),
    val selectedStatus: Status = Status.TODO,
    val isEditCategoryBottomSheetShow: Boolean = false,
    val isAddCategoryBottomSheetShow: Boolean = false,
    val isDeleteCategoryBottomSheetShow: Boolean = false,
    val isSnackBarShow: Boolean = false,

    )