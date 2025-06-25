package com.moscow.tudee.presentation.model


data class CategoryUi(
    val id: Long = 0L,
    val title: String = "",
    val isPredefined: Boolean = true,
    val numberOfTasksInCategory: Int = 0,
    val iconUrl: String = "",
    val countOfTasks: Int = 0
)