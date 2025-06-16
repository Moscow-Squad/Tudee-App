package com.moscow.tudee.presentation.task

import androidx.compose.material3.Card
import com.moscow.tudee.presentation.designSystem.theme.Theme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.component.CustomFAB
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskScreen(viewModel: TaskViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val tabs = listOf("In progress", "To Do", "Done")

    Scaffold(
        floatingActionButton = {
               CustomFAB(
                   onClick = {  }
               )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(selectedTabIndex = state.selectedTab) {
                tabs.forEachIndexed { i, title ->
                    Tab(
                        selected = i == state.selectedTab,
                        onClick = { viewModel.onTabSelected(i) },
                        text = { Text(title) }
                    )
                }
            }

            val filtered = when (state.selectedTab) {
                0 -> state.tasks.filter { it.status == Status.InProgress }
                1 -> state.tasks.filter { it.status == Status.ToDo }
                2 -> state.tasks.filter { it.status == Status.Done }
                else -> state.tasks
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filtered.size) { i ->
                    TaskCard(filtered[i])
                }
            }
        }
    }
}

@Composable
fun TaskCard(task: Task) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                task.title,
            )

            Text(
                task.description,
            )
            Spacer(Modifier.height(8.dp))
            Chip(text = task.priority.name)
        }
    }
}

@Composable
fun Chip(text: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = when (text) {
            "Low" -> Theme.colors.primary.copy(alpha = 0.1f)
            "Medium" -> Theme.colors.secondary.copy(alpha = 0.1f)
            "High" -> Theme.colors.error.copy(alpha = 0.1f)
            else -> Theme.colors.primary.copy(alpha = 0.1f)
        }
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        )
    }
}
