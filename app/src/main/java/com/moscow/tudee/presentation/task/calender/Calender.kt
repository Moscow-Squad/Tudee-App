package com.moscow.tudee.presentation.task.calender

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.util.LocalePreferences
import com.moscow.tudee.presentation.component.DayItem
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import com.moscow.tudee.presentation.task.TaskUiState
import kotlinx.datetime.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarApp(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Header()
        Content()
    }
}
@Composable
fun Content() {
    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = List(14) { Pair("Sun", "21") }) { date ->
            DayItem(
                day = date.first,
                dayDate = date.second.toInt(),
                isSelected = true,
                onDayClick = { }
            )
        }
    }
}

@Preview(showBackground = true, apiLevel = 33)
@Composable
private fun CalendarAppPreview() {
    TudeeTheme {
        CalendarApp()
    }
}
