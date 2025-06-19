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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.bottomSheet.TudeeBottomSheet
import com.moscow.tudee.presentation.components.TudeeTextField
import com.moscow.tudee.presentation.designSystem.component.CategoryCard
import com.moscow.tudee.presentation.designSystem.component.PriorityChip
import com.moscow.tudee.presentation.designSystem.component.SnackBar
import com.moscow.tudee.presentation.designSystem.theme.Theme

data class TaskData(
    val title: String = "",
    val description: String = "",
    val date: Long? = null,
    val priority: String = "",
    val category: String = ""
)

@Composable
fun TaskBottomSheet(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    isEditMode: Boolean = false,
    initialTaskData: TaskData = TaskData(),
    onDismiss: () -> Unit,
    onSaveTask: (
        title: String,
        description: String,
        date: String,
        priority: String,
        category: String
    ) -> Unit,
    onShowSnackBar: (String) -> Unit = {}
) {
    if (isVisible) {
        TudeeBottomSheet(
            onDismissRequest = onDismiss,
            contentHorizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxHeight(0.8f)
        ) {
            var taskTitle by remember { mutableStateOf(initialTaskData.title) }
            var taskDescription by remember { mutableStateOf(initialTaskData.description) }
            var selectedPriority by remember { mutableStateOf(initialTaskData.priority) }
            var selectedCategory by remember { mutableStateOf(initialTaskData.category) }
            var selectedDate by remember { mutableStateOf(initialTaskData.date) }

            val isFormValid by remember {
                derivedStateOf {
                    if (isEditMode) {
                        true
                    } else {
                        taskTitle.isNotBlank() &&
                                taskDescription.isNotBlank() &&
                                selectedDate != null &&
                                selectedPriority.isNotBlank() &&
                                selectedCategory.isNotBlank()
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
                        text = if (isEditMode) "Edit task" else "Add new task",
                        color = Theme.colors.title,
                        style = Theme.textStyle.title.large,
                        fontSize = 20.sp
                    )

                    TudeeTextField(
                        value = taskTitle,
                        onValueChange = { taskTitle = it },
                        keyboardOptions = KeyboardOptions.Default,
                        singleLine = true,
                        hint = "Task title",
                        startIcon = painterResource(id = R.drawable.ic_document_outlined),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                            .padding(top = 16.dp)
                    )

                    TudeeTextField(
                        value = taskDescription,
                        onValueChange = { taskDescription = it },
                        keyboardOptions = KeyboardOptions.Default,
                        singleLine = true,
                        hint = "Description",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(top = 16.dp)
                    )

                    TudeeDatePickerTextField(
                        selectedDate = selectedDate,
                        onDateSelected = { date ->
                            selectedDate = date
                        },
                        startIcon = painterResource(id = R.drawable.ic_calendar_add),
                        dateFormat = "dd-MM-yyyy",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                            .padding(top = 16.dp)
                    )

                    TudeeText(
                        "Priority",
                        color = Theme.colors.title,
                        style = Theme.textStyle.title.medium,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Row(modifier = Modifier.padding(top = 8.dp)) {
                        PriorityChip(
                            text = "High",
                            selected = selectedPriority == "High",
                            backgroundColor = Theme.colors.pinkAccent,
                            icon = painterResource(id = R.drawable.ic_flag),
                            modifier = Modifier.clickable {
                                selectedPriority = if (selectedPriority == "High") "" else "High"
                            }
                        )
                        PriorityChip(
                            text = "Medium",
                            selected = selectedPriority == "Medium",
                            backgroundColor = Theme.colors.yellowAccent,
                            icon = painterResource(id = R.drawable.ic_alert),
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable {
                                    selectedPriority =
                                        if (selectedPriority == "Medium") "" else "Medium"
                                }
                        )
                        PriorityChip(
                            text = "Low",
                            selected = selectedPriority == "Low",
                            backgroundColor = Theme.colors.greenAccent,
                            icon = painterResource(id = R.drawable.ic_trade_down),
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable {
                                    selectedPriority = if (selectedPriority == "Low") "" else "Low"
                                }
                        )
                    }

                    TudeeText(
                        "Category",
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
                        items(20) { index ->
                            val categoryLabel = "Category $index"
                            CategoryCard(
                                icon = painterResource(id = R.drawable.ic_flag),
                                label = categoryLabel,
                                count = 10,
                                selected = selectedCategory == categoryLabel,
                                iconTint = Theme.colors.purpleAccent,
                                modifier = Modifier.clickable {
                                    selectedCategory =
                                        if (selectedCategory == categoryLabel) "" else categoryLabel
                                }
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    PrimaryButton(
                        text = if (isEditMode) "Save" else "Add Task",
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
                                "Edited task successfully"
                            } else {
                                "Add task successfully"
                            }
                            onShowSnackBar(message)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    CustomTextButton(
                        text = "Cancel",
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
        onShowSnackBar = onShowSnackBar
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
        onShowSnackBar = onShowSnackBar
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
                // Handle save task logic
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