package com.moscow.tudee.presentation.home
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.component.BottomNavBar
import com.moscow.tudee.presentation.designSystem.component.PriorityChip
import com.moscow.tudee.presentation.designSystem.component.TaskCard
import com.moscow.tudee.presentation.designSystem.component.TudeeText
import com.moscow.tudee.presentation.designSystem.component.slider.SliderState
import com.moscow.tudee.presentation.designSystem.component.slider.TudeeSlider
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale



@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {},
        floatingActionButton = { },
        bottomBar = { BottomNavBar(0, {}) }
    )
    { paddingValues ->

        HomeContent(SliderState.STAY_WORKING,
            listOf(),
            paddingValues)
    }
}

@Composable
fun HomeContent(sliderState: SliderState,
                tasks:List<TaskDetails>,
                paddingValues: PaddingValues) {


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.surface)
                .padding(paddingValues)
        ) {
            item {
                Box(
                    Modifier
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(170.dp)
                            .background(color = Theme.colors.primary)
                            .align(Alignment.TopStart)
                            .zIndex(0f)

                    )
                    OverviewSection(sliderState,tasks)
                }
            }
            tasks.groupBy { it.state.labelResInt }.entries.forEach {
                item {
                    TaskListHeader(it.key, it.value.size)
                    TaskList(it.value, {},)
                }
            }

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
fun TaskListHeader(taskState: Int, taskCount: Int, modifier: Modifier = Modifier) {
    Row(
        modifier

            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        TudeeText(
            value = stringResource(taskState),
            textStyle = Theme.textStyle.title.large.copy(color = Theme.colors.title),
        )
        TaskListCount(taskCount)

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
            value = "$count",
            textStyle = Theme.textStyle.label.small.copy(
                color = Theme.colors.body
            ),
        )
        Image(
            painterResource(R.drawable.ic_right_arrow),
            contentDescription = stringResource(R.string.right_arrow)

        )
    }
}

@Composable
fun OverviewSection(sliderState: SliderState,
                    tasks:List<TaskDetails>,
                    modifier: Modifier = Modifier) {
    val todayDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val formattedDate = todayDate.toJavaLocalDateTime()
        .format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.US))

    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Theme.colors.surfaceHigh, RoundedCornerShape(16.dp))
    ) {
        TodayDate(
            date = formattedDate,
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
        )

        TudeeSlider(sliderState, modifier = Modifier.padding(horizontal = 6.dp))

        TudeeText(
            value = stringResource(R.string.overview),
            textStyle = Theme.textStyle.title.large.copy(color = Theme.colors.title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tasks.groupBy { it.state }.entries.forEach {
                OverviewCard(it.key, it.value.count(), modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun TodayDate(date: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_calendar_favorite),
            contentDescription = stringResource(R.string.calendar_icon)
        )
        TudeeText(
            value = stringResource(R.string.today, date),
            textStyle = Theme.textStyle.body.small.copy(color = Theme.colors.body)
        )
    }
}

@Composable
fun OverviewCard(taskState: TaskState, count: Int, modifier: Modifier = Modifier) {
    val offsetX = 55
    val offsetY = -50

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                color = when (taskState) {
                    TaskState.DONE -> Theme.colors.greenAccent
                    TaskState.IN_PROGRESS -> Theme.colors.yellowAccent
                    TaskState.TODO -> Theme.colors.purpleAccent
                },
                shape = RoundedCornerShape(20.dp)
            )
            .padding(12.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.img_overview_card_background),
            contentDescription = stringResource(R.string.overview_card_background),
            modifier = Modifier
                .offset(x = offsetX.dp, y = offsetY.dp)
                .align(Alignment.TopEnd)
        )

        Column(
            Modifier.wrapContentWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = when (taskState) {
                    TaskState.DONE -> painterResource(R.drawable.ic_done_tasks)
                    TaskState.IN_PROGRESS -> painterResource(R.drawable.ic_in_progress_tasks)
                    TaskState.TODO -> painterResource(R.drawable.ic_to_do_tasks)
                },
                contentDescription = stringResource(R.string.overview_card_icon),
                modifier = Modifier
                    .border(
                        1.dp,
                        Theme.colors.surfaceHigh.copy(alpha = 0.12f),
                        RoundedCornerShape(12.dp)
                    )
                    .background(
                        Theme.colors.surfaceHigh.copy(alpha = 0.24f),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(8.dp)
            )

            TudeeText(
                value = count.toString(),
                textStyle = Theme.textStyle.headline.medium.copy(color = Theme.colors.onPrimary)
            )
            TudeeText(
                value = stringResource(taskState.labelResInt),
                textStyle = Theme.textStyle.label.small.copy(color = Theme.colors.onPrimaryCaption)
            )
        }
    }
}

data class TaskDetails(
    val taskIcon: Int,
    val title: String,
    val description: String,
    val taskIconTint: Color,
    val priority: String,
    val priorityBackgroundColor: Color,
    val priorityIcon: Int,
    val state: TaskState
)

enum class TaskState(val labelResInt: Int) {
    DONE(R.string.done),
    IN_PROGRESS(R.string.in_progress),
    TODO(R.string.to_do)
}

@Preview
@Composable
private fun ShowSmallComponent() {
    val tasks = listOf(
        TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Fix Login Bug",
            description = "Resolve issue with login screen",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "High",
            priorityBackgroundColor =Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,
            state = TaskState.DONE
        ),
        TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Update UI",
            description = "Redesign the home screen UI",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "Medium",
            priorityBackgroundColor =  Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,

            state = TaskState.IN_PROGRESS
        ),
        TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Write Unit Tests",
            description = "Add test coverage to the auth module",
            taskIconTint =  Theme.colors.pinkAccent,
            priority = "Low",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,
            state = TaskState.TODO
        ),
        TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Fix Login Bug",
            description = "Resolve issue with login screen",
            taskIconTint =  Theme.colors.pinkAccent,
            priority = "High",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,
            state = TaskState.DONE
        ),
        TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Update UI",
            description = "Redesign the home screen UI",
            taskIconTint =  Theme.colors.pinkAccent,
            priority = "Medium",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,

            state = TaskState.IN_PROGRESS
        ),
        TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Write Unit Tests",
            description = "Add test coverage to the auth module",
            taskIconTint =  Theme.colors.pinkAccent,
            priority = "Low",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,
            state = TaskState.TODO
        ),
        TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Fix Login Bug",
            description = "Resolve issue with login screen",
            taskIconTint = Theme.colors.pinkAccent,
            priority = "High",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,
            state = TaskState.DONE
        ),
        TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Update UI",
            description = "Redesign the home screen UI",
            taskIconTint =  Theme.colors.pinkAccent,
            priority = "Medium",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,

            state = TaskState.IN_PROGRESS
        ),
        TaskDetails(
            taskIcon = R.drawable.ic_briefcase,
            title = "Write Unit Tests",
            description = "Add test coverage to the auth module",
            taskIconTint =  Theme.colors.pinkAccent,
            priority = "Low",
            priorityBackgroundColor = Theme.colors.yellowAccent,
            priorityIcon = R.drawable.ic_alert,
            state = TaskState.TODO
        )
    )
    TudeeTheme {
        HomeContent(SliderState.STAY_WORKING , tasks, PaddingValues(0.dp))
    }
}
