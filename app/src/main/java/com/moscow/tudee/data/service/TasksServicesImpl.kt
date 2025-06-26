package com.moscow.tudee.data.service

import com.moscow.tudee.data.local.dao.CategoryDao
import com.moscow.tudee.data.local.dao.TaskDao
import com.moscow.tudee.data.local.mapper.toCategory
import com.moscow.tudee.data.local.mapper.toTask
import com.moscow.tudee.data.local.mapper.toTaskEntity
import com.moscow.tudee.data.local.mapper.toTasksByCategories
import com.moscow.tudee.data.local.mapper.toTasksBySingleCategory
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.TasksServices
import kotlinx.datetime.LocalDate

class TasksServicesImpl(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao
) : TasksServices {

    override suspend fun getTasks(): List<Task> {
        val tasks = taskDao.getTasks()
        val categories = categoryDao.getCategories().associateBy { it.id }
        return tasks.toTasksByCategories(categories)
    }

    override suspend fun getTasksByDate(date: LocalDate): List<Task> {
        val tasks = taskDao.getTasksByDate(date.toString())
        val categories = categoryDao.getCategories().associateBy { it.id }
        return tasks.toTasksByCategories(categories)
    }

    override suspend fun getTasksByStatus(status: Task.Status): List<Task> {
        val tasks = taskDao.getTasksByStatus(status.name)
        val categories = categoryDao.getCategories().associateBy { it.id }
        return tasks.toTasksByCategories(categories)
    }

    override suspend fun getTasksByDateAndStatus(date: LocalDate, status: Task.Status): List<Task> {
        val tasks = taskDao.getTasksByDateAndStatus(date.toString(), status.name)
        val categories = categoryDao.getCategories().associateBy { it.id }
        return tasks.toTasksByCategories(categories)
    }

    override suspend fun getTasksByCategory(categoryId: Long): List<Task> {
        val tasks = taskDao.getTasksByCategory(categoryId)
        val category = categoryDao.getCategoryById(categoryId)
            ?: throw IllegalStateException("No Category for id=$categoryId")
        return tasks.toTasksBySingleCategory(category)
    }

    override suspend fun getTasksByDateAndCategory(date: LocalDate, categoryId: Long): List<Task> {
        val tasks = taskDao.getTasksByDateAndCategory(date.toString(), categoryId)
        val category = categoryDao.getCategoryById(categoryId)
            ?: throw IllegalStateException("No Category for id=$categoryId")
        return tasks.toTasksBySingleCategory(category)
    }

    override suspend fun getTasksByCategoryAndStatus(
        categoryId: Long,
        status: Task.Status
    ): List<Task> {
        val tasks = taskDao.getTasksByCategoryAndStatus(categoryId, status.name)
        val category = categoryDao.getCategoryById(categoryId)
            ?: throw IllegalStateException("No Category for id=$categoryId")
        return tasks.toTasksBySingleCategory(category)
    }


    override suspend fun getTaskById(taskId: Long): Task {
        val taskEntity = taskDao.getTaskById(taskId)
            ?: throw Exception("Task not found with id=$taskId")
        val categoryEntity = categoryDao.getCategoryById(taskEntity.categoryId)
            ?: throw Exception("Category not found for id=${taskEntity.categoryId}")
        val domainCategory = categoryEntity.toCategory()
        return taskEntity.toTask(domainCategory)
    }

    override suspend fun changeTaskStatus(taskId: Long, updatedStatus: Task.Status) {
        taskDao.updateTaskStatus(taskId, updatedStatus.name)
    }

    override suspend fun addTask(task: Task) {
        taskDao.addTask(task.toTaskEntity())

        val taskCategoryId = task.category.id
        categoryDao.incrementTaskCount(taskCategoryId)
    }

    override suspend fun updateTask(task: Task) {
        task.id?.let { taskId ->
            taskDao.getTaskById(taskId)?.let { oldTask ->
                categoryDao.getCategoryById(oldTask.categoryId)?.let { oldCategory ->
                    categoryDao.decrementTaskCount(oldCategory.id)
                }
            }
        }

        taskDao.updateTask(task.toTaskEntity())

        categoryDao.incrementTaskCount(task.category.id)
    }

    override suspend fun deleteTask(taskId: Long) {
        val taskEntityToDelete = taskDao.getTaskById(taskId)
            ?: throw Exception("Task not found with id=$taskId")

        taskDao.deleteTask(taskId)
        categoryDao.decrementTaskCount(taskEntityToDelete.categoryId)
    }
}
