package com.moscow.tudee.presentation.screen.home

import com.moscow.tudee.presentation.model.CategoryUi
import com.moscow.tudee.presentation.model.TaskUi

data class HomeState(
    val isLoading: Boolean = true,
    val date: String? = null,
    val sliderState: SliderState = SliderState.NOTHING_ON_YOUR_LIST,
    val doneTasks: List<TaskUi> = mutableListOf(),
    val inProgressTasks: List<TaskUi> = mutableListOf(),
    val todoTasks: List<TaskUi> = mutableListOf(),
    val todoTasksCount: Int = 0,
    val doneTasksCount: Int = 0,
    val inProgressTasksCount: Int = 0,
    val selectedTask: TaskUi? = null,
    val addedTask: TaskUi? = null,
    val categories: List<CategoryUi> = emptyList(),
    val showAddTaskBottomSheet: Boolean = false,
    val showEditTaskBottomSheet: Boolean = false,
    val showTaskDetailsBottomSheet: Boolean = false,
    val formattedDate: String? = null
) {
    enum class SliderState {
        STAY_WORKING,
        TADAA,
        ZERO_PROGRESS,
        NOTHING_ON_YOUR_LIST
    }
}