package com.moscow.tudee.presentation.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.designSystem.component.TaskCard
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.component.PriorityChip
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors

data class TaskInfo(
    val icon: Int,
    val title: String,
    val description: String,
    val date: String,
    val priority: String
)

@Composable
fun CategoryScreen(
    tasks: List<TaskInfo>
) {
    // there is topBar has the Category name  of these tasks "Coding, "Education", ... ??
    // Where are Tabs (In progress, To Do, Done)
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tasks.size) { currentTask ->
            TaskCard(
                icon = painterResource(tasks[currentTask].icon),
                title = tasks[currentTask].title,
                description = tasks[currentTask].description,
                date = tasks[currentTask].date,
            ) {
                Priority(priority = tasks[currentTask].priority)
            }
        }
    }
}

@Composable
fun Priority(priority: String) {
    when (priority) {
        "High" -> {
            PriorityChip(
                text = priority,
                backgroundColor = colors.pinkAccent,
                icon = painterResource(id = R.drawable.ic_flag)
            )
        }

        "Medium" -> {
            PriorityChip(
                text = priority,
                backgroundColor = colors.yellowAccent,
                icon = painterResource(id = R.drawable.ic_alert)
            )
        }

        "Low" -> {
            PriorityChip(
                text = priority,
                backgroundColor = colors.greenAccent,
                icon = painterResource(id = R.drawable.ic_trade_down)
            )
        }
    }
}

@Preview(apiLevel = 34)
@Composable
fun CategoryScreenPreview() {
    val tasks: List<TaskInfo> = listOf(
        TaskInfo(
            icon = R.drawable.ic_quran,
            title = "Review Flashcards",
            description = "Study biology flashcards for 15 minutes",
            date = "03/12/2025",
            priority = "High"
        ),
        TaskInfo(
            icon = R.drawable.ic_briefcase,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            date = "03/12/2025",
            priority = "Medium"
        ),
        TaskInfo(
            icon = R.drawable.ic_quran,
            title = "Review Flashcards",
            description = "Study biology flashcards for 15 minutes",
            date = "03/12/2025",
            priority = "Low"
        ),
        TaskInfo(
            icon = R.drawable.ic_briefcase,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            date = "03/12/2025",
            priority = "High"
        ),
        TaskInfo(
            icon = R.drawable.ic_quran,
            title = "Review Flashcards",
            description = "Study biology flashcards for 15 minutes",
            date = "03/12/2025",
            priority = "High"
        ),
        TaskInfo(
            icon = R.drawable.ic_briefcase,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            date = "03/12/2025",
            priority = "Medium"
        ),
        TaskInfo(
            icon = R.drawable.ic_quran,
            title = "Review Flashcards",
            description = "Study biology flashcards for 15 minutes",
            date = "03/12/2025",
            priority = "Medium"
        ),
        TaskInfo(
            icon = R.drawable.ic_briefcase,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            date = "03/12/2025",
            priority = "Medium"
        ),
        TaskInfo(
            icon = R.drawable.ic_quran,
            title = "Review Flashcards",
            description = "Study biology flashcards for 15 minutes",
            date = "03/12/2025",
            priority = "Low"
        ),
        TaskInfo(
            icon = R.drawable.ic_briefcase,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            date = "03/12/2025",
            priority = "Medium"
        ),
    )
    CategoryScreen(tasks = tasks)
}