package com.moscow.tudee.presentation.screen.home

import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task.Priority
import com.moscow.tudee.domain.entity.Task.Status
import kotlinx.datetime.LocalDateTime

data class HomeState(
    val isLoading: Boolean = true,
    val date: String? = null,
    val update: SliderState = SliderState.NOTHING_ON_YOUR_LIST,
    val doneTasks: List<HomeTask> = emptyList(),
    val inProgressTasks: List<HomeTask> = emptyList(),
    val todoTasks: List<HomeTask> = emptyList(),
    val selectedTask: HomeTask? = null,
    val addedTask: HomeTask? = null,
    val categories: List<Category> = emptyList(),
    val showAddTaskBottomSheet: Boolean = false,
    val showEditTaskBottomSheet: Boolean = false,
    val showTaskDetailsBottomSheet: Boolean = false
) {
    enum class SliderState {
        STAY_WORKING,
        TADAA,
        ZERO_PROGRESS,
        NOTHING_ON_YOUR_LIST
    }

    data class HomeTask(
        val id: Long? = null,
        val title: String,
        val description: String,
        val priority: Priority? = null,
        val status: Status,
        val date: LocalDateTime,
        val category: Category? = null
    )
}