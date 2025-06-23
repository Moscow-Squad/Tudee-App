package com.moscow.tudee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.domain.service.TasksServices
import com.moscow.tudee.presentation.designSystem.theme.ThemeState
import com.moscow.tudee.presentation.navigation.TudeeGraph
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import com.moscow.tudee.presentation.navigation.TudeeGraph
import kotlinx.datetime.LocalDateTime
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val taskService = get<TasksServices>()
            val categoryService = get<com.moscow.tudee.domain.service.CategoryServices>()
            val isDark = isSystemInDarkTheme()
            val (isDarkThemeState, onThemeStateChanged) = remember { mutableStateOf(isDark) }
            val themeState by remember(isDarkThemeState) {
                derivedStateOf {
                    ThemeState(
                        isDark = isDarkThemeState,
                        onThemeChanged = onThemeStateChanged
                    )
                }
            }
            TudeeTheme(state = themeState) {
                TudeeGraph()
            }
//            LaunchedEffect(Unit) {
//                val category=categoryService.getCategoryById(1L)
//                taskService.addTask(
//                    com.moscow.tudee.domain.entity.Task(
//                        id = 12L,
//                        title = "gfgretr",
//                        description = "This is a test task",
//                        status = com.moscow.tudee.domain.entity.Task.Status.TODO,
//                        date = LocalDateTime(2024, 1, 1, 0, 0),
//                        category = category,
//                        priority = Task.Priority.LOW
//                    )
//                )
//                taskService.addTask(
//                    com.moscow.tudee.domain.entity.Task(
//                        id = 9L,
//                        title = "eeeeeeee",
//                        description = "This is a test task",
//                        status = com.moscow.tudee.domain.entity.Task.Status.DONE,
//                        date = LocalDateTime(2024, 1, 1, 0, 0),
//                        category = category,
//                        priority = Task.Priority.LOW
//                    )
//                )
//                taskService.addTask(
//                    com.moscow.tudee.domain.entity.Task(
//                        id = 10L,
//                        title = "ppppppoo",
//                        description = "This is a test task",
//                        status = com.moscow.tudee.domain.entity.Task.Status.IN_PROGRESS,
//                        date = LocalDateTime(2024, 1, 1, 0, 0),
//                        category = category,
//                        priority = Task.Priority.LOW
//                    )
//                )
        }
    }
}

