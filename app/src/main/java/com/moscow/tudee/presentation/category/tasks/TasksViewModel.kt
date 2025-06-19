package com.moscow.tudee.presentation.category.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.CategoryServices
import com.moscow.tudee.domain.service.TasksServices
import com.moscow.tudee.presentation.category.CategoriesUiState
import com.moscow.tudee.presentation.category.toCategory
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed class TasksEvents {
    data class NavigateToTasks(val categoryId: Long) : TasksEvents()
    data object NavigateToCategories : TasksEvents()
}

class TasksViewModel(
    categoryId: Long,
    private val tasksServices: TasksServices,
    private val categoryServices: CategoryServices
) : ViewModel(), TasksInteractionListener {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<TasksEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            runCatching {
                _uiState.update {
                    it.copy(isLoading = true)
                }
                tasksServices.getTasksByCategory(categoryId)
            }.onSuccess {
                _uiState.update {
                    it.copy(
                        todoList = it.todoList.filter { it.status == Task.Status.TODO },
                        inProgressList = it.inProgressList.filter { it.status == Task.Status.IN_PROGRESS },
                        doneList = it.doneList.filter { it.status == Task.Status.DONE },
                        isLoading = false
                    )
                }
            }.onFailure {
                _uiState.update {
                    it.copy(
                        error = it.error,
                        isLoading = false
                    )
                }
            }
        }
    }

    override fun onEditCategory(newCategory: CategoriesUiState.CategoryUi) {
        viewModelScope.launch {
            runCatching {
                _uiState.update {
                    it.copy(isLoading = true)
                }
                categoryServices.updateCategory(newCategory.toCategory())
            }
                .onSuccess {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _uiEvent.send(TasksEvents.NavigateToTasks(newCategory.id))
                }.onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = throwable.message
                        )
                    }
                }
        }
    }

    override fun onDeleteCategory(category: CategoriesUiState.CategoryUi) {
        viewModelScope.launch {
            runCatching {
                _uiState.update {
                    it.copy(isLoading = true)
                }
                categoryServices.deleteCategory(category.id)
            }
                .onSuccess {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _uiEvent.send(TasksEvents.NavigateToCategories)
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = throwable.message
                        )
                    }
                }
        }
    }
}
