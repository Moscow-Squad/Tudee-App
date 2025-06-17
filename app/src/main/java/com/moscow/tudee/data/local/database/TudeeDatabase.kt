package com.moscow.tudee.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moscow.tudee.data.local.dao.CategoryDao
import com.moscow.tudee.data.local.dao.TaskDao
import com.moscow.tudee.data.local.entity.CategoryEntity
import com.moscow.tudee.data.local.entity.TaskEntity

@Database(entities = [TaskEntity::class, CategoryEntity::class], version = 1)
abstract class TudeeDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao
}