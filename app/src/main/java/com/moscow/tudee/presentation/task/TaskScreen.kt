package com.moscow.tudee.presentation.task

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.component.DayItem
import com.moscow.tudee.presentation.designSystem.component.PriorityChip
import com.moscow.tudee.presentation.designSystem.component.TaskCard
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import com.moscow.tudee.presentation.task.calender.Header
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.koinViewModel
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    val startDate = today.minus(DatePeriod(days = today.dayOfWeek.isoDayNumber - 1))
    val weeks = remember { generateWeeks(startDate) }

    val currentWeekIndex by remember { mutableStateOf(0) }
    val currentWeek = weeks.getOrNull(currentWeekIndex).orEmpty()

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


    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Theme.colors.surface)
    ) {

        val currentMonthYear = currentWeek.firstOrNull()?.let {
            "${it.month.getDisplayName(TextStyle.FULL, Locale.getDefault())}, ${it.year}"
        }
        currentMonthYear?.let {
            Header(it)
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .background(Theme.colors.surfaceHigh)
                .padding(vertical = 8.dp)
        ) {
            items(currentWeek) { date ->
                val dayName = date.dayOfWeek.name.take(3)
                    .lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                val isSelected = date == uiState.selectedDate
                DayItem(
                    day = dayName,
                    dayDate = date.dayOfMonth,
                    isSelected = isSelected,
                    onDayClick = {
                        viewModel.selectDate(date)
                    }
                )
            }
        }

        Tabs(
            tabs = allTabs,
            selectedTabIndex = selectedTabIndex,
            onTabClick = {
                viewModel.selectStatus(
                    when (it) {
                        0 -> Task.Status.IN_PROGRESS
                        1 -> Task.Status.TODO
                        else -> Task.Status.DONE
                    }
                )
            },
            modifier = Modifier.background(Theme.colors.surfaceHigh)

        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp)
        ) {
            items(uiState.tasksForSelectedState) { task ->
                TaskCard(
                    icon = when (task.priority) {
                        Task.Priority.HIGH -> painterResource(id = R.drawable.ic_quran)
                        Task.Priority.MEDIUM -> painterResource(id = R.drawable.ic_briefcase)
                        Task.Priority.LOW -> painterResource(id = R.drawable.ic_trade_down)
                    },
                    title = task.title,
                    description = task.description,
                    iconTint = Theme.colors.secondary
                ) {
                    PriorityChip(
                        text = task.priority.name.lowercase().replaceFirstChar { it.uppercase() },
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

}

private fun generateWeeks(startDate: LocalDate): List<List<LocalDate>> {
    return List(4) { weekOffset ->
        List(7) { dayOffset ->
            startDate.plus(DatePeriod(days = weekOffset * 7 + dayOffset))
        }
    }
}


private val DayOfWeek.isoDayNumber: Int
    get() = when (this) {
        DayOfWeek.MONDAY -> 1
        DayOfWeek.TUESDAY -> 2
        DayOfWeek.WEDNESDAY -> 3
        DayOfWeek.THURSDAY -> 4
        DayOfWeek.FRIDAY -> 5
        DayOfWeek.SATURDAY -> 6
        DayOfWeek.SUNDAY -> 7
    }

@Preview(apiLevel = 33, showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(apiLevel = 33, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TaskScreenPreview() {
    TudeeTheme {
        TaskScreen(
            viewModel = mockViewModel()
        )
    }

}