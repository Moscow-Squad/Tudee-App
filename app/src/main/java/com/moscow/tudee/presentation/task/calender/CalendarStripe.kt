package com.moscow.tudee.presentation.task.calender
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.component.DayItem
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import com.moscow.tudee.presentation.task.TaskUiState
import java.time.LocalDate

@Composable
fun CalendarStrip(uiState: TaskUiState, onDaySelected: (Int) -> Unit) {
    val today = LocalDate.now()
    var index = 0

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        uiState.weeks.forEach { week ->
            item {
                Text(
                    text = week.label,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(week.days) { day ->
                        val currentIndex = index++
                        DayItem(
                            day = day.dayName,
                            dayDate = day.dayNumber,
                            isSelected = uiState.selectedDayIndex == currentIndex,
                            isToday = today == day.date,
                            onDayClick = { onDaySelected(currentIndex) }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CalenderStripPreview() {
    TudeeTheme {
        val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").mapIndexed { index, name ->
            name to LocalDate.now().plusDays(index.toLong()).dayOfMonth
        }
        val state = TaskUiState(
            selectedDayIndex = 4,
            selectedDate = LocalDate.now().plusDays(4),
        )
        CalendarStrip(uiState = state, onDaySelected = {})
    }

}
