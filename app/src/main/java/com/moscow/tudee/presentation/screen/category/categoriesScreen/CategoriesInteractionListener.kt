package com.moscow.tudee.presentation.screen.category.categoriesScreen

import com.moscow.tudee.presentation.screen.category.CategoriesScreenState

interface CategoriesInteractionListener {
    fun onAddCategory(categoryUi: CategoriesScreenState.CategoryUi)
    fun onHideAddCategoryBottomSheet()
    fun onShowAddCategoryBottomSheet()
    fun onCategoryClick(categoryID: Long)
    fun onHideSnackBar()
}