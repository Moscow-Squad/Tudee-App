package com.moscow.tudee.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories_table")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val iconUrl: String,
    val countOfTasks: Int = 0
)
