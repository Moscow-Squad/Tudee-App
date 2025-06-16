package com.moscow.tudee.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.moscow.tudee.domain.entity.Category


@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories_table")
    suspend fun getCategories(): List<Category>?

    @Insert
    suspend fun addCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Query("DELETE FROM categories_table WHERE id = :categoryId")
    suspend fun deleteCategory(categoryId: String)
}