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
import com.moscow.tudee.presentation.category.categoryScreen.getPriorityFromString
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
                            priority = getPriorityFromString(stringResource(R.string.high)),
                            selected = selectedPriority == Priority.HIGH,
                            modifier = Modifier.clickable {
                                onPrioritySelected(Priority.HIGH)
                            }
                        )
                        PriorityChip(
                            priority = getPriorityFromString(stringResource(R.string.medium)),
                            selected = selectedPriority == Priority.MEDIUM,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable {
                                    onPrioritySelected(Priority.MEDIUM)
                                }
                        )
                        PriorityChip(
                            priority = getPriorityFromString(stringResource(R.string.low)),
                            selected = selectedPriority == Priority.LOW,
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
                            onSaveTask()

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
    selectedPriority: Priority,
    onPrioritySelected: (Priority) -> Unit,
    categories: List<Category>,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit,
    selectedDate: Long?,
    onDateSelected: (Long) -> Unit,
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