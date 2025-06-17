package com.moscow.tudee.data.service

import com.moscow.tudee.data.local.dao.CategoryDao
import com.moscow.tudee.data.local.mapper.getRoomEntityFromCategory
import com.moscow.tudee.data.local.mapper.toCategory
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.service.CategoryServices

class CategoryServicesImpl(
    private val categoryDao: CategoryDao
): CategoryServices {

    override suspend fun getCategories(): List<Category> {
        return categoryDao.getCategories().map { it.toCategory() }
    }

    override suspend fun addCategory(category: Category) {
        categoryDao.addCategory(getRoomEntityFromCategory(category))
    }

    override suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(getRoomEntityFromCategory(category))
    }

    override suspend fun deleteCategory(categoryId: Long) {
        categoryDao.deleteCategory(categoryId)
    }
}