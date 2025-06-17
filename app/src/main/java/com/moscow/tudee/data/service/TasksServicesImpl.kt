package com.moscow.tudee.data.service

import com.moscow.tudee.data.local.dao.TaskDao
import com.moscow.tudee.data.local.mapper.toTask
import com.moscow.tudee.data.local.mapper.toTaskEntity
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.data.local.dao.CategoryDao
import com.moscow.tudee.data.local.mapper.getRoomEntityFromCategory
import com.moscow.tudee.data.local.mapper.toCategory
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.service.TasksServices
import kotlinx.datetime.LocalDate

class TasksServicesImpl(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao
) : TasksServices {
    override suspend fun getTasks(): List<Task> {
        return taskDao.getTasks().map { it.toTask() }
    }

    override suspend fun getTasksByDate(date: LocalDate): List<Task> {
        return taskDao.getTasksByDate(date.toString()).map { it.toTask() }
    }
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

    override suspend fun getTaskById(taskId: Long): Task? {
        return taskDao.getTaskById(taskId)?.toTask()
    }

    override suspend fun changeTaskStatus(taskId: Long) {
        val taskEntity = taskDao.getTaskById(taskId) ?: return throw Exception("task not existed")
        val currentStatus = Task.Status.valueOf(taskEntity.status)
        val newStatus = when (currentStatus) {
            Task.Status.TODO -> Task.Status.IN_PROGRESS
            Task.Status.IN_PROGRESS -> Task.Status.DONE
            Task.Status.DONE -> return
        }
        taskDao.updateTaskStatus(taskId, newStatus.name)
    }

    override suspend fun addTask(task: Task) {
        taskDao.addTask(task.toTaskEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toTaskEntity())
    }

    override suspend fun deleteTask(taskId: Long) {
        taskDao.deleteTask(taskId)
    }
}