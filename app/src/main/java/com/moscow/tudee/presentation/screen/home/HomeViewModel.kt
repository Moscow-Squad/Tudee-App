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
        updateState { it.copy(showAddTaskBottomSheet = true) }
    }

    override fun onTaskClick(taskDetails: HomeState.TaskDetails) {
        updateState { it.copy(showTaskDetailsBottomSheet = true ) }

    }

    override fun addTask(taskDetails: HomeState.TaskDetails) {
        launchWithResult(
            action = {
                updateState { it.copy(todoTasks = it.todoTasks + taskDetails)}
                val taskDetails = Task(
                    title = taskDetails.title,
                    description = taskDetails.description,
                    priority = Task.Priority.valueOf(taskDetails.priorityName),
                    categoryId=0L,
                    status =
                        when(taskDetails.state){
                            HomeState.TaskState.DONE -> Task.Status.DONE
                            HomeState.TaskState.IN_PROGRESS -> Task.Status.IN_PROGRESS
                            HomeState.TaskState.TODO -> Task.Status.TODO
                        },
                    date = taskDetails.date

                )
                tasksServices.addTask(taskDetails)
            },
            onSuccess = { sendEvent(HomeEvent.OnDoneClick)},
            onError = { handleHomeError(it) },
            onStart = { toggleLoading() },
            onFinally = { toggleLoading() }
        )
    }

    override fun onViewAllClick(taskStatus: Task.Status) {
        sendEvent(HomeEvent.ViewAll(taskStatus))
    }

    override fun onEditTaskClick(taskDetails: HomeState.TaskDetails) {
        sendEvent(HomeEvent.ShowEditTaskBottomSheet)
    }

    override fun onMoveToDoneClick(taskDetails: HomeState.TaskDetails) {
        val updated = taskDetails.copy(state = HomeState.TaskState.DONE)
        updateState {
            it.copy(
                inProgressTasks = it.inProgressTasks - taskDetails,
                doneTasks = it.doneTasks + updated
            )
        }
        sendEvent(HomeEvent.OnDoneClick)
    }

    override fun onSaveTaskClick(taskDetails: HomeState.TaskDetails) {
        val updated = taskDetails.copy(state = HomeState.TaskState.DONE)
        updateState {
            it.copy(
                inProgressTasks = it.inProgressTasks - taskDetails,
                doneTasks = it.doneTasks + updated
            )
        }
        sendEvent(HomeEvent.OnDoneClick)
    }

    override fun onPriorityClick(taskPriority: Task.Priority) {
        updateState { it.copy(selectedTask = it.selectedTask?.copy(priorityName = taskPriority.toString())) }
    }

    override fun onTitleChange(newTitle: String) {
        updateState { it.copy(selectedTask = it.selectedTask?.copy(title = newTitle)) }
    }

    override fun onDescriptionChange(newDescription: String) {
        updateState { it.copy(selectedTask = it.selectedTask?.copy(description = newDescription)) }
    }

    override fun onDateChange(newDate: kotlinx.datetime.LocalDateTime) {
        updateState { it.copy(date = newDate.toString()) }
    }

    override fun onDismissEditBottomSheet() {
      updateState { it.copy(showEditTaskBottomSheet =false) }
    }

    override fun onDismissAddBottomSheet() {
        updateState { it.copy(showAddTaskBottomSheet=false) }
    }

    override fun onDismissDetailsBottomSheet() {
        updateState { it.copy(showTaskDetailsBottomSheet =false) }
    }

    private fun toggleLoading() {
        updateState { it.copy(isLoading = !it.isLoading) }
    }

    private fun handleHomeError(throwable: Throwable) {
        // TODO: Some error handling we can do later
    }

}