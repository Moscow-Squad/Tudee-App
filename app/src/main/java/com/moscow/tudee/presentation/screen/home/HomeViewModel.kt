package com.moscow.tudee.presentation.screen.home

import android.util.Log
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.CategoryServices
import com.moscow.tudee.domain.service.TasksServices
import com.moscow.tudee.presentation.BaseViewModel
import com.moscow.tudee.presentation.screen.home.HomeState.SliderState
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime

class HomeViewModel(
    private val tasksServices: TasksServices,
    private val categoryServices: CategoryServices
) : BaseViewModel<HomeState, HomeEvent>(HomeState()), HomeInteractionListener {

    init {
        loadTasks()
    }



    private fun loadTasks() {
        //TODO: get tasks from database
    }

    override fun onFloatingActionButtonClick() {
        //TODO : show add task bottom sheet
    }

    override fun onTaskClick(task: HomeState.HomeTask) {
        updateState { it.copy(showTaskDetailsBottomSheet = true) }
        updateState { it.copy(selectedTask = task) }
    }

    override fun onAddTask(task: HomeState.HomeTask) {
        //TODO: add new task
    }

    override fun onViewAllClick(taskStatus: Task.Status) {
        sendEvent(HomeEvent.ViewAll(taskStatus))
    }

    override fun onEditTaskIconClick(task: HomeState.HomeTask) {
        updateState { it.copy(selectedTask = task) }
        updateState { it.copy(showEditTaskBottomSheet = true) }
    }


    override fun onMoveTaskClick(task: HomeState.HomeTask) {
        // TODO : move task from current state to a different state
    }

    override fun onSaveEditTaskClick(task: HomeState.HomeTask) {
         // TODO: save task after editing it
    }

    override fun onPriorityClick(taskPriority: Task.Priority) {
        updateState { it.copy(selectedTask = it.selectedTask?.copy(priority = taskPriority)) }
    }

    override fun onTitleChange(newTitle: String) {
        // TODO:Implement logic to change title of task
    }

    override fun onDescriptionChange(newDescription: String) {
        // TODO:Implement logic to change description of task
    }

    override fun onDateChange(newDate: LocalDateTime) {
        //// TODO:Implement logic to change date of task
    }

    override fun onCategoryClick(category: Category) {
        updateState { it.copy(selectedTask = it.selectedTask?.copy(category = category)) }
    }

    override fun onShowAddBottomSheet() {
        updateState { it.copy(showAddTaskBottomSheet = true) }
    }

    override fun onShowEditBottomSheet() {
        updateState { it.copy(showEditTaskBottomSheet = true) }
    }

    override fun onShowDetailsBottomSheet() {
        updateState { it.copy(showTaskDetailsBottomSheet = true) }
    }

    override fun onDismissEditBottomSheet() {
        updateState { it.copy(showEditTaskBottomSheet = false) }
    }

    override fun onDismissAddBottomSheet() {
        updateState { it.copy(showAddTaskBottomSheet = false) }
    }

    override fun onDismissDetailsBottomSheet() {
        updateState { it.copy(showTaskDetailsBottomSheet = false) }
    }

    private fun startLoading() {
        updateState { it.copy(isLoading = true) }
    }

    private fun endLoading() {
        updateState { it.copy(isLoading = false) }
    }

    private fun handleHomeError(throwable: Throwable) {
        // TODO: Some error handling we can do later
    }

}