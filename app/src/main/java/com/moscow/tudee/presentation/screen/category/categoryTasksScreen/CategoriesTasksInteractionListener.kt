package com.moscow.tudee.presentation.screen.category.categoryTasksScreen

import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.model.CategoryUi
import com.moscow.tudee.presentation.screen.category.CategoriesScreenState

interface CategoriesTasksInteractionListener {
    fun onUpdateCategory(newCategory: CategoryUi)
    fun onDeleteCategory(category: CategoryUi)
    fun onTasksStatusClick(categoryID: Long, status: Task.Status)
    fun onShowEditCategoryBottomSheet()
    fun onHideEditCategoryBottomSheet()
    fun onShowDeleteCategoryBottomSheet()
    fun onHideDeleteCategoryBottomSheet()
    fun onHideSnackBar()
    fun onBackPress()

}