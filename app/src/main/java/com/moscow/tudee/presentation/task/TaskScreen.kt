package com.moscow.tudee.presentation.task

import androidx.compose.material3.Card
import com.moscow.tudee.presentation.designSystem.theme.Theme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.component.CustomFAB
import org.koin.androidx.compose.koinViewModel
//@Composable
//fun TaskScreen(viewModel: TaskViewModel = getViewModel()) {
//    val uiState by viewModel.state.collectAsState()
//
//    TaskContent(
//        uiState = uiState,
//        onDaySelected = { viewModel.onDaySelected(it) }
//    )
//}

