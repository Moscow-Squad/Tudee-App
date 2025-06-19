package com.moscow.tudee.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moscow.tudee.domain.service.CategoryServices
import com.moscow.tudee.domain.service.TasksServices
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class CategoriesEvents {
    data object NavigateToCategories : CategoriesEvents()
}

class CategoryViewModel(
    private val categoryServices: CategoryServices,
    private val taskServices: TasksServices
) : ViewModel(), CategoriesInteractionListener {
    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<CategoriesEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            runCatching {
                categoryServices.getCategories().map { category ->
                    async {
                        val tasks = taskServices.getTasksByCategory(category.id!!)
                        category.toCategoryUi(tasks)
                    }
                }.awaitAll()
            }.onSuccess { response ->
                _uiState.update {
                    it.copy(
                        categoryList = response,
                        isLoading = false
                    )
                }
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        error = throwable.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    override fun onAddCategory(category: CategoriesUiState.CategoryUi) {
        viewModelScope.launch {
            runCatching {
                _uiState.update {
                    it.copy(isLoading = true)
                }
                categoryServices.addCategory(category.toCategory())
            }
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            categoryList = it.categoryList + category,
                            isLoading = false
                        )
                    }
                    _uiEvent.send(CategoriesEvents.NavigateToCategories)
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            error = throwable.message,
                            isLoading = false
                        )
                    }
                }
        }
    }
}