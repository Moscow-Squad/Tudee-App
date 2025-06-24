package com.moscow.tudee.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    selectedDate: Long? = null
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        colors = DatePickerDefaults.colors(
            containerColor = Theme.colors.surface
        ),
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TudeeTextButton(
                    text = stringResource(R.string.clear),
                    onClick = {
                        onDateSelected(null)
                        onDismiss()
                    },
                    colors = Theme.colors.primary,
                    style = Theme.textStyle.label.large,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                TudeeTextButton(
                    text = stringResource(R.string.cancel),
                    onClick = onDismiss,
                    colors = Theme.colors.primary,
                    style = Theme.textStyle.label.large,
                    fontSize = 16.sp
                )
                TudeeTextButton(
                    text = stringResource(R.string.ok),
                    onClick = {
                        onDateSelected(datePickerState.selectedDateMillis)
                        onDismiss()
                    },
                    colors = Theme.colors.primary,
                    style = Theme.textStyle.label.large,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(end = 16.dp, bottom = 8.dp)
                )
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = Theme.colors.surface,
                titleContentColor = Theme.colors.title,
                headlineContentColor = Theme.colors.title,
                weekdayContentColor = Theme.colors.title,
                selectedYearContentColor = Theme.colors.onPrimary,
                selectedYearContainerColor = Theme.colors.primary,
                selectedDayContentColor = Theme.colors.onPrimary,
                selectedDayContainerColor = Theme.colors.primary,
                todayContentColor = Theme.colors.primary,
                todayDateBorderColor = Theme.colors.primary,
                yearContentColor = Theme.colors.body
            )
        )
    }
}


@Composable
fun TudeeDatePickerTextField(
    selectedDate: Long?,
    onDateSelected: (Long?) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    startIcon: Painter? = null,
    dateFormat: String = "dd-MM-yyyy"
) {
    var showDatePicker by remember { mutableStateOf(false) }

    fun formatDate(localDate: LocalDate): String {
        return when (dateFormat) {
            "dd-MM-yyyy" -> "${
                localDate.dayOfMonth.toString().padStart(2, '0')
            }-${localDate.monthNumber.toString().padStart(2, '0')}-${localDate.year}"

            "dd/MM/yyyy" -> "${
                localDate.dayOfMonth.toString().padStart(2, '0')
            }/${localDate.monthNumber.toString().padStart(2, '0')}/${localDate.year}"

            "MM/dd/yyyy" -> "${
                localDate.monthNumber.toString().padStart(2, '0')
            }/${localDate.dayOfMonth.toString().padStart(2, '0')}/${localDate.year}"

            "yyyy-MM-dd" -> "${localDate.year}-${
                localDate.monthNumber.toString().padStart(2, '0')
            }-${localDate.dayOfMonth.toString().padStart(2, '0')}"

            else -> "${
                localDate.dayOfMonth.toString().padStart(2, '0')
            }-${localDate.monthNumber.toString().padStart(2, '0')}-${localDate.year}"
        }
    }

    val todayDate = remember {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        formatDate(today)
    }

    val hintText = if (hint.isEmpty()) todayDate else hint
    val displayText = selectedDate?.let {
        val date = Instant.fromEpochMilliseconds(it)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
        formatDate(date)
    } ?: ""

    val borderColor by animateColorAsState(
        targetValue = if (showDatePicker) Theme.colors.primary else Theme.colors.stroke,
        label = "BorderColor"
    )

    val iconColor by animateColorAsState(
        targetValue = if (displayText.isEmpty()) Theme.colors.hint else Theme.colors.body,
        label = "IconColor"
    )

    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { showDatePicker = true }
            .padding(horizontal = 12.dp, vertical = if (startIcon != null) 16.dp else 12.dp)
    ) {
        Row(
            modifier = Modifier.matchParentSize()
        ) {
            if (startIcon != null) {
                Image(
                    painter = startIcon,
                    colorFilter = ColorFilter.tint(iconColor),
                    contentDescription = stringResource(R.string.text_field_icon),
                    modifier = Modifier.size(24.dp)
                )

                Image(
                    painter = painterResource(R.drawable.ic_line),
                    contentDescription = stringResource(R.string.divider),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                if (displayText.isEmpty()) {
                    TudeeText(
                        text = hintText,
                        color = Theme.colors.hint,
                        maxLines = 1,
                        style = Theme.textStyle.label.medium
                    )
                } else {
                    TudeeText(
                        text = displayText,
                        color = Theme.colors.body,
                        maxLines = 1,
                        style = Theme.textStyle.body.medium
                    )
                }
            }
        }
    }

    if (showDatePicker) {
        DatePickerModal(
            onDateSelected = onDateSelected,
            onDismiss = { showDatePicker = false },
            selectedDate = selectedDate
        )
    }
}


@Preview
@Composable
private fun CustomDatePickerDialogPreview() {
    DatePickerModal(onDismiss = {}, onDateSelected = {})
}