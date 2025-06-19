package com.moscow.tudee.presentation.screen.home

import com.moscow.tudee.domain.entity.Task

data class HomeState(
    val isLoading: Boolean = true,
    val date: String? = null,
    val update: SliderState = SliderState.NOTHING_ON_YOUR_LIST,
    val doneTasks: List<Task> = emptyList(),
    val inProgressTasks: List<Task> = emptyList(),
    val todoTasks: List<Task> = emptyList(),
    val selectedTask: Task? = null,
    val categories: List<EditTaskCategory> = emptyList(),
) {
    enum class SliderState {
        STAY_WORKING,
        TADAA,
        ZERO_PROGRESS,
        NOTHING_ON_YOUR_LIST
    }

    data class EditTaskCategory(
        val categoryId: Long? = null,
        val tasksCount: Int = 0,
        val imageUrl: String? = null,
        val title: String? = null,
        val isSelected: Boolean = false,
    )
}