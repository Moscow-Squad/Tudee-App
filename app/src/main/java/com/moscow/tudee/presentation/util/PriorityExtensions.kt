package com.moscow.tudee.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.designSystem.theme.Theme

@Composable
fun Task.Priority.getPriorityBackground(): Color {
    return when(this){
        Task.Priority.LOW -> Theme.colors.greenAccent
        Task.Priority.MEDIUM -> Theme.colors.yellowAccent
        Task.Priority.HIGH -> Theme.colors.pinkAccent
    }
}