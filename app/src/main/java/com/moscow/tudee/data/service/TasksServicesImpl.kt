package com.moscow.tudee.data.service

import com.moscow.tudee.data.local.dao.CategoryDao
import com.moscow.tudee.data.local.dao.TaskDao
import com.moscow.tudee.data.local.mapper.toCategory
import com.moscow.tudee.data.local.mapper.toTask
import com.moscow.tudee.data.local.mapper.toTaskEntity
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.TasksServices
import kotlinx.datetime.LocalDate

class TasksServicesImpl(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao
) : TasksServices {

    override suspend fun getTasks(): List<Task> {
        val allTaskEntities = taskDao.getTasks()
        val categoriesById = categoryDao
            .getCategories()
            .associateBy { it.id }

        return allTaskEntities.map { taskEntity ->
            val categoryEntity = categoriesById[taskEntity.categoryId]
                ?: throw IllegalStateException("No Category for id=${taskEntity.categoryId}")
            val domainCategory = categoryEntity.toCategory()
            taskEntity.toTask(domainCategory)
        }
    }

    override suspend fun getTasksByDate(date: LocalDate): List<Task> {
        val taskEntitiesOnDate = taskDao.getTasksByDate(date.toString())
        val categoriesById = categoryDao
            .getCategories()
            .associateBy { it.id }

        return taskEntitiesOnDate.map { taskEntity ->
            val categoryEntity = categoriesById[taskEntity.categoryId]
                ?: throw IllegalStateException("No Category for id=${taskEntity.categoryId}")
            val domainCategory = categoryEntity.toCategory()
            taskEntity.toTask(domainCategory)
        }
    }

    override suspend fun getTaskById(taskId: Long): Task {
        val taskEntity = taskDao.getTaskById(taskId)
            ?: throw Exception("Task not found with id=$taskId")
        val categoryEntity = categoryDao.getCategoryById(taskEntity.categoryId)
            ?: throw Exception("Category not found for id=${taskEntity.categoryId}")
        val domainCategory = categoryEntity.toCategory()
        return taskEntity.toTask(domainCategory)
    }

    override suspend fun changeTaskStatus(taskId: Long,updatedStatus: String) {
        taskDao.updateTaskStatus(taskId, updatedStatus)
    }

    override suspend fun addTask(task: Task) {
        // Persist the Task, then bump the count on its Category
        taskDao.addTask(task.toTaskEntity())

        val taskCategoryId = task.category.id
            ?: throw IllegalStateException("Cannot add Task without a Category id")
        categoryDao.incrementTaskCount(taskCategoryId)
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toTaskEntity())
    }

    override suspend fun deleteTask(taskId: Long) {
        val taskEntityToDelete = taskDao.getTaskById(taskId)
            ?: throw Exception("Task not found with id=$taskId")

        taskDao.deleteTask(taskId)
        categoryDao.decrementTaskCount(taskEntityToDelete.categoryId)
    }

    override suspend fun getTasksByCategory(categoryId: Long): List<Task> {
        val taskEntities = taskDao.getTasksByCategory(categoryId)
        val categoryEntity = categoryDao.getCategoryById(categoryId)
            ?: throw IllegalStateException("No Category for id=$categoryId")
        val domainCategory = categoryEntity.toCategory()

        return taskEntities.map { taskEntity ->
            taskEntity.toTask(domainCategory)
        }
    }

    override suspend fun getTasksByStatus(status: Task.Status): List<Task> {
        val taskEntities = taskDao.getTasksByStatus(status.name)
        val categoriesById = categoryDao
            .getCategories()
            .associateBy { it.id }

        return taskEntities.map { taskEntity ->
            val categoryEntity = categoriesById[taskEntity.categoryId]
                ?: throw IllegalStateException("No Category for id=${taskEntity.categoryId}")
            val domainCategory = categoryEntity.toCategory()
            taskEntity.toTask(domainCategory)
        }
    }

    override suspend fun getTasksByDateAndStatus(
        date: LocalDate,
        status: Task.Status
    ): List<Task> {
        val taskEntitiesOnDate =
            taskDao.getTasksByDateAndStatus(date.toString(), status.name)
        val categoriesById = categoryDao
            .getCategories()
            .associateBy { it.id }

        return taskEntitiesOnDate.map { taskEntity ->
            val categoryEntity = categoriesById[taskEntity.categoryId]
                ?: throw IllegalStateException("No Category for id=${taskEntity.categoryId}")
            val domainCategory = categoryEntity.toCategory()
            taskEntity.toTask(domainCategory)
        }
    }

    override suspend fun getTasksByDateAndCategory(
        date: LocalDate,
        categoryId: Long
    ): List<Task> {
        val taskEntitiesOnDate =
            taskDao.getTasksByDateAndCategory(date.toString(), categoryId)
        val categoryEntity = categoryDao.getCategoryById(categoryId)
            ?: throw IllegalStateException("No Category for id=$categoryId")
        val domainCategory = categoryEntity.toCategory()

        return taskEntitiesOnDate.map { taskEntity ->
            taskEntity.toTask(domainCategory)
        }
    }

    override suspend fun getTasksByCategoryAndStatus(
        categoryId: Long,
        status: Task.Status
    ): List<Task> {
        val taskEntities =
            taskDao.getTasksByCategoryAndStatus(categoryId, status.name)
        val categoryEntity = categoryDao.getCategoryById(categoryId)
            ?: throw IllegalStateException("No Category for id=$categoryId")
        val domainCategory = categoryEntity.toCategory()

        return taskEntities.map { taskEntity ->
            taskEntity.toTask(domainCategory)
        }
    }
}
