package com.moscow.tudee.presentation.ui.tasks

import androidx.navigation.NavGraphBuilder
import com.moscow.tudee.presentation.navigation.entry.TasksScreen
import com.moscow.tudee.presentation.navigation.entry.TudeeAppBar
import com.moscow.tudee.presentation.navigation.extensions.tudeeComposable
import com.moscow.tudee.presentation.task.TaskScreen
import com.moscow.tudee.presentation.task.TaskScreen

fun NavGraphBuilder.tasksRoute(
    appBar: (TudeeAppBar) -> Unit,
    isBottomNavigationVisible: (Boolean) -> Unit,
){
    tudeeComposable<TasksScreen>{
        appBar(TasksScreen.appBar)
        isBottomNavigationVisible(TasksScreen.isBottomNavigationVisible)
        TaskScreen()
    }
}