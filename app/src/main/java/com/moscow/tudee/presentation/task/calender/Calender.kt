package com.moscow.tudee.presentation.task.calender

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.component.DayItem
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import kotlinx.datetime.LocalDate

@Composable
fun CalenderContent() {
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
        Column() {
            Header(
                date = LocalDate.fromEpochDays(1).toString()
            )
            CalenderContent()
        }
    }
}
