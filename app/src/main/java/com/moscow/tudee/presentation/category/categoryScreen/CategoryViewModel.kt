package com.moscow.tudee.presentation.category.categoryScreen

import android.util.Log
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.CategoryServices
import com.moscow.tudee.domain.service.TasksServices
import com.moscow.tudee.presentation.BaseViewModel

class CategoryViewModel(
    private val categoryServices: CategoryServices,
    private val taskServices: TasksServices
) : BaseViewModel<CategoriesScreenState, CategoriesEvents>(CategoriesScreenState()),
    CategoriesInteractionListener {

    init {
        launchWithResult(action = { categoryServices.getCategories() }, onSuccess = { categories ->
            val categoryUiList = categories.map { it.toCategoryUi() }
            updateState {
                it.copy(
                    categories = categoryUiList,
                    isLoading = false,
                )
            }
        }, onError = { error ->
            updateState {
                it.copy(
                    errorMessage = error.message,
                    isLoading = false,
                    isSnackBarShow = true
                )
            }
        }, onStart = { onLoading() })
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



    private fun onLoading() {
        updateState { it.copy(isLoading = true) }
    }
}