package com.moscow.tudee.presentation.screen.category.categoriesScreen

sealed class CategoriesEvents {
    data class NavigateToTasks(val categoryId: Long) : CategoriesEvents()
}