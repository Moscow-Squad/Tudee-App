package com.moscow.tudee.domain.entity
import kotlinx.datetime.LocalDateTime


data class Task(
    val id: Long? = null,
    val title: String,
    val description: String,
    val priority: Priority,
    val categoryId: Long,
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
