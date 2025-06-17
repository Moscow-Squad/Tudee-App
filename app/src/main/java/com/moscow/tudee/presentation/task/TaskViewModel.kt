//package com.moscow.tudee.presentation.task
//
//
//import androidx.lifecycle.ViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//
//class TaskViewModel : ViewModel() {
//    private val _state = MutableStateFlow(TaskUiState(
//        tasks = listOf(
//            Task(1, "Organize Study Desk", "Review cell structure...", Priority.Medium, Status.ToDo),
//            Task(2, "Organize Study Desk", "Review cell structure...", Priority.Low, Status.ToDo),
//            Task(3, "Organize Study Desk", "Review cell structure...", Priority.High, Status.ToDo),
//            Task(4, "Organize Study Desk", "Review cell structure...", Priority.Medium, Status.InProgress)
//        )
//    ))
//    val state: StateFlow<TaskUiState> = _state
//
//    fun onTabSelected(index: Int) {
//        _state.value = _state.value.copy(selectedTab = index)
//    }
//}
