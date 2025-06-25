package com.moscow.tudee.presentation.screen.category.categoriesScreen

import com.google.common.truth.Truth.assertThat
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.service.CategoryServices
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
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var categoryServices: CategoryServices
    private lateinit var viewModel: CategoryViewModel

    private val category = Category(
        id = 1L,
        title = "Test",
        iconUri = "uri",
        isPredefined = false,
        countOfTasks = 2
    )

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        categoryServices = mockk()

        coEvery { categoryServices.getCategories() } returns listOf(category)

        viewModel = CategoryViewModel(categoryServices)
        testScope.advanceUntilIdle()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should return categories when viewModel initialized`() {
        coVerify { categoryServices.getCategories() }

        val state = viewModel.uiState.value
        assertThat(state.categories).hasSize(1)
        assertThat(state.categories.first().id).isEqualTo(category.id)
    }

    @Test
    fun `should return updated categories and show snackBar when returned from edit with message`() {
        viewModel.onReturnedFromEditWithMessage(R.string.category_added_successfully)
        testScope.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.successMessage).isEqualTo(R.string.category_added_successfully)
        assertThat(state.isSnackBarShow).isTrue()
    }

    @Test
    fun `should return true when show add category bottom sheet is triggered`() {
        viewModel.onShowAddCategoryBottomSheet()

        val state = viewModel.uiState.value
        assertThat(state.isAddCategoryBottomSheetShow).isTrue()
    }

    @Test
    fun `should return false when hide add category bottom sheet is triggered`() {
        viewModel.onShowAddCategoryBottomSheet()
        viewModel.onHideAddCategoryBottomSheet()

        val state = viewModel.uiState.value
        assertThat(state.isAddCategoryBottomSheetShow).isFalse()
    }

    @Test
    fun `should return success state when category is added successfully`() {
        coEvery { categoryServices.addCategory(any()) } returns Unit
        coEvery { categoryServices.getCategories() } returns listOf(category)

        viewModel.onAddCategory(category.toCategoryUi())
        testScope.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.successMessage).isEqualTo(R.string.category_added_successfully)
        assertThat(state.isSnackBarShow).isTrue()
        assertThat(state.isAddCategoryBottomSheetShow).isFalse()
    }

    @Test
    fun `should return error state when adding category fails`() {
        coEvery { categoryServices.addCategory(any()) } throws Exception("Failed")

        viewModel.onAddCategory(category.toCategoryUi())
        testScope.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.errorMessage).isEqualTo(R.string.category_added_failed)
        assertThat(state.isSnackBarShow).isTrue()
        assertThat(state.isAddCategoryBottomSheetShow).isFalse()
    }

    @Test
    fun `should return cleared snackBar state when snackBar is hidden`() {
        viewModel.onReturnedFromEditWithMessage(R.string.category_added_successfully)
        viewModel.onHideSnackBar()

        val state = viewModel.uiState.value
        assertThat(state.successMessage).isNull()
        assertThat(state.errorMessage).isNull()
        assertThat(state.isSnackBarShow).isFalse()
    }

    @Test
    fun `should return error state when fetching categories fails`() {
        coEvery { categoryServices.getCategories() } throws Exception("Failed")
        viewModel = CategoryViewModel(categoryServices)
        testScope.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.errorMessage).isEqualTo(R.string.get_category_failed)
        assertThat(state.isSnackBarShow).isTrue()
    }

}
