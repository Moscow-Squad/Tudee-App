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
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.component.AddTaskBottomSheet
import com.moscow.tudee.presentation.component.CustomFAB
import com.moscow.tudee.presentation.component.EditTaskBottomSheet
import com.moscow.tudee.presentation.component.home_components.OverviewSection
import com.moscow.tudee.presentation.component.home_components.TaskList
import com.moscow.tudee.presentation.component.home_components.TaskListHeader
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.mapper.asLong
import com.moscow.tudee.presentation.model.CategoryUi
import com.moscow.tudee.presentation.ui.home.TaskDetailsBottomSheet
import com.moscow.tudee.presentation.utils.ObserveAsEvent
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.koinViewModel


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

    Box(
        modifier = Modifier.fillMaxSize(),
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
                        sliderState = uiState.update,
                        todoTasksCount = uiState.todoTasksCount,
                        inProgressTasksCount = uiState.inProgressTasksCount,
                        doneTasksCount = uiState.doneTasksCount,
                        formattedDate =uiState.formattedDate?:"hhhhhhhhhh",
                    )
                }
            }
            item {
                if (uiState.todoTasks.isEmpty() &&
                    uiState.inProgressTasks.isEmpty()  &&
                    uiState.doneTasks.isEmpty()
                ) {
                    // TODO: handle empty State

                } else {
                    Task.Status.entries.forEach { state ->
                        when (state) {
                            Task.Status.DONE -> {
                                if (uiState.doneTasks.isNotEmpty()) {
                                    TaskListHeader(state, uiState.doneTasksCount, {
                                        interactionListener.onViewAllClick(state)
                                    })
                                    TaskList(
                                        tasks = uiState.doneTasks,
                                        onTaskClick = { task ->
                                            interactionListener.onTaskClick(task)
                                        },
                                    )
                                }
                            }

                            Task.Status.IN_PROGRESS -> {
                                if (uiState.inProgressTasks.isNotEmpty()) {
                                    TaskListHeader(state, uiState.inProgressTasksCount, {
                                        interactionListener.onViewAllClick(state)
                                    })
                                    TaskList(
                                        tasks = uiState.inProgressTasks,
                                        onTaskClick = { task ->
                                            interactionListener.onTaskClick(task)
                                        },
                                    )
                                }
                            }

                            Task.Status.TODO -> {
                                if (uiState.todoTasks.isNotEmpty())
                                    TaskListHeader(state, uiState.todoTasksCount, {
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
                }
            }

    }

    CustomFAB(
        onClick = { interactionListener.onFloatingActionButtonClick() },
        icon = R.drawable.ic_add_task
    )
}

if (uiState.showTaskDetailsBottomSheet) {
    uiState.selectedTask?.let { task->
        TaskDetailsBottomSheet(
            task = task,
            onDismiss = { interactionListener.onDismissDetailsBottomSheet() },
            onEditClick = { interactionListener.onEditTaskIconClick(task) },
            onMoveClick = { interactionListener.onUpdateStatusClick(task) }
        )
    }
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
        selectedCategory = uiState.selectedTask.category ?: CategoryUi(
            id = 0,
            title = "",
            iconUrl = "",
            isPredefined = false,
            countOfTasks = 10
        ),
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

if (uiState.showAddTaskBottomSheet) {
    AddTaskBottomSheet(
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
        selectedCategory = uiState.selectedTask.category ?: CategoryUi(
            id = 0,
            title = "",
            iconUrl = "",
            isPredefined = false,
            countOfTasks = 10
        ),
        onCategorySelected = {
            interactionListener.onCategoryClick(it)
        },
        selectedDate = uiState.selectedTask.date.asLong(),
        onDateSelected = {
            interactionListener.onDateChange(uiState.selectedTask.date)
        },
        onDismiss = {
            interactionListener.onDismissAddBottomSheet()
        },
        onSaveTask = {
            interactionListener.onAddTask(uiState.selectedTask)
        }

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