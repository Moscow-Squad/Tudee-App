package com.moscow.tudee.presentation.screen.home

import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task

data class HomeState(
    val date: String? = null,
    val update: Update = Update.NOTHING_ON_YOUR_LIST,
    val doneTasks: List<Task> = emptyList(),
    val inProgressTasks: List<Task> = emptyList(),
    val todoTasks: List<Task> = emptyList(),
    val isTaskDetailsBottomSheetOpen: Boolean = false,
    val selectedTask: Task? = null,
    val categories: List<Category> = emptyList(),

    ) {
    enum class Update {
        STAY_WORKING,
        TADAA,
        ZERO_PROGRESS,
        NOTHING_ON_YOUR_LIST
    }
}