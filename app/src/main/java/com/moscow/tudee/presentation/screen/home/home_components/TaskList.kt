package com.moscow.tudee.presentation.screen.home.home_components

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
import androidx.compose.foundation.lazy.grid.GridItemSpan
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
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.entity.Task.Status
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.component.PriorityChip
import com.moscow.tudee.presentation.designSystem.component.TaskCard
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.screen.home.getColor
import com.moscow.tudee.presentation.screen.home.getIcon
import com.moscow.tudee.presentation.screen.home.getText

@Composable
fun TaskListHeader(
    taskState: Status,
    taskCount: Int,
    onCountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        TudeeText(
            text = when (taskState) {
                Status.IN_PROGRESS -> stringResource(R.string.in_progress)
                Status.TODO -> stringResource(R.string.to_do)
                Status.DONE -> stringResource(R.string.done)
            },
            style = Theme.textStyle.title.large.copy(color = Theme.colors.title),
        )
        TaskListCount(taskCount, onCountClick)

    }
}

@Composable
fun TaskList(
    tasks: List<Task>,
    onTaskClick: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyHorizontalGrid(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp),
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(tasks) { task ->
            TaskCard(
                modifier = Modifier
                    .width(320.dp)
                    .clickable { onTaskClick(task) },
                category = task.category!!,
                title = task.title,
                description = task.description,
            ) {
                PriorityChip(
                    text = task.priority.getText(),
                    backgroundColor = task.priority.getColor(),
                    icon = painterResource(id = task.priority.getIcon()),
                    selected = true
                )
            }
        }

    }

}

@Composable
fun TaskListCount(
    count: Int,
    onCountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .clickable { onCountClick() }
            .background(color = Theme.colors.surfaceHigh, shape = CircleShape)
            .padding(vertical = 6.dp, horizontal = 8.dp)
           ,
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

