package com.moscow.tudee.presentation.category

sealed class CategoriesEvents {
    data class NavigateToTasks(val categoryId:Long) : CategoriesEvents()
}