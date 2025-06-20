package com.moscow.tudee.presentation.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.TasksServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.*

open class TaskViewModel(
    private val taskService: TasksServices
) : ViewModel(){

    private val today: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    private val _uiState = MutableStateFlow(TaskUiState())
    open val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    private val _showDatePicker = MutableStateFlow(false)
    val showDatePicker: StateFlow<Boolean> = _showDatePicker.asStateFlow()

    fun showDatePicker() {
        _showDatePicker.value = true
    }

    fun dismissDatePicker() {
        _showDatePicker.value = false
    }


    init {
        updateMonth(today)
    }

    fun selectDate(date: LocalDate) {
        viewModelScope.launch {
            val tasksForDate = taskService.getTasksByDate(date)
            _uiState.update {
                it.copy(
                    selectedDate = date,
                    allTasksForSelectedDate = tasksForDate,
                    tasksForSelectedState = filterTasksByStatus(tasksForDate, it.selectedStatus)
                )
            }
        }
    }

    fun selectStatus(status: Task.Status) {
        val currentState = _uiState.value
        val filtered = filterTasksByStatus(currentState.allTasksForSelectedDate, status)

        _uiState.update {
            it.copy(
                selectedStatus = status,
                tasksForSelectedState = filtered
            )
        }
    }
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            task.id?.let { taskService.deleteTask(it) }
        }
    }

    fun previousMonth() {
        val current = _uiState.value
        val newDate = LocalDate(current.currentYear, current.currentMonth, 1).minus(DatePeriod(months = 1))
        updateMonth(newDate)
    }

    fun nextMonth() {
        val current = _uiState.value
        val newDate = LocalDate(current.currentYear, current.currentMonth, 1).plus(DatePeriod(months = 1))
        updateMonth(newDate)
    }

    fun updateMonthFromPicker(epochMillis: Long?) {
        dismissDatePicker()
        epochMillis?.let {
            val instant = Instant.fromEpochMilliseconds(it)
            val date = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
            updateMonth(date)
        }
    }

    private fun updateMonth(date: LocalDate) {
        _uiState.update {
            it.copy(
                currentMonth = date.month,
                currentYear = date.year,
                monthDays = generateMonthDays(date.year, date.monthNumber),
                selectedDate = date
            )
        }
        selectDate(date)
    }

    private fun generateMonthDays(year: Int, month: Int): List<LocalDate> {
        val firstDay = LocalDate(year, month,1)
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
