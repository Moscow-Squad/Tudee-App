package com.moscow.tudee.presentation.screen.home
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.entity.Task.Status
import com.moscow.tudee.domain.service.CategoryServices
import com.moscow.tudee.domain.service.TasksServices
import com.moscow.tudee.presentation.base.BaseViewModel
import com.moscow.tudee.presentation.mapper.toCategoryUi
import com.moscow.tudee.presentation.mapper.toTask
import com.moscow.tudee.presentation.mapper.toTaskUi
import com.moscow.tudee.presentation.model.CategoryUi
import com.moscow.tudee.presentation.model.TaskUi
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

class HomeViewModel(
    private val tasksServices: TasksServices,
    private val categoryServices: CategoryServices
) : BaseViewModel<HomeState, HomeEvent>(HomeState()), HomeInteractionListener {

    init {
        getTodayDate()
        loadTasks()
    }


    private fun loadTasks() {
        launchWithResult(
            action    = { tasksServices.getTasksByDate(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date).map { it.toTaskUi() } },
            onSuccess = ::onSuccessLoadingTasks ,
            onError   = { handleHomeError(it) },
            onStart   = { startLoading() },
            onFinally = { endLoading() }
        )
    }

    private fun getTodayDate(){
        updateState { it.copy(formattedDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toJavaLocalDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy")).toString()) }
    }
    private fun onSuccessLoadingTasks(tasks:List<TaskUi>){
        val groupedTasks = tasks.groupBy{ it.status }

        updateState {
            it.copy(
                todoTasks = groupedTasks[Status.TODO].orEmpty() ,
                doneTasks = groupedTasks[Status.DONE].orEmpty(),
                inProgressTasks = groupedTasks[Status.IN_PROGRESS].orEmpty(),
                sliderState = updateSliderState( uiState.value.doneTasks.size ,uiState.value.todoTasks.size ,uiState.value.inProgressTasks.size)

            )
        }
    }

    private fun updateSliderState(doneTasks:Int, todoTasks:Int,inProgressTasks:Int): HomeState.SliderState {
        val totalTasks = todoTasks + inProgressTasks + doneTasks
        return when {
            totalTasks == 0 -> HomeState.SliderState.NOTHING_ON_YOUR_LIST
            doneTasks == totalTasks -> HomeState.SliderState.TADAA
            todoTasks > 0 && doneTasks == 0 && inProgressTasks == 0 -> HomeState.SliderState.ZERO_PROGRESS
            todoTasks > 0 && doneTasks in 1 until totalTasks -> HomeState.SliderState.STAY_WORKING
            else -> HomeState.SliderState.STAY_WORKING
        }

    }
    private fun getCategories() {
        if(uiState.value.categories.isEmpty()) {
            launchWithResult(
                action = { categoryServices.getCategories() },
                onSuccess = { response ->
                    updateState { it.copy(categories = response.map { it.toCategoryUi() }) }
                },
                onError = { handleHomeError(it) },
                onStart = { startLoading() },
                onFinally = { endLoading() }
            )
        }
    }

    override fun onFloatingActionButtonClick() {
        getCategories()
        updateState {
            it.copy(
                addedTask = TaskUi(
                    id = null,
                    title = "",
                    description = "",
                    priority = Task.Priority.LOW,
                    status = Status.TODO,
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
                        showSnackbar = true,
                        snackbarMessage = "Task added successfully!"
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
        getCategories()
        updateState { it.copy(addedTask = task) }
        updateState { it.copy(showEditTaskBottomSheet = true) }
    }

    override fun onUpdateStatusClick(task: TaskUi) {

        val nextStatus = getNextStatus(task.status)

        launchWithResult(
            action = {
                task.id?.let { tasksServices.changeTaskStatus(it, nextStatus) }
            },
            onSuccess = {
                updateState {
                    it.copy(
                        showTaskDetailsBottomSheet = false,
                        inProgressTasks = it.inProgressTasks.filter { it.id != task.id },
                        todoTasks = it.todoTasks.filter { it.id != task.id },
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
            Status.IN_PROGRESS -> {
                updateState {
                    it.copy(
                        inProgressTasks = it.inProgressTasks + task.copy(status = nextStatus),

                        )
                }
            }

            Status.DONE -> {
                updateState {
                    it.copy(
                        doneTasks = it.doneTasks + task.copy(status = nextStatus),

                        )
                }
            }

            else -> {}
        }
    }

    private fun getNextStatus(currentStatus: Task.Status): Task.Status {
        return when (currentStatus) {
            Status.TODO -> Status.IN_PROGRESS
            Status.IN_PROGRESS ->Status.DONE
            Status.DONE -> Status.DONE
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
                        showSnackbar = true,
                        snackbarMessage = "Task updated successfully!"
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

    override fun onShowSnackbar(message: String) {
        updateState {
            it.copy(
                showSnackbar = true,
                snackbarMessage = message
            )
        }
    }

    override fun onSnackbarDismissed() {
        updateState {
            it.copy(
                showSnackbar = false,
                snackbarMessage = ""
            )
        }
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