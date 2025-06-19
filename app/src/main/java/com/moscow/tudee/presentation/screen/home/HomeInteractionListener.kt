package com.moscow.tudee.presentation.screen.home

import com.moscow.tudee.domain.entity.Task

interface HomeInteractionListener {

    fun onFloatingActionButtonClick()

    fun onTaskClick(taskDetails: HomeState.TaskDetails)

    fun onAddTask(taskDetails: HomeState.TaskDetails)

    fun onViewAllClick(taskStatus: Task.Status)
}