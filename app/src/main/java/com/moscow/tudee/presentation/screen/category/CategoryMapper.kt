package com.moscow.tudee.presentation.screen.category

import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task

//fun Task.toTaskUi() = CategoriesScreenState.TaskUi(
//    title = title,
//    description = description,
//    date = date.toString(),
//    priority = priority,
//    status = status,
//    category = category.toCategoryUi()
//)
//
//fun Category.toCategoryUi() = CategoriesScreenState.CategoryUi(
//    id = id,
//    title = title,
//    iconUrl = iconUri,
//    numberOfTasksInCategory = countOfTasks,
//    isPredefined = isPredefined
//)
//
//
//fun CategoriesScreenState.CategoryUi.toCategory() = Category(
//    id = id,
//    title = title,
//    iconUri = iconUrl,
//    countOfTasks = numberOfTasksInCategory,
//    isPredefined =isPredefined
//)
//
//fun getPriorityFromString(priority: String): Task.Priority {
//    return when (priority) {
//        "LOW" -> Task.Priority.LOW
//        "MEDIUM" -> Task.Priority.MEDIUM
//        "HIGH" -> Task.Priority.HIGH
//        else -> throw IllegalArgumentException("Invalid priority string: $priority")
//    }
//}
