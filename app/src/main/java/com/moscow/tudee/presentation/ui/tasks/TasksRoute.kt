package com.moscow.tudee.presentation.ui.tasks

import androidx.navigation.NavGraphBuilder
import com.moscow.tudee.presentation.navigation.entry.TasksScreen
import com.moscow.tudee.presentation.navigation.extensions.tudeeComposable
import com.moscow.tudee.presentation.task.TaskScreen

fun NavGraphBuilder.tasksRoute(){
    tudeeComposable<TasksScreen>{
       TaskScreen()
    }
}