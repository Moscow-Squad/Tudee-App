package com.moscow.tudee.domain.entity

import kotlin.uuid.ExperimentalUuidApi

data class Category @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Long,
    val title: String,
    val iconUri: String,
    val isPredefined: Boolean = false,
    val countOfTasks: Int = 0
)