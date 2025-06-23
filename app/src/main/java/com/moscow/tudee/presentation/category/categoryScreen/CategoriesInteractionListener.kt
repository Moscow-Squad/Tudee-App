package com.moscow.tudee.presentation.category.categoryScreen

import android.net.Uri
import com.moscow.tudee.presentation.category.CategoriesScreenState

interface CategoriesInteractionListener {
    fun onAddCategory(categoryUi: CategoriesScreenState.CategoryUi)
    fun onHideAddCategoryBottomSheet()
    fun onShowAddCategoryBottomSheet()
    fun onCategoryClick(categoryID: Long)
    fun onHideSnackBar()
}