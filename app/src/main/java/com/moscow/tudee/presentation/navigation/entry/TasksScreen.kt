package com.moscow.tudee.presentation.navigation.entry

import com.moscow.tudee.domain.entity.Task
import kotlinx.serialization.Serializable

@Serializable
data class TasksScreen(val status: Task.Status) {
    companion object : BottomNavigationType {
        override val isBottomNavigationVisible: Boolean
            get() = true

    }
}
