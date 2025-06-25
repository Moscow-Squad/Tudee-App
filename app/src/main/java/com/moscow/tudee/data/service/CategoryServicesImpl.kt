package com.moscow.tudee.data.service

import com.moscow.tudee.data.local.dao.CategoryDao
import com.moscow.tudee.data.local.mapper.toCategory
import com.moscow.tudee.data.local.mapper.toCategoryEntity
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.exception.AppException
import com.moscow.tudee.domain.service.CategoryServices

class CategoryServicesImpl(
    private val categoryDao: CategoryDao
) : CategoryServices {

    override suspend fun getCategories(): List<Category> {
        try {
            return categoryDao.getCategories().map { it.toCategory() }
        } catch (e: Exception) {
            throw AppException.DatabaseException(e.message ?: "")
        }
    }

    override suspend fun addCategory(category: Category) {
        try {
            categoryDao.addCategory(category.toCategoryEntity())
        } catch (e: Exception) {
            throw AppException.CouldNotAddCategoryException(e.message ?: "")
        }
    }

    override suspend fun updateCategory(category: Category) {
        try {
            categoryDao.updateCategory(category.toCategoryEntity())
        } catch (e: Exception) {
            throw AppException.CategoryNotFoundException(e.message ?: "")
        }
    }

    override suspend fun deleteCategory(categoryId: Long) {
        try {
            categoryDao.deleteCategory(categoryId)
        } catch (e: Exception) {
            throw AppException.CategoryNotFoundException(e.message ?: "")
        }
    }

    override suspend fun getCategoryById(categoryId: Long): Category {
        try {
            return categoryDao.getCategoryById(categoryId)?.toCategory()
                ?: throw Exception()
        } catch (e: Exception) {
            throw AppException.CategoryNotFoundException(e.message ?: "")
        }
    }
}