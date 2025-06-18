package com.moscow.tudee.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.home_components.OverviewSection
import com.moscow.tudee.presentation.component.home_components.TaskList
import com.moscow.tudee.presentation.component.home_components.TaskListHeader
import com.moscow.tudee.presentation.designSystem.component.BottomNavBar
import com.moscow.tudee.presentation.designSystem.component.slider.SliderState
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme



@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {},
        floatingActionButton = { },
        bottomBar = { BottomNavBar(0, {}) }
    )
    { paddingValues ->

        HomeContent(
            SliderState.STAY_WORKING,
            listOf(),
            paddingValues
        )
    }
}

@Composable
fun HomeContent(
    sliderState: SliderState,
    tasks: List<TaskDetails>,
    paddingValues: PaddingValues
) {


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.surface)
            .padding(paddingValues)
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
                OverviewSection(sliderState, tasks)
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
    DONE(R.string.done),
    IN_PROGRESS(R.string.in_progress),
    TODO(R.string.to_do)
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
        ),
        TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Update UI",
            description = "Redesign the home screen UI",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "Medium",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,

            state = TaskState.IN_PROGRESS
        ),
        TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Write Unit Tests",
            description = "Add test coverage to the auth module",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "Low",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,
            state = TaskState.TODO
        ),
        TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Fix Login Bug",
            description = "Resolve issue with login screen",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "High",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,
            state = TaskState.DONE
        ),
        TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Update UI",
            description = "Redesign the home screen UI",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "Medium",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,

            state = TaskState.IN_PROGRESS
        ),
        TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Write Unit Tests",
            description = "Add test coverage to the auth module",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "Low",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,
            state = TaskState.TODO
        ),
        TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Fix Login Bug",
            description = "Resolve issue with login screen",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "High",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,
            state = TaskState.DONE
        ),
        TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Update UI",
            description = "Redesign the home screen UI",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "Medium",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,

            state = TaskState.IN_PROGRESS
        ),
        TaskDetails(
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
        HomeContent(SliderState.STAY_WORKING, tasks, PaddingValues(0.dp))
    }
}
