package com.moscow.tudee.data.service

import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.service.TasksServices
import kotlinx.coroutines.flow.Flow

class TasksServicesImpl: TasksServices {
    override suspend fun getCategories(): Flow<List<Category>> {
        TODO("Not yet implemented")
    }

    override suspend fun addCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCategory(categoryId: String) {
        TODO("Not yet implemented")
    }

}