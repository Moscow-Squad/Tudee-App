package com.moscow.tudee.presentation.category.categoryTasksScreen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.ObserveAsEvent
import com.moscow.tudee.presentation.category.CategoriesEvents
import com.moscow.tudee.presentation.category.CategoriesScreenState
import com.moscow.tudee.presentation.category.categoryScreen.component.CategoryBottomSheet
import com.moscow.tudee.presentation.category.categoryScreen.component.DeleteCategoryBottomSheet
import com.moscow.tudee.presentation.category.toCategory
import com.moscow.tudee.presentation.component.TabItem
import com.moscow.tudee.presentation.component.Tabs
import com.moscow.tudee.presentation.designSystem.component.ErrorSnackBar
import com.moscow.tudee.presentation.designSystem.component.SuccessSnackBar
import com.moscow.tudee.presentation.designSystem.component.TaskCard
import com.moscow.tudee.presentation.designSystem.component.TopBar
import com.moscow.tudee.presentation.designSystem.theme.Theme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryTasksScreen(
    categoryId: Long, viewModel: CategoryTasksViewModel = koinViewModel()
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
        categoryId = 1L, uiState = uiState, listener = viewModel
    )
}

@Composable
fun CategoryTasksContent(
    categoryId: Long,
    uiState: CategoriesScreenState,
    listener: CategoriesTasksInteractionListener
) {
    val tabs = listOf(
        TabItem("To Do", uiState.tasks.size, Task.Status.TODO),
        TabItem("In progress", uiState.tasks.size, Task.Status.IN_PROGRESS),
        TabItem("Done", uiState.tasks.size, Task.Status.DONE)
    )
    Scaffold(
        topBar = {
            CategoriesTopBar(
                uiState, listener, tabs, uiState.selectedStatus, categoryId
            )

        }) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
                .padding(padding)
        ) {
            items(uiState.tasks) { task ->
                TaskCard(
                    category = task.category,
                    title = task.title,
                    description = task.description,
                    date = task.date
                ) {
//                    PriorityChip(
//                        text = task.priority,
//                        backgroundColor = when (task.priority) {
//                            "High" -> Theme.colors.pinkAccent
//                            "Medium" -> Theme.colors.yellowAccent
//                            else -> Theme.colors.greenAccent
//                        },
//                        icon = painterResource(
//                            id = when (task.priority) {
//                                "High" -> R.drawable.ic_flag
//                                else -> R.drawable.ic_alert
//                            }
//                        )
//                    )
                }
            }
        }

        if (uiState.isSnackBarShow) {
            val icon = if (uiState.successMessage != null) {
                painterResource(id = R.drawable.ic_checkmark_badge)
            } else {
                painterResource(id = R.drawable.ic_information_diamond)
            }

            val messageId = uiState.successMessage ?: uiState.errorMessage
            messageId?.let {
                if (uiState.successMessage != null) {
                    SuccessSnackBar(message = stringResource(id = it))
                } else {
                    ErrorSnackBar(message = stringResource(id = it))
                }
                    LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(3000)
                    listener.onHideSnackBar()
                }
            }


        }
        if (uiState.isEditCategoryBottomSheetShow) {
            CategoryBottomSheet(
                initialTitle = uiState.category.title,
                initialImageUri = uiState.category.iconUrl
                    .takeIf(String::isNotBlank)
                    ?.let(Uri::parse),
                isEdit = true,
                onConfirm = { title, imageUri ->
                    listener.onUpdateCategory(
                        uiState.category.copy(title = title, iconUrl = imageUri?.toString() ?: "")
                    )
                },
                onDismiss = { listener.onHideEditCategoryBottomSheet() },
                onShowDeleteCategory = {
                    listener.onHideEditCategoryBottomSheet()
                    listener.onShowDeleteCategoryBottomSheet()
                }
            )
        }

        if (uiState.isDeleteCategoryBottomSheetShow) {
            DeleteCategoryBottomSheet(
                title = uiState.category.title,
                onDelete = {
                    listener.onDeleteCategory(uiState.category)
                },
                onDismiss = {
                    listener.onHideDeleteCategoryBottomSheet()
                    listener.onShowEditCategoryBottomSheet()
                }
            )
        }


    }
}

@Composable
private fun CategoriesTopBar(
    uiState: CategoriesScreenState,
    listener: CategoriesTasksInteractionListener,
    tabs: List<TabItem>,
    selectedStatus: Task.Status,
    categoryId: Long
) {
    Column {
        TopBar(
            title = uiState.category.title,
            startIcon = painterResource(id = R.drawable.ic_arrow_head_back),
            endIcon = if (uiState.category.isPredefined) null else painterResource(id = R.drawable.ic_pencil_edit),
            onEndClick = { listener.onShowEditCategoryBottomSheet() })
        Tabs(
            tabs = tabs, selectedStatus = selectedStatus, onTabClick = { status ->
                listener.onTasksStatusClick(categoryId, status)
            }, modifier = Modifier
                .background(Theme.colors.surfaceHigh)
                .padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryTasksPreview() {
    val sampleState = CategoriesScreenState(
        category = CategoriesScreenState.CategoryUi(id = 1L, title = "Sample"),
        categories = emptyList(),
        tasks = listOf(
            CategoriesScreenState.TaskUi(
                title = "Review Flashcards",
                description = "Study biology flashcards",
                priority = Task.Priority.LOW,
                date = "03/12/2025"
            )
        ),
        isLoading = false,
        isSnackBarShow = false,
        errorMessage = null,
        successMessage = null,
    )
    CategoryTasksContent(
        categoryId = 1L,
        uiState = sampleState,
        listener = object : CategoriesTasksInteractionListener {
            override fun onUpdateCategory(newCategory: CategoriesScreenState.CategoryUi) {
                TODO("Not yet implemented")
            }

            override fun onDeleteCategory(category: CategoriesScreenState.CategoryUi) {
                TODO("Not yet implemented")
            }

            override fun onTasksStatusClick(categoryID: Long, status: Task.Status) {}
            override fun onShowEditCategoryBottomSheet() {}
            override fun onHideEditCategoryBottomSheet() {}
            override fun onShowDeleteCategoryBottomSheet() {}
            override fun onHideDeleteCategoryBottomSheet() {}
            override fun onHideSnackBar() {
                TODO("Not yet implemented")
            }

        })
}
