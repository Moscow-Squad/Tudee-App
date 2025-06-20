package com.moscow.tudee.presentation.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import com.moscow.tudee.presentation.component.Tab
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.component.Tabs
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.component.PriorityChip
import com.moscow.tudee.presentation.designSystem.component.TaskCard
import com.moscow.tudee.presentation.designSystem.component.scaffold.TudeeScaffold
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.util.getPriorityBackground
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

val tabs = listOf(
    Tab("In progress", 14),
    Tab("To Do", 10),
    Tab("Done", 5),
)

@Preview
@Composable
fun NewCategoryScreen(modifier: Modifier = Modifier) {
    val sampleTasks = listOf(
        Task(
            id = 1,
            title = "Buy groceries",
            description = "Milk, Bread, Eggs, Cheese",
            priority = Task.Priority.MEDIUM,
            categoryId = 101,
            status = Task.Status.TODO,
            date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        ), Task(
            id = 2,
            title = "Workout",
            description = "1 hour of cardio",
            priority = Task.Priority.HIGH,
            categoryId = 102,
            status = Task.Status.IN_PROGRESS,
            date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        ), Task(
            id = 3,
            title = "Call Mom",
            description = "Weekly check-in call",
            priority = Task.Priority.LOW,
            categoryId = 103,
            status = Task.Status.DONE,
            date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        ), Task(
            id = 4,
            title = "Submit project",
            description = "Finish and submit Android project",
            priority = Task.Priority.HIGH,
            categoryId = 104,
            status = Task.Status.TODO,
            date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        ), Task(
            id = 4,
            title = "Submit project",
            description = "Finish and submit Android project",
            priority = Task.Priority.HIGH,
            categoryId = 104,
            status = Task.Status.TODO,
            date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        ), Task(
            id = 4,
            title = "Submit project",
            description = "Finish and submit Android project",
            priority = Task.Priority.HIGH,
            categoryId = 104,
            status = Task.Status.TODO,
            date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        ), Task(
            id = 4,
            title = "Submit project",
            description = "Finish and submit Android project",
            priority = Task.Priority.HIGH,
            categoryId = 104,
            status = Task.Status.TODO,
            date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )
    )

    NewCategoryContent(sampleTasks)
}

@Composable
fun NewCategoryContent(tasks: List<Task>, modifier: Modifier = Modifier) {

    TudeeScaffold(
        topBar = {
            Column {
                ScreenBar("Coding", false)
                Tabs(
                    tabs = tabs,
                    {},
                    modifier = modifier
                        .background(Theme.colors.surfaceHigh)
                        .padding(top = 8.dp)
                )
            }
        }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 12.dp)

        ) {
            items(tasks) {
//                TaskCard(
//                    modifier = modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 4.dp)
//                        .clickable { },
//                    icon = painterResource(R.drawable.ic_developer),
//                    title = it.title,
//                    description = it.description,
//                    date = it.date.date.toString()
//                ) {
//                    PriorityChip(
//                        text = it.priority.name,
//                        backgroundColor = it.priority.getPriorityBackground(),
//                        icon = painterResource(id = R.drawable.ic_alert/*it.priority.ordinal*/)
//                    )
//                }
            }

        }
    }

}

@Composable
fun ScreenBar(categoryName: String, categoryType: Boolean, modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Theme.colors.surfaceHigh)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Image(
            painter = painterResource(R.drawable.ic_arrow_head_back),
            contentDescription = stringResource(R.string.back_button),
            modifier = Modifier
                .border(
                    width = 1.dp, color = Theme.colors.stroke, shape = CircleShape
                )
                .padding(12.dp)
        )
        TudeeText(
            text = categoryName, style = Theme.textStyle.title.large, color = Theme.colors.title
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.ic_pencil_edit),
            contentDescription = stringResource(R.string.edit_category_icon),
            modifier = Modifier
                .border(
                    width = 1.dp, color = Theme.colors.stroke, shape = CircleShape
                )
                .padding(12.dp)
        )
    }
}