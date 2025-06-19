package com.moscow.tudee.presentation.screen.home

import androidx.compose.ui.graphics.Color
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task


fun Task.toTaskDetails(): HomeState.TaskDetails {

    val priorityName = when (priority) {
        Task.Priority.HIGH -> "High"
        Task.Priority.MEDIUM ->"Medium"
        Task.Priority.LOW ->"Low"
    }

    val priorityIc = when (priority) {
        Task.Priority.HIGH -> R.drawable.ic_flag
        Task.Priority.MEDIUM -> R.drawable.ic_alert
        Task.Priority.LOW -> R.drawable.ic_trade_down
    }

    val taskState = when (status) {
        Task.Status.TODO -> HomeState.TaskState.TODO
        Task.Status.IN_PROGRESS -> HomeState.TaskState.IN_PROGRESS
        Task.Status.DONE -> HomeState.TaskState.DONE
    }
    return HomeState.TaskDetails(
        id = id,
        taskIcon = 0,
        title = title,
        description = description,
        taskIconTint = Color.Gray,
        priority = priority.name.lowercase().replaceFirstChar { it.uppercase() },
        priorityName = priorityName,
        priorityIcon = priorityIc,
        state = taskState
    )
}
