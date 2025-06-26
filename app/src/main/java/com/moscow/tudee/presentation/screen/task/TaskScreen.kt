package com.moscow.tudee.presentation.screen.task

import SwipeToDeleteItem
import android.content.Context
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.component.AddTaskBottomSheet
import com.moscow.tudee.presentation.component.CustomFAB
import com.moscow.tudee.presentation.component.DatePickerModal
import com.moscow.tudee.presentation.component.EmptyScreen
import com.moscow.tudee.presentation.component.PriorityChip
import com.moscow.tudee.presentation.component.SnackBar
import com.moscow.tudee.presentation.component.TaskCard
import com.moscow.tudee.presentation.component.bottomSheet.DeleteBottomSheet
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import com.moscow.tudee.presentation.mapper.toCategory
import com.moscow.tudee.presentation.mapper.toCategoryUi
import com.moscow.tudee.presentation.screen.category.component.CategoryTabs
import com.moscow.tudee.presentation.screen.category.component.TabItem
import com.moscow.tudee.presentation.screen.task.components.DayItem
import com.moscow.tudee.presentation.screen.task.components.Header
import com.moscow.tudee.presentation.util.getPriorityBackground
import com.moscow.tudee.presentation.util.iconRes
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import okhttp3.internal.immutableListOf
import org.koin.androidx.compose.koinViewModel
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = koinViewModel(),
    addTaskBottomSheetViewModel: AddTaskBottomSheetViewModel = koinViewModel(),
    status: Task.Status = Task.Status.TODO
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
    val allTabs = immutableListOf(
        TabItem(
            stringResource(R.string.to_do),
            uiState.tasksForSelectedState.size,
            Task.Status.TODO
        ),
        TabItem(
            stringResource(R.string.in_progress_status),
            uiState.tasksForSelectedState.size,
            Task.Status.IN_PROGRESS
        ),
        TabItem(stringResource(R.string.done), uiState.tasksForSelectedState.size, Task.Status.DONE)
    )

    val lazyListState = rememberLazyListState()
    val isAtStart by remember { derivedStateOf { lazyListState.firstVisibleItemIndex == 0 } }
    var selectedTaskToDelete by remember { mutableStateOf<Task?>(null) }
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(status) {
        viewModel.selectStatus(status)
    }

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.loadTasks()
        }
    }

    LaunchedEffect(uiState.selectedDate) {
        val index = uiState.monthDays.indexOf(uiState.selectedDate.date)
        if (index >= 0) lazyListState.animateScrollToItem(index)
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

    TaskContent(
        interactionListener = viewModel,
        uiState = uiState,
        showDatePicker = showDatePicker,
        bottomSheetUiState = bottomSheetUiState,
        bottomSheetListener = addTaskBottomSheetViewModel,
        snackbarHostState = snackbarHostState,
        snackBar = snackBar,
        showCustomSnackBar = showCustomSnackBar,
        allTabs = allTabs,
        isAtStart = isAtStart,
        selectedTaskToDelete = selectedTaskToDelete,
        lazyListState = lazyListState,
        onDeletedTask = { selectedTaskToDelete = it }
    )
}

@Composable
private fun TaskContent(
    modifier: Modifier = Modifier,
    interactionListener: TaskScreenInteractionListener,
    uiState: TaskUiState,
    showDatePicker: Boolean,
    bottomSheetUiState: AddTaskBottomSheetUiState,
    bottomSheetListener: AddTaskBottomSheetViewModel,
    snackbarHostState: SnackbarHostState,
    snackBar: SnackBarUi,
    showCustomSnackBar: Boolean,
    allTabs: List<TabItem>,
    isAtStart: Boolean,
    selectedTaskToDelete: Task?,
    lazyListState: LazyListState,
    onDeletedTask: (Task?) -> Unit
) {
    val context = LocalContext.current
    Box(Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Theme.colors.surface,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Theme.colors.surfaceHigh)
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                ) {
                    Text(
                        text = stringResource(R.string.tasks),
                        style = Theme.textStyle.title.large,
                        color = Theme.colors.title
                    )
                }
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            floatingActionButton = {
                CustomFAB(
                    onClick = { bottomSheetListener.onShowAddTaskBottomSheet() },
                    icon = R.drawable.ic_add_task
                )
            }
        ) { innerPadding ->
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .background(Theme.colors.surface)
                    .fillMaxSize()
            ) {
                if (showDatePicker) {
                    DatePickerModal(
                        onDateSelected = { interactionListener.updateMonthFromPicker(it) },
                        onDismiss = interactionListener::dismissDatePicker,
                        selectedDate = uiState.selectedDate.toInstant(UtcOffset.ZERO)
                            .toEpochMilliseconds()
                    )
                }

                Header(
                    date = "${
                        uiState.currentMonth.getDisplayName(
                            TextStyle.FULL,
                            Locale.getDefault()
                        )
                    }, ${context.localizeNumber(uiState.currentYear)}",
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
                        val dayName = context.getLocalizedDayName(date.dayOfWeek.value)
                        val dayDate = context.localizeNumber(date.dayOfMonth)

                        DayItem(
                            day = dayName,
                            dayDate = dayDate,
                            isSelected = date == uiState.selectedDate.date,
                            onDayClick = { interactionListener.selectDate(date) },
                            isToday = date == uiState.selectedDate.date
                        )
                    }
                }

                CategoryTabs(
                    tabs = allTabs,
                    selectedStatus = uiState.selectedStatus,
                    onTabClick = { status -> interactionListener.selectStatus(status) },
                    modifier = Modifier.background(Theme.colors.surfaceHigh)
                )

                if (uiState.tasksForSelectedState.isNotEmpty()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        items(uiState.tasksForSelectedState) { task ->
                            SwipeToDeleteItem(
                                onDelete = { onDeletedTask(task) },
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
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        EmptyScreen(modifier = Modifier.padding(16.dp))
                    }
                }

                selectedTaskToDelete?.let {
                    DeleteBottomSheet(
                        title = stringResource(R.string.delete_task),
                        description = stringResource(R.string.are_you_sure_to_continue),
                        onDelete = {
                            interactionListener.deleteTask(it)
                            onDeletedTask(null)
                        },
                        onDismiss = { onDeletedTask(null) }
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
                    categories = bottomSheetUiState.availableCategories.map { it.toCategoryUi() },
                    selectedCategory = bottomSheetUiState.category?.toCategoryUi(),
                    onCategorySelected = { newCategory ->
                        bottomSheetListener.onCategoryClick(newCategory.toCategory())
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

    }
}

fun Context.getLocalizedDayName(dayOfWeek: Int): String {
    val fullName = when (dayOfWeek) {
        1 -> getString(R.string.monday)
        2 -> getString(R.string.tuesday)
        3 -> getString(R.string.wednesday)
        4 -> getString(R.string.thursday)
        5 -> getString(R.string.friday)
        6 -> getString(R.string.saturday)
        7 -> getString(R.string.sunday)
        else -> ""
    }

    val currentLang = resources.configuration.locales[0].language
    return if (currentLang == "en") {
        fullName.take(3).replaceFirstChar { it.uppercaseChar() }
    } else {
        fullName
    }
}

fun Context.localizeNumber(number: Int): String {
    val locale = resources.configuration.locales[0]
    return String.format(locale, "%d", number)
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
