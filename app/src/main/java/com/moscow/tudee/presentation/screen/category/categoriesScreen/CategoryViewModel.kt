package com.moscow.tudee.presentation.screen.category.categoriesScreen

import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.service.CategoryServices
import com.moscow.tudee.presentation.BaseViewModel
import com.moscow.tudee.presentation.mapper.toCategory
import com.moscow.tudee.presentation.mapper.toCategoryUi
import com.moscow.tudee.presentation.model.CategoryUi
import com.moscow.tudee.presentation.screen.category.CategoriesScreenState

class CategoryViewModel(
    private val categoryServices: CategoryServices,
) : BaseViewModel<CategoriesScreenState, CategoriesEvents>(CategoriesScreenState()),
    CategoriesInteractionListener {

    init {
        getAllCategories()
    }

    fun onReturnedFromEditWithMessage(messageResId: Int?) {
        getAllCategories()
        messageResId?.let {
            updateState {
                it.copy(
                    successMessage = messageResId,
                    isSnackBarShow = true
                )
            }
        }
    }

    private fun getAllCategories() {
        launchWithResult(
            action = { categoryServices.getCategories() },
            onSuccess = ::onGetAllCategoriesSuccess,
            onError = ::onGetAllCategoriesFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onGetAllCategoriesSuccess(categories: List<Category>) {
        val categoryUiList = categories.map { it.toCategoryUi() }
        updateState {
            it.copy(
                categories = categoryUiList,
            )
        }
    }

    private fun onGetAllCategoriesFailed(error: Throwable) {
        updateState {
            it.copy(
                errorMessage = R.string.get_category_failed,
                isSnackBarShow = true
            )
        }
    }

    override fun onAddCategory(categoryUi: CategoryUi) {
        launchWithResult(
            action = { categoryServices.addCategory(categoryUi.toCategory()) },
            onSuccess = { onAddCategorySuccess() },
            onError = ::onAddCategoryFailed,
            onStart = ::onLoading,
            onFinally = ::onFinally
        )
    }

    private fun onAddCategorySuccess() {
        getAllCategories()
        updateState {
            it.copy(
                isAddCategoryBottomSheetShow = false,
                successMessage = R.string.category_added_successfully,
                isSnackBarShow = true
            )
        }
    }

    private fun onAddCategoryFailed(error: Throwable) {
        updateState {
            it.copy(
                errorMessage = R.string.category_added_failed,
                isAddCategoryBottomSheetShow = false,
                isSnackBarShow = true
            )
        }
    }

    override fun onShowAddCategoryBottomSheet() {
        updateState { it.copy(isAddCategoryBottomSheetShow = true) }
    }

    override fun onHideAddCategoryBottomSheet() {
        updateState { it.copy(isAddCategoryBottomSheetShow = false) }
    }

    override fun onCategoryClick(categoryID: Long) {
        sendEvent(CategoriesEvents.NavigateToTasks(categoryID))
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