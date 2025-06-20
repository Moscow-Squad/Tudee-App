package com.moscow.tudee.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task.Priority
import com.moscow.tudee.presentation.component.bottomSheet.TudeeBottomSheet
import com.moscow.tudee.presentation.components.TudeeTextField
import com.moscow.tudee.presentation.designSystem.component.CategoryCard
import com.moscow.tudee.presentation.designSystem.component.PriorityChip
import com.moscow.tudee.presentation.designSystem.component.SnackBar
import com.moscow.tudee.presentation.designSystem.theme.Theme
import kotlinx.coroutines.delay

@Composable
fun TaskBottomSheet(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    isEditMode: Boolean = false,
    taskTitle: String,
    onTaskTitleChange: (String) -> Unit,
    taskDescription: String,
    onTaskDescriptionChange: (String) -> Unit,
    selectedPriority: Priority,
    onPrioritySelected: (Priority) -> Unit,
    categories: List<Category>,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit,
    selectedDate: Long?,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit,
    onShowSnackBar: (String) -> Unit = {},
    addSuccessMessage: String = stringResource(R.string.add_task_successfully),
    editSuccessMessage: String = stringResource(R.string.edited_task_successfully)
) {
    if (isVisible) {
        TudeeBottomSheet(
            onDismissRequest = onDismiss,
            contentHorizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxHeight(0.8f)
        ) {
            val isFormValid by remember {
                derivedStateOf {
                    if (isEditMode) {
                        true
                    } else {
                        taskTitle.isNotBlank() &&
                                taskDescription.isNotBlank() &&
                                selectedDate != null &&
                                selectedCategory.title.isNotBlank()
                    }
                }
            }

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    TudeeText(
                        text = if (isEditMode) stringResource(R.string.edit_task)
                        else stringResource(
                            R.string.add_new_task
                        ),
                        color = Theme.colors.title,
                        style = Theme.textStyle.title.large,
                        fontSize = 20.sp
                    )

                    TudeeTextField(
                        value = taskTitle,
                        onValueChange = { onTaskTitleChange(it) },
                        keyboardOptions = KeyboardOptions.Default,
                        singleLine = true,
                        hint = stringResource(R.string.task_title),
                        startIcon = painterResource(id = R.drawable.ic_document_outlined),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                            .padding(top = 16.dp)
                    )

                    TudeeTextField(
                        value = taskDescription,
                        onValueChange = { onTaskDescriptionChange(it) },
                        keyboardOptions = KeyboardOptions.Default,
                        singleLine = true,
                        hint = stringResource(R.string.description),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(top = 16.dp)
                    )

                    TudeeDatePickerTextField(
                        selectedDate = selectedDate ?: 0L,
                        onDateSelected = { date ->
                            onDateSelected(date ?: 0L)
                        },
                        startIcon = painterResource(id = R.drawable.ic_calendar_add),
                        dateFormat = "dd-MM-yyyy",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                            .padding(top = 16.dp)
                    )

                    TudeeText(
                        stringResource(R.string.priority),
                        color = Theme.colors.title,
                        style = Theme.textStyle.title.medium,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Row(modifier = Modifier.padding(top = 8.dp)) {
                        PriorityChip(
                            text = stringResource(R.string.high),
                            selected = selectedPriority == Priority.HIGH,
                            backgroundColor = Theme.colors.pinkAccent,
                            icon = painterResource(id = R.drawable.ic_flag),
                            modifier = Modifier.clickable {
                                onPrioritySelected(Priority.HIGH)
                            }
                        )
                        PriorityChip(
                            text = stringResource(R.string.medium),
                            selected = selectedPriority == Priority.MEDIUM,
                            backgroundColor = Theme.colors.yellowAccent,
                            icon = painterResource(id = R.drawable.ic_alert),
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable {
                                    onPrioritySelected(Priority.MEDIUM)
                                }
                        )
                        PriorityChip(
                            text = stringResource(R.string.low),
                            selected = selectedPriority == Priority.LOW,
                            backgroundColor = Theme.colors.greenAccent,
                            icon = painterResource(id = R.drawable.ic_trade_down),
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable {
                                    onPrioritySelected(Priority.LOW)
                                }
                        )
                    }

                    TudeeText(
                        stringResource(R.string.category),
                        color = Theme.colors.title,
                        style = Theme.textStyle.title.medium,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .height(300.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categories) { category ->
                            val categoryLabel = category.title
                            CategoryCard(
                                icon = painterResource(id = R.drawable.ic_flag),
                                label = categoryLabel,
                                count = 10,
                                selected = selectedCategory.id == category.id,
                                iconTint = Theme.colors.purpleAccent,
                                modifier = Modifier.clickable {
                                    onCategorySelected(category)
                                }
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .background(Theme.colors.surfaceHigh)
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    PrimaryButton(
                        text = if (isEditMode) stringResource(R.string.save)
                        else stringResource(R.string.add_task),
                        isEnabled = isFormValid,
                        backgroundColor = if (isFormValid) Theme.colors.primaryGradient
                        else {
                            Brush.verticalGradient(
                                colors = listOf(
                                    Theme.colors.disable,
                                    Theme.colors.disable
                                )
                            )
                        },
                        textColor = if (isFormValid) {
                            Color.White
                        } else {
                            Theme.colors.stroke
                        },
                        onClick = {
                            onSaveTask(
                                taskTitle,
                                taskDescription,
                                selectedDate?.toString() ?: "",
                                selectedPriority,
                                selectedCategory
                            )

                            val message = if (isEditMode) {
                                editSuccessMessage
                            } else {
                                addSuccessMessage
                            }
                            onShowSnackBar(message)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    CustomTextButton(
                        text = stringResource(R.string.cancel),
                        onClick = { onDismiss() },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun AddTaskBottomSheet(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onAddTask: (String, String, String, String, String) -> Unit,
    onShowSnackBar: (String) -> Unit = {}
) {
    TaskBottomSheet(
        modifier = modifier,
        isVisible = isVisible,
        isEditMode = false,
        onDismiss = onDismiss,
        onSaveTask = onAddTask,
        onShowSnackBar = onShowSnackBar,
        addSuccessMessage = stringResource(R.string.add_task_successfully),
        editSuccessMessage = stringResource(R.string.edited_task_successfully)
    )
}

@Composable
fun EditTaskBottomSheet(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    taskData: TaskData,
    onDismiss: () -> Unit,
    onSaveTask: (String, String, String, String, String) -> Unit,
    onShowSnackBar: (String) -> Unit = {}
) {
    TaskBottomSheet(
        modifier = modifier,
        isVisible = isVisible,
        isEditMode = true,
        initialTaskData = taskData,
        onDismiss = onDismiss,
        onSaveTask = onSaveTask,
        onShowSnackBar = onShowSnackBar,
        addSuccessMessage = stringResource(R.string.add_task_successfully),
        editSuccessMessage = stringResource(R.string.edited_task_successfully)
    )
}

@Preview
@Composable
private fun AddEditTaskPreview() {
    var showAddTaskSheet by remember { mutableStateOf(false) }
    var showEditTaskSheet by remember { mutableStateOf(false) }
    var showSnackBar by remember { mutableStateOf(false) }
    var snackBarMessage by remember { mutableStateOf("") }

    val sampleTaskData = TaskData(
        title = "Sample Task",
        description = "This is a sample task description",
        date = System.currentTimeMillis(),
        priority = "High",
        category = "Category 1"
    )

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryButton(
                text = "Add Task",
                onClick = { showAddTaskSheet = true }
            )

            PrimaryButton(
                text = "Edit Task",
                onClick = { showEditTaskSheet = true },
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        AddTaskBottomSheet(
            isVisible = showAddTaskSheet,
            onDismiss = { showAddTaskSheet = false },
            onAddTask = { title, description, date, priority, category ->
                showAddTaskSheet = false
            },
            onShowSnackBar = { message ->
                snackBarMessage = message
                showSnackBar = true
            }
        )

        EditTaskBottomSheet(
            isVisible = showEditTaskSheet,
            taskData = sampleTaskData,
            onDismiss = { showEditTaskSheet = false },
            onSaveTask = { title, description, date, priority, category ->
                showEditTaskSheet = false
            },
            onShowSnackBar = { message ->
                snackBarMessage = message
                showSnackBar = true
            }
        )

        if (showSnackBar) {
            SnackBar(
                icon = painterResource(id = R.drawable.ic_checkmark_badge),
                message = snackBarMessage,
                iconBackground = Theme.colors.greenVariant,
                iconTint = Theme.colors.greenAccent,
                modifier = Modifier.align(Alignment.TopCenter)
            )

            LaunchedEffect(showSnackBar) {
                if (showSnackBar) {
                    delay(3000)
                    showSnackBar = false
                }
            }
        }
    }
}