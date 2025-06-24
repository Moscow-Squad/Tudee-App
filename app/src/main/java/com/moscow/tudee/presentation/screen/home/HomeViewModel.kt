package com.moscow.tudee.presentation.screen.home

import android.util.Log
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.entity.Task.Status
import com.moscow.tudee.domain.service.CategoryServices
import com.moscow.tudee.domain.service.TasksServices
import com.moscow.tudee.presentation.BaseViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeViewModel(
    private val tasksServices: TasksServices,
    private val categoryServices: CategoryServices
) : BaseViewModel<HomeState, HomeEvent>(HomeState()), HomeInteractionListener {

    init {
        loadTasks()
    }



    private fun loadTasks() {
        //TODO: get tasks from database

        Log.d("TAG", "loadTasks:${Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date} ")
        launchWithResult(
            action    = { tasksServices.getTasksByDate(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) },
            onSuccess = ::onSuccessLoadingTasks ,
            onError   = { handleHomeError(it) },
            onStart   = { startLoading() },
            onFinally = { endLoading() }
        )
    }

    private fun onSuccessLoadingTasks(tasks:List<Task>){

        Log.d("TAG", "updateTasksListState: $tasks")
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
        //TODO : show add task bottom sheet
    }

    override fun onTaskClick(task: Task) {
        updateState { it.copy(showTaskDetailsBottomSheet = true) }
        updateState { it.copy(selectedTask = task) }
    }

    override fun onAddTask(task: Task) {
        //TODO: add new task
    }

    override fun onViewAllClick(taskStatus: Task.Status) {
        sendEvent(HomeEvent.ViewAll(taskStatus))
    }

    override fun onEditTaskIconClick(task: Task) {
        updateState { it.copy(selectedTask = task) }
        updateState { it.copy(showEditTaskBottomSheet = true) }
    }


    override fun onMoveTaskClick(task: Task) {
        // TODO : move task from current state to a different state
    }

    override fun onSaveEditTaskClick(task: Task) {
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
        Log.d("TAG", "handleHomeError: ${throwable.message}")
        // TODO: Some error handling we can do later
    }

}