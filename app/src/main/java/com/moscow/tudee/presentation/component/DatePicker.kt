package com.moscow.tudee.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

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


@Preview
@Composable
private fun CustomDatePickerDialogPreview() {
    DatePickerModal(onDismiss = {}, onDateSelected = {})
}