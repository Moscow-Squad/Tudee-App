package com.moscow.tudee.presentation.screen.task

import androidx.navigation.NavGraphBuilder
import com.moscow.tudee.presentation.navigation.entry.TasksScreen
import com.moscow.tudee.presentation.navigation.extensions.tudeeComposable

fun NavGraphBuilder.tasksRoute(
    isBottomNavigationVisible: (Boolean) -> Unit,
){
    tudeeComposable<TasksScreen>{
        isBottomNavigationVisible(TasksScreen.isBottomNavigationVisible)
        TaskScreen()
    }
}