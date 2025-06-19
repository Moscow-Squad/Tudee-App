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
            onSuccess = { response -> updateState { it.copy(todoTasks = response.map { it }) } },
            onError = { handleHomeError(it) },
            onStart = { startLoading() },
            onFinally = { endLoading() }
        )
        launchWithResult(
            action = { tasksServices.getTasksByStatus(Task.Status.IN_PROGRESS) },
            onSuccess = { response -> updateState { it.copy(inProgressTasks = response.map { it }) } },
            onError = { handleHomeError(it) },
            onStart = { startLoading() },
            onFinally = { endLoading() }
        )
        launchWithResult(
            action = { tasksServices.getTasksByStatus(Task.Status.DONE) },
            onSuccess = { response -> updateState { it.copy(doneTasks = response.map { it }) } },
            onError = { handleHomeError(it) },
            onStart = { startLoading() },
            onFinally = { endLoading() }
        )
    }

    override fun onFloatingActionButtonClick() {
        updateState { it.copy(showAddTaskBottomSheet = true) }
    }

    override fun onTaskClick(task: Task) {
        updateState { it.copy(showTaskDetailsBottomSheet = true) }
        updateState { it.copy(selectedTask = task) }
    }

    override fun onAddTask(task: Task) {
        launchWithResult(
            action = {
                tasksServices.addTask(task)
            },
            onSuccess = {
                updateState {
                    it.copy(
                        showAddTaskBottomSheet = false,
                        todoTasks = it.todoTasks + task
                    )
                }
            },
            onError = { handleHomeError(it) },
            onStart = { startLoading() },
            onFinally = { endLoading() }
        )
    }

    override fun onViewAllClick(taskStatus: Task.Status) {
        sendEvent(HomeEvent.ViewAll(taskStatus))
    }

    override fun onEditTaskIconClick(task: Task) {
        updateState { it.copy(showEditTaskBottomSheet = true) }
    }

    override fun onMoveTaskClick(task: Task) {
        val updated = task.copy(
            status = when (task.status) {
                Task.Status.TODO -> Task.Status.IN_PROGRESS
                Task.Status.IN_PROGRESS -> Task.Status.DONE
                Task.Status.DONE -> Task.Status.TODO
            }
        )

        launchWithResult(
            action = { tasksServices.updateTask(updated) },
            onSuccess = {
                updateState {
                    it.copy(
                        showTaskDetailsBottomSheet = false,
                        inProgressTasks =
                            if (task.status == Task.Status.IN_PROGRESS) it.inProgressTasks - task else it.inProgressTasks + task,
                        doneTasks = if (task.status == Task.Status.IN_PROGRESS) it.doneTasks + task else it.doneTasks,
                        todoTasks = if (task.status == Task.Status.IN_PROGRESS) it.todoTasks else it.todoTasks - task
                    )
                }
            },
            onError = { handleHomeError(it) },
            onStart = { startLoading() },
            onFinally = { endLoading() }
        )
    }

    override fun onSaveEditTaskClick(task: Task) {
        launchWithResult(
            action = { tasksServices.updateTask(task) },
            onSuccess = {
                updateState {
                    it.copy(
                        showEditTaskBottomSheet = false
                    )
                }
            },
            onError = { handleHomeError(it) },
            onStart = { startLoading() },
            onFinally = { endLoading() }
        )
    }

    override fun onPriorityClick(taskPriority: Task.Priority) {
        updateState { it.copy(selectedTask = it.selectedTask?.copy(priority = taskPriority)) }
    }

    override fun onTitleChange(newTitle: String) {
        updateState { it.copy(selectedTask = it.selectedTask?.copy(title = newTitle)) }
    }

    override fun onDescriptionChange(newDescription: String) {
        updateState { it.copy(selectedTask = it.selectedTask?.copy(description = newDescription)) }
    }

    override fun onDateChange(newDate: LocalDateTime) {
        updateState { it.copy(selectedTask = it.selectedTask?.copy(date = newDate)) }
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