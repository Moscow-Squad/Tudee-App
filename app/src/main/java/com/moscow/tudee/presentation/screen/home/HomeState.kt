package com.moscow.tudee.presentation.screen.home

import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task

data class HomeState(
    val isLoading: Boolean = true,
    val date: String? = null,
    val update: SliderState = SliderState.NOTHING_ON_YOUR_LIST,
    val doneTasks: List<Task> = emptyList(),
    val inProgressTasks: List<Task> = emptyList(),
    val todoTasks: List<Task> = emptyList(),
    val selectedTask: Task? = null,
    val categories: List<Category> = emptyList(),
    val showAddTaskBottomSheet: Boolean = false,
    val showEditTaskBottomSheet: Boolean = false,
    val showTaskDetailsBottomSheet:Boolean = false
) {
    enum class SliderState {
        STAY_WORKING,
        TADAA,
        ZERO_PROGRESS,
        NOTHING_ON_YOUR_LIST
    }
}