package com.moscow.tudee.presentation.category.categoryTasks

import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.category.categoryScreen.CategoriesScreenState

interface CategoriesTasksInteractionListener {
    fun onAddCategoryConfirmed(category: CategoriesScreenState.CategoryUi)
    fun onEditCategoryConfirmed(newCategory: CategoriesScreenState.CategoryUi)
    fun onDeleteCategoryConfirmed(category: CategoriesScreenState.CategoryUi)
    fun onTasksStatusClick(categoryID: Long, status: Task.Status)
    fun onShowEditCategoryBottomSheet()
    fun onHideEditCategoryBottomSheet()
    fun onShowDeleteCategoryBottomSheet()
    fun onHideDeleteCategoryBottomSheet()
}