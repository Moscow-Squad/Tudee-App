package com.moscow.tudee.presentation.category.categoryTasks

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.ObserveAsEvent
import com.moscow.tudee.presentation.category.categoryScreen.CategoriesEvents
import com.moscow.tudee.presentation.category.categoryScreen.CategoriesInteractionListener
import com.moscow.tudee.presentation.category.categoryScreen.CategoriesScreenState
import com.moscow.tudee.presentation.category.categoryScreen.CategoryViewModel
import com.moscow.tudee.presentation.component.Tabs
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.component.PriorityChip
import com.moscow.tudee.presentation.designSystem.component.TaskCard
import com.moscow.tudee.presentation.designSystem.component.scaffold.TudeeScaffold
import com.moscow.tudee.presentation.designSystem.theme.Theme
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryTasksScreen(
    categoryId: Long,
    viewModel: CategoryTasksViewModel = koinViewModel(),
//    navigateBackToCategoryScreen: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    ObserveAsEvent(viewModel.uiEvent) { event ->
        when (event) {
            is CategoriesEvents.NavigateToTasks -> {
//                navigateBackToCategoryScreen()
            }
        }
    }
    CategoryTasksContent(
        categoryId,
        uiState.value,
        viewModel
    )
}

@Composable
fun CategoryTasksContent(
    categoryId: Long,
    uiState: CategoriesScreenState,
    categoriesInteractionListener: CategoriesTasksInteractionListener,
) {
    val tabs = listOf(
        Tab("In progress", uiState.categories.size),
        Tab("To Do", uiState.categories.size),
        Tab("Done", uiState.categories.size),
    )
    TudeeScaffold(
        topBar = {
            Column {
                ScreenBar(uiState.category.title, false)
                Tabs(
                    tabs = tabs,
                    {},
                    modifier = Modifier
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
            items(uiState.tasks) {
//                TaskCard(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 4.dp)
//                        .clickable { },
//                    icon = painterResource(R.drawable.ic_developer),
//                    title = it.title,
//                    description = it.description,
//                    date = it.date
//                ) {
//                    PriorityChip(
//                        text = it.priority,
//                        backgroundColor = when (it.priority) {
//                            Task.Priority.HIGH.toString() -> Theme.colors.pinkAccent
//                            Task.Priority.MEDIUM.toString() -> Theme.colors.yellowAccent
//                            Task.Priority.LOW.toString() -> Theme.colors.greenAccent
//                            else -> {
//                                Theme.colors.greenAccent
//                            }
//                        },
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


@Preview
@Composable
fun CategoryTasksPreview(
    modifier: Modifier = Modifier
) {

//
//    CategoryTasksContent(1L,sampleTasks)
}