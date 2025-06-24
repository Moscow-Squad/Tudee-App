package com.moscow.tudee.presentation.screen.home

import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task
import kotlinx.datetime.LocalDateTime

interface HomeInteractionListener {

    fun onFloatingActionButtonClick()

    //fun onSwitchColorTheme()

    fun onTaskClick(task: Task)

    fun onAddTask(task: Task)

    fun onViewAllClick(taskStatus: Task.Status)

    fun onEditTaskIconClick(task: Task)

    fun onMoveTaskClick(task: Task)

    fun onSaveEditTaskClick(task: Task)

    fun onPriorityClick(taskPriority: Task.Priority)

    fun onTitleChange(newTitle: String)

    fun onDescriptionChange(newDescription: String)

    fun onDateChange(newDate: LocalDateTime)

    fun onCategoryClick(category: Category)

    fun onShowAddBottomSheet()

    fun onShowEditBottomSheet()

    fun onShowDetailsBottomSheet()

    fun onDismissEditBottomSheet()

    fun onDismissAddBottomSheet()

    fun onDismissDetailsBottomSheet()


}