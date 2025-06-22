package com.moscow.tudee.presentation.category.categoryTasks

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.moscow.tudee.presentation.category.categoryScreen.CategoriesScreenState
import com.moscow.tudee.presentation.category.categoryScreen.toCategory
import com.moscow.tudee.presentation.component.TabItem
import com.moscow.tudee.presentation.component.Tabs
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.component.PriorityChip
import com.moscow.tudee.presentation.designSystem.component.TaskCard
import com.moscow.tudee.presentation.designSystem.component.scaffold.TudeeScaffold
import com.moscow.tudee.presentation.designSystem.theme.Theme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryTasksScreen(
    categoryId: Long,
    viewModel: CategoryTasksViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ObserveAsEvent(viewModel.uiEvent) { event ->
        when (event) {
            is CategoriesEvents.NavigateToTasks -> {
//                navigateBackToCategoryScreen()
            }
        }
    }
    CategoryTasksContent(
        categoryId = categoryId,
        uiState = uiState,
        categoriesInteractionListener = viewModel
    )
}

@Composable
fun CategoryTasksContent(
    categoryId: Long,
    uiState: CategoriesScreenState,
    categoriesInteractionListener: CategoriesTasksInteractionListener
) {
    var selectedStatus by rememberSaveable { mutableStateOf(Task.Status.TODO) }
    val tabs = listOf(
        TabItem("To Do", uiState.categories.size, Task.Status.TODO),
        TabItem("In progress", uiState.categories.size, Task.Status.IN_PROGRESS),
        TabItem("Done", uiState.categories.size, Task.Status.DONE)
    )
    TudeeScaffold(
        topBar = {
            Column {
                ScreenBar(uiState.category.title)
                Tabs(
                    tabs = tabs,
                    selectedStatus = selectedStatus,
                    onTabClick = { status ->
                        selectedStatus = status
                        categoriesInteractionListener.onTasksStatusClick(categoryId, status)
                    },
                    modifier = Modifier
                        .background(Theme.colors.surfaceHigh)
                        .padding(top = 8.dp)
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 12.dp)
        ) {
            items(uiState.tasks) { task ->
                TaskCard(
                    category = uiState.category.toCategory(),
                    title = task.title,
                    description = task.description,
                    date = task.date
                ) {
                    PriorityChip(
                        text = task.priority,
                        backgroundColor = when (task.priority) {
                            "High" -> Theme.colors.pinkAccent
                            "Medium" -> Theme.colors.yellowAccent
                            else -> Theme.colors.greenAccent
                        },
                        icon = painterResource(
                            id = when (task.priority) {
                                "High" -> R.drawable.ic_flag
                                else -> R.drawable.ic_alert
                            }
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ScreenBar(categoryName: String, modifier: Modifier = Modifier) {
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
                .border(1.dp, Theme.colors.stroke, CircleShape)
                .padding(12.dp)
        )
        TudeeText(
            text = categoryName,
            style = Theme.textStyle.title.large,
            color = Theme.colors.title
        )
        Spacer(Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.ic_pencil_edit),
            contentDescription = stringResource(R.string.edit_category_icon),
            modifier = Modifier
                .border(1.dp, Theme.colors.stroke, CircleShape)
                .padding(12.dp)
        )
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun CategoryTasksPreview() {
    val sampleState = CategoriesScreenState(
        category = CategoriesScreenState.CategoryUi(id = 1L, title = "Sample"),
        categories = emptyList(),
        tasks = listOf(
            CategoriesScreenState.TaskUi(
                title = "Review Flashcards",
                description = "Study biology flashcards",
                priority = "High",
                date = "03/12/2025"
            )
        ),
        isLoading = false,
        isSnackBarShow = false,
        errorMessage = null
    )
    CategoryTasksContent(
        categoryId = 1L,
        uiState = sampleState,
        categoriesInteractionListener = object : CategoriesTasksInteractionListener {
            override fun onTasksStatusClick(categoryID: Long, status: Task.Status) {}
            override fun onShowEditCategoryBottomSheet() {}
            override fun onHideEditCategoryBottomSheet() {}
            override fun onShowDeleteCategoryBottomSheet() {}
            override fun onHideDeleteCategoryBottomSheet() {}
            override fun onAddCategoryConfirmed(category: CategoriesScreenState.CategoryUi) {}
            override fun onEditCategoryConfirmed(newCategory: CategoriesScreenState.CategoryUi) {}
            override fun onDeleteCategoryConfirmed(category: CategoriesScreenState.CategoryUi) {}
        }
    )
}
