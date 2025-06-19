package com.moscow.tudee.presentation.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.TasksServices
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.LocalDate

open class TaskViewModel(
    private val taskService: TasksServices
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    open val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    private val today: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    init {
        selectDate(today)
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

    private fun filterTasksByStatus(tasks: List<Task>, status: Task.Status): List<Task> {
        return tasks.filter { it.status == status }
    }
}

