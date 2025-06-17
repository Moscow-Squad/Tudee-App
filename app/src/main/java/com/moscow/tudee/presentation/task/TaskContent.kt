package com.moscow.tudee.presentation.task

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task.Priority
import com.moscow.tudee.presentation.designSystem.component.PriorityChip
import com.moscow.tudee.presentation.designSystem.component.TaskCard
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import com.moscow.tudee.presentation.task.calender.CalendarStrip
import com.moscow.tudee.presentation.task.calender.Content
import com.moscow.tudee.presentation.task.calender.Header
import java.time.LocalDate

@Composable
fun TaskContent(
    uiState: TaskUiState,
    onDaySelected: (Int) -> Unit,
) {
    var selectedTab by remember { mutableStateOf(0) }

    val tasks = when (selectedTab) {
        0 -> listOf(
            Task(
                "Study Biology",
                "Review flashcards for 30 mins",
                painterResource(R.drawable.ic_quran),
                "17/06/2025",
                Priority.HIGH
            ),
            Task(
                "Read Kotlin Guide",
                "Jetpack Compose and Coroutines",
                painterResource(R.drawable.ic_alert),
                null,
                Priority.MEDIUM
            ),
            Task(
                "Write Summary",
                "Summarize Clean Code book",
                painterResource(R.drawable.ic_briefcase),
                "18/06/2025",
                Priority.LOW
            )
        )

        1 -> listOf(
            Task(
                "Update Project",
                "Push latest changes to GitHub",
                painterResource(R.drawable.ic_briefcase),
                "19/06/2025",
                Priority.HIGH
            ),
            Task(
                "Schedule Meeting",
                "Plan sync with squad",
                painterResource(R.drawable.ic_flag),
                null,
                Priority.MEDIUM
            )
        )

        2 -> listOf(
            Task(
                "Buy Stationery",
                "Pens, notebooks, sticky notes",
                painterResource(R.drawable.ic_chef),
                "15/06/2025",
                Priority.LOW
            )
        )

        else -> emptyList()
    }
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Tasks",
            style = Theme.textStyle.title.large,
            color = Theme.colors.title
        )
        Header()
        Content()
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(tasks) { task ->
                TaskCard(
                    task.iconRes,
                    task.title,
                    task.description,
                    task.date,
                    iconTint = Theme.colors.secondary,
                    modifier = Modifier.padding(vertical = 8.dp),
                    priorityChip = {
                        PriorityChip(
                            text = "Medium",
                            backgroundColor = Theme.colors.yellowAccent,
                            icon = painterResource(id = R.drawable.ic_alert)
                        )
                    }
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    val days =
        listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").mapIndexed { index, name ->
            name to LocalDate.now().plusDays(index.toLong()).dayOfMonth
        }
    val previewState = TaskUiState(
        weeks = listOf(
            Week(
                "Week 1", listOf(
                    CalendarDay(LocalDate.now().plusDays(0), days[0].first, days[0].second),
                    CalendarDay(LocalDate.now().plusDays(1), days[1].first, days[1].second),
                    CalendarDay(LocalDate.now().plusDays(2), days[2].first, days[2].second),
                    CalendarDay(LocalDate.now().plusDays(3), days[3].first, days[3].second),
                    CalendarDay(LocalDate.now().plusDays(4), days[4].first, days[4].second),
                    CalendarDay(LocalDate.now().plusDays(5), days[5].first, days[5].second),
                    CalendarDay(LocalDate.now().plusDays(6), days[6].first, days[6].second)
                )
            )
        ),
        selectedDayIndex = 5,
        selectedDate = LocalDate.now().plusDays(5),
        allTasks = listOf(
            Task(
                "1",
                "Sample Task",
                painterResource(R.drawable.ic_briefcase),
                "15/06/2025",
                Priority.LOW
            )
        )
    )
    TaskContent(uiState = previewState, onDaySelected = {})
}

@Preview(apiLevel = 33, showBackground = true)
@Composable
private fun TaskContentPreview() {
    TudeeTheme {
        TaskContent(
            uiState = TaskUiState(),
            onDaySelected = {}
        )
    }
}
