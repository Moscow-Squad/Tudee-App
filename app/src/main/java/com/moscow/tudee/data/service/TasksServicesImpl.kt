package com.moscow.tudee.data.service

import com.moscow.tudee.data.local.dao.CategoryDao
import com.moscow.tudee.data.local.mapper.getEntityFromCategory
import com.moscow.tudee.data.local.mapper.toCategory
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.service.TasksServices

class TasksServicesImpl(private val categoryDao: CategoryDao) : TasksServices {
    override suspend fun getCategories(): List<Category>? {
        return categoryDao.getCategories()?.map { it.toCategory() }
    }

    override suspend fun addCategory(category: Category) {
        categoryDao.addCategory(getEntityFromCategory(category))
    }

    override suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(getEntityFromCategory(category))
    }

    override suspend fun deleteCategory(categoryId: Long) {
        categoryDao.deleteCategory(categoryId)
    }

}