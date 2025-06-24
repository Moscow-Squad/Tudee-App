package com.moscow.tudee.presentation.screen.home

import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.CategoryServices
import com.moscow.tudee.domain.service.TasksServices
import com.moscow.tudee.presentation.BaseViewModel
import com.moscow.tudee.presentation.mapper.toTask
import com.moscow.tudee.presentation.mapper.toTaskUi
import com.moscow.tudee.presentation.model.TaskUi
import kotlinx.datetime.LocalDateTime

class HomeViewModel(
    private val tasksServices: TasksServices,
    private val categoryServices: CategoryServices
) : BaseViewModel<HomeState, HomeEvent>(HomeState()), HomeInteractionListener {

    init {
        loadTasks()
    }

    private fun loadTasks() {
        launchWithResult(
            action = { tasksServices.getTasks() },
            onSuccess = { tasks ->
                val todoList = tasks.map { it.toTaskUi() }
                    .filter { it.status == Task.Status.TODO }
                val inProgressList = tasks.map { it.toTaskUi() }
                    .filter { it.status == Task.Status.IN_PROGRESS }
                val doneList =  tasks.map { it.toTaskUi() }
                    .filter { it.status == Task.Status.DONE }

                updateState {
                    it.copy(
                        todoTasks = todoList ,
                        inProgressTasks =inProgressList,
                        doneTasks =doneList,
                        todoTasksCount = todoList.size,
                        inProgressTasksCount = inProgressList.size,
                        doneTasksCount = doneList.size,
                    )
                }
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

    override fun onTaskClick(task: TaskUi) {
        updateState { it.copy(showTaskDetailsBottomSheet = true) }
        updateState { it.copy(selectedTask = task) }
    }

    override fun onAddTask(task: TaskUi) {
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

    override fun onEditTaskIconClick(task: TaskUi) {
        updateState { it.copy(addedTask = task) }
        updateState { it.copy(showEditTaskBottomSheet = true) }
    }



    override fun onUpdateStatusClick(task: TaskUi) {

        val nextStatus = getNextStatus(task.status)

        launchWithResult(
            action = {
                task.id?.let { tasksServices.changeTaskStatus(it,nextStatus.name) }
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
                updateTaskStatus(task,nextStatus)
            },
            onError = { handleHomeError(it) },
            onStart = { startLoading() },
            onFinally = { endLoading() }
        )
    }
    private fun updateTaskStatus(task: TaskUi,nextStatus:Task.Status){
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

            else -> {  }
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