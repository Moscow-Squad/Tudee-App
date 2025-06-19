package com.moscow.tudee.domain.service

import com.moscow.tudee.domain.entity.Category

interface CategoryServices {

    suspend fun getCategories(): List<Category>

    suspend fun addCategory(category: Category)

    suspend fun updateCategory(category: Category)

    suspend fun deleteCategory(categoryId: Long)

    suspend fun getCategoryById(categoryId: Long): Category
}