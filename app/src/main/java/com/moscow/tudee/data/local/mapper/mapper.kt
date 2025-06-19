package com.moscow.tudee.data.local.mapper

import com.moscow.tudee.data.local.entity.CategoryEntity
import com.moscow.tudee.data.local.entity.TaskEntity
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

fun CategoryEntity.toCategory(): Category {
    return Category(
        id = id,
        title = title,
        iconUrl = iconUrl,
        countOfTasks = countOfTasks
    )
}

fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = id ?: 0L,
        title = title,
        iconUrl = iconUrl,
        countOfTasks = countOfTasks
    )
}

fun TaskEntity.toTask(): Task {
    return Task(
        id = id.takeIf { it.toInt() != 0 },
        title = title,
        description = description,
        priority = getPriorityFromString(priority),
        categoryId = categoryId,
        status = getStatusFromString(status),
        date = if (date.contains("T")) {
            LocalDateTime.parse(date)
        } else {
            LocalDateTime.parse("${date}T00:00:00") // Append default time
        }
    )
}

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id ?: 0, // Use provided ID or 0 for Room’s auto-generate
        title = title,
        description = description,
        priority = priority.toString(),
        categoryId = categoryId,
        status = status.toString(),
        date = date.toString().substringBefore("T") // Store only date part (YYYY-MM-DD)
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