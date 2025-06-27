package com.moscow.tudee.data.local.mapper

import com.moscow.tudee.data.local.entity.CategoryEntity
import com.moscow.tudee.data.local.entity.TaskEntity
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task
import kotlinx.datetime.LocalDateTime

fun CategoryEntity.toCategory(): Category {
    return Category(
        id = id,
        title = title,
        iconUri = iconUri ?: "",
        isPredefined = isPredefined,
        countOfTasks = countOfTasks
    )
}

fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        title = title,
        iconUri = iconUri,
        isPredefined = isPredefined,
        countOfTasks = countOfTasks
    )
}

fun TaskEntity.toTask(category: Category): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        priority = getPriorityFromString(priority),
        category = category,
        status = getStatusFromString(status),
        date = if (date.contains("T")) {
            LocalDateTime.parse(date)
        } else {
            LocalDateTime.parse("${date}T00:00:00")
        }
    )
}

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        priority = priority.toString(),
        categoryId = category.id,
        status = status.toString(),
        date = date.toString().substringBefore("T")
    )
}

private fun getPriorityFromString(priority: String): Task.Priority {
    return when (priority) {
        "LOW" -> Task.Priority.LOW
        "MEDIUM" -> Task.Priority.MEDIUM
        "HIGH" -> Task.Priority.HIGH
        else -> throw IllegalArgumentException("Invalid priority string: $priority")
    }
}

private fun getStatusFromString(status: String): Task.Status {
    return when (status) {
        "TODO" -> Task.Status.TODO
        "IN_PROGRESS" -> Task.Status.IN_PROGRESS
        "DONE" -> Task.Status.DONE
        else -> throw IllegalArgumentException("Invalid status string: $status")
    }
}

fun List<TaskEntity>.toTasksByCategories(categoriesById: Map<Long, CategoryEntity>): List<Task> {
    return this.map { taskEntity ->
        val categoryEntity = categoriesById[taskEntity.categoryId]
            ?: throw IllegalStateException("No Category for id=${taskEntity.categoryId}")
        taskEntity.toTask(categoryEntity.toCategory())
    }
}

fun List<TaskEntity>.toTasksBySingleCategory(categoryEntity: CategoryEntity): List<Task> {
    val domainCategory = categoryEntity.toCategory()
    return this.map { it.toTask(domainCategory) }
}

fun createCategoryEntity(
    title: String,
    iconUri: String,
    isPredefined: Boolean,
    countOfTasks: Int
): CategoryEntity {
    return CategoryEntity(
        id = 0,
        title = title,
        iconUri = iconUri,
        isPredefined = isPredefined,
        countOfTasks = countOfTasks
    )
}