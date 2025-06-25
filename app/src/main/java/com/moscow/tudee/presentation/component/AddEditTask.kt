package com.moscow.tudee.presentation.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import coil.compose.rememberAsyncImagePainter
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task.Priority
import com.moscow.tudee.presentation.component.bottomSheet.TudeeBottomSheet
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.model.CategoryUi
import com.moscow.tudee.presentation.util.getPredefinedIconRes

@Composable
fun TaskBottomSheet(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    isEditMode: Boolean = false,
    taskTitle: String,
    onTaskTitleChange: (String) -> Unit,
    taskDescription: String,
    onTaskDescriptionChange: (String) -> Unit,
    selectedPriority: Priority?,
    onPrioritySelected: (Priority) -> Unit,
    categories: List<CategoryUi>,
    selectedCategory: CategoryUi?,
    onCategorySelected: (CategoryUi) -> Unit,
    selectedDate: Long?,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    onCancel: () -> Unit = onDismiss,
    onSaveTask: () -> Unit,
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
            val scrollState = rememberScrollState()

            val isTitleValid = taskTitle.trim().isNotBlank()
            val isDescriptionValid = taskDescription.trim().isNotBlank()
            val isPriorityValid = selectedPriority != null
            val isDateValid = selectedDate != null
            val isCategoryValid =
                selectedCategory != null && selectedCategory.id!! > 0
            val isFormValid =
                isTitleValid && isDescriptionValid && isDateValid && isCategoryValid && isPriorityValid


            Box(modifier = modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 160.dp
                        )
                ) {
                    TudeeText(
                        text = if (isEditMode) stringResource(R.string.edit_task)
                        else stringResource(R.string.add_new_task),
                        color = Theme.colors.title,
                        style = Theme.textStyle.title.large,
                        fontSize = 20.sp
                    )

                    TudeeTextField(
                        value = taskTitle,
                        onValueChange = { newTitle ->
                            onTaskTitleChange(newTitle)
                        },
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
                        onValueChange = { newDescription ->
                            onTaskDescriptionChange(newDescription)
                        },
                        keyboardOptions = KeyboardOptions.Default,
                        singleLine = true,
                        hint = stringResource(R.string.description),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(top = 16.dp)
                    )

                    TudeeDatePickerTextField(
                        selectedDate = selectedDate,
                        onDateSelected = { newDate ->
                            Log.d("TaskBottomSheet_Debug", "📅 Date changed: $newDate")
                            onDateSelected(newDate)
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
                                    Log.d("TaskBottomSheet_Debug", "⚠️ Priority changed: MEDIUM")
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
                                    Log.d("TaskBottomSheet_Debug", "⬇️ Priority changed: LOW")
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

                    LaunchedEffect(categories) {
                        Log.d(
                            "TaskBottomSheet_Debug",
                            "🏷️ Categories loaded: ${categories.map { "${it.title}(id:${it.id})" }}"
                        )
                    }

                    Column(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        categories.chunked(3).forEach { rowCategories ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                rowCategories.forEach { category ->
                                    Box(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        CategoryCard(
                                            icon = if (category.isPredefined) painterResource(
                                                getPredefinedIconRes(category.title)
                                            )
                                            else rememberAsyncImagePainter(category.iconUrl),
                                            label = category.title,
                                            selected = selectedCategory?.id == category.id,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    Log.d(
                                                        "TaskBottomSheet_Debug",
                                                        "🏷️ Category selected: ${category.title} (id: ${category.id})"
                                                    )
                                                    onCategorySelected(category)
                                                }
                                        )
                                    }
                                }
                                repeat(3 - rowCategories.size) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .background(Theme.colors.surfaceHigh)
                        .padding(16.dp)
                ) {
                    PrimaryButton(
                        text = if (isEditMode) stringResource(R.string.save)
                        else stringResource(R.string.add_task),
                        isEnabled = isFormValid,
                        backgroundColor = if (isFormValid) Theme.colors.primaryGradient
                        else Brush.verticalGradient(
                            listOf(
                                Theme.colors.disable,
                                Theme.colors.disable
                            )
                        ),
                        textColor = if (isFormValid) Color.White else Theme.colors.stroke,
                        onClick = {
                            if (isFormValid) {
                                onSaveTask()
                                onShowSnackBar(
                                    if (isEditMode) editSuccessMessage else addSuccessMessage
                                )
                            } else {
                                Log.w("TaskBottomSheet_Debug", "❌ Cannot save - form invalid!")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    SecondaryButton(
                        text = stringResource(R.string.cancel),
                        onClick = { onCancel() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
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
    taskTitle: String,
    onTaskTitleChange: (String) -> Unit,
    taskDescription: String,
    onTaskDescriptionChange: (String) -> Unit,
    selectedPriority: Priority?,
    onPrioritySelected: (Priority) -> Unit,
    categories: List<CategoryUi>,
    selectedCategory: CategoryUi?,
    onCategorySelected: (CategoryUi) -> Unit,
    selectedDate: Long?,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    onCancel: () -> Unit = onDismiss,
    onSaveTask: () -> Unit,
    onShowSnackBar: (String) -> Unit = {},
    ) {
    TaskBottomSheet(
        modifier = modifier,
        isVisible = isVisible,
        isEditMode = false,
        taskTitle = taskTitle,
        onTaskTitleChange = onTaskTitleChange,
        taskDescription = taskDescription,
        onTaskDescriptionChange = onTaskDescriptionChange,
        selectedPriority = selectedPriority,
        onPrioritySelected = onPrioritySelected,
        categories = categories,
        selectedCategory = selectedCategory,
        onCategorySelected = onCategorySelected,
        selectedDate = selectedDate,
        onDateSelected = onDateSelected,
        onDismiss = onDismiss,
        onCancel = onCancel,
        onSaveTask = onSaveTask,
        onShowSnackBar = onShowSnackBar,
        addSuccessMessage = stringResource(R.string.add_task_successfully),
        editSuccessMessage = stringResource(R.string.edited_task_successfully)
    )
}

@Composable
fun EditTaskBottomSheet(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    taskTitle: String,
    onTaskTitleChange: (String) -> Unit,
    taskDescription: String,
    onTaskDescriptionChange: (String) -> Unit,
    selectedPriority: Priority?,
    onPrioritySelected: (Priority) -> Unit,
    categories: List<CategoryUi>,
    selectedCategory: CategoryUi?,
    onCategorySelected: (CategoryUi) -> Unit,
    selectedDate: Long?,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    onSaveTask: () -> Unit,
    onShowSnackBar: (String) -> Unit = {}
) {
    TaskBottomSheet(
        modifier = modifier,
        isVisible = isVisible,
        isEditMode = true,
        taskTitle = taskTitle,
        onTaskTitleChange = onTaskTitleChange,
        taskDescription = taskDescription,
        onTaskDescriptionChange = onTaskDescriptionChange,
        selectedPriority = selectedPriority,
        onPrioritySelected = onPrioritySelected,
        categories = categories,
        selectedCategory = selectedCategory,
        onCategorySelected = onCategorySelected,
        selectedDate = selectedDate,
        onDateSelected = onDateSelected,
        onDismiss = onDismiss,
        onSaveTask = onSaveTask,
        onShowSnackBar = onShowSnackBar,
        addSuccessMessage = stringResource(R.string.add_task_successfully),
        editSuccessMessage = stringResource(R.string.edited_task_successfully)
    )
}


@Preview(showBackground = true)
@Composable
fun TaskBottomSheetPreview() {
    val mockCategories = listOf(
        CategoryUi(id = 1, title = "Work", iconUrl = "", isPredefined = false, countOfTasks = 0),
        CategoryUi(id = 2, title = "Personal", iconUrl = "", isPredefined = false, countOfTasks = 0),
        CategoryUi(id = 3, title = "Study", iconUrl = "", isPredefined = false, countOfTasks = 0)
    )

    var taskTitle by remember { mutableStateOf("My Task") }
    var taskDescription by remember { mutableStateOf("This is a task description") }
    var selectedPriority by remember { mutableStateOf(Priority.MEDIUM) }
    var selectedCategory by remember { mutableStateOf(mockCategories.first()) }
    var selectedDate by remember { mutableStateOf<Long?>(System.currentTimeMillis()) }

    TaskBottomSheet(
        isVisible = true,
        isEditMode = false,
        taskTitle = taskTitle,
        onTaskTitleChange = { taskTitle = it },
        taskDescription = taskDescription,
        onTaskDescriptionChange = { taskDescription = it },
        selectedPriority = selectedPriority,
        onPrioritySelected = { selectedPriority = it },
        categories = mockCategories,
        selectedCategory = selectedCategory,
        onCategorySelected = { selectedCategory = it },
        selectedDate = selectedDate,
        onDateSelected = { selectedDate = it },
        onDismiss = {},
        onSaveTask = {},
    )
}