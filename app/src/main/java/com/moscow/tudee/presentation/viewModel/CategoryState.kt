package com.moscow.tudee.presentation.viewModel

import com.moscow.tudee.domain.entity.Category

data class CategoryState(
    val categories: List<Category> = emptyList(),
    val showAddCategorySheet: Boolean = false,
    val showDeleteCategorySheet: Boolean = false,
    val showEditCategorySheet: Boolean = false,
    val selectedCategory: Category? = null,
    val deletedCategoryId: Long? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class CategorySheetState(
    val categoryTitle: String = "",
    val selectedIcon: String = "",
)

