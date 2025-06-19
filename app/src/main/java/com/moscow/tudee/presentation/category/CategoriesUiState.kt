package com.moscow.tudee.presentation.category

import androidx.compose.runtime.Stable

@Stable
data class CategoriesUiState(
    val categoryList: List<CategoryUi> = listOf(),
    val isLoading: Boolean = false,
    val error: String? = null
) {
    data class CategoryUi(
        val id: Long,
        val title: String,
        val isEditable: Boolean,
        val count: Int,
        val iconUrl: String
    )
}