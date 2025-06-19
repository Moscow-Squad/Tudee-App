package com.moscow.tudee.presentation.screen.home

import androidx.compose.ui.graphics.Color
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task

data class HomeState(
    val isLoading: Boolean = true,
    val date: String? = null,
    val update: SliderState = SliderState.NOTHING_ON_YOUR_LIST,
    val doneTasks: List<TaskDetails> = emptyList(),
    val inProgressTasks: List<TaskDetails> = emptyList(),
    val todoTasks: List<TaskDetails> = emptyList(),
    val selectedTask: TaskDetails? = null,
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

    data class TaskDetails(
        val id: Long? = null,
        val taskIcon: Int,
        val title: String,
        val description: String,
        val taskIconTint: Color,
        val priority: String,
        val priorityName: String,
        val priorityIcon: Int,
        val state: TaskState
    )

    enum class TaskState(val labelResInt: Int) {
        DONE(R.string.done), IN_PROGRESS(R.string.in_progress), TODO(R.string.to_do)
    }
}