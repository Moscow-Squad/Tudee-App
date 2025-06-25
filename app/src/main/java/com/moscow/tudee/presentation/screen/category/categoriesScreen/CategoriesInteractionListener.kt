package com.moscow.tudee.presentation.screen.category.categoriesScreen

import com.moscow.tudee.presentation.model.CategoryUi

interface CategoriesInteractionListener {
    fun onAddCategory(categoryUi: CategoryUi)
    fun onHideAddCategoryBottomSheet()
    fun onShowAddCategoryBottomSheet()
    fun onCategoryClick(categoryID: Long)
    fun onHideSnackBar()
}