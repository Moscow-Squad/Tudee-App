package com.moscow.tudee.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.moscow.tudee.data.local.entity.TaskEntity

@Dao
interface TaskDao {

    @Insert
    suspend fun addTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("DELETE FROM tasks_table WHERE id = :taskId")
    suspend fun deleteTask(taskId: Long)

    @Query("SELECT * FROM tasks_table")
    suspend fun getTasks(): List<TaskEntity>

    @Query("SELECT * FROM tasks_table WHERE SUBSTR(date, 1, 10) = :date")
    suspend fun getTasksByDate(date: String): List<TaskEntity>

    @Query("SELECT * FROM tasks_table WHERE id = :taskId")
    suspend fun getTaskById(taskId: Long): TaskEntity?

    @Query("UPDATE tasks_table SET status = :newStatus WHERE id = :taskId")
    suspend fun updateTaskStatus(taskId: Long, newStatus: String)

    @Query("SELECT * FROM tasks_table WHERE categoryId = :categoryId")
    suspend fun getTasksByCategory(categoryId: Long): List<TaskEntity>

    @Query("SELECT * FROM tasks_table WHERE status = :status")
    suspend fun getTasksByStatus(status: String): List<TaskEntity>

    @Query(
        """
      SELECT * 
      FROM tasks_table 
      WHERE SUBSTR(date, 1, 10) = :date 
        AND status = :status
    """
    )
    suspend fun getTasksByDateAndStatus(date: String, status: String): List<TaskEntity>

    @Query("""
    SELECT * 
      FROM tasks_table 
     WHERE SUBSTR(date, 1, 10) = :date 
       AND categoryId = :categoryId
  """)
    suspend fun getTasksByDateAndCategory(date: String, categoryId: Long): List<TaskEntity>
}