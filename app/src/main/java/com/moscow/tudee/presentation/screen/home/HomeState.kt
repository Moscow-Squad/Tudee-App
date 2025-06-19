package com.moscow.tudee.presentation.screen.home

import androidx.compose.ui.graphics.Color
import com.moscow.tudee.R
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime

data class HomeState(
    val isLoading: Boolean = true,
    val date: String? = null,
    val update: SliderState = SliderState.NOTHING_ON_YOUR_LIST,
    val doneTasks: List<TaskDetails> = emptyList(),
    val inProgressTasks: List<TaskDetails> = emptyList(),
    val todoTasks: List<TaskDetails> = emptyList(),
    val selectedTask: TaskDetails? = null,
    val categories: List<EditTaskCategory> = emptyList(),
    val showAddTaskBottomSheet: Boolean = false,
    val showTaskDetailsBottomSheet:Boolean = false
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
        val taskIcon: Int,//String,
        val title: String,
        val description: String,
        val priorityName: String,
        val priorityIcon: Int,
        val state: TaskState,
        val date: LocalDateTime=java.time.LocalDateTime.now().toKotlinLocalDateTime()

        val state: Task.Status
    )

    enum class TaskState(val labelResInt: Int) {
        DONE(R.string.done),
        IN_PROGRESS(R.string.in_progress),
        TODO(R.string.to_do)
    }
}