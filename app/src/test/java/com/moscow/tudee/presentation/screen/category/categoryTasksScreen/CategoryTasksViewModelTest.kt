package com.moscow.tudee.presentation.screen.category.categoryTasksScreen

import com.google.common.truth.Truth.assertThat
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.entity.Task.Priority
import com.moscow.tudee.domain.entity.Task.Status
import com.moscow.tudee.domain.service.CategoryServices
import com.moscow.tudee.domain.service.TasksServices
import com.moscow.tudee.presentation.screen.category.toCategoryUi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryTasksViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var categoryServices: CategoryServices
    private lateinit var tasksServices: TasksServices
    private lateinit var viewModel: CategoryTasksViewModel

    private val testCategoryId = 10L
    private lateinit var testCategory: Category

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        categoryServices = mockk()
        tasksServices = mockk()

        testCategory = Category(
            id = testCategoryId,
            title = "Work",
            iconUri = "icon_url",
            isPredefined = false,
            countOfTasks = 5
        )

        coEvery { categoryServices.getCategoryById(testCategoryId) } returns testCategory
        coEvery {
            tasksServices.getTasksByCategoryAndStatus(testCategoryId, Status.TODO)
        } returns emptyList()

        viewModel = CategoryTasksViewModel(testCategoryId, categoryServices, tasksServices)
        testScope.advanceUntilIdle()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should load category and tasks on init`() {
        coVerify { categoryServices.getCategoryById(testCategoryId) }
        coVerify { tasksServices.getTasksByCategoryAndStatus(testCategoryId, Status.TODO) }

        val state = viewModel.uiState.value
        assertThat(state.category.id).isEqualTo(testCategoryId)
        assertThat(state.selectedStatus).isEqualTo(Status.TODO)
        assertThat(state.tasks).isEmpty()
    }

    @Test
    fun `onTasksStatusClick should update status and load tasks`() {
        val expectedTask = Task(
            id = 1L,
            title = "Important Task",
            description = "Handle reports",
            priority = Priority.HIGH,
            category = testCategory,
            status = Status.IN_PROGRESS,
            date = LocalDateTime(2025, 6, 25, 10, 0)
        )

        coEvery {
            tasksServices.getTasksByCategoryAndStatus(testCategoryId, Status.IN_PROGRESS)
        } returns listOf(expectedTask)

        viewModel.onTasksStatusClick(testCategoryId, Status.IN_PROGRESS)
        testScope.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.selectedStatus).isEqualTo(Status.IN_PROGRESS)
        assertThat(state.tasks).hasSize(1)
        assertThat(state.tasks.first().title).isEqualTo("Important Task")
        assertThat(state.tasks.first().status).isEqualTo(Status.IN_PROGRESS)
    }

    @Test
    fun `onUpdateCategory should call service and close edit sheet`() {
        coEvery { categoryServices.updateCategory(any()) } returns Unit

        viewModel.onUpdateCategory(testCategory.toCategoryUi())
        testScope.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.isEditCategoryBottomSheetShow).isFalse()
    }

    @Test
    fun `onDeleteCategory should call service and close delete sheet`() {
        coEvery { categoryServices.deleteCategory(any()) } returns Unit

        viewModel.onDeleteCategory(testCategory.toCategoryUi())
        testScope.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.isDeleteCategoryBottomSheetShow).isFalse()
    }

    @Test
    fun `onShowEditCategoryBottomSheet should show edit sheet`() {
        viewModel.onShowEditCategoryBottomSheet()
        val state = viewModel.uiState.value
        assertThat(state.isEditCategoryBottomSheetShow).isTrue()
    }

    @Test
    fun `onHideEditCategoryBottomSheet should hide edit sheet`() {
        viewModel.onShowEditCategoryBottomSheet()
        viewModel.onHideEditCategoryBottomSheet()
        val state = viewModel.uiState.value
        assertThat(state.isEditCategoryBottomSheetShow).isFalse()
    }

    @Test
    fun `onShowDeleteCategoryBottomSheet should show delete sheet`() {
        viewModel.onShowDeleteCategoryBottomSheet()
        val state = viewModel.uiState.value
        assertThat(state.isDeleteCategoryBottomSheetShow).isTrue()
    }

    @Test
    fun `onHideDeleteCategoryBottomSheet should hide delete sheet`() {
        viewModel.onShowDeleteCategoryBottomSheet()
        viewModel.onHideDeleteCategoryBottomSheet()
        val state = viewModel.uiState.value
        assertThat(state.isDeleteCategoryBottomSheetShow).isFalse()
    }

    @Test
    fun `getCategoryById should handle failure`() {
        coEvery { categoryServices.getCategoryById(testCategoryId) } throws Exception("Fail")

        viewModel = CategoryTasksViewModel(testCategoryId, categoryServices, tasksServices)
        testScope.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.errorMessage).isEqualTo(R.string.get_category_failed)
        assertThat(state.isSnackBarShow).isTrue()
    }

    @Test
    fun `onTasksStatusClick should handle task loading failure`() {
        coEvery {
            tasksServices.getTasksByCategoryAndStatus(testCategoryId, Status.IN_PROGRESS)
        } throws Exception("Failed to load tasks")

        viewModel.onTasksStatusClick(testCategoryId, Status.IN_PROGRESS)
        testScope.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.errorMessage).isEqualTo(R.string.get_tasks_failed)
        assertThat(state.isSnackBarShow).isTrue()
        assertThat(state.isLoading).isFalse()
    }

    @Test
    fun `onUpdateCategory should handle update failure`() {
        coEvery { categoryServices.updateCategory(any()) } throws Exception("Update failed")

        viewModel.onUpdateCategory(testCategory.toCategoryUi())
        testScope.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.isEditCategoryBottomSheetShow).isFalse()
        assertThat(state.errorMessage).isEqualTo(R.string.category_updated_failed)
        assertThat(state.isSnackBarShow).isTrue()
    }

    @Test
    fun `onDeleteCategory should handle delete failure`() {
        coEvery { categoryServices.deleteCategory(any()) } throws Exception("Delete failed")

        viewModel.onDeleteCategory(testCategory.toCategoryUi())
        testScope.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.isDeleteCategoryBottomSheetShow).isFalse()
        assertThat(state.errorMessage).isEqualTo(R.string.category_deleted_failed)
        assertThat(state.isSnackBarShow).isTrue()
    }
}
