package com.moscow.tudee.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.service.TasksServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val tasksServices: TasksServices
) : ViewModel() {

    private val _categoryState = MutableStateFlow(CategoryState())
    val categoryState = _categoryState.asStateFlow()

    private val _sheetState = MutableStateFlow(CategorySheetState())
    val sheetState = _sheetState.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _categoryState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val categories = tasksServices.getCategories()
                _categoryState.update {
                    it.copy(categories = categories, isLoading = false)
                }
            } catch (e: Exception) {
                _categoryState.update {
                    it.copy(isLoading = false, errorMessage = "Error: $e")
                }
            }
        }
    }

    fun onCategoryClick(category: Category) {
        _categoryState.update {
            it.copy(selectedCategory = category)
        }
    }

    fun showAddCategorySheet() {
        _categoryState.update { it.copy(showAddCategorySheet = true) }
        resetSheet()
    }

    fun hideAddCategorySheet() {
        _categoryState.update { it.copy(showAddCategorySheet = false) }
        resetSheet()
    }

    fun addCategory() {
        val sheetState = _sheetState.value
        viewModelScope.launch {
            _categoryState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val newCategory = Category(
                    title = sheetState.categoryTitle,
                    iconUrl = sheetState.selectedIcon
                )
                tasksServices.addCategory(newCategory)
                hideAddCategorySheet()
                loadCategories()
            } catch (e: Exception) {
                _categoryState.update {
                    it.copy(isLoading = false, errorMessage = "Error: $e")
                }
            }
        }
    }

    fun onCategoryTitleChange(title: String) {
        _sheetState.update { it.copy(categoryTitle = title) }
    }

    fun onIconSelect(icon: String) {
        _sheetState.update { it.copy(selectedIcon = icon) }
    }

    fun showEditCategorySheet(category: Category) {
        _categoryState.update {
            it.copy(showEditCategorySheet = true, selectedCategory = category)
        }
        _sheetState.update {
            CategorySheetState(
                categoryTitle = category.title,
                selectedIcon = category.iconUrl
            )
        }
    }

    fun hideEditCategorySheet() {
        _categoryState.update {
            it.copy(showEditCategorySheet = false, selectedCategory = null)
        }
        resetSheet()
    }

    fun editCategory() {
        val selectedCategory = _categoryState.value.selectedCategory ?: return
        val currentCategoryState = _sheetState.value
        viewModelScope.launch {
            _categoryState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val updatedCategory = selectedCategory.copy(
                    title = currentCategoryState.categoryTitle,
                    iconUrl = currentCategoryState.selectedIcon
                )
                tasksServices.updateCategory(updatedCategory)
                hideEditCategorySheet()
                loadCategories()
            } catch (e: Exception) {
                _categoryState.update {
                    it.copy(isLoading = false, errorMessage =  "Error: $e")
                }
            }
        }
    }

    fun showDeleteCategorySheet(category: Category) {
        _categoryState.update {
            it.copy(showDeleteCategorySheet = true, deletedCategoryId = category.id)
        }
    }

    fun hideDeleteCategorySheet() {
        _categoryState.update {
            it.copy(showDeleteCategorySheet = false, deletedCategoryId = null)
        }
    }

    fun deleteCategory() {
        val deletedCategoryId = _categoryState.value.deletedCategoryId ?: return
        viewModelScope.launch {
            _categoryState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                tasksServices.deleteCategory(deletedCategoryId)
                hideDeleteCategorySheet()
                loadCategories()
            } catch (e: Exception) {
                _categoryState.update {
                    it.copy(isLoading = false, errorMessage = "Error: $e")
                }
            }
        }
    }

    fun clearError() {
        _categoryState.update { it.copy(errorMessage = null) }
    }

    private fun resetSheet() {
        _sheetState.value = CategorySheetState()
    }
}
