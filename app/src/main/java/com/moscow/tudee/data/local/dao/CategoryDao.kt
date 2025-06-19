package com.moscow.tudee.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.moscow.tudee.data.local.entity.CategoryEntity


@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories_table")
    suspend fun getCategories(): List<CategoryEntity>

    @Insert
    suspend fun addCategory(category: CategoryEntity)

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Query("DELETE FROM categories_table WHERE id = :categoryId")
    suspend fun deleteCategory(categoryId: Long)

    @Query("UPDATE categories_table SET countOfTasks = countOfTasks + 1 WHERE id = :categoryId")
    suspend fun incrementTaskCount(categoryId: Long)

    @Query("UPDATE categories_table SET countOfTasks = countOfTasks - 1 WHERE id = :categoryId")
    suspend fun decrementTaskCount(categoryId: Long)
}