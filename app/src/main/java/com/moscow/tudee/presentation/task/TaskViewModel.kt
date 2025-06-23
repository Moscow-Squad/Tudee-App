package com.moscow.tudee.presentation.task

import androidx.lifecycle.viewModelScope
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.TasksServices
import com.moscow.tudee.presentation.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

open class TaskViewModel(
    private val taskService: TasksServices
) : BaseViewModel<TaskUiState, TaskUiEvent>(TaskUiState()),
    TaskScreenInteractionListener
{

    private val today: LocalDate =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    init {
        updateMonth(today)
    }

    override fun selectDate(date: LocalDate) {
        viewModelScope.launch {
            val tasksForDate = taskService.getTasksByDate(date)
            updateState  {
                it.copy(
                    selectedDate = date,
                    allTasksForSelectedDate = tasksForDate,
                    tasksForSelectedState = filterTasksByStatus(tasksForDate, it.selectedStatus)
                )
            }
        }
    }

    override fun selectStatus(status: Task.Status) {
        updateState {currentState ->
            val filtered = filterTasksByStatus(currentState.allTasksForSelectedDate, status)
            currentState.copy(
                selectedStatus = status,
                tasksForSelectedState = filtered
            )
        }
    }
    override fun deleteTask(task: Task) {
        launchWithResult(
            action = { task.id?.let { taskService.deleteTask(it) } },
            onSuccess = {
                updateState {
                    val updatedAllTasks =
                        it.allTasksForSelectedDate.filterNot { t -> t.id == task.id }
                    val updatedFiltered =
                        updatedAllTasks.filter { t -> t.status == it.selectedStatus }
                    it.copy(
                        allTasksForSelectedDate = updatedAllTasks,
                        tasksForSelectedState = updatedFiltered,
                    )
                }
                sendEvent(TaskUiEvent.ShowDeleteResult(true))
            },
            onError = {
                sendEvent(TaskUiEvent.ShowDeleteResult(false))
            }
        )
    }



    override fun previousMonth() {
        val current = uiState.value
        val newDate =
            LocalDate(current.currentYear, current.currentMonth, 1).minus(DatePeriod(months = 1))
        updateMonth(newDate)
    }

    override fun nextMonth() {
        val current = uiState.value
        val newDate =
            LocalDate(current.currentYear, current.currentMonth, 1).plus(DatePeriod(months = 1))
        updateMonth(newDate)
    }

    override fun updateMonthFromPicker(epochMillis: Long?) {
        sendEvent(TaskUiEvent.DismissDatePicker)
        epochMillis?.let {
            val instant = Instant.fromEpochMilliseconds(it)
            val date = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
            updateMonth(date)
        }
    }


    private fun updateMonth(date: LocalDate) {
        val newMonthDays = generateMonthDays(date.year, date.monthNumber)

        updateState {
            val newSelectedDate = if (it.selectedDate.year == date.year && it.selectedDate.month == date.month) {
                it.selectedDate
            } else {
                date
            }
            it.copy(
                currentMonth = date.month,
                currentYear = date.year,
                monthDays = newMonthDays,
                selectedDate = newSelectedDate
            )
        }

        selectDate(uiState.value.selectedDate)
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
