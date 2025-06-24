package com.moscow.tudee.presentation.category.categoryTasksScreen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.ObserveAsEvent
import com.moscow.tudee.presentation.category.CategoriesScreenState
import com.moscow.tudee.presentation.category.CategoryTasksEvents
import com.moscow.tudee.presentation.category.categoryScreen.component.CategoryBottomSheet
import com.moscow.tudee.presentation.category.categoryScreen.component.CategorySnackBar
import com.moscow.tudee.presentation.category.categoryScreen.component.DeleteCategoryBottomSheet
import com.moscow.tudee.presentation.component.TabItem
import com.moscow.tudee.presentation.component.Tabs
import com.moscow.tudee.presentation.designSystem.component.*
import com.moscow.tudee.presentation.designSystem.theme.Theme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CategoryTasksScreen(
    categoryId: Long,
    navigateBack: () -> Unit,
    navigateBackToCategoryScreen: (Int?) -> Unit

) {
    val viewModel: CategoryTasksViewModel = koinViewModel(parameters = { parametersOf(categoryId) })
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ObserveAsEvent(viewModel.uiEvent) { event ->
         when(event){
             is CategoryTasksEvents.NavigateBack-> {
                 navigateBack()
             }

             is CategoryTasksEvents.NavigateBackWithResult -> {
                 navigateBackToCategoryScreen(event.messageID)
             }
         }
    }
    CategoryTasksContent(categoryId = categoryId, uiState = uiState, listener = viewModel)
}

@Composable
fun CategoryTasksContent(
    categoryId: Long,
    uiState: CategoriesScreenState,
    listener: CategoriesTasksInteractionListener
) {
    val tabs = listOf(
        TabItem(stringResource(R.string.to_do), uiState.tasks.size, Task.Status.TODO),
        TabItem(
            stringResource(R.string.in_progress_status),
            uiState.tasks.size,
            Task.Status.IN_PROGRESS
        ),
        TabItem(stringResource(R.string.done), uiState.tasks.size, Task.Status.DONE)
    )

    Scaffold(
        containerColor = Theme.colors.surface,
        topBar = {
            TasksTopBar(
                uiState = uiState,
                listener = listener,
                tabs = tabs,
                selectedStatus = uiState.selectedStatus,
                categoryId = categoryId
            )
        }
    ) { padding ->
        TasksList(uiState = uiState, modifier = Modifier.padding(padding))
        HandleSnackBarAppearance(uiState, listener)
        HandleEditBottomSheet(uiState = uiState, listener = listener)
        HandleDeleteBottomSheet(uiState = uiState, listener = listener)
    }
}

@Composable
private fun TasksTopBar(
    uiState: CategoriesScreenState,
    listener: CategoriesTasksInteractionListener,
    tabs: List<TabItem>,
    selectedStatus: Task.Status,
    categoryId: Long
) {
    Column {

        TopBar(
            modifier = Modifier.background(Theme.colors.surfaceHigh),
            title = uiState.category.title,
            startIcon = painterResource(id = R.drawable.ic_arrow_head_back),
            endIcon = if (uiState.category.isPredefined) null else painterResource(id = R.drawable.ic_pencil_edit),
            onEndClick = listener::onShowEditCategoryBottomSheet
            , onStartClick = { listener.onBackPress() }
        )
        Tabs(
            tabs = tabs,
            selectedStatus = selectedStatus,
            onTabClick = { status -> listener.onTasksStatusClick(categoryId, status) },
            modifier = Modifier
                .background(Theme.colors.surfaceHigh)
                .padding(top = 8.dp)
        )
    }
}

@Composable
private fun TasksList(uiState: CategoriesScreenState, modifier: Modifier = Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(uiState.tasks) { task ->
            TaskCard(
                category = task.category,
                title = task.title,
                description = task.description,
                date = task.date,
            ) {
                PriorityChip(priority = task.priority)
            }
        }
    }
}


@Composable
private fun HandleEditBottomSheet(
    uiState: CategoriesScreenState,
    listener: CategoriesTasksInteractionListener
) {
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
            onDismiss = listener::onHideEditCategoryBottomSheet,
            onShowDeleteCategory = {
                listener.onHideEditCategoryBottomSheet()
                listener.onShowDeleteCategoryBottomSheet()
            }
        )
    }
}

@Composable
private fun HandleDeleteBottomSheet(
    uiState: CategoriesScreenState,
    listener: CategoriesTasksInteractionListener
) {
    if (uiState.isDeleteCategoryBottomSheetShow) {
        DeleteCategoryBottomSheet(
            title = uiState.category.title,
            onDelete = { listener.onDeleteCategory(uiState.category) },
            onDismiss = {
                listener.onHideDeleteCategoryBottomSheet()
                listener.onShowEditCategoryBottomSheet()
            }
        )
    }
}

@Composable
private fun HandleSnackBarAppearance(
    uiState: CategoriesScreenState, listener: CategoriesTasksInteractionListener
) {
    if (uiState.isSnackBarShow) {
        CategorySnackBar(uiState) {
            listener.onHideSnackBar()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryTasksPreview() {
    val sampleState = CategoriesScreenState(
        category = CategoriesScreenState.CategoryUi(
            id = 1L,
            title = "Study",
            iconUrl = "",
            isPredefined = false
        ),
        tasks = listOf(
            CategoriesScreenState.TaskUi(
                title = "Review Flashcards",
                description = "Biology deck - Chapter 4",
                priority = Task.Priority.LOW,
                date = "03/12/2025"
            ),
            CategoriesScreenState.TaskUi(
                title = "Write Essay",
                description = "History on WW2 - 1000 words",
                priority = Task.Priority.HIGH,
                date = "03/13/2025"
            ),
            CategoriesScreenState.TaskUi(
                title = "Practice Math",
                description = "Algebra exercises",
                priority = Task.Priority.MEDIUM,
                date = "03/14/2025"
            )
        ),
        selectedStatus = Task.Status.TODO,
    )

    CategoryTasksContent(
        categoryId = 1L,
        uiState = sampleState,
        listener = FakeCategoryTasksListener
    )
}

private val FakeCategoryTasksListener = object : CategoriesTasksInteractionListener {
    override fun onUpdateCategory(newCategory: CategoriesScreenState.CategoryUi) {}
    override fun onDeleteCategory(category: CategoriesScreenState.CategoryUi) {}
    override fun onTasksStatusClick(categoryID: Long, status: Task.Status) {}
    override fun onShowEditCategoryBottomSheet() {}
    override fun onHideEditCategoryBottomSheet() {}
    override fun onShowDeleteCategoryBottomSheet() {}
    override fun onHideDeleteCategoryBottomSheet() {}
    override fun onHideSnackBar() {}
    override fun onBackPress() {
    }
}
