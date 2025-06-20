package com.moscow.tudee.presentation.category.categoryScreen

sealed class CategoriesEvents {
    data class NavigateToTasks(val categoryId:Long) : CategoriesEvents()
}