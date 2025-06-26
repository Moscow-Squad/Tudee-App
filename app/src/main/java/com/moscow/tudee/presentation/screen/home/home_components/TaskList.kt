package com.moscow.tudee.presentation.screen.home.home_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task.Status
import com.moscow.tudee.presentation.component.PriorityChip
import com.moscow.tudee.presentation.component.TaskCard
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.mapper.getColor
import com.moscow.tudee.presentation.mapper.getIcon
import com.moscow.tudee.presentation.mapper.getText
import com.moscow.tudee.presentation.model.CategoryUi
import com.moscow.tudee.presentation.model.TaskUi

@Composable
fun TaskListHeader(
    taskState: Status, taskCount: Int, onCountClick: () -> Unit, modifier: Modifier = Modifier
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
                Status.IN_PROGRESS -> stringResource(R.string.in_progress_status)
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
    tasks: List<TaskUi>, onTaskClick: (TaskUi) -> Unit, modifier: Modifier = Modifier
) {

    androidx.compose.foundation.layout.FlowRow(

        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        maxItemsInEachRow = 5
    ) {
        tasks.take(10).forEach { task ->
            TaskCard(
                modifier = modifier
                    .width(320.dp)
                    .clickable { onTaskClick(task) },
                category = task.category ?: CategoryUi(),
                title = task.title,
                description = task.description,
            ) {
                task.priority?.let { priority ->
                    PriorityChip(
                        text = priority.getText(),
                        backgroundColor = priority.getColor(),
                        icon = painterResource(id = priority.getIcon()),
                        selected = true
                    )
                }
            }
        }
    }


}

@Composable
fun TaskListCount(
    count: Int, onCountClick: () -> Unit, modifier: Modifier = Modifier
) {
    Row(modifier
        .clickable { onCountClick() }
        .background(color = Theme.colors.surfaceHigh, shape = CircleShape)
        .padding(vertical = 6.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        TudeeText(
            text = "$count",
            maxLines = 1,
            color =Theme.colors.body ,
            style = Theme.textStyle.label.small,
        )
        Image(
            painterResource(R.drawable.ic_right_arrow),
            colorFilter = ColorFilter.tint(Theme.colors.body),
            contentDescription = stringResource(R.string.right_arrow)

        )
    }
}

