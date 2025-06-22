package com.moscow.tudee.data.service

import com.google.common.truth.Truth.assertThat
import com.moscow.tudee.data.local.dao.CategoryDao
import com.moscow.tudee.data.local.dao.TaskDao
import com.moscow.tudee.data.local.entity.CategoryEntity
import com.moscow.tudee.data.local.entity.TaskEntity
import com.moscow.tudee.data.local.mapper.toCategory
import com.moscow.tudee.domain.entity.Category
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

    private val sampleCategoryEntity = CategoryEntity(
        id = 1L,
        title = "Sample Category",
        iconUri = null,
        isPredefined = false,
        countOfTasks = 0
    )
    private val sampleCategory: Category = sampleCategoryEntity.toCategory()

    private val sampleTaskEntity = TaskEntity(
        id = 1L,
        title = "Test Task",
        description = "Test Description",
        priority = Task.Priority.HIGH.name,
        categoryId = 1L,
        status = Task.Status.TODO.name,
        date = "2025-06-18"
    )

    @BeforeEach
    fun setUp() {
        taskDao = mockk(relaxed = true)
        categoryDao = mockk(relaxed = true)

        coEvery { categoryDao.getCategories() } returns listOf(sampleCategoryEntity)
        coEvery { categoryDao.getCategoryById(any()) } returns sampleCategoryEntity

        tasksServices = TasksServicesImpl(taskDao, categoryDao)
    }

    @Test
    fun `should return list of tasks when tasks exist`() = runTest {
        val taskEntity = sampleTaskEntity.copy(
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
            category = sampleCategory,
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
        val taskEntity = sampleTaskEntity.copy(date = "2025-06-18")
        val expectedTask = Task(
            id = 1,
            title = taskEntity.title,
            description = taskEntity.description,
            priority = Task.Priority.HIGH,
            category = sampleCategory,
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
        val taskEntity = sampleTaskEntity.copy(id = taskId)
        val expectedTask = Task(
            id = taskId,
            title = taskEntity.title,
            description = taskEntity.description,
            priority = Task.Priority.HIGH,
            category = sampleCategory,
            status = Task.Status.TODO,
            date = LocalDateTime.parse("2025-06-18T00:00:00")
        )

        coEvery { taskDao.getTaskById(taskId) } returns taskEntity

        val result = tasksServices.getTaskById(taskId)

        assertThat(result).isEqualTo(expectedTask)
        coVerify { taskDao.getTaskById(taskId) }
        coVerify { categoryDao.getCategoryById(taskEntity.categoryId) }
    }

    @Test
    fun `should throw exception when task not found`() = runTest {
        val taskId = 1L
        coEvery { taskDao.getTaskById(taskId) } returns null

        val thrown = assertThrows<Exception> { tasksServices.getTaskById(taskId) }
        assertThat(thrown).hasMessageThat().isEqualTo("task not found")

        coVerify { taskDao.getTaskById(taskId) }
    }

    @Test
    fun `should update status from TODO to IN_PROGRESS when status is TODO`() = runTest {
        val taskId = 1L
        val entity = sampleTaskEntity.copy(status = Task.Status.TODO.name)
        coEvery { taskDao.getTaskById(taskId) } returns entity
        coEvery { taskDao.updateTaskStatus(taskId, Task.Status.IN_PROGRESS.name) } returns Unit

        tasksServices.changeTaskStatus(taskId)

        coVerify { taskDao.getTaskById(taskId) }
        coVerify { taskDao.updateTaskStatus(taskId, Task.Status.IN_PROGRESS.name) }
    }

    @Test
    fun `should update status from IN_PROGRESS to DONE when status is IN_PROGRESS`() = runTest {
        val taskId = 1L
        val entity = sampleTaskEntity.copy(status = Task.Status.IN_PROGRESS.name)
        coEvery { taskDao.getTaskById(taskId) } returns entity
        coEvery { taskDao.updateTaskStatus(taskId, Task.Status.DONE.name) } returns Unit

        tasksServices.changeTaskStatus(taskId)

        coVerify { taskDao.getTaskById(taskId) }
        coVerify { taskDao.updateTaskStatus(taskId, Task.Status.DONE.name) }
    }

    @Test
    fun `should not update status when status is DONE`() = runTest {
        val taskId = 1L
        val entity = sampleTaskEntity.copy(status = Task.Status.DONE.name)
        coEvery { taskDao.getTaskById(taskId) } returns entity

        tasksServices.changeTaskStatus(taskId)

        coVerify { taskDao.getTaskById(taskId) }
        coVerify(exactly = 0) { taskDao.updateTaskStatus(any(), any()) }
    }

    @Test
    fun `should throw exception when changing status if task not found`() = runTest {
        val taskId = 1L
        coEvery { taskDao.getTaskById(taskId) } returns null

        val thrown = assertThrows<Exception> { tasksServices.changeTaskStatus(taskId) }
        assertThat(thrown).hasMessageThat().isEqualTo("task not existed")

        coVerify { taskDao.getTaskById(taskId) }
        coVerify(exactly = 0) { taskDao.updateTaskStatus(any(), any()) }
    }

    @Test
    fun `should insert task into dao when adding new task`() = runTest {
        val newTask = Task(
            id = null,
            title = "Test Task",
            description = "Description",
            priority = Task.Priority.HIGH,
            category = sampleCategory,
            status = Task.Status.TODO,
            date = LocalDateTime.parse("2025-06-18T00:00:00")
        )
        val expectedEntity = TaskEntity(
            id = 0,
            title = "Test Task",
            description = "Description",
            priority = Task.Priority.HIGH.name,
            categoryId = sampleCategory.id!!,
            status = Task.Status.TODO.name,
            date = "2025-06-18"
        )
        coEvery { taskDao.addTask(expectedEntity) } returns Unit

        tasksServices.addTask(newTask)

        coVerify { taskDao.addTask(expectedEntity) }
        coVerify { categoryDao.incrementTaskCount(sampleCategory.id!!) }
    }

    @Test
    fun `should update task in dao when updating existing task`() = runTest {
        val upd = Task(
            id = 1,
            title = "Updated Task",
            description = "Updated Description",
            priority = Task.Priority.MEDIUM,
            category = sampleCategory,
            status = Task.Status.IN_PROGRESS,
            date = LocalDateTime.parse("2025-06-18T00:00:00")
        )
        val expectedEntity = TaskEntity(
            id = 1,
            title = "Updated Task",
            description = "Updated Description",
            priority = Task.Priority.MEDIUM.name,
            categoryId = sampleCategory.id!!,
            status = Task.Status.IN_PROGRESS.name,
            date = "2025-06-18"
        )
        coEvery { taskDao.updateTask(expectedEntity) } returns Unit

        tasksServices.updateTask(upd)

        coVerify { taskDao.updateTask(expectedEntity) }
    }

    @Test
    fun `deleteTask should call DAO delete and decrement count when valid taskID provided`() =
        runTest {
            val taskId = 1L
            coEvery { taskDao.getTaskById(taskId) } returns sampleTaskEntity
            coEvery { taskDao.deleteTask(taskId) } returns Unit

            tasksServices.deleteTask(taskId)

            coVerify { taskDao.getTaskById(taskId) }
            coVerify { taskDao.deleteTask(taskId) }
            coVerify { categoryDao.decrementTaskCount(sampleTaskEntity.categoryId) }
        }

    @Test
    fun `getTasksByCategory should return list of tasks when categoryID provided`() = runTest {
        val categoryId = 1L
        val taskEntities = listOf(sampleTaskEntity, sampleTaskEntity.copy(id = 2L))
        coEvery { taskDao.getTasksByCategory(categoryId) } returns taskEntities

        val result = tasksServices.getTasksByCategory(categoryId)

        assertThat(result).hasSize(2)
        // Optionally, verify each has the proper Category object:
        result.forEach { assertThat(it.category).isEqualTo(sampleCategory) }
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
            result.forEach { assertThat(it.category).isEqualTo(sampleCategory) }
        }

    @Test
    fun `getTasksByDateAndStatus should return tasks when dao returns task entities for specific date and status`() =
        runTest {
            val date = LocalDate.parse("2025-06-18")
            val status = Task.Status.TODO
            val taskEntities = listOf(sampleTaskEntity)
            coEvery {
                taskDao.getTasksByDateAndStatus(
                    date.toString(),
                    status.name
                )
            } returns taskEntities

            val result = tasksServices.getTasksByDateAndStatus(date, status)

            assertThat(result).hasSize(1)
            assertThat(result[0].category).isEqualTo(sampleCategory)
        }

    @Test
    fun `getTasksByDateAndCategory should return tasks when dao returns task entities for specific date and category`() =
        runTest {
            val date = LocalDate.parse("2025-06-18")
            val categoryId = 1L
            val taskEntities = listOf(sampleTaskEntity)
            coEvery {
                taskDao.getTasksByDateAndCategory(
                    date.toString(),
                    categoryId
                )
            } returns taskEntities

            val result = tasksServices.getTasksByDateAndCategory(date, categoryId)

            assertThat(result).hasSize(1)
            assertThat(result[0].category).isEqualTo(sampleCategory)
        }

    @Test
    fun `getTasksByDateAndCategory should return empty list when no tasks match date and category criteria`() =
        runTest {
            val date = LocalDate.parse("2024-12-31")
            val categoryId = 999L
            coEvery {
                taskDao.getTasksByDateAndCategory(
                    date.toString(),
                    categoryId
                )
            } returns emptyList()

            val result = tasksServices.getTasksByDateAndCategory(date, categoryId)

            assertThat(result).isEmpty()
        }
}
