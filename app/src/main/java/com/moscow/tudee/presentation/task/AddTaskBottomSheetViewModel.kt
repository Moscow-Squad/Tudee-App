package com.moscow.tudee.presentation.task

import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.CategoryServices
import com.moscow.tudee.domain.service.TasksServices
import com.moscow.tudee.presentation.BaseViewModel
import kotlinx.datetime.LocalDateTime

class AddTaskBottomSheetViewModel(
    private val tasksServices: TasksServices,
    private val categoryServices: CategoryServices
) : BaseViewModel<AddTaskBottomSheetUiState, AddTaskBottomSheetEvents>(AddTaskBottomSheetUiState()),
    AddTaskInteractionListener {

    init {
        loadCategories()
    }

    override fun onShowAddTaskBottomSheet() {
        updateState { it.copy(showAddTaskBottomSheet = true) }
    }

    override fun onDismissAddBottomSheet() {
        updateState { it.copy(showAddTaskBottomSheet = false) }
    }

    override fun onTitleChange(newTitle: String) {
        updateState { it.copy(title = newTitle) }
    }

    override fun onDescriptionChange(newDescription: String) {
        updateState { it.copy(description = newDescription) }
    }

    override fun onDateChange(newDate: LocalDateTime) {
        updateState { it.copy(date = newDate) }
    }

    override fun onPriorityClick(taskPriority: Task.Priority) {
        updateState { it.copy(priority = taskPriority) }
    }

    override fun onCategoryClick(category: Category) {
        updateState { it.copy(category = category) }
    }

    override fun onCancelAddTask() {
        updateState { AddTaskBottomSheetUiState(availableCategories = it.availableCategories) }
    }

    override fun onAddTask() {
        launchWithResult(
            action = {
                tasksServices.addTask(
                    Task(
                        id = null,
                        title = uiState.value.title,
                        description = uiState.value.description,
                        priority = uiState.value.priority ?: return@launchWithResult onErrorAddTask(
                            message = "Priority must be select"
                        ),
                        category = uiState.value.category ?: return@launchWithResult onErrorAddTask(
                            message = "Category must be select"
                        ),
                        status = uiState.value.status,
                        date = uiState.value.date
                    )
                )
            },
            onSuccess = ::onAddTaskSuccess,
            onError = ::onAddTaskError,
            onStart = ::startLoading,
            onFinally = ::endLoading
        )
    }

    private fun onAddTaskSuccess(response: Unit) {
        sendEvent(AddTaskBottomSheetEvents.NotifyTaskAdded(uiState.value.date.date))
        updateState { AddTaskBottomSheetUiState(availableCategories = it.availableCategories) }
    }

    private fun onAddTaskError(throwable: Throwable) {
        onErrorAddTask(
            message = "Error in adding task: ${throwable.message}"
        )
        sendEvent(AddTaskBottomSheetEvents.NotifyTaskNotAdded)
    }

    private fun onErrorAddTask(message: String) {
        updateState { it.copy(errorMessage = message) }
    }

    private fun loadCategories() {
        launchWithResult(
            action = ::loadCategoriesAction,
            onSuccess = ::onLoadCategoriesSuccess,
            onError = ::onLoadCategoriesError,
            onStart = ::startLoading,
            onFinally = ::endLoading
        )
    }

    private suspend fun loadCategoriesAction(): List<Category> {
        return categoryServices.getCategories()
    }

    private fun onLoadCategoriesSuccess(response: List<Category>) {
        updateState { it.copy(availableCategories = response) }
    }

    private fun onLoadCategoriesError(throwable: Throwable) {
        onErrorLoadCategories(
            errorMessage = "Error in loading available categories: ${throwable.message}"
        )
    }

    private fun onErrorLoadCategories(errorMessage: String) {
        updateState { it.copy(errorMessage = errorMessage) }
    }

    private fun startLoading() {
        updateState { it.copy(isLoading = true) }
    }

    private fun endLoading() {
        updateState { it.copy(isLoading = false) }
    }
}