package com.moscow.tudee.presentation.category.categoryTasks

import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.CategoryServices
import com.moscow.tudee.domain.service.TasksServices
import com.moscow.tudee.presentation.BaseViewModel
import com.moscow.tudee.presentation.category.categoryScreen.CategoriesEvents
import com.moscow.tudee.presentation.category.categoryScreen.CategoriesScreenState
import com.moscow.tudee.presentation.category.categoryScreen.toCategory
import com.moscow.tudee.presentation.category.categoryScreen.toCategoryUi
import com.moscow.tudee.presentation.category.categoryScreen.toTaskUi

class CategoryTasksViewModel(
    private val categoryServices: CategoryServices,
    private val taskServices: TasksServices
) : BaseViewModel<CategoriesScreenState, CategoriesEvents>(CategoriesScreenState()),
    CategoriesTasksInteractionListener {

    init {
        getTasksByCategoryAndStatus()
        getCategoryByID()
    }

    private fun getCategoryByID() {
        launchWithResult(
            action = { categoryServices.getCategoryById(1L) },
            onSuccess = { category ->
                updateState {
                    it.copy(
                        category = category.toCategoryUi()
                    )
                }
            },
            onError = { error ->
                updateState {
                    it.copy(
                        errorMessage = error.message,
                        isLoading = false,
                        isSnackBarShow = true
                    )
                }
            },
            onStart = { onLoading() }
        )
    }

    private fun getTasksByCategoryAndStatus() {
        launchWithResult(
            action = { taskServices.getTasksByCategoryAndStatus(1L, Task.Status.TODO) },
            onSuccess = { tasks ->
                val taskUiList = tasks.map { it.toTaskUi() }
                updateState {
                    it.copy(
                        tasks = taskUiList,
                        isLoading = false
                    )
                }
            },
            onError = { error ->
                updateState {
                    it.copy(
                        errorMessage = error.message,
                        isLoading = false,
                        isSnackBarShow = true
                    )
                }
            },
            onStart = { onLoading() }
        )
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

    override fun onAddCategoryConfirmed(category: CategoriesScreenState.CategoryUi) {
        launchWithResult(
            action = { categoryServices.addCategory(category.toCategory()) },
            onSuccess = {
                updateState {
                    it.copy(
                        isAddCategoryBottomSheetShow = false,
                        successMessage = "Category added successfully",
                        isSnackBarShow = true
                    )
                }
            },
            onError = { error ->
                updateState {
                    it.copy(
                        errorMessage = error.message,
                        isAddCategoryBottomSheetShow = false,
                        isSnackBarShow = true
                    )
                }
            }, onStart = { onLoading() }
        )

    }

    override fun onEditCategoryConfirmed(newCategory: CategoriesScreenState.CategoryUi) {
        launchWithResult(
            action = { categoryServices.updateCategory(newCategory.toCategory()) },
            onSuccess = {
                updateState {
                    it.copy(
                        isEditCategoryBottomSheetShow = false,
                        successMessage = "Category updated successfully",
                        isSnackBarShow = true
                    )
                }
            },
            onError = { error ->
                updateState {
                    it.copy(
                        errorMessage = error.message,
                        isEditCategoryBottomSheetShow = false,
                        isSnackBarShow = true
                    )
                }
            },
            onStart = { onLoading() }
        )
    }

    override fun onDeleteCategoryConfirmed(category: CategoriesScreenState.CategoryUi) {
        launchWithResult(
            action = { categoryServices.deleteCategory(category.id) },
            onSuccess = {
                updateState {
                    it.copy(
                        isDeleteCategoryBottomSheetShow = false,
                        successMessage = "Category deleted successfully",
                        isSnackBarShow = true
                    )
                }
            },
            onError = { error ->
                updateState {
                    it.copy(
                        errorMessage = error.message,
                        isDeleteCategoryBottomSheetShow = false,
                        isSnackBarShow = true
                    )
                }
            },
            onStart = { onLoading() },
            onFinally = { updateState { it.copy(isDeleteCategoryBottomSheetShow = false) }}
        )
    }

    override fun onTasksStatusClick(categoryID: Long, status: Task.Status) {
        launchWithResult(
            action = { taskServices.getTasksByCategoryAndStatus(categoryID, status) },
            onSuccess = { tasks ->
                val taskUiList = tasks.map { it.toTaskUi() }
                updateState {
                    it.copy(
                        tasks = taskUiList,
                        isLoading = false
                    )
                }
            },
            onError = { error ->
                updateState {
                    it.copy(
                        errorMessage = error.message,
                        isLoading = false,
                        isSnackBarShow = true
                    )
                }
            },
            onStart = { onLoading() }
        )
    }

    private fun onLoading() {
        updateState { it.copy(isLoading = true) }
    }
}