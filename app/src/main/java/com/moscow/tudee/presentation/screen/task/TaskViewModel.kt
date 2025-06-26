package com.moscow.tudee.presentation.screen.task

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.TasksServices
import com.moscow.tudee.presentation.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class TaskViewModel(
    private val taskService: TasksServices
) : BaseViewModel<TaskUiState, Nothing>(TaskUiState()), TaskScreenInteractionListener {

    init {
        loadTasks()
        selectStatus(Task.Status.TODO)
    }

    override fun showDatePicker() {
        updateState { it.copy(isDatePickerVisible = true) }
    }

    override fun dismissDatePicker() {
        updateState { it.copy(isDatePickerVisible = false) }
    }

    override fun showSnackBar(snackBarUi: TaskUiState.SnackBarUi) {
        viewModelScope.launch {
            updateState { it.copy(snackBarUi = snackBarUi) }
            delay(3000)
            hideSnackBar()
        }
    }

    override fun hideSnackBar() {
        updateState { it.copy(snackBarUi = null) }
    }

    override fun selectDate(date: LocalDate) {
        viewModelScope.launch {
            val tasksForDate = taskService.getTasksByDate(date)
            val selectedDate = LocalDateTime(date, LocalTime(0, 0, 0))
            val newMonthDays = generateMonthDays(date.year, date.monthNumber)


            updateState {
                it.copy(
                    selectedDate = selectedDate,
                    allTasksForSelectedDate = tasksForDate,
                    tasksForSelectedState = filterTasksByStatus(tasksForDate, it.selectedStatus),
                    currentMonth = date.month,
                    currentYear = date.year,
                    monthDays = newMonthDays
                )
            }
        }
    }

    override fun selectStatus(status: Task.Status) {
        updateState { currentState ->
            val filtered = filterTasksByStatus(currentState.allTasksForSelectedDate, status)
            currentState.copy(
                selectedStatus = status,
                tasksForSelectedState = filtered
            )
        }
    }

    override fun deleteTask() {
        launchWithResult(
            action = { uiState.value.selectedTaskToDelete?.id?.let { taskService.deleteTask(it) } },
            onSuccess = {
                updateState {
                    val updatedAllTasks =
                        it.allTasksForSelectedDate.filterNot { t -> t.id == uiState.value.selectedTaskToDelete?.id }
                    val updatedFiltered =
                        updatedAllTasks.filter { t -> t.status == it.selectedStatus }
                    it.copy(
                        allTasksForSelectedDate = updatedAllTasks,
                        tasksForSelectedState = updatedFiltered,
                        selectedTaskToDelete = null
                    )
                }
                val snackBarUi = TaskUiState.SnackBarUi(
                    type = TaskUiState.SnackBarUi.SnackBarType.SUCCESS,
                    messageId = R.string.deleted_task_successfully,
                    iconId = R.drawable.ic_checkmark_badge
                )
                showSnackBar(snackBarUi)
            },
            onError = {
                Log.e("TaskViewModel", "Error in deleting task: ${it.message}")
                updateState { it.copy(selectedTaskToDelete = null) }
                val snackBarUi = TaskUiState.SnackBarUi(
                    type = TaskUiState.SnackBarUi.SnackBarType.ERROR,
                    messageId = R.string.some_error_happened,
                    iconId = R.drawable.ic_information_diamond
                )
                showSnackBar(snackBarUi)
            }
        )
    }

    override fun previousMonth() {
        val current = uiState.value
        val newDate =
            LocalDate(
                current.currentYear,
                current.currentMonth,
                current.selectedDate.dayOfMonth
            ).minus(DatePeriod(months = 1))
        updateMonth(newDate)
    }

    override fun nextMonth() {
        val current = uiState.value
        val newDate =
            LocalDate(
                current.currentYear,
                current.currentMonth,
                current.selectedDate.dayOfMonth
            ).plus(DatePeriod(months = 1))
        updateMonth(newDate)
    }

    override fun updateMonthFromPicker(epochMillis: Long?) {
        dismissDatePicker()
        epochMillis?.let {
            val instant = Instant.fromEpochMilliseconds(it)
            val date = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
            updateMonth(date)
        }
    }

    override fun showDeleteTaskBottomSheet(task: Task) {
        updateState { it.copy(selectedTaskToDelete = task) }
    }

    override fun hideDeleteTaskBottomSheet() {
        updateState { it.copy(selectedTaskToDelete = null) }
    }

    fun loadTasks() {
        val today: LocalDateTime = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())

        updateState {
            it.copy(
                selectedDate = today,
                monthDays = generateMonthDays(today.year, today.month.value),
                currentMonth = today.month,
                currentYear = today.year
            )
        }
        selectDate(date = uiState.value.selectedDate.date)
    }

    private fun updateMonth(date: LocalDate) {
        val newMonthDays = generateMonthDays(date.year, date.monthNumber)
        updateState {
            it.copy(
                currentMonth = date.month,
                currentYear = date.year,
                monthDays = newMonthDays,
                selectedDate = LocalDateTime(date, LocalTime(0, 0, 0))
            )
        }
        selectDate(uiState.value.selectedDate.date)
    }

    private fun generateMonthDays(year: Int, month: Int): List<LocalDate> {
        val firstDay = LocalDate(year, month, 1)
        val daysInMonth = firstDay.month.length(isLeapYear(year))
        return List(daysInMonth) { index ->
            firstDay.plus(DatePeriod(days = index))
        }
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

    private fun filterTasksByStatus(tasks: List<Task>, status: Task.Status): List<Task> {
        return tasks.filter { it.status == status }
    }

}