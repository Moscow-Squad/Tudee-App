package com.moscow.tudee.presentation.category

import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task

fun Category.toCategoryUi(tasks: List<Task>) =  CategoriesUiState.CategoryUi(
    id = id!!,
    title = title,
    iconUri = iconUri,
    count = tasks.size,
    isEditable = TODO()
)

fun CategoriesUiState.CategoryUi.toCategory() = Category(
    id = id,
    title = title,
    iconUri = iconUri
)
