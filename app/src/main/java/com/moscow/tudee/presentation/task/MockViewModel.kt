package com.moscow.tudee.presentation.task


import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.TasksServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.*

@Composable
fun mockViewModel(): TaskViewModel {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        return object : TaskViewModel(
            taskService = object : TasksServices {
                override suspend fun getTasks(): List<Task> = mockTasks
                override suspend fun getTasksByDate(date: LocalDate): List<Task> {
                    return mockTasks.filter { it.date.date == date }
                }
                override suspend fun getTaskById(taskId: Long): Task = mockTasks[0]
                override suspend fun changeTaskStatus(taskId: Long) {}
                override suspend fun addTask(task: Task) {}
                override suspend fun updateTask(task: Task) {}
                override suspend fun deleteTask(taskId: Long) {}
                override suspend fun getCategories(): List<Category> = emptyList()
                override suspend fun addCategory(category: Category) {}
                override suspend fun updateCategory(category: Category) {}
                override suspend fun deleteCategory(categoryId: Long) {}
            }
        ) {}
    }

private val mockTasks = listOf(
    Task(
        id = 1L,
        title = "Mock Task 1",
        description = "Description 1",
        priority = Task.Priority.HIGH,
        categoryId = 1L,
        status = Task.Status.TODO,
        date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ),
    Task(
        id = 2L,
        title = "Mock Task 2",
        description = "Description 2",
        priority = Task.Priority.LOW,
        categoryId = 1L,
        status = Task.Status.DONE,
        date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ),
    Task(
        id = 3L,
        title = "Mock Task 3",
        description = "Description 3",
        priority = Task.Priority.MEDIUM,
        categoryId = 1L,
        status = Task.Status.IN_PROGRESS,
        date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ),
    Task(
        id = 4L,
        title = "Mock Task 4",
        description = "Description 4",
        priority = Task.Priority.HIGH,
        categoryId = 1L,
        status = Task.Status.IN_PROGRESS,
        date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ),
    Task(
        id = 5L,
        title = "Mock Task 5",
        description = "Description 5",
        priority = Task.Priority.LOW,
        categoryId = 1L,
        status = Task.Status.TODO,
        date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    )
)

