package com.moscow.tudee.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.component.modifier.applyIf
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors
import com.moscow.tudee.presentation.designSystem.theme.Theme.textStyle

@Composable
fun DayItem(
    modifier: Modifier = Modifier,
    day: String,
    dayDate: Int,
    isSelected: Boolean,
    onDayClick: () -> Unit,
    isToday: Boolean = false
) {

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .applyIf(isSelected) { background(colors.primaryGradient) }
            .applyIf(!isSelected) { background(colors.surface) }
            .clickable(onClick = onDayClick)
            .width(56.dp)
            .wrapContentHeight()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = "$dayDate",
            color = if (isSelected) colors.onPrimary else colors.body,
            style = textStyle.title.medium,

            )
        Text(
            text = day,
            color = if (isSelected) colors.onPrimaryCaption else colors.hint,
            style = textStyle.body.small,
        )
    }
}

@Preview(apiLevel = 33)
@Composable
fun PreviewDay() {
    DayItem(
        day = "Mon",
        dayDate = 12,
        isSelected = true,
        onDayClick = { }
    )
}