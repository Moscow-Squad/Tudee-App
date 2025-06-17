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
        iconUrl = iconUrl
    )
}


fun TaskEntity.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        priority = getPriorityFromString(priority),
        categoryId = categoryId,
        status = getStatusFromString(status),
        date = LocalDateTime.parse(date)

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