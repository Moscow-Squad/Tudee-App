package com.moscow.tudee.presentation.task

import androidx.compose.runtime.Composable
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.TasksServices
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun mockTaskViewModel(): TaskViewModel {

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
            override suspend fun getTasksByCategory(categoryId: Long): List<Task> =
                TODO( "Not yet implemented")
            override suspend fun getTasksByStatus(status: Task.Status): List<Task> =
                TODO("Not yet implemented")
            override suspend fun getTasksByDateAndStatus(
                date: LocalDate,
                status: Task.Status,
            ): List<Task> =
                TODO("Not yet implemented")
            override suspend fun getTasksByDateAndCategory(
                date: LocalDate,
                categoryId: Long,
            ): List<Task> =
                TODO("Not yet implemented")
            override suspend fun getTasksByCategoryAndStatus(
                categoryId: Long,
                status: Task.Status,
            ): List<Task> =
                TODO("Not yet implemented")
        }
    ) {}
}
private val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
private val mockTasks = listOf(
    Task(
        id = 1L,
        title = "Mock Task 1",
        description = "Description 1",
        priority = Task.Priority.HIGH,
        category = Category(
            id = 1,
            title = "Study",
            iconUri = "",
            isPredefined = true
        ),
        status = Task.Status.TODO,
        date = today
    ),
    Task(
        id = 2L,
        title = "Mock Task 2",
        description = "Description 2",
        priority = Task.Priority.LOW,
        category = Category(
            id = 1,
            title = "Study",
            iconUri = "",
            isPredefined = true
        ),
        status = Task.Status.DONE,
        date = today
    ),
    Task(
        id = 3L,
        title = "Mock Task 3",
        description = "Description 3",
        priority = Task.Priority.MEDIUM,
        category = Category(
            id = 1,
            title = "Study",
            iconUri = "",
            isPredefined = true
        ),
        status = Task.Status.IN_PROGRESS,
        date = today
    ),
    Task(
        id = 4L,
        title = "Tomorrow Task",
        description = "Different day",
        priority = Task.Priority.HIGH,
        category = Category(
            id = 1,
            title = "Study",
            iconUri = "",
            isPredefined = true
        ),
        status = Task.Status.IN_PROGRESS,
        date = today
    )
)

