package com.moscow.tudee.presentation.category.tasks

import com.moscow.tudee.domain.entity.Task.Status
import com.moscow.tudee.presentation.category.CategoriesUiState
import kotlinx.datetime.LocalDate

data class TasksUiState(
    val categoryUi: CategoriesUiState.CategoryUi? = null,
    val todoList: List<TaskUi> = listOf(),
    val inProgressList: List<TaskUi> = listOf(),
    val doneList: List<TaskUi> = listOf(),
    val error: String? = null,
    val isLoading: Boolean = false
) {
    data class TaskUi(
        val icon: Int,
        val title: String,
        val description: String,
        val date: DateUi,
        val priority: String,
        val status: Status
    ) {
        data class DateUi(
            val date: LocalDate,
            val formattedDate: String
        )
    }
}