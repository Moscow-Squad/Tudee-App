package com.moscow.tudee.presentation.model

import androidx.annotation.StringRes

data class CategoryUi(
    val id: Long = 0L,
    val title: String = "",
    @StringRes val titleRes: Int? = null,
    val isPredefined: Boolean = false,
    val numberOfTasksInCategory: Int = 0,
    val iconUrl: String = "",
    val countOfTasks: Int = 0
)