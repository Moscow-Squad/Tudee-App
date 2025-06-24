package com.moscow.tudee.domain.exception

sealed class Exceptions (message: String): Exception(message){
    class CategoryNotFound(categoryId: Long): Exceptions("Category id=$categoryId not found")
    class TaskNotFound(taskId: Long): Exceptions("Task id=$taskId not found")
    class InvalidStatus(status: String): Exceptions("Invalid status: $status")
    class InvalidPriority(priority: String): Exceptions("Invalid priority: $priority")
}