package com.moscow.tudee.presentation.screen.home

import com.moscow.tudee.domain.entity.Task
import kotlinx.datetime.LocalDateTime

interface HomeInteractionListener {

    fun onFloatingActionButtonClick()

    //fun onSwitchColorTheme()

    fun onTaskClick(task: Task)

    fun onAddTask(task: Task)

    fun onViewAllClick(taskStatus: Task.Status)

    fun onEditTaskClick(task: Task)

    fun onMoveToDoneClick(task: Task)

    fun onSaveTaskClick(task: Task)

    fun onPriorityClick(taskPriority: Task.Priority)

    fun onTitleChange(newTitle: String)

    fun onDescriptionChange(newDescription: String)

    fun onDateChange(newDate: LocalDateTime)

    fun onShowEditBottomSheet()

    fun onShowAddBottomSheet()

    fun onShowDetailsBottomSheet()

    fun onDismissEditBottomSheet()

    fun onDismissAddBottomSheet()

    fun onDismissDetailsBottomSheet()


}