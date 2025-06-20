package com.moscow.tudee.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.component.EditTaskBottomSheet
import com.moscow.tudee.presentation.component.home_components.OverviewSection
import com.moscow.tudee.presentation.component.home_components.TaskList
import com.moscow.tudee.presentation.component.home_components.TaskListHeader
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.ui.home.TaskDetailsBottomSheet
import com.moscow.tudee.presentation.utils.ObserveAsEvent
import kotlinx.datetime.toKotlinLocalDateTime
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navigateToTaskScreen: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    ObserveAsEvent(viewModel.uiEvent) { event ->
        when (event) {
            is HomeEvent.ViewAll -> {
                navigateToTaskScreen()
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.surface)
            .clickable { interactionListener.onTaskClick(
                HomeState.HomeTask(
                    id = 1,
                    title = "Organize Study Desk",
                    description = "Solve all exercises from page 45 to 50 in the textbook, Solve all exercises from page 45 to 50 in the textbook.",
                    priority = Task.Priority.HIGH,
                    status = Task.Status.IN_PROGRESS,
                    category = Category(
                        id = 1,
                        title = "Study",
                        iconUri = ""
                    ),
                    date = LocalDateTime.now().toKotlinLocalDateTime(),
                )
            ) } // TODO: Remove
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
                OverviewSection(
                    sliderState = uiState.update,
                    todoTasks = uiState.todoTasks.map { it.toTask() },
                    inProgressTasks = uiState.inProgressTasks.map { it.toTask() },
                    doneTasks = uiState.doneTasks.map { it.toTask() }
                )
            }
        }
        item {
            if (uiState.todoTasks.isNotEmpty() || uiState.inProgressTasks.isNotEmpty() || uiState.doneTasks.isNotEmpty()) {
                Task.Status.entries.forEach { state ->
                    when (state) {
                        Task.Status.DONE -> {
                            TaskListHeader(state, uiState.doneTasks.size, {
                                interactionListener.onViewAllClick(state)
                            })
                            TaskList(
                                tasks = uiState.doneTasks,
                                onTaskClick = { task ->
                                    interactionListener.onTaskClick(task)
                                },
                            )
                        }

                        Task.Status.IN_PROGRESS -> {
                            TaskListHeader(state, uiState.inProgressTasks.size, {
                                interactionListener.onViewAllClick(state)
                            })
                            TaskList(
                                tasks = uiState.inProgressTasks,
                                onTaskClick = { task ->
                                    interactionListener.onTaskClick(task)
                                },
                            )
                        }

                        Task.Status.TODO -> {
                            TaskListHeader(state, uiState.todoTasks.size, {
                                interactionListener.onViewAllClick(state)
                            })
                            TaskList(
                                tasks = uiState.todoTasks,
                                onTaskClick = { task ->
                                    interactionListener.onTaskClick(task)
                                },
                            )

                        }
                    }
                }
            } else {
                // TODO: handle empty State
            }
        }

    }

    if (uiState.showTaskDetailsBottomSheet) {
        TaskDetailsBottomSheet(
            task = uiState.selectedTask!!,
            onDismiss = { interactionListener.onDismissDetailsBottomSheet() },
            onEditClick = {
                interactionListener.onEditTaskIconClick(uiState.selectedTask)
            },
            onMoveClick = {
                interactionListener.onMoveTaskClick(uiState.selectedTask)
            }
        )
    }

    if (uiState.showEditTaskBottomSheet) {
        EditTaskBottomSheet(
            modifier = Modifier,
            isVisible = true,
            taskTitle = uiState.selectedTask!!.title,
            onTaskTitleChange = {
                interactionListener.onTitleChange(it)
            },
            taskDescription = uiState.selectedTask.description,

            onTaskDescriptionChange = {
                interactionListener.onDescriptionChange(it)
            },
            selectedPriority = uiState.selectedTask.priority,
            onPrioritySelected = {
                interactionListener.onPriorityClick(it)
            },
            categories = uiState.categories,
            selectedCategory = uiState.selectedTask.category!!,
            onCategorySelected = {
                interactionListener.onCategoryClick(it)
            },
            selectedDate = uiState.selectedTask.date.asLong(),
            onDateSelected = {
                interactionListener.onDateChange(uiState.selectedTask.date)
            },
            onDismiss = {
                interactionListener.onDismissEditBottomSheet()
            },
            onSaveTask = {
                interactionListener.onSaveEditTaskClick(uiState.selectedTask)
            },
        )
    }

}


@Preview
@Composable
private fun PreviewScreen() {
    HomeScreen(
        navigateToTaskScreen = {}
    )
}