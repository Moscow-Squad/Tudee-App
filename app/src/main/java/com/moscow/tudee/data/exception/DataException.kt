package com.moscow.tudee.data.exception

sealed class DataException (message: String): Exception(message){
    class CategoryNotFound(categoryId: Long): DataException("Category id=$categoryId not found")
    class TaskNotFound(taskId: Long): DataException("Task id=$taskId not found")

}