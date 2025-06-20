package com.moscow.tudee.data.service

import com.moscow.tudee.data.local.dao.CategoryDao
import com.moscow.tudee.data.local.dao.TaskDao
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
        return taskDao.getTasks().map { it.toTask() }
    }

    override suspend fun getTasksByDate(date: LocalDate): List<Task> {
        return taskDao.getTasksByDate(date.toString()).map { it.toTask() }
    }

    override suspend fun getTaskById(taskId: Long): Task {
        return taskDao.getTaskById(taskId)?.toTask() ?: throw Exception("task not found")
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
        categoryDao.incrementTaskCount(task.categoryId)
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toTaskEntity())
    }

    override suspend fun deleteTask(taskId: Long) {
        val existing = taskDao.getTaskById(taskId)
            ?: throw Exception("task not found")
        taskDao.deleteTask(taskId)
        categoryDao.decrementTaskCount(existing.categoryId)
    }

    override suspend fun getTasksByCategory(categoryId: Long): List<Task> {
        return taskDao
            .getTasksByCategory(categoryId)
            .map { it.toTask() }
    }

    override suspend fun getTasksByStatus(status: Task.Status): List<Task> {
        return taskDao
            .getTasksByStatus(status.name)
            .map { it.toTask() }
    }

    override suspend fun getTasksByDateAndStatus(
        date: LocalDate,
        status: Task.Status
    ): List<Task> {
        return taskDao
            .getTasksByDateAndStatus(date.toString(), status.name)
            .map { it.toTask() }
    }

    override suspend fun getTasksByDateAndCategory(
        date: LocalDate,
        categoryId: Long
    ): List<Task> {
        return taskDao
            .getTasksByDateAndCategory(date.toString(), categoryId)
            .map { it.toTask() }
    }

    override suspend fun getTasksByCategoryAndStatus(
        categoryId: Long,
        status: Task.Status
    ): List<Task> {
        return taskDao
            .getTasksByCategoryAndStatus(categoryId, status.name)
            .map { it.toTask() }
    }
}