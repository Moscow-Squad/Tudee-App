package com.moscow.tudee.domain.exception

sealed class AppException: Throwable(){
    data class DatabaseException(override val message: String) : AppException()
    data class TaskNotFoundException(override val message: String) : AppException()
    data class CouldNotAddTaskException(override val message: String) : AppException()
    data class CategoryNotFoundException(override val message: String) : AppException()
    data class CouldNotAddCategoryException(override val message: String) : AppException()
    data class OnboardingStateException(override val message: String) : AppException()
    data class UnknownException(override val message: String) : AppException()
}