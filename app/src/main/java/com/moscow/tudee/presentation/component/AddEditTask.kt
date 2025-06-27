package com.moscow.tudee.presentation.component

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task.Priority
import com.moscow.tudee.presentation.component.bottomSheet.TudeeBottomSheet
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.model.CategoryUi
import com.moscow.tudee.presentation.screen.category.component.CategoryCard
import com.moscow.tudee.presentation.util.getPredefinedIconRes
import kotlin.math.max

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
) {
    if (isVisible) {
        TudeeBottomSheet(
            onDismissRequest = onDismiss,
            contentHorizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxHeight(0.8f)
        ) {
            val configuration = LocalConfiguration.current
            val screenWidth = configuration.screenWidthDp.dp
            val categoriesPerRow = remember(screenWidth) {
                max(2, (screenWidth / 120.dp).toInt().coerceAtMost(4))
            }

            val isTitleValid = taskTitle.trim().isNotBlank()
            val isDescriptionValid = taskDescription.trim().isNotBlank()
            val isPriorityValid = selectedPriority != null
            val isDateValid = selectedDate != null
            val isCategoryValid = selectedCategory != null && selectedCategory.id!! > 0
            val isFormValid =
                isTitleValid && isDescriptionValid && isDateValid && isCategoryValid && isPriorityValid

            Box(modifier = modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 160.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        TudeeText(
                            text = if (isEditMode) stringResource(R.string.edit_task)
                            else stringResource(R.string.add_new_task),
                            color = Theme.colors.title,
                            style = Theme.textStyle.title.large,
                            fontSize = 20.sp
                        )
                    }

                    item {
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
                                .height(58.dp)
                        )
                    }

                    item {
                        TudeeTextField(
                            value = taskDescription,
                            onValueChange = { newDescription ->
                                onTaskDescriptionChange(newDescription)
                            },
                            keyboardOptions = KeyboardOptions.Default,
                            singleLine = false,
                            hint = stringResource(R.string.description),
                            placeholderAlignment = Alignment.TopStart,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }

                    item {
                        TudeeDatePickerTextField(
                            selectedDate = selectedDate,
                            onDateSelected = { newDate ->
                                onDateSelected(newDate)
                            },
                            startIcon = painterResource(id = R.drawable.ic_calendar_add),
                            dateFormat = "dd-MM-yyyy",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(58.dp)
                        )
                    }

                    item {
                        TudeeText(
                            stringResource(R.string.priority),
                            color = Theme.colors.title,
                            style = Theme.textStyle.title.medium,
                            fontSize = 18.sp,
                        )
                    }

                    item {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            item {
                                PriorityChip(
                                    text = stringResource(R.string.high),
                                    selected = selectedPriority == Priority.HIGH,
                                    backgroundColor = Theme.colors.pinkAccent,
                                    icon = painterResource(id = R.drawable.ic_flag),
                                    modifier = Modifier.clickable {
                                        onPrioritySelected(Priority.HIGH)
                                    }
                                )
                            }
                            item {
                                PriorityChip(
                                    text = stringResource(R.string.medium),
                                    selected = selectedPriority == Priority.MEDIUM,
                                    backgroundColor = Theme.colors.yellowAccent,
                                    icon = painterResource(id = R.drawable.ic_alert),
                                    modifier = Modifier.clickable {
                                        onPrioritySelected(Priority.MEDIUM)
                                    }
                                )
                            }
                            item {
                                PriorityChip(
                                    text = stringResource(R.string.low),
                                    selected = selectedPriority == Priority.LOW,
                                    backgroundColor = Theme.colors.greenAccent,
                                    icon = painterResource(id = R.drawable.ic_trade_down),
                                    modifier = Modifier.clickable {
                                        onPrioritySelected(Priority.LOW)
                                    }
                                )
                            }
                        }
                    }

                    item {
                        TudeeText(
                            stringResource(R.string.category),
                            color = Theme.colors.title,
                            style = Theme.textStyle.title.medium,
                            fontSize = 18.sp,
                        )
                    }

                    items(categories.chunked(categoriesPerRow)) { rowCategories ->
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
                                        label = category.titleRes?.let { stringResource(category.titleRes) }
                                            ?: category.title,
                                        selected = selectedCategory?.id == category.id,
                                        isPredefined = category.isPredefined,
                                        iconTint = Color.Unspecified,
                                        count = -1,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                onCategorySelected(category)
                                            }
                                    )
                                }
                            }
                            repeat(categoriesPerRow - rowCategories.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
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
    onSaveTask: () -> Unit
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
    )
}