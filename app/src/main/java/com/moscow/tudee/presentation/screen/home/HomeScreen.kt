package com.moscow.tudee.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.component.AddTaskBottomSheet
import com.moscow.tudee.presentation.component.CustomFAB
import com.moscow.tudee.presentation.component.EditTaskBottomSheet
import com.moscow.tudee.presentation.screen.home.home_components.OverviewSection
import com.moscow.tudee.presentation.screen.home.home_components.TaskList
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.ui.home.TaskDetailsBottomSheet
import com.moscow.tudee.presentation.utils.ObserveAsEvent
import org.koin.androidx.compose.koinViewModel
import com.moscow.tudee.R
import com.moscow.tudee.presentation.screen.home.home_components.TaskListHeader


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(), navigateToTaskScreen: () -> Unit
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
        viewModel,
    )

}

@Composable
fun HomeContent(
    uiState: HomeState,
    interactionListener: HomeInteractionListener,
) {
    Scaffold(
        floatingActionButton = {
            CustomFAB(
                onClick = { interactionListener.onFloatingActionButtonClick() },
                icon = R.drawable.ic_add_task,
            )

        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.BottomEnd
        ) {
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

                        OverviewSection(
                            sliderState = uiState.sliderState,
                            todoTasks = uiState.todoTasks,
                            inProgressTasks = uiState.inProgressTasks,
                            doneTasks = uiState.doneTasks
                        )
                    }
                }
                item {
                    if (uiState.todoTasks.isNotEmpty() || uiState.inProgressTasks.isNotEmpty() || uiState.doneTasks.isNotEmpty()) {

                        if (uiState.inProgressTasks.isNotEmpty()) {
                            TaskListHeader(
                                taskState = Task.Status.IN_PROGRESS,
                                taskCount = uiState.inProgressTasks.size,
                                onCountClick = { interactionListener.onViewAllClick(Task.Status.IN_PROGRESS) })
                            TaskList(
                                tasks = uiState.inProgressTasks,
                                onTaskClick = { task -> interactionListener.onTaskClick(task) },
                            )
                        }

                        if (uiState.todoTasks.isNotEmpty()) {
                            TaskListHeader(
                                taskState = Task.Status.TODO,
                                taskCount = uiState.todoTasks.size,
                                onCountClick = { interactionListener.onViewAllClick(Task.Status.TODO) }

                            )
                            TaskList(
                                tasks = uiState.todoTasks,
                                onTaskClick = { task -> interactionListener.onTaskClick(task) },
                            )
                        }

                        if (uiState.doneTasks.isNotEmpty()) {
                            TaskListHeader(
                                taskState = Task.Status.DONE,
                                taskCount = uiState.doneTasks.size,
                                onCountClick = { interactionListener.onViewAllClick(Task.Status.DONE) })
                            TaskList(
                                tasks = uiState.doneTasks,
                                onTaskClick = { task -> interactionListener.onTaskClick(task) },
                            )
                        }

                    } else {
                        // TODO: handle empty State
                    }
                }

            }
        }

    }
    if (uiState.showTaskDetailsBottomSheet) {

        TaskDetailsBottomSheet(
            task = uiState.selectedTask!!,
            onDismiss = { interactionListener.onDismissDetailsBottomSheet() },
            onEditClick = { interactionListener.onEditTaskIconClick(uiState.selectedTask) },
            onMoveClick = { interactionListener.onMoveTaskClick(uiState.selectedTask) })
    }

    if (uiState.showEditTaskBottomSheet) {

        EditTaskBottomSheet(
            modifier = Modifier,
            isVisible = true,
            taskTitle = uiState.selectedTask!!.title,
            onTaskTitleChange = { interactionListener.onTitleChange(it) },
            taskDescription = uiState.selectedTask.description,
            onTaskDescriptionChange = { interactionListener.onDescriptionChange(it) },
            selectedPriority = uiState.selectedTask.priority,
            onPrioritySelected = { interactionListener.onPriorityClick(it) },
            categories = uiState.categories,
            selectedCategory = uiState.selectedTask.category,
            onCategorySelected = { interactionListener.onCategoryClick(it) },
            selectedDate = uiState.selectedTask.date.asLong(),
            onDateSelected = { interactionListener.onDateChange(uiState.selectedTask.date) },
            onDismiss = { interactionListener.onDismissEditBottomSheet() },
            onSaveTask = { interactionListener.onSaveEditTaskClick(uiState.selectedTask) },
        )

    }

    if (uiState.showAddTaskBottomSheet) {

        AddTaskBottomSheet(
            modifier = Modifier,
            isVisible = true,
            taskTitle = uiState.selectedTask!!.title,
            onTaskTitleChange = { interactionListener.onTitleChange(it) },
            taskDescription = uiState.selectedTask.description,
            onTaskDescriptionChange = { interactionListener.onDescriptionChange(it) },
            selectedPriority = uiState.selectedTask.priority,
            onPrioritySelected = { interactionListener.onPriorityClick(it) },
            categories = uiState.categories,
            selectedCategory = uiState.selectedTask.category,
            onCategorySelected = { interactionListener.onCategoryClick(it) },
            selectedDate = uiState.selectedTask.date.asLong(),
            onDateSelected = { interactionListener.onDateChange(uiState.selectedTask.date) },
            onDismiss = { interactionListener.onDismissAddBottomSheet() },
            onSaveTask = { interactionListener.onAddTask(uiState.selectedTask) })

    }
}


@Preview
@Composable
private fun PreviewScreen() {
    HomeScreen(
        navigateToTaskScreen = {})
}