package com.moscow.tudee.presentation.category.tasks

import com.moscow.tudee.presentation.category.CategoriesUiState

interface TasksInteractionListener {
    fun onEditCategory(newCategory: CategoriesUiState.CategoryUi)
    fun onDeleteCategory(category: CategoriesUiState.CategoryUi)
}