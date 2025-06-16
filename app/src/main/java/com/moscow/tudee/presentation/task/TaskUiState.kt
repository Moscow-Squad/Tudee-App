package com.moscow.tudee.presentation.task



data class TaskUiState(
    val selectedTab: Int = 1,
    val tasks: List<Task> = emptyList()
)
enum class Priority { Low, Medium, High }
enum class Status { InProgress, ToDo, Done }

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Priority,
    val status: Status
)
