package com.moscow.tudee.presentation.screen.home.home_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.theme.Theme

@Composable
fun TodayDate(date: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            modifier = Modifier.size(16.dp),
            painter = painterResource(R.drawable.ic_calendar_favorite),
            colorFilter = ColorFilter.tint(color = Theme.colors.body),
            contentDescription = stringResource(R.string.calendar_icon)

        )
        TudeeText(
            text = stringResource(R.string.today, date),
            style = Theme.textStyle.body.small,
            color = Theme.colors.body
        )
    }
}
