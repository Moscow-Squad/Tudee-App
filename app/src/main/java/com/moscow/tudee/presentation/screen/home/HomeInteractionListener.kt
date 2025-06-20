package com.moscow.tudee.presentation.screen.home

import com.moscow.tudee.domain.entity.Task
import kotlinx.datetime.LocalDateTime

interface HomeInteractionListener {

    fun onFloatingActionButtonClick()

    //fun onSwitchColorTheme()

    fun onTaskClick(task: HomeState.HomeTask)

    fun onAddTask(task: HomeState.HomeTask)

    fun onViewAllClick(taskStatus: Task.Status)

    fun onEditTaskIconClick(task: HomeState.HomeTask)

    fun onMoveTaskClick(task: HomeState.HomeTask)

    fun onSaveEditTaskClick(task: HomeState.HomeTask)

    fun onPriorityClick(taskPriority: Task.Priority)

    fun onTitleChange(newTitle: String)

    fun onDescriptionChange(newDescription: String)

    fun onDateChange(newDate: LocalDateTime)

    fun onShowAddBottomSheet()

    fun onShowEditBottomSheet()

    fun onShowDetailsBottomSheet()

    fun onDismissEditBottomSheet()

    fun onDismissAddBottomSheet()

    fun onDismissDetailsBottomSheet()


}