package com.moscow.tudee.data.service

import com.google.common.truth.Truth.assertThat
import com.moscow.tudee.data.local.dao.TaskDao
import com.moscow.tudee.data.local.entity.TaskEntity
import com.moscow.tudee.data.local.mapper.toTask
import com.moscow.tudee.data.local.mapper.toTaskEntity
import com.moscow.tudee.domain.entity.Task
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TasksServicesImplTest {
    private lateinit var taskDao: TaskDao
    private lateinit var tasksServices: TasksServicesImpl

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
        taskDao = mockk()
        tasksServices = TasksServicesImpl(taskDao)

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

