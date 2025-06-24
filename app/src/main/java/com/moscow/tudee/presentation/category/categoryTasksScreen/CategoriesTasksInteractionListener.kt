package com.moscow.tudee.presentation.category.categoryTasksScreen

import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.category.CategoriesScreenState

interface CategoriesTasksInteractionListener {
    fun onUpdateCategory(newCategory: CategoriesScreenState.CategoryUi)
    fun onDeleteCategory(category: CategoriesScreenState.CategoryUi)
    fun onTasksStatusClick(categoryID: Long, status: Task.Status)
    fun onShowEditCategoryBottomSheet()
    fun onHideEditCategoryBottomSheet()
    fun onShowDeleteCategoryBottomSheet()
    fun onHideDeleteCategoryBottomSheet()
    fun onHideSnackBar()
    fun onBackPress()

}