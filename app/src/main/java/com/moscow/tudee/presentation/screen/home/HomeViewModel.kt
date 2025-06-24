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
        loadCategories()
        loadTasks()
    }

    private fun calculateSliderState(todo: Int, inProgress: Int, done: Int): SliderState {
        val total = todo + inProgress + done
        return when {
            total == 0 -> SliderState.NOTHING_ON_YOUR_LIST
            done == total -> SliderState.TADAA
            else -> SliderState.STAY_WORKING
        }
    }

    private fun loadCategories() {
        launchWithResult(
            action = { categoryServices.getCategories() },
            onSuccess = { response ->
                Log.e("BLA", response.toString())
                updateState { it.copy(categories = response) }
            },
            onError = { handleHomeError(it) },
            onStart = { startLoading() },
            onFinally = { endLoading() }
        )
    }

    private fun loadTasks() {
        launchWithResult(
            action = { tasksServices.getTasksByStatus(Task.Status.TODO) },
            onSuccess = { response ->
                Log.e("BLA", response.toString())
                updateState { it.copy(todoTasks = response.map { it.toTaskItem() }) }
            },
            onError = { handleHomeError(it) },
            onStart = { startLoading() },
            onFinally = { endLoading() }
        )
        launchWithResult(
            action = { tasksServices.getTasksByStatus(Task.Status.IN_PROGRESS) },
            onSuccess = { response ->
                Log.e("BLA", response.toString())
                updateState { it.copy(inProgressTasks = response.map { it.toTaskItem() }) }
            },
            onError = { handleHomeError(it) },
            onStart = { startLoading() },
            onFinally = { endLoading() }
        )
        launchWithResult(
            action = { tasksServices.getTasksByStatus(Task.Status.DONE) },
            onSuccess = { response ->
                Log.e("BLA", response.toString())
                updateState {
                    val doneTasks = response.map { it.toTaskItem() }
                    val newSliderState = calculateSliderState(
                        it.todoTasks.size,
                        it.inProgressTasks.size,
                        doneTasks.size
                    )
                    it.copy(
                        doneTasks = doneTasks,
                        update = newSliderState
                    )
                }
            },
            onError = { handleHomeError(it) },
            onStart = { startLoading() },
            onFinally = { endLoading() }
        )
    }

    override fun onFloatingActionButtonClick() {
        updateState {
            it.copy(
                addedTask = HomeState.HomeTask(
                    id = null,
                    title = "",
                    description = "",
                    priority = null,
                    status = Task.Status.TODO,
                    category = null,
                    date = java.time.LocalDateTime.now().toKotlinLocalDateTime()
                ),
                showAddTaskBottomSheet = true
            )
        }
    }

    override fun onTaskClick(task: HomeState.HomeTask) {
        updateState { it.copy(showTaskDetailsBottomSheet = true) }
        updateState { it.copy(selectedTask = task) }
    }

    override fun onAddTask(task: HomeState.HomeTask) {
        if (task.title.isBlank() || task.description.isBlank() || task.category == null) {
            Log.e("HomeViewModel", "Cannot add task: missing required fields")
            return
        }

        launchWithResult(
            action = { tasksServices.addTask(task.toTask()) },
            onSuccess = { addedTask ->
                updateState { state ->
                    Log.d("HomeViewModel", "addedTask type: ${addedTask::class.simpleName}")
                    Log.d("HomeViewModel", "addedTask value: $addedTask")
                    loadTasks()

                    state.copy(
                        showAddTaskBottomSheet = false,
                        addedTask = null,
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

    override fun onEditTaskIconClick(task: HomeState.HomeTask) {
        updateState { it.copy(addedTask = task) }
        getCategory(task)
        updateState { it.copy(showEditTaskBottomSheet = true) }
    }

    private fun getCategory(task: HomeState.HomeTask) {
        launchWithResult(
            action = { categoryServices.getCategoryById(task.category!!.id!!) },
            onSuccess = { response ->
                updateState { it.copy(selectedTask = task.copy(category = response)) }
            },
            onError = { handleHomeError(it) },
            onStart = { startLoading() },
            onFinally = { endLoading() }
        )
    }

    override fun onMoveTaskClick(task: HomeState.HomeTask) {
        val updated = task.copy(
            status = when (task.status) {
                Task.Status.TODO -> Task.Status.IN_PROGRESS
                Task.Status.IN_PROGRESS -> Task.Status.DONE
                Task.Status.DONE -> Task.Status.TODO
            }
        )

        launchWithResult(
            action = { tasksServices.updateTask(updated.toTask()) },
            onSuccess = {
                updateState { state ->
                    val newTodoTasks = when (task.status) {
                        Task.Status.TODO -> state.todoTasks - task
                        else -> state.todoTasks
                    }

                    val newInProgressTasks = when (task.status) {
                        Task.Status.IN_PROGRESS -> state.inProgressTasks - task
                        else -> state.inProgressTasks
                    }

                    val newDoneTasks = when (task.status) {
                        Task.Status.DONE -> state.doneTasks - task
                        else -> state.doneTasks
                    }

                    val finalTodoTasks = when (updated.status) {
                        Task.Status.TODO -> newTodoTasks + updated
                        else -> newTodoTasks
                    }

                    val finalInProgressTasks = when (updated.status) {
                        Task.Status.IN_PROGRESS -> newInProgressTasks + updated
                        else -> newInProgressTasks
                    }

                    val finalDoneTasks = when (updated.status) {
                        Task.Status.DONE -> newDoneTasks + updated
                        else -> newDoneTasks
                    }

                    val newSliderState = calculateSliderState(
                        finalTodoTasks.size,
                        finalInProgressTasks.size,
                        finalDoneTasks.size
                    )

                    state.copy(
                        showTaskDetailsBottomSheet = false,
                        todoTasks = finalTodoTasks,
                        inProgressTasks = finalInProgressTasks,
                        doneTasks = finalDoneTasks,
                        update = newSliderState
                    )
                }
            },
            onError = { handleHomeError(it) },
            onStart = { startLoading() },
            onFinally = { endLoading() }
        )
    }

    override fun onSaveEditTaskClick(task: HomeState.HomeTask) {
        launchWithResult(
            action = { tasksServices.updateTask(task.toTask()) },
            onSuccess = {
                loadTasks()
                updateState {
                    it.copy(
                        showEditTaskBottomSheet = false,
                        selectedTask = it.addedTask,
                        addedTask = null,
                    )
                }
            },
            onError = { handleHomeError(it) },
            onStart = { startLoading() },
            onFinally = { endLoading() }
        )
    }

    override fun onPriorityClick(taskPriority: Task.Priority) {
        updateState { it.copy(addedTask = it.addedTask?.copy(priority = taskPriority)) }
    }

    override fun onTitleChange(newTitle: String) {
        updateState { it.copy(addedTask = it.addedTask?.copy(title = newTitle)) }
    }

    override fun onDescriptionChange(newDescription: String) {
        updateState { it.copy(addedTask = it.addedTask?.copy(description = newDescription)) }
    }

    override fun onDateChange(newDate: LocalDateTime) {
        updateState { it.copy(addedTask = it.addedTask?.copy(date = newDate)) }
    }

    override fun onCategoryClick(category: Category) {
        updateState { it.copy(addedTask = it.addedTask?.copy(category = category)) }
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