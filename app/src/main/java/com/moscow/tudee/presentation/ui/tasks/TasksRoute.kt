package com.moscow.tudee.presentation.ui.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavGraphBuilder
import com.moscow.tudee.presentation.navigation.entry.TasksScreen
import com.moscow.tudee.presentation.navigation.entry.TudeeAppBar
import com.moscow.tudee.presentation.navigation.extensions.tudeeComposable

fun NavGraphBuilder.tasksRoute(
    appBar: (TudeeAppBar) -> Unit,
    isBottomNavigationVisible: (Boolean) -> Unit,
){
    tudeeComposable<TasksScreen>{
        appBar(TasksScreen.appBar)
        isBottomNavigationVisible(TasksScreen.isBottomNavigationVisible)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Tasks Screen",
                fontWeight = FontWeight.Bold
            )
        }
    }
}