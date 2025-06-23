package com.moscow.tudee.presentation.task

import SwipeToDeleteItem
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.component.DatePickerModal
import com.moscow.tudee.presentation.component.DayItem
import com.moscow.tudee.presentation.component.Tab
import com.moscow.tudee.presentation.component.Tabs
import com.moscow.tudee.presentation.component.bottomSheet.DeleteBottomSheet
import com.moscow.tudee.presentation.designSystem.component.PriorityChip
import com.moscow.tudee.presentation.designSystem.component.TaskCard
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import com.moscow.tudee.presentation.task.components.EmptyScreen
import com.moscow.tudee.presentation.task.components.Header
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.koinViewModel
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val showDatePicker by viewModel.showDatePicker.collectAsStateWithLifecycle()

    TaskContent(
        interactionListener = viewModel,
        uiState = uiState,
        showDatePicker = showDatePicker
    )
}


@Composable
private fun TaskContent(
    modifier: Modifier = Modifier,
    interactionListener: TaskScreenInteractionListener,
    uiState: TaskUiState,
    showDatePicker: Boolean
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
    val currentMonthYear = uiState.currentMonth.getDisplayName(
        TextStyle.FULL,
        Locale.getDefault()
    ) + ", ${uiState.currentYear}"
    val lazyListState = rememberLazyListState()
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val isAtStart by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0
        }
    }

    var selectedTaskToDelete by remember { mutableStateOf<Task?>(null) }

    LaunchedEffect(uiState.selectedDate) {
        val index = uiState.monthDays.indexOf(uiState.selectedDate)
        if (index >= 0) {
            lazyListState.animateScrollToItem(index)
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Theme.colors.surface)
    ) {

        if (showDatePicker) {
            DatePickerModal(
                onDateSelected = { epochMillis ->
                    interactionListener.updateMonthFromPicker(epochMillis)
                },
                onDismiss = interactionListener::dismissDatePicker,
                selectedDate = uiState.selectedDate
                    .atStartOfDayIn(TimeZone.currentSystemDefault())
                    .toEpochMilliseconds()
            )
        }

        Header(
            currentMonthYear,
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
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 8.dp
                ),
        ) {
            items(uiState.monthDays) { date ->
                val dayName = date.dayOfWeek.name.take(3)
                    .lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                val isSelected = date == uiState.selectedDate
                val isToday = date == today
                DayItem(
                    day = dayName,
                    dayDate = date.dayOfMonth,
                    isSelected = isSelected,
                    onDayClick = {
                        interactionListener.selectDate(date)
                    },
                    isToday = isToday
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
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp)
            ) {
                items(uiState.tasksForSelectedState) { task ->
                    SwipeToDeleteItem(
                        onDelete = { selectedTaskToDelete = task },
                        animationDuration = 200L
                    ) {
                        TaskCard(
                            category = Category(
                                id = task.category.id,
                                title = task.category.title,
                                iconUri = task.category.iconUri,
                                isPredefined = task.category.isPredefined
                            ),
                            title = task.title,
                            description = task.description,
                        ) {
                            PriorityChip(
                                text = task.priority.name.lowercase()
                                    .replaceFirstChar { it.uppercase() },
                                backgroundColor = when (task.priority) {
                                    Task.Priority.HIGH -> Theme.colors.pinkAccent
                                    Task.Priority.MEDIUM -> Theme.colors.yellowAccent
                                    Task.Priority.LOW -> Theme.colors.greenAccent
                                },
                                icon = when (task.priority) {
                                    Task.Priority.HIGH -> painterResource(id = R.drawable.ic_flag)
                                    Task.Priority.MEDIUM -> painterResource(id = R.drawable.ic_alert)
                                    Task.Priority.LOW -> painterResource(id = R.drawable.ic_trade_down)
                                }
                            )
                        }
                    }
                }
            }
        } else EmptyScreen(modifier = Modifier.padding(start = 16.dp, top = 121.dp))

        AnimatedVisibility(
            visible = selectedTaskToDelete != null
        ) {
            DeleteBottomSheet(
                title = stringResource(R.string.delete_task),
                description = stringResource(R.string.are_you_sure_to_continue),
                onDelete = {
                    selectedTaskToDelete?.let { interactionListener.deleteTask(it) }
                    selectedTaskToDelete = null
                           },
                onDismiss = {
                    selectedTaskToDelete = null
                }
            )
        }
    }
}

@Preview(showBackground = true,apiLevel = 33, uiMode = UI_MODE_NIGHT_NO)
@Preview(showBackground = true,apiLevel = 33, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TaskScreenPreview() {
    TudeeTheme {
        TaskScreen(
            viewModel = mockTaskViewModel()
        )
    }

}