package com.moscow.tudee.presentation.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.designSystem.color.LocalTudeeColors
import com.moscow.tudee.presentation.designSystem.typography.LocalTudeeTextStyle

@Composable
fun Day(
    modifier: Modifier = Modifier,
    day: String,
    dayDate: Int,
    isSelected: Boolean,
    onDayClick: () -> Unit = {},
) {
    val colors = LocalTudeeColors.current
    val textStyle = LocalTudeeTextStyle.current.body
    val modifierWithBackground = if (isSelected) {
        modifier
            .clip(RoundedCornerShape(16.dp))
            .background(colors.primaryGradient)
    } else {
        modifier
            .clip(RoundedCornerShape(16.dp))
            .background(colors.surface)
    }

    Column(
        modifier = modifierWithBackground
            .clickable(onClick = onDayClick)
            .width(56.dp)
            .wrapContentHeight()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "$dayDate",
            color = if (isSelected) colors.onPrimary else colors.body,
            style = textStyle.large,
            modifier = Modifier.wrapContentSize()

        )
        Text(
            text = day,
            color = if (isSelected) colors.onPrimary else colors.hint,
            style = textStyle.small,
            modifier = Modifier.wrapContentSize()
        )
    }
}

@Preview
@Composable
fun PreviewDay() {
    Day(
        day = "Mon",
        dayDate = 12,
        isSelected = true,
    )
}