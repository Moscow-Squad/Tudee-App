package com.moscow.tudee.presentation.screen.home

import com.moscow.tudee.domain.entity.Task

sealed interface HomeEvent {
    data object ShowTaskDetailsBottomSheet : HomeEvent
    data object ShowEditTaskBottomSheet : HomeEvent
    data object ShowAddTaskBottomSheet : HomeEvent
    data class ViewAll(val taskStatus: Task.Status) : HomeEvent
}