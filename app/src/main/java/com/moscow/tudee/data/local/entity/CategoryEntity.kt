package com.moscow.tudee.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories_table")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val iconUri: String? = null,
    val isPredefined: Boolean = false,
    val countOfTasks: Int = 0
)