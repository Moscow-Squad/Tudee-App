package com.moscow.tudee.presentation.screen.home

import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.TasksServices
import com.moscow.tudee.presentation.BaseViewModel

class HomeViewModel(
    private val tasksServices: TasksServices
) : BaseViewModel<HomeState, HomeEvent>(HomeState()), HomeInteractionListener {

    init {
        loadTasks()
    }

    private fun loadTasks() {
        launchWithResult(
            action = { tasksServices.getTasksByStatus(Task.Status.TODO) },
            onSuccess = { response -> updateState { it.copy(todoTasks = response) } },
            onError = { handleHomeError(it) },
            onStart = { toggleLoading() },
            onFinally = { toggleLoading() }
        )
        launchWithResult(
            action = { tasksServices.getTasksByStatus(Task.Status.IN_PROGRESS) },
            onSuccess = { response -> updateState { it.copy(inProgressTasks = response) } },
            onError = { handleHomeError(it) },
            onStart = { toggleLoading() },
            onFinally = { toggleLoading() }
        )
        launchWithResult(
            action = { tasksServices.getTasksByStatus(Task.Status.DONE) },
            onSuccess = { response -> updateState { it.copy(doneTasks = response) } },
            onError = { handleHomeError(it) },
            onStart = { toggleLoading() },
            onFinally = { toggleLoading() }
        )
    }

    override fun onFloatingActionButtonClick() {
        sendEvent(HomeEvent.ShowAddTaskBottomSheet)
    }

    override fun onTaskClick(task: Task) {
        updateState { it.copy(selectedTask = task) }
        sendEvent(HomeEvent.ShowTaskDetailsBottomSheet)
    }

    override fun onAddTask(task: Task) {
        launchWithResult(
            action = { tasksServices.addTask(task) },
            onSuccess = { updateState { it.copy(todoTasks = it.todoTasks + task) } },
            onError = { handleHomeError(it) },
            onStart = { toggleLoading() },
            onFinally = { toggleLoading() }
        )
    }

    override fun onViewAllClick(taskStatus: Task.Status) {
        sendEvent(HomeEvent.ViewAll(taskStatus))
    }

    private fun toggleLoading() {
        updateState { it.copy(isLoading = !it.isLoading) }
    }

    private fun handleHomeError(throwable: Throwable) {
        // TODO: Some error handling we can do later
    }

}