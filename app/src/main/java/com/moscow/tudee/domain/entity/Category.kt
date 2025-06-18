package com.moscow.tudee.domain.entity

import kotlin.uuid.ExperimentalUuidApi

data class Category @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Long? = null,
    val title: String,
    val iconUrl: String,
)