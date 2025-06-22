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
            total == 0 ->  SliderState.NOTHING_ON_YOUR_LIST
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
                getCategoryAndSaveTasks(response)
            },
            onError = { handleHomeError(it) },
            onStart = { startLoading() },
            onFinally = { endLoading() }
        )
        launchWithResult(
            action = { tasksServices.getTasksByStatus(Task.Status.IN_PROGRESS) },
            onSuccess = { response ->
                Log.e("BLA", response.toString())
                getCategoryAndSaveTasks(response)
            },
            onError = { handleHomeError(it) },
            onStart = { startLoading() },
            onFinally = { endLoading() }
        )
        launchWithResult(
            action = { tasksServices.getTasksByStatus(Task.Status.DONE) },
            onSuccess = { response ->
                Log.e("BLA", response.toString())
                getCategoryAndSaveTasks(response)
            },
            onError = { handleHomeError(it) },
            onStart = { startLoading() },
            onFinally = { endLoading() }
        )
    }

    private fun getCategoryAndSaveTasks(tasks: List<Task>) {
        tasks.forEach { task ->
            launchWithResult(
                action = { categoryServices.getCategoryById(task.category.id) },
                onSuccess = { response ->
                    saveCategory(task, response)
                },
                onError = { handleHomeError(it) },
                onStart = { startLoading() },
                onFinally = { endLoading() }
            )
        }
    }

    private fun saveCategory(
        task: Task,
        response: Category
    ) {
        updateState { state ->
            val newTask = HomeState.HomeTask(
                id = task.id,
                title = task.title,
                description = task.description,
                priority = task.priority,
                status = task.status,
                date = task.date,
                category = response
            )

            val newTodoTasks = if (task.status == Task.Status.TODO) state.todoTasks + newTask else state.todoTasks
            val newInProgressTasks = if (task.status == Task.Status.IN_PROGRESS) state.inProgressTasks + newTask else state.inProgressTasks
            val newDoneTasks = if (task.status == Task.Status.DONE) state.doneTasks + newTask else state.doneTasks

            val newSliderState = calculateSliderState(
                newTodoTasks.size,
                newInProgressTasks.size,
                newDoneTasks.size
            )

            state.copy(
                todoTasks = newTodoTasks,
                inProgressTasks = newInProgressTasks,
                doneTasks = newDoneTasks,
                update = newSliderState
            )
        }
    }


    override fun onFloatingActionButtonClick() {
        updateState {
            it.copy(
                selectedTask = HomeState.HomeTask(
                    id = null,
                    title = "",
                    description = "",
                    priority = Task.Priority.LOW,
                    status = Task.Status.TODO,
                    category = null,
                    date = java.time.LocalDateTime.now().toKotlinLocalDateTime()
                )
            )
        }
        updateState { it.copy(showAddTaskBottomSheet = true) }
    }

    override fun onTaskClick(task: HomeState.HomeTask) {
        updateState { it.copy(showTaskDetailsBottomSheet = true) }
        updateState { it.copy(selectedTask = task) }
    }

    override fun onAddTask(task: HomeState.HomeTask) {
        launchWithResult(
            action = {
                tasksServices.addTask(
                    task.toTask()
                )
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

    override fun onEditTaskIconClick(task: HomeState.HomeTask) {
        updateState { it.copy(selectedTask = task) }
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
            action = { tasksServices.updateTask(
                updated.toTask()
            ) },
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

    override fun onSaveEditTaskClick(task: HomeState.HomeTask) {
        launchWithResult(
            action = { tasksServices.updateTask(
                task.toTask()
            ) },
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