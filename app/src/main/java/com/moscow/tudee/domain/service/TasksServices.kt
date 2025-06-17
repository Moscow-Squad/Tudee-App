package com.moscow.tudee.domain.service

import com.moscow.tudee.domain.entity.Category

import com.moscow.tudee.domain.entity.Task
import kotlinx.datetime.LocalDate

interface TasksServices {

    suspend fun getTasks(): List<Task>
    suspend fun getTasksByDate(date: LocalDate): List<Task>
    suspend fun getTaskById(taskId: Long): Task?
    suspend fun changeTaskStatus(taskId: Long)
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(taskId: Long)
    suspend fun getCategories(): List<Category>

    suspend fun addCategory(category: Category)

    suspend fun updateCategory(category: Category)

    suspend fun deleteCategory(categoryId: Long)

}