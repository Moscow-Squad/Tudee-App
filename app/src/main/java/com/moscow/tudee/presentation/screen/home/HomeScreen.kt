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
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.component.home_components.OverviewSection
import com.moscow.tudee.presentation.component.home_components.TaskList
import com.moscow.tudee.presentation.component.home_components.TaskListHeader
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import com.moscow.tudee.presentation.navigation.entry.TasksScreen
import com.moscow.tudee.presentation.screen.home.HomeState.TaskDetails
import com.moscow.tudee.presentation.screen.home.HomeState.TaskState
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
            HomeEvent.ShowAddTaskBottomSheet -> TODO()
            HomeEvent.ShowEditTaskBottomSheet -> TODO()
            HomeEvent.ShowTaskDetailsBottomSheet -> TODO()
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
        tasks.groupBy { it.state.labelResInt }.entries.forEach {
            item {
                TaskListHeader(it.key, it.value.size)
                TaskList(it.value, {})
            }
        }

    }

}




@Preview
@Composable
private fun ShowSmallComponent() {
    val tasks = listOf(
        TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Fix Login Bug",
            description = "Resolve issue with login screen",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "High",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,
            state = TaskState.DONE
        ), TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Update UI",
            description = "Redesign the home screen UI",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "Medium",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,

            state = TaskState.IN_PROGRESS
        ), TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Write Unit Tests",
            description = "Add test coverage to the auth module",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "Low",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,
            state = TaskState.TODO
        ), TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Fix Login Bug",
            description = "Resolve issue with login screen",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "High",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,
            state = TaskState.DONE
        ), TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Update UI",
            description = "Redesign the home screen UI",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "Medium",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,

            state = TaskState.IN_PROGRESS
        ), TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Write Unit Tests",
            description = "Add test coverage to the auth module",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "Low",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,
            state = TaskState.TODO
        ), TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Fix Login Bug",
            description = "Resolve issue with login screen",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "High",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,
            state = TaskState.DONE
        ), TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Update UI",
            description = "Redesign the home screen UI",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "Medium",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,

            state = TaskState.IN_PROGRESS
        ), TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Write Unit Tests",
            description = "Add test coverage to the auth module",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "Low",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,
            state = TaskState.TODO
        )
    )
    TudeeTheme {
        HomeContent(
            HomeState(),
            object : HomeInteractionListener {
                override fun onFloatingActionButtonClick() {
                    TODO("Not yet implemented")
                }

                override fun onTaskClick(taskDetails: TaskDetails) {
                    TODO("Not yet implemented")
                }

                override fun onAddTask(taskDetails: TaskDetails) {
                    TODO("Not yet implemented")
                }

                override fun onViewAllClick(taskStatus: Task.Status) {
                    TODO("Not yet implemented")
                }
            }
        )
    }
}
