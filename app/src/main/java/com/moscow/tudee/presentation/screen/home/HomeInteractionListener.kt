package com.moscow.tudee.presentation.screen.home

import com.moscow.tudee.domain.entity.Task
import kotlinx.datetime.LocalDateTime

interface HomeInteractionListener {

    fun onFloatingActionButtonClick()

    //fun onSwitchColorTheme()

    fun onTaskClick(task: HomeState.TaskUi)

    fun onAddTask(task: HomeState.TaskUi)

    fun onViewAllClick(taskStatus: Task.Status)

    fun onEditTaskIconClick(task: HomeState.TaskUi)

    fun onUpdateStatusClick(task: HomeState.TaskUi)

    fun onSaveEditTaskClick(task: HomeState.TaskUi)

    fun onPriorityClick(taskPriority: Task.Priority)

    fun onTitleChange(newTitle: String)

    fun onDescriptionChange(newDescription: String)

    fun onDateChange(newDate: LocalDateTime)

    fun onCategoryClick(category: HomeState.CategoryUi)

    fun onShowAddBottomSheet()

    fun onShowEditBottomSheet()

    fun onShowDetailsBottomSheet()

    fun onDismissEditBottomSheet()

    fun onDismissAddBottomSheet()

    fun onDismissDetailsBottomSheet()


}