package com.moscow.tudee.domain.entity
import kotlinx.datetime.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class TaskEntity @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val title: String,
    val description: String,
    val priority: Priority,
    val category: CategoryEntity,
    val status: Status,
    val date: LocalDateTime
) {
    enum class Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    enum class Status {
        TODO,
        IN_PROGRESS,
        DONE
    }
}