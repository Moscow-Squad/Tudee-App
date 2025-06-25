package com.moscow.tudee.presentation.screen.category.categoryTasksScreen

import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.CategoryServices
import com.moscow.tudee.domain.service.TasksServices
import com.moscow.tudee.presentation.BaseViewModel
import com.moscow.tudee.presentation.screen.category.CategoriesScreenState
import com.moscow.tudee.presentation.screen.category.categoryTasksScreen.CategoryTasksEvents
import com.moscow.tudee.presentation.screen.category.toCategory
import com.moscow.tudee.presentation.screen.category.toCategoryUi
import com.moscow.tudee.presentation.screen.category.toTaskUi

class CategoryTasksViewModel(
    private val categoryId: Long,
    private val categoryServices: CategoryServices,
    private val taskServices: TasksServices
) : BaseViewModel<CategoriesScreenState, CategoryTasksEvents>(CategoriesScreenState()),
    CategoriesTasksInteractionListener {

    init {
        onTasksStatusClick(categoryId, Task.Status.TODO)
        getCategoryById()
    }

    private fun getCategoryById() {
        launchWithResult(
            action = { categoryServices.getCategoryById(categoryId) },
            onSuccess = { category ->
                onGetCategoryByIdSuccess(category)
            },
            onError = {
                onGetCategoryByIdFailed()
            },
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onGetCategoryByIdSuccess(category: Category) {
        updateState {
            it.copy(
                category = category.toCategoryUi()
            )
        }
    }

    private fun onGetCategoryByIdFailed() {
        updateState {
            it.copy(
                errorMessage = R.string.get_category_failed,
                isSnackBarShow = true
            )
        }
    }


    override fun onTasksStatusClick(categoryID: Long, status: Task.Status) {
        updateState { it.copy(selectedStatus = status) }
        launchWithResult(
            action = { taskServices.getTasksByCategoryAndStatus(categoryID, status) },
            onSuccess = ::onGetTasksSuccess,
            onError = ::onGetTasksFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onGetTasksSuccess(tasks: List<Task>) {
        val taskUiList = tasks.map { it.toTaskUi() }
        updateState {
            it.copy(
                tasks = taskUiList, isLoading = false
            )
        }
    }

    private fun onGetTasksFailed(error: Throwable) {
        updateState {
            it.copy(
                errorMessage = R.string.get_tasks_failed, isLoading = false, isSnackBarShow = true
            )
        }
    }

    override fun onUpdateCategory(newCategory: CategoriesScreenState.CategoryUi) {
        launchWithResult(
            action = { categoryServices.updateCategory(newCategory.toCategory()) },
            onSuccess = { onUpdateCategorySuccess() },
            onError = ::onUpdateCategoryFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onUpdateCategorySuccess() {
        updateState {
            it.copy(
                isEditCategoryBottomSheetShow = false,
            )
        }
        sendEvent(CategoryTasksEvents.NavigateBackWithResult(R.string.category_updated_successfully))
    }

    private fun onUpdateCategoryFailed(error: Throwable) {
        updateState {
            it.copy(
                errorMessage = R.string.category_updated_failed,
                isEditCategoryBottomSheetShow = false,
                isSnackBarShow = true
            )
        }

    }

    override fun onDeleteCategory(category: CategoriesScreenState.CategoryUi) {
        launchWithResult(
            action = { categoryServices.deleteCategory(category.id) },
            onSuccess = { onDeleteCategorySuccess() },
            onError = ::onDeleteCategoryFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onDeleteCategorySuccess() {
        updateState {
            it.copy(
                isDeleteCategoryBottomSheetShow = false,
            )
        }
        sendEvent(CategoryTasksEvents.NavigateBackWithResult(R.string.category_deleted_successfully))
    }

    private fun onDeleteCategoryFailed(error: Throwable) {
        updateState {
            it.copy(
                errorMessage = R.string.category_deleted_failed,
                isDeleteCategoryBottomSheetShow = false,
                isSnackBarShow = true
            )
        }
    }

    override fun onShowEditCategoryBottomSheet() {
        updateState { it.copy(isEditCategoryBottomSheetShow = true) }
    }

    override fun onHideEditCategoryBottomSheet() {
        updateState { it.copy(isEditCategoryBottomSheetShow = false) }
    }

    override fun onShowDeleteCategoryBottomSheet() {
        updateState { it.copy(isDeleteCategoryBottomSheetShow = true) }
    }


    override fun onHideDeleteCategoryBottomSheet() {
        updateState { it.copy(isDeleteCategoryBottomSheetShow = false) }
    }


    override fun onHideSnackBar() {
        updateState {
            it.copy(
                isSnackBarShow = false,
                successMessage = null,
                errorMessage = null
            )
        }

    }

    override fun onBackPress() {
        sendEvent(CategoryTasksEvents.NavigateBack)
    }

    private fun onLoading() {
        updateState { it.copy(isLoading = true) }
    }

    private fun onFinally() {
        updateState { it.copy(isLoading = false) }
    }
}