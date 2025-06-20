package com.moscow.tudee.presentation.category.categoryScreen

import com.moscow.tudee.domain.entity.Task

interface CategoriesInteractionListener {

    fun onHideAddCategoryBottomSheet()
    fun onShowAddCategoryBottomSheet()
    fun onCategoryClick(categoryID: Long)
}