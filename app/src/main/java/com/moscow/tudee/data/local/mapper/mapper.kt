package com.moscow.tudee.data.local.mapper

import com.moscow.tudee.data.local.entity.TaskEntity
import com.moscow.tudee.domain.entity.Task
import kotlinx.datetime.LocalDateTime

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

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id ?: 0,
        title = title,
        description = description,
        priority = priority.toString(),
        categoryId = categoryId,
        status = status.toString(),
        date = date.toString()
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