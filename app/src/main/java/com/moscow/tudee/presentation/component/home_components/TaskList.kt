package com.moscow.tudee.presentation.component.home_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.component.PriorityChip
import com.moscow.tudee.presentation.designSystem.component.TaskCard
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.screen.home.TaskDetails

@Composable
fun TaskListHeader(taskState: Int, taskCount: Int, modifier: Modifier = Modifier) {
    Row(
        modifier

            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        TudeeText(
            text = stringResource(taskState),
            style = Theme.textStyle.title.large.copy(color = Theme.colors.title),
        )
        TaskListCount(taskCount)

    }
}

@Composable
fun TaskList(
    taskDetails: List<TaskDetails>,
    onTaskClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyHorizontalGrid(
        modifier = Modifier.height(230.dp),
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(taskDetails) {
            TaskCard(
                modifier = modifier
                    .width(320.dp)
                    .clickable { onTaskClick() },
                icon = painterResource(it.taskIcon),
                title = it.title,
                description = it.description,
                iconTint = it.taskIconTint
            ) {
                PriorityChip(
                    text = it.priority,
                    backgroundColor = it.priorityBackgroundColor,
                    icon = painterResource(id = it.priorityIcon)
                )
            }
        }

    }

}

@Composable
fun TaskListCount(count: Int, modifier: Modifier = Modifier) {

    Row(
        modifier
            .background(color = Theme.colors.surfaceHigh, shape = CircleShape)
            .padding(vertical = 6.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TudeeText(
            text = "$count",
            style = Theme.textStyle.label.small.copy(
                color = Theme.colors.body
            ),
        )
        Image(
            painterResource(R.drawable.ic_right_arrow),
            contentDescription = stringResource(R.string.right_arrow)

        )
    }
}

