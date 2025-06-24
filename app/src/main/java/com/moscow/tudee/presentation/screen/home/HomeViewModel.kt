package com.moscow.tudee.presentation.screen.home

import android.util.Log
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.CategoryServices
import com.moscow.tudee.domain.service.TasksServices
import com.moscow.tudee.presentation.BaseViewModel
import com.moscow.tudee.presentation.mapper.toTask
import com.moscow.tudee.presentation.mapper.toTaskUi
import com.moscow.tudee.presentation.model.CategoryUi
import com.moscow.tudee.presentation.model.TaskUi
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
        launchWithResult(
            action    = { tasksServices.getTasksByDate(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) },
            onSuccess = ::onSuccessLoadingTasks ,
            onError   = { handleHomeError(it) },
            onStart   = { startLoading() },
            onFinally = { endLoading() }
        )
    }

    private fun onSuccessLoadingTasks(tasks:List<Task>){
        val groupedTasks = tasks.groupBy{ it.status }
        updateState {
            it.copy(

                todoTasks = groupedTasks[Status.TODO].orEmpty() ,
                doneTasks = groupedTasks[Status.DONE].orEmpty(),
                inProgressTasks = groupedTasks[Status.IN_PROGRESS].orEmpty(),
                sliderState = updateSliderState(groupedTasks)

            )
        }
    }

    private fun updateSliderState(groupedTasks: Map<Status, List<Task>>): HomeState.SliderState {
        val todoTasks = groupedTasks[Status.TODO]?.size ?: 0
        val inProgressTasks = groupedTasks[Status.IN_PROGRESS]?.size ?: 0
        val doneTasks = groupedTasks[Status.DONE]?.size ?: 0
        val totalTasks = todoTasks + inProgressTasks + doneTasks
        return when {
            totalTasks == 0 -> HomeState.SliderState.NOTHING_ON_YOUR_LIST
            doneTasks == totalTasks -> HomeState.SliderState.TADAA
            todoTasks > 0 && doneTasks == 0 && inProgressTasks == 0 -> HomeState.SliderState.ZERO_PROGRESS
            todoTasks > 0 && doneTasks > 0 -> HomeState.SliderState.STAY_WORKING
            else -> HomeState.SliderState.STAY_WORKING
        }

    }
    override fun onFloatingActionButtonClick() {
        updateState {
            it.copy(
                addedTask = TaskUi(
                    id = null,
                    title = "",
                    description = "",
                    priority = Task.Priority.LOW,
                    status = Task.Status.TODO,
                    category = CategoryUi(
                        id = 1,
                        title = "ggfgf",
                        isPredefined = false,
                        numberOfTasksInCategory = 5,
                        iconUrl = "",
                        countOfTasks = 10
                    ),
                    date = java.time.LocalDateTime.now().toKotlinLocalDateTime()
                ),
                showAddTaskBottomSheet = true
            )
        }
    }

    override fun onTaskClick(task: TaskUi) {
        updateState { it.copy(showTaskDetailsBottomSheet = true) }
        updateState { it.copy(addedTask = task) }
    }

    override fun onAddTask(task: TaskUi) {
        if (task.title.isBlank() || task.description.isBlank()) {
            return
        }

        launchWithResult(
            action = { tasksServices.addTask(task.toTask()) },
            onSuccess = { addedTask ->
                updateState { state ->
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

    override fun onEditTaskIconClick(task: TaskUi) {
        updateState { it.copy(addedTask = task) }
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

    override fun onUpdateStatusClick(task: TaskUi) {

        val nextStatus = getNextStatus(task.status)

        launchWithResult(
            action = {
                task.id?.let { tasksServices.changeTaskStatus(it, nextStatus.name) }
            },
            onSuccess = {
                updateState {
                    it.copy(
                        showTaskDetailsBottomSheet = false,
                        inProgressTasks = it.inProgressTasks.filter { it.id != task.id },
                        todoTasks = it.todoTasks.filter { it.id != task.id },
                        inProgressTasksCount = if (task.status == Task.Status.IN_PROGRESS)
                            it.inProgressTasksCount - 1 else it.inProgressTasksCount,
                        todoTasksCount = if (task.status == Task.Status.TODO)
                            it.todoTasksCount - 1 else it.todoTasksCount,
                        doneTasksCount = if (task.status == Task.Status.DONE)
                            it.doneTasksCount - 1 else it.doneTasksCount
                    )
                }
                updateTaskStatus(task, nextStatus)
            },
            onError = { handleHomeError(it) },
            onStart = { startLoading() },
            onFinally = { endLoading() }
        )
    }

    private fun updateTaskStatus(task: TaskUi, nextStatus: Task.Status) {
        when (nextStatus) {
            Task.Status.IN_PROGRESS -> {
                updateState {
                    it.copy(
                        inProgressTasks = it.inProgressTasks + task.copy(status = nextStatus),
                        inProgressTasksCount = it.inProgressTasksCount + 1
                    )
                }
            }

            Task.Status.DONE -> {
                updateState {
                    it.copy(
                        doneTasks = it.doneTasks + task.copy(status = nextStatus),
                        doneTasksCount = it.doneTasksCount + 1
                    )
                }
            }

            else -> {}
        }
    }

    private fun getNextStatus(currentStatus: Task.Status): Task.Status {
        return when (currentStatus) {
            Task.Status.TODO -> Task.Status.IN_PROGRESS
            Task.Status.IN_PROGRESS -> Task.Status.DONE
            Task.Status.DONE -> Task.Status.DONE
        }
    }

    override fun onSaveEditTaskClick(task: TaskUi) {
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

    override fun onCategoryClick(category: CategoryUi) {
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