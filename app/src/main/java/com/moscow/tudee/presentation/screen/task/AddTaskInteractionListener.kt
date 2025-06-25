package com.moscow.tudee.presentation.screen.task

import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task.Priority
import kotlinx.datetime.LocalDateTime

interface AddTaskInteractionListener {
    fun onShowAddTaskBottomSheet()

    fun onDismissAddBottomSheet()

    fun onAddTask()

    fun onTitleChange(newTitle: String)

    fun onDescriptionChange(newDescription: String)

    fun onDateChange(newDate: LocalDateTime)

    fun onPriorityClick(taskPriority: Priority)

    fun onCategoryClick(category: Category)

    fun onCancelAddTask()
}