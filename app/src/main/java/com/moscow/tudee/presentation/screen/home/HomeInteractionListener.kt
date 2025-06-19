package com.moscow.tudee.presentation.screen.home

import com.moscow.tudee.domain.entity.Task

interface HomeInteractionListener {

    fun onFloatingActionButtonClick()

    //fun onSwitchColorTheme()

    fun onTaskClick(taskDetails: HomeState.TaskDetails)

    fun addTask(task: Task)

    fun onViewAllClick(taskStatus: Task.Status)
}