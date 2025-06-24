package com.moscow.tudee.data.service

import com.moscow.tudee.data.exception.DataException
import com.moscow.tudee.data.local.dao.CategoryDao
import com.moscow.tudee.data.local.mapper.toCategory
import com.moscow.tudee.data.local.mapper.toCategoryEntity
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.service.CategoryServices

class CategoryServicesImpl(
    private val categoryDao: CategoryDao
): CategoryServices {

    override suspend fun getCategories(): List<Category> {
        return categoryDao.getCategories().map { it.toCategory() }
    }

    override suspend fun addCategory(category: Category) {
        categoryDao.addCategory(category.toCategoryEntity())
    }

    override suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category.toCategoryEntity())
    }

    override suspend fun deleteCategory(categoryId: Long) {
        categoryDao.deleteCategory(categoryId)
    }

    override suspend fun getCategoryById(categoryId: Long): Category {
        return categoryDao.getCategoryById(categoryId)?.toCategory() ?: throw DataException.CategoryNotFound(categoryId)
    }
}