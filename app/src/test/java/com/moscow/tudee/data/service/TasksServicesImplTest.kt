package com.moscow.tudee.data.service

import com.google.common.truth.Truth.assertThat
import com.moscow.tudee.data.local.dao.CategoryDao
import com.moscow.tudee.data.local.dao.TaskDao
import com.moscow.tudee.data.local.entity.TaskEntity
import com.moscow.tudee.domain.entity.Task
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TasksServicesImplTest {

    private lateinit var taskDao: TaskDao
    private lateinit var categoryDao: CategoryDao
    private lateinit var tasksServices: TasksServicesImpl
    private lateinit var categoryDao: CategoryDao

    private val sampleTaskEntity = TaskEntity(
        id = 1L,
        title = "Test Task",
        description = "Test Description",
        categoryId = 1L,
        status = "TODO",
        date = "2024-06-18T15:45",
        priority = "HIGH"
    )

    @BeforeEach
    fun setUp() {
        taskDao = mockk(relaxed = true)
        categoryDao = mockk(relaxed = true)
        tasksServices = TasksServicesImpl(taskDao,categoryDao)
    }

    @Test
    fun `should return list of tasks when tasks exist`() = runTest {
        val taskEntity = TaskEntity(
            id = 1,
            title = "Test Task",
            description = "Description",
            priority = Task.Priority.HIGH.name,
            categoryId = 1,
            status = Task.Status.TODO.name,
            date = "2025-06-18"
        )
        val expectedTask = Task(
            id = 1,
            title = "Test Task",
            description = "Description",
            priority = Task.Priority.HIGH,
            categoryId = 1,
            status = Task.Status.TODO,
            date = LocalDateTime.parse("2025-06-18T00:00:00")
        )
        coEvery { taskDao.getTasks() } returns listOf(taskEntity)

        val result = tasksServices.getTasks()

        assertThat(result).containsExactly(expectedTask)
        coVerify { taskDao.getTasks() }
    }

    @Test
    fun `should return empty list when no tasks exist`() = runTest {
        coEvery { taskDao.getTasks() } returns emptyList()

        val result = tasksServices.getTasks()

        assertThat(result).isEmpty()
        coVerify { taskDao.getTasks() }
    }

    @Test
    fun `should return tasks for given date when tasks exist`() = runTest {
        val date = LocalDate.parse("2025-06-18")
        val taskEntity = TaskEntity(
            id = 1,
            title = "Test Task",
            description = "Description",
            priority = Task.Priority.HIGH.name,
            categoryId = 1,
            status = Task.Status.TODO.name,
            date = "2025-06-18"
        )
        val expectedTask = Task(
            id = 1,
            title = "Test Task",
            description = "Description",
            priority = Task.Priority.HIGH,
            categoryId = 1,
            status = Task.Status.TODO,
            date = LocalDateTime.parse("2025-06-18T00:00:00")
        )
        coEvery { taskDao.getTasksByDate("2025-06-18") } returns listOf(taskEntity)

        val result = tasksServices.getTasksByDate(date)

        assertThat(result).containsExactly(expectedTask)
        coVerify { taskDao.getTasksByDate("2025-06-18") }
    }

    @Test
    fun `should return empty list when no tasks exist for given date`() = runTest {
        val date = LocalDate.parse("2025-06-18")
        coEvery { taskDao.getTasksByDate("2025-06-18") } returns emptyList()

        val result = tasksServices.getTasksByDate(date)

        assertThat(result).isEmpty()
        coVerify { taskDao.getTasksByDate("2025-06-18") }
    }

    @Test
    fun `should return task when task exists`() = runTest {
        val taskId = 1L
        val taskEntity = TaskEntity(
            id = taskId,
            title = "Test Task",
            description = "Description",
            priority = Task.Priority.HIGH.name,
            categoryId = 1,
            status = Task.Status.TODO.name,
            date = "2025-06-18"
        )
        val expectedTask = Task(
            id = taskId,
            title = "Test Task",
            description = "Description",
            priority = Task.Priority.HIGH,
            categoryId = 1,
            status = Task.Status.TODO,
            date = LocalDateTime.parse("2025-06-18T00:00:00")
        )
        coEvery { taskDao.getTaskById(taskId) } returns taskEntity

        val result = tasksServices.getTaskById(taskId)

        assertThat(result).isEqualTo(expectedTask)
        coVerify { taskDao.getTaskById(taskId) }
    }

    @Test
    fun `should throw exception when task not found`() = runTest {
        val taskId = 1L
        coEvery { taskDao.getTaskById(taskId) } returns null

        assertThrows<Exception> { tasksServices.getTaskById(taskId) }.also {
            assertThat(it.message).isEqualTo("task not found")
        }
        coVerify { taskDao.getTaskById(taskId) }
    }

    @Test
    fun `should update status from TODO to IN_PROGRESS when status is TODO`() = runTest {
        val taskId = 1L
        val taskEntity = TaskEntity(
            id = taskId,
            title = "Test Task",
            description = "Description",
            priority = Task.Priority.HIGH.name,
            categoryId = 1,
            status = Task.Status.TODO.name,
            date = "2025-06-18"
        )
        coEvery { taskDao.getTaskById(taskId) } returns taskEntity
        coEvery { taskDao.updateTaskStatus(taskId, Task.Status.IN_PROGRESS.name) } returns Unit

        tasksServices.changeTaskStatus(taskId)

        coVerify { taskDao.getTaskById(taskId) }
        coVerify { taskDao.updateTaskStatus(taskId, Task.Status.IN_PROGRESS.name) }
    }

    @Test
    fun `should update status from IN_PROGRESS to DONE when status is IN_PROGRESS`() = runTest {
        val taskId = 1L
        val taskEntity = TaskEntity(
            id = taskId,
            title = "Test Task",
            description = "Description",
            priority = Task.Priority.HIGH.name,
            categoryId = 1,
            status = Task.Status.IN_PROGRESS.name,
            date = "2025-06-18"
        )
        coEvery { taskDao.getTaskById(taskId) } returns taskEntity
        coEvery { taskDao.updateTaskStatus(taskId, Task.Status.DONE.name) } returns Unit

        tasksServices.changeTaskStatus(taskId)

        coVerify { taskDao.getTaskById(taskId) }
        coVerify { taskDao.updateTaskStatus(taskId, Task.Status.DONE.name) }
    }

    @Test
    fun `should not update status when status is DONE`() = runTest {
        val taskId = 1L
        val taskEntity = TaskEntity(
            id = taskId,
            title = "Test Task",
            description = "Description",
            priority = Task.Priority.HIGH.name,
            categoryId = 1,
            status = Task.Status.DONE.name,
            date = "2025-06-18"
        )
        coEvery { taskDao.getTaskById(taskId) } returns taskEntity

        tasksServices.changeTaskStatus(taskId)

        coVerify { taskDao.getTaskById(taskId) }
        coVerify(exactly = 0) { taskDao.updateTaskStatus(any(), any()) }
    }

    @Test
    fun `should throw exception when changing status if task not found`() = runTest {
        val taskId = 1L
        coEvery { taskDao.getTaskById(taskId) } returns null

        assertThrows<Exception> { tasksServices.changeTaskStatus(taskId) }.also {
            assertThat(it.message).isEqualTo("task not existed")
        }
        coVerify { taskDao.getTaskById(taskId) }
        coVerify(exactly = 0) { taskDao.updateTaskStatus(any(), any()) }
    }

    @Test
    fun `should insert task into dao when adding new task`() = runTest {
        val task = Task(
            id = null,
            title = "Test Task",
            description = "Description",
            priority = Task.Priority.HIGH,
            categoryId = 1,
            status = Task.Status.TODO,
            date = LocalDateTime.parse("2025-06-18T00:00:00")
        )
        val taskEntity = TaskEntity(
            id = 0,
            title = "Test Task",
            description = "Description",
            priority = Task.Priority.HIGH.name,
            categoryId = 1,
            status = Task.Status.TODO.name,
            date = "2025-06-18"
        )
        coEvery { taskDao.addTask(taskEntity) } returns Unit

        tasksServices.addTask(task)

        coVerify { taskDao.addTask(taskEntity) }
    }

    @Test
    fun `should update task in dao when updating existing task`() = runTest {
        val task = Task(
            id = 1,
            title = "Updated Task",
            description = "Updated Description",
            priority = Task.Priority.MEDIUM,
            categoryId = 1,
            status = Task.Status.IN_PROGRESS,
            date = LocalDateTime.parse("2025-06-18T00:00:00")
        )
        val taskEntity = TaskEntity(
            id = 1,
            title = "Updated Task",
            description = "Updated Description",
            priority = Task.Priority.MEDIUM.name,
            categoryId = 1,
            status = Task.Status.IN_PROGRESS.name,
            date = "2025-06-18"
        )
        coEvery { taskDao.updateTask(taskEntity) } returns Unit

        tasksServices.updateTask(task)

        coVerify { taskDao.updateTask(taskEntity) }
    }

    @Test
    fun `should propagate exception when DAO getTasks throws`() = runTest {
        coEvery { taskDao.getTasks() } throws RuntimeException("db error")

        assertThrows<RuntimeException> { tasksServices.getTasks() }.also {
            assertThat(it.message).isEqualTo("db error")
        }
        coVerify { taskDao.getTasks() }
    }

    @Test
    fun `should propagate exception when DAO getTasksByDate throws`() = runTest {
        val date = LocalDate.parse("2025-06-18")
        coEvery { taskDao.getTasksByDate("2025-06-18") } throws IllegalStateException("db failure")

        assertThrows<IllegalStateException> { tasksServices.getTasksByDate(date) }.also {
            assertThat(it.message).isEqualTo("db failure")
        }
        coVerify { taskDao.getTasksByDate("2025-06-18") }
    }

    @Test
    fun `should throw IllegalArgumentException when mapping getTasksByDate with invalid status`() = runTest {
        val date = LocalDate.parse("2025-06-18")
        val badEntity = TaskEntity(
            id = 6,
            title = "Bad Status",
            description = "Bad",
            priority = Task.Priority.MEDIUM.name,
            categoryId = 1,
            status = "UNKNOWN_STATUS",
            date = "2025-06-18"
        )
        coEvery { taskDao.getTasksByDate("2025-06-18") } returns listOf(badEntity)

        assertThrows<IllegalArgumentException> {
            tasksServices.getTasksByDate(date)
        }
        coVerify { taskDao.getTasksByDate("2025-06-18") }
    }

    @Test
    fun `should propagate exception when updateTaskStatus DAO update fails`() = runTest {
        val taskId = 7L
        val entity = TaskEntity(
            id = taskId,
            title = "Will Fail",
            description = "Desc",
            priority = Task.Priority.HIGH.name,
            categoryId = 2,
            status = Task.Status.TODO.name,
            date = "2025-06-18"
        )
        coEvery { taskDao.getTaskById(taskId) } returns entity
        coEvery { taskDao.updateTaskStatus(taskId, Task.Status.IN_PROGRESS.name) } throws UnsupportedOperationException("update error")

        assertThrows<UnsupportedOperationException> {
            tasksServices.changeTaskStatus(taskId)
        }
        coVerify { taskDao.getTaskById(taskId) }
        coVerify { taskDao.updateTaskStatus(taskId, Task.Status.IN_PROGRESS.name) }
    }

    @Test
    fun `should propagate exception when DAO addTask throws`() = runTest {
        val task = Task(
            id = null,
            title = "Add Fail",
            description = "Desc",
            priority = Task.Priority.LOW,
            categoryId = 3,
            status = Task.Status.TODO,
            date = LocalDateTime.parse("2025-06-18T00:00:00")
        )
        coEvery { taskDao.addTask(any()) } throws IllegalStateException("insert error")

        assertThrows<IllegalStateException> { tasksServices.addTask(task) }
        coVerify { taskDao.addTask(any()) }
    }

    @Test
    fun `should propagate exception when DAO updateTask throws`() = runTest {
        val task = Task(
            id = 8,
            title = "Update Fail",
            description = "Desc",
            priority = Task.Priority.MEDIUM,
            categoryId = 4,
            status = Task.Status.IN_PROGRESS,
            date = LocalDateTime.parse("2025-06-18T00:00:00")
        )
        coEvery { taskDao.updateTask(any()) } throws RuntimeException("update error")

        assertThrows<RuntimeException> { tasksServices.updateTask(task) }
        coVerify { taskDao.updateTask(any()) }
    }

    @Test
    fun `deleteTask should call DAO delete when valid taskID provided`() = runTest {
        val taskId = 1L
        coEvery { taskDao.deleteTask(taskId) } returns Unit

        tasksServices.deleteTask(taskId)

        coVerify { taskDao.deleteTask(taskId) }
    }

    @Test
    fun `getTaskByCategory should return list of tasks when categoryID provided`() = runTest {
        val categoryId = 1L
        val taskEntities = listOf(sampleTaskEntity, sampleTaskEntity.copy(id = 2L))
        coEvery { taskDao.getTasksByCategory(categoryId) } returns taskEntities

        val result = tasksServices.getTasksByCategory(categoryId)

        assertThat(result).hasSize(2)
    }

    @Test
    fun `getTasksByCategory should return empty list when no tasks exist for category`() = runTest {
        val categoryId = 1L
        coEvery { taskDao.getTasksByCategory(categoryId) } returns emptyList()

        val result = tasksServices.getTasksByCategory(categoryId)

        assertThat(result).isEmpty()
    }

    @Test
    fun `getTasksByStatus should return tasks when dao returns task entities for specific status`() =
        runTest {
            val status = Task.Status.TODO
            val taskEntities = listOf(
                sampleTaskEntity,
                sampleTaskEntity.copy(id = 2L)
            )
            coEvery { taskDao.getTasksByStatus(status.name) } returns taskEntities

            val result = tasksServices.getTasksByStatus(status)

            assertThat(result).hasSize(2)
        }

    @Test
    fun `getTasksByDateAndStatus should return tasks when dao returns task entities for specific date and status`() = runTest {
        val date = LocalDate(2024, 1, 1)
        val status = Task.Status.TODO
        val taskEntities = listOf(sampleTaskEntity)
        coEvery { taskDao.getTasksByDateAndStatus(date.toString(), status.name) } returns taskEntities

        val result = tasksServices.getTasksByDateAndStatus(date, status)

        assertThat(result).hasSize(1)
    }

    @Test
    fun `getTasksByDateAndCategory should return tasks when dao returns task entities for specific date and category`() = runTest {
        val date = LocalDate(2024, 1, 1)
        val categoryId = 1L
        val taskEntities = listOf(sampleTaskEntity)
        coEvery { taskDao.getTasksByDateAndCategory(date.toString(), categoryId) } returns taskEntities

        val result = tasksServices.getTasksByDateAndCategory(date, categoryId)

        assertThat(result).hasSize(1)
    }

    @Test
    fun `getTasksByDateAndCategory should return empty list when no tasks match date and category criteria`() = runTest {
        val date = LocalDate(2024, 12, 31)
        val categoryId = 999L
        coEvery { taskDao.getTasksByDateAndCategory(date.toString(), categoryId) } returns emptyList()

        val result = tasksServices.getTasksByDateAndCategory(date, categoryId)

        assertThat(result).isEmpty()
    }
}