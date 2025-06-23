package com.moscow.tudee.presentation.category.categoryTasksScreen

import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.CategoryServices
import com.moscow.tudee.domain.service.TasksServices
import com.moscow.tudee.presentation.BaseViewModel
import com.moscow.tudee.presentation.category.CategoriesEvents
import com.moscow.tudee.presentation.category.CategoriesScreenState
import com.moscow.tudee.presentation.category.toCategory
import com.moscow.tudee.presentation.category.toCategoryUi
import com.moscow.tudee.presentation.category.toTaskUi

class CategoryTasksViewModel(
    private val categoryServices: CategoryServices, private val taskServices: TasksServices
) : BaseViewModel<CategoriesScreenState, CategoriesEvents>(CategoriesScreenState()),
    CategoriesTasksInteractionListener {

    // TODO: replace this id with outside id came from previous screen 
    init {
        onTasksStatusClick(1L, Task.Status.IN_PROGRESS)
        test()
    }

    fun test() {
        launchWithResult(
            action = { categoryServices.getCategoryById(22L) },
            onSuccess = { category ->
                updateState {
                    it.copy(
                        category = category.toCategoryUi(),
                        isEditCategoryBottomSheetShow = true
                    )
                }
            },
            onError = {},
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
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
                successMessage = R.string.category_updated_successfully,
                isSnackBarShow = true
            )
        }
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
                successMessage = R.string.category_deleted_successfully,
                isSnackBarShow = true
            )
        }
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
        // TODO: get current category
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

    private fun onLoading() {
        updateState { it.copy(isLoading = true) }
    }

    private fun onFinally() {
        updateState { it.copy(isLoading = false) }
    }
}