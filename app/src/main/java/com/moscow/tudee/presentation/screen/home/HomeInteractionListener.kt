package com.moscow.tudee.presentation.screen.home

import com.moscow.tudee.domain.entity.Task

interface HomeInteractionListener {

    fun onFloatingActionButtonClick()

    fun onTaskClick(task: Task)

    fun onAddTask(task: Task)

    fun onViewAllClick(taskStatus: Task.Status)
}