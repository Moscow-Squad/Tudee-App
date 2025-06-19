package com.moscow.tudee.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
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
//    HomeContent(
//        uiState.value,
//        viewModel
//    )
}

//@Composable
//fun HomeContent(
//    uiState: HomeState = HomeState(),
//    interactionListener: HomeInteractionListener
//) {
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Theme.colors.surface)
//    ) {
//        item {
//            Box(
//                Modifier
//            ) {
//                Box(
//                    Modifier
//                        .fillMaxWidth()
//                        .height(170.dp)
//                        .background(color = Theme.colors.primary)
//                        .align(Alignment.TopStart)
//                        .zIndex(0f)
//
//                )
//                OverviewSection(uiState.update, tasks)
//            }
//        }
//        tasks.groupBy { it.state.labelResInt }.entries.forEach {
//            item {
//                TaskListHeader(it.key, it.value.size)
//                TaskList(it.value, {})
//            }
//        }
//
//    }
//
//}


data class TaskDetails(
    val taskIcon: Int,
    val title: String,
    val description: String,
    val taskIconTint: Color,
    val priority: String,
    val priorityBackgroundColor: Color,
    val priorityIcon: Int,
    val state: TaskState
)

enum class TaskState(val labelResInt: Int) {
    DONE(R.string.done), IN_PROGRESS(R.string.in_progress), TODO(R.string.to_do)
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
        //HomeContent(SliderState.STAY_WORKING, tasks)
    }
}
