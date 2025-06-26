package com.moscow.tudee.presentation.screen.home

import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.model.CategoryUi
import com.moscow.tudee.presentation.model.TaskUi
import kotlinx.datetime.LocalDateTime

interface HomeInteractionListener {

    fun onFloatingActionButtonClick()

    //fun onSwitchColorTheme()

    fun onTaskClick(task: TaskUi)

    fun onAddTask(task: TaskUi)

    fun onViewAllClick(taskStatus: Task.Status)

    fun onEditTaskIconClick(task: TaskUi)

    fun onUpdateStatusClick(task: TaskUi)

    fun onSaveEditTaskClick(task: TaskUi)

    fun onPriorityClick(taskPriority: Task.Priority)

    fun onTitleChange(newTitle: String)

    fun onDescriptionChange(newDescription: String)

    fun onDateChange(newDate: LocalDateTime)

    fun onCategoryClick(category: CategoryUi)

    fun onShowAddBottomSheet()

    fun onShowEditBottomSheet()

    fun onShowDetailsBottomSheet()

    fun onDismissEditBottomSheet()

    fun onDismissAddBottomSheet()

    fun onDismissDetailsBottomSheet()

    fun onShowSnackbar(message: String)

    fun onSnackbarDismissed()
}