package com.moscow.tudee.presentation.screen.home

import com.moscow.tudee.domain.entity.Task
import kotlinx.datetime.LocalDateTime

interface HomeInteractionListener {

    fun onFloatingActionButtonClick()

    fun onTaskClick(taskDetails: HomeState.TaskDetails)

    fun onAddTask(taskDetails: HomeState.TaskDetails)

    fun onViewAllClick(taskStatus: Task.Status)

    fun onEditTaskClick(taskDetails: HomeState.TaskDetails)

    fun onMoveToDoneClick(taskDetails: HomeState.TaskDetails)

    fun onSaveTaskClick(taskDetails: HomeState.TaskDetails)

    fun onPriorityClick(taskPriority: Task.Priority)

    fun onTitleChange(newTitle: String)

    fun onDescriptionChange(newDescription: String)

    fun onDateChange(newDate: LocalDateTime)

    fun onDismissEditBottomSheet()

    fun onDismissAddBottomSheet()

    fun onDismissDetailsBottomSheet()


}