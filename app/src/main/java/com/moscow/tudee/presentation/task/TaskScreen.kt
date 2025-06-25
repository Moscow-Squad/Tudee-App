package com.moscow.tudee.presentation.task

import SwipeToDeleteItem
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.component.AddTaskBottomSheet
import com.moscow.tudee.presentation.component.CustomFAB
import com.moscow.tudee.presentation.component.DatePickerModal
import com.moscow.tudee.presentation.component.EmptyScreen
import com.moscow.tudee.presentation.component.Tab
import com.moscow.tudee.presentation.component.Tabs
import com.moscow.tudee.presentation.component.bottomSheet.DeleteBottomSheet
import com.moscow.tudee.presentation.designSystem.component.PriorityChip
import com.moscow.tudee.presentation.designSystem.component.SnackBar
import com.moscow.tudee.presentation.designSystem.component.TaskCard
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import com.moscow.tudee.presentation.screen.category.toCategoryUi
import com.moscow.tudee.presentation.task.components.DayItem
import com.moscow.tudee.presentation.task.components.Header
import com.moscow.tudee.presentation.util.getPriorityBackground
import com.moscow.tudee.presentation.util.iconRes
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.koinViewModel
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = koinViewModel(),
    addTaskBottomSheetViewModel: AddTaskBottomSheetViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val bottomSheetUiState by addTaskBottomSheetViewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val showDatePicker by viewModel.showDatePicker.collectAsStateWithLifecycle()
    var showCustomSnackBar by remember { mutableStateOf(false) }
    var snackBar: SnackBarUi by remember {
        mutableStateOf(
            SnackBarUi(
                type = SnackBarType.SUCCESS,
                messageId = R.string.add_task_successfully
            )
        )
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            if (event is TaskUiEvent.ShowSnackBar) {
                snackBar = event.snackBarUi
                showCustomSnackBar = true
                coroutineScope.launch {
                    kotlinx.coroutines.delay(3000)
                    showCustomSnackBar = false
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        addTaskBottomSheetViewModel.uiEvent.collect { event ->
            snackBar = event.snackBarUi
            showCustomSnackBar = true
            when (event) {
                is AddTaskBottomSheetEvents.NotifyTaskAdded -> {
                    viewModel.selectDate(event.date)
                    viewModel.selectStatus(Task.Status.TODO)
                }
                AddTaskBottomSheetEvents.NotifyTaskNotAdded -> Unit
            }
            coroutineScope.launch {
                kotlinx.coroutines.delay(3000)
                showCustomSnackBar = false
            }
        }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.colors.surfaceHigh)
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                Text(
                    text = "Task",
                    style = Theme.textStyle.title.large,
                    color = Theme.colors.title
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            CustomFAB(
                onClick = { addTaskBottomSheetViewModel.onShowAddTaskBottomSheet() },
                icon = R.drawable.ic_add_task
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TaskContent(
                modifier = Modifier
                    .fillMaxSize(),
                interactionListener = viewModel,
                uiState = uiState,
                bottomSheetUiState = bottomSheetUiState,
                bottomSheetListener = addTaskBottomSheetViewModel,
                showDatePicker = showDatePicker,
            )
            if (showCustomSnackBar) {
                val visuals = getSnackBarVisuals(snackBar.type)
                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically { -100 } + fadeIn(),
                    exit = slideOutVertically { -100 } + fadeOut(),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                ) {
                    SnackBar(
                        icon = painterResource(id = visuals.icon),
                        message = stringResource(snackBar.messageId),
                        iconBackground = visuals.iconBackground,
                        iconTint = visuals.iconTint
                    )
                }
            }

            if (showDatePicker) {
                DatePickerModal(
                    onDateSelected = { viewModel.updateMonthFromPicker(it) },
                    onDismiss = viewModel::dismissDatePicker,
                    selectedDate = uiState.selectedDate.toInstant(UtcOffset.ZERO).toEpochMilliseconds()
                )
            }
        }
    }
}

@Composable
private fun TaskContent(
    modifier: Modifier = Modifier,
    interactionListener: TaskScreenInteractionListener,
    uiState: TaskUiState,
    showDatePicker: Boolean,
    bottomSheetUiState: AddTaskBottomSheetUiState,
    bottomSheetListener: AddTaskBottomSheetViewModel
) {
    val selectedTabIndex = when (uiState.selectedStatus) {
        Task.Status.IN_PROGRESS -> 0
        Task.Status.TODO -> 1
        Task.Status.DONE -> 2
    }

    val inProgressCount =
        uiState.allTasksForSelectedDate.count { it.status == Task.Status.IN_PROGRESS }
    val todoCount = uiState.allTasksForSelectedDate.count { it.status == Task.Status.TODO }
    val doneCount = uiState.allTasksForSelectedDate.count { it.status == Task.Status.DONE }

    val allTabs = listOf(
        Tab("In Progress", inProgressCount),
        Tab("To Do", todoCount),
        Tab("Done", doneCount)
    )

    val currentMonthYear = "${
        uiState.currentMonth.getDisplayName(
            TextStyle.FULL,
            Locale.getDefault()
        )
    }, ${uiState.currentYear}"
    val lazyListState = rememberLazyListState()
    val isAtStart by remember { derivedStateOf { lazyListState.firstVisibleItemIndex == 0 } }
    var selectedTaskToDelete by remember { mutableStateOf<Task?>(null) }

    LaunchedEffect(uiState.selectedDate) {
        val index = uiState.monthDays.indexOf(uiState.selectedDate.date)
        if (index >= 0) lazyListState.animateScrollToItem(index)
    }
    Column(
        modifier = Modifier
            .background(Theme.colors.surface)
            .fillMaxSize()
    ) {
        Header(
            date = currentMonthYear,
            onBackClick = interactionListener::previousMonth,
            onNextClick = interactionListener::nextMonth,
            onDownClick = interactionListener::showDatePicker
        )

        LazyRow(
            state = lazyListState,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .background(Theme.colors.surfaceHigh)
                .padding(
                    start = if (isAtStart) 16.dp else 0.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
        ) {
            items(uiState.monthDays) { date ->
                val dayName = date.dayOfWeek.name.take(3).lowercase()
                    .replaceFirstChar { it.titlecase(Locale.ROOT) }
//                val isSelected =
                DayItem(
                    day = dayName,
                    dayDate = date.dayOfMonth,
                    isSelected = date == uiState.selectedDate.date,
                    onDayClick = { interactionListener.selectDate(date) },
                    isToday = date == uiState.selectedDate.date
                )
            }
        }

        Tabs(
            tabs = allTabs,
            selectedTabIndex = selectedTabIndex,
            onTabClick = {
                interactionListener.selectStatus(
                    when (it) {
                        0 -> Task.Status.IN_PROGRESS
                        1 -> Task.Status.TODO
                        else -> Task.Status.DONE
                    }
                )
            },
            modifier = Modifier.background(Theme.colors.surfaceHigh)
        )

        if (uiState.tasksForSelectedState.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                items(uiState.tasksForSelectedState) { task ->
                    SwipeToDeleteItem(
                        onDelete = { selectedTaskToDelete = task },
                        animationDuration = 200L
                    ) {
                        TaskCard(
                            category = task.category.toCategoryUi(),
                            title = task.title,
                            description = task.description,
                        ) {
                            PriorityChip(
                                text = task.priority.name.lowercase()
                                    .replaceFirstChar { it.uppercase() },
                                backgroundColor = task.priority.getPriorityBackground(),
                                icon = painterResource(task.priority.iconRes()),
                                selected = true
                            )
                        }
                    }
                }
            }
        } else EmptyScreen(modifier = Modifier.padding(start = 10.dp, top = 121.dp))

        selectedTaskToDelete?.let {
            DeleteBottomSheet(
                title = stringResource(R.string.delete_task),
                description = stringResource(R.string.are_you_sure_to_continue),
                onDelete = {
                    interactionListener.deleteTask(it)
                    selectedTaskToDelete = null
                },
                onDismiss = { selectedTaskToDelete = null }
            )
        }

        AddTaskBottomSheet(
            isVisible = bottomSheetUiState.showAddTaskBottomSheet,
            taskTitle = bottomSheetUiState.title,
            onTaskTitleChange = { newTitle ->
                bottomSheetListener.onTitleChange(newTitle)
            },
            taskDescription = bottomSheetUiState.description,
            onTaskDescriptionChange = { newDescription ->
                bottomSheetListener.onDescriptionChange(newDescription)
            },
            selectedPriority = bottomSheetUiState.priority,
            onPrioritySelected = { newPriority ->
                bottomSheetListener.onPriorityClick(newPriority)
            },
            categories = bottomSheetUiState.availableCategories,
            selectedCategory = bottomSheetUiState.category,
            onCategorySelected = { newCategory ->
                bottomSheetListener.onCategoryClick(newCategory)
            },
            selectedDate = bottomSheetUiState.date.toInstant(offset = UtcOffset.ZERO)
                .toEpochMilliseconds(),
            onDateSelected = { newDate ->
                newDate?.let {
                    val instant = Instant.fromEpochMilliseconds(newDate)
                    val date = instant.toLocalDateTime(TimeZone.currentSystemDefault())
                    bottomSheetListener.onDateChange(date)
                }
            },
            onDismiss = { bottomSheetListener.onDismissAddBottomSheet() },
            onCancel = { bottomSheetListener.onCancelAddTask() },
            onSaveTask = { bottomSheetListener.onAddTask() },
        )
    }
}

@Preview(showBackground = true, apiLevel = 33, uiMode = UI_MODE_NIGHT_NO)
@Preview(showBackground = true, apiLevel = 33, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TaskScreenPreview() {
    TudeeTheme {
        TaskScreen(
            viewModel = mockTaskViewModel()
        )
    }
}
