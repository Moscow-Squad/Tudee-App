package com.moscow.tudee.presentation.model

import com.moscow.tudee.domain.entity.Task.Priority
import com.moscow.tudee.domain.entity.Task.Status
import kotlinx.datetime.LocalDateTime

data class TaskUi(
    val id: Long? = null,
    val title: String,
    val description: String,
    val priority: Priority? = null,
    val status: Status,
    val date: LocalDateTime,
    val category: CategoryUi? = null
)
