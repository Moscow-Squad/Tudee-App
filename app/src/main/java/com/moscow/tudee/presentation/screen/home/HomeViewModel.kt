package com.moscow.tudee.presentation.screen.home

import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.TasksServices
import com.moscow.tudee.presentation.BaseViewModel
import kotlinx.datetime.LocalDateTime

class HomeViewModel(
    private val tasksServices: TasksServices
) : BaseViewModel<HomeState, HomeEvent>(HomeState()), HomeInteractionListener {

    init {
        loadTasks()
    }

    private fun loadTasks() {
        launchWithResult(
            action = { tasksServices.getTasksByStatus(Task.Status.TODO) },
            onSuccess = { response -> updateState { it.copy(todoTasks = response.map { it.toTaskDetails() }) } },
            onError = { handleHomeError(it) },
            onStart = { toggleLoading() },
            onFinally = { toggleLoading() }
        )
        launchWithResult(
            action = { tasksServices.getTasksByStatus(Task.Status.IN_PROGRESS) },
            onSuccess = { response -> updateState { it.copy(inProgressTasks = response.map { it.toTaskDetails() }) } },
            onError = { handleHomeError(it) },
            onStart = { toggleLoading() },
            onFinally = { toggleLoading() }
        )
        launchWithResult(
            action = { tasksServices.getTasksByStatus(Task.Status.DONE) },
            onSuccess = { response -> updateState { it.copy(doneTasks = response.map { it.toTaskDetails() }) } },
            onError = { handleHomeError(it) },
            onStart = { toggleLoading() },
            onFinally = { toggleLoading() }
        )
    }

    override fun onFloatingActionButtonClick() {
        sendEvent(HomeEvent.ShowAddTaskBottomSheet)
    }

    override fun onTaskClick(taskDetails: HomeState.TaskDetails) {
        updateState { it.copy(selectedTask = taskDetails) }
        sendEvent(HomeEvent.ShowTaskDetailsBottomSheet)
    }

    override fun onAddTask(taskDetails: HomeState.TaskDetails) {
        launchWithResult(
            action = {
                val task = tasksServices.getTaskById(taskDetails.id ?: 0)

                tasksServices.addTask(task)
             },
            onSuccess = { updateState { it.copy(todoTasks = it.todoTasks + taskDetails) } },
            onError = { handleHomeError(it) },
            onStart = { toggleLoading() },
            onFinally = { toggleLoading() }
        )
    }

    override fun onViewAllClick(taskStatus: Task.Status) {
        sendEvent(HomeEvent.ViewAll(taskStatus))
    }

    override fun onEditTaskClick(taskDetails: HomeState.TaskDetails) {
        TODO("Not yet implemented")
    }

    override fun onMoveToDoneClick(taskDetails: HomeState.TaskDetails) {
        TODO("Not yet implemented")
    }

    override fun onSaveTaskClick(taskDetails: HomeState.TaskDetails) {
        TODO("Not yet implemented")
    }

    override fun onPriorityClick(taskPriority: Task.Priority) {
        TODO("Not yet implemented")
    }

    override fun onTitleChange(newTitle: String) {
        TODO("Not yet implemented")
    }

    override fun onDescriptionChange(newDescription: String) {
        TODO("Not yet implemented")
    }

    override fun onDateChange(newDate: LocalDateTime) {
        TODO("Not yet implemented")
    }

    override fun onDismissEditBottomSheet() {
        TODO("Not yet implemented")
    }

    override fun onDismissAddBottomSheet() {
        TODO("Not yet implemented")
    }

    override fun onDismissDetailsBottomSheet() {
        TODO("Not yet implemented")
    }

    private fun toggleLoading() {
        updateState { it.copy(isLoading = !it.isLoading) }
    }

    private fun handleHomeError(throwable: Throwable) {
        // TODO: Some error handling we can do later
    }

}