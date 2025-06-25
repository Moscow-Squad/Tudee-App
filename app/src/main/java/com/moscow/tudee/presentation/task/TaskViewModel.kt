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

open class TaskViewModel(
    private val taskService: TasksServices
) : ViewModel(), TaskScreenInteractionListener {

    private val today: LocalDateTime =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    private val _uiState = MutableStateFlow(TaskUiState())
    open val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    private val _showDatePicker = MutableStateFlow(false)
    val showDatePicker: StateFlow<Boolean> = _showDatePicker.asStateFlow()

    override fun showDatePicker() {
        _showDatePicker.value = true
    }

    override fun dismissDatePicker() {
        _showDatePicker.value = false
    }


    init {
        _uiState.update {
            it.copy(
                selectedDate = today,
                monthDays = generateMonthDays(today.year, today.month.value),
                currentMonth = today.month,
                currentYear = today.year
            )
        }
    }


    override fun selectDate(date: LocalDate) {
        viewModelScope.launch {
            val tasksForDate = taskService.getTasksByDate(date)
            val selectedDate = LocalDateTime(date, LocalTime(0, 0, 0))
            _uiState.update {
                it.copy(
                    selectedDate = selectedDate,
                    allTasksForSelectedDate = tasksForDate,
                    tasksForSelectedState = filterTasksByStatus(tasksForDate, it.selectedStatus)
                )
            }
        }
    }

    override fun selectStatus(status: Task.Status) {
        val currentState = _uiState.value
        val filtered = filterTasksByStatus(currentState.allTasksForSelectedDate, status)

        _uiState.update {
            it.copy(
                selectedStatus = status,
                tasksForSelectedState = filtered
            )
        }
    }

    override fun deleteTask(task: Task) {
        viewModelScope.launch {
            runCatching {
                task.id?.let { taskService.deleteTask(it) }
            }.onSuccess {
                _uiState.update {
                    it.copy(showDeletedTaskNotification = true, isTaskDeleted = true)
                }
            }.onFailure {
                _uiState.update {
                    it.copy(showDeletedTaskNotification = true, isTaskDeleted = false)
                }
            }
        }
    }

    override fun previousMonth() {
        val current = _uiState.value
        val newDate =
            LocalDate(current.currentYear, current.currentMonth, 1).minus(DatePeriod(months = 1))
        updateMonth(newDate)
    }

    override fun nextMonth() {
        val current = _uiState.value
        val newDate =
            LocalDate(current.currentYear, current.currentMonth, 1).plus(DatePeriod(months = 1))
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


    private fun updateMonth(date: LocalDate) {
        val newMonthDays = generateMonthDays(date.year, date.monthNumber)


        _uiState.update {
            it.copy(
                currentMonth = date.month,
                currentYear = date.year,
                monthDays = newMonthDays,
                selectedDate = LocalDateTime(date, LocalTime(0, 0, 0))
            )
        }

        selectDate(_uiState.value.selectedDate.date)
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
