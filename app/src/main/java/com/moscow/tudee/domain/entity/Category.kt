package com.moscow.tudee.domain.entity


data class Category(
    val id: Long,
    val title: String,
    val iconUri: String,
    val isPredefined: Boolean = false,
    val countOfTasks: Int = 0
)