package com.moscow.tudee.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.component.home_components.OverviewSection
import com.moscow.tudee.presentation.component.home_components.TaskList
import com.moscow.tudee.presentation.component.home_components.TaskListHeader
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.navigation.entry.TasksScreen
import com.moscow.tudee.presentation.utils.ObserveAsEvent
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    ObserveAsEvent(viewModel.uiEvent) { event ->
        when (event) {
            is HomeEvent.ViewAll -> {
                navController.navigate(
                    route = TasksScreen
                )
            }
        }
    }
    HomeContent(
        uiState.value,
        viewModel
    )
}

@Composable
fun HomeContent(
    uiState: HomeState,
    interactionListener: HomeInteractionListener
) {
    val tasks = uiState.inProgressTasks + uiState.todoTasks + uiState.doneTasks


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.surface)
    ) {
        item {
            Box(
                Modifier
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .background(color = Theme.colors.primary)
                        .align(Alignment.TopStart)
                        .zIndex(0f)

                )
                OverviewSection(uiState.update, tasks)
            }
        }
        item{
            if (tasks.isNotEmpty()) {
                Task.Status.entries.forEach { state ->
                    when (state) {
                        Task.Status.DONE -> {
                            TaskListHeader(state, uiState.doneTasks.size, {
                                 interactionListener.onViewAllClick(state)
                            })
                            TaskList(uiState.doneTasks, { taskDetails ->
                                interactionListener.onTaskClick(taskDetails)
                            })
                        }

                        Task.Status.IN_PROGRESS -> {
                            TaskListHeader(state, uiState.inProgressTasks.size, {
                                 interactionListener.onViewAllClick(state)
                            })
                            TaskList(uiState.inProgressTasks, { taskDetails ->
                                interactionListener.onTaskClick(taskDetails)
                            })
                        }

                        Task.Status.TODO -> {
                            TaskListHeader(state, uiState.todoTasks.size, {
                                  interactionListener.onViewAllClick(state)
                            })
                            TaskList(uiState.todoTasks, { taskDetails ->
                                interactionListener.onTaskClick(taskDetails)
                            })

                        }
                    }
                }
            }else{
                // handle empty State
            }
        }

    }

}


@Preview
@Composable
private fun PreviewScreen() {
    val viewModel: HomeViewModel = koinViewModel()

    HomeScreen(viewModel = viewModel,
        rememberNavController()
    )
}