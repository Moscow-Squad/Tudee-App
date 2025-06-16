package com.moscow.tudee.domain.service

import com.moscow.tudee.domain.entity.Category
import kotlinx.coroutines.flow.Flow

interface TasksServices {
    suspend fun getCategories(): List<Category>?

    suspend fun addCategory(category: Category)

    suspend fun updateCategory(category: Category)

    suspend fun deleteCategory(categoryId: String)

}