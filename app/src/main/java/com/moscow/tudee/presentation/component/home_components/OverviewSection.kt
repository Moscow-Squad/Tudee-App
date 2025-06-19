package com.moscow.tudee.presentation.component.home_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.component.slider.TudeeSlider
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.screen.home.HomeState.SliderState
import com.moscow.tudee.presentation.screen.home.TaskDetails
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun OverviewSection(
    sliderState: SliderState,
    tasks: List<TaskDetails>,
    modifier: Modifier = Modifier
) {
    val todayDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val formattedDate = todayDate.toJavaLocalDateTime()
        .format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.US))

    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Theme.colors.surfaceHigh, RoundedCornerShape(16.dp))
    ) {
        TodayDate(
            date = formattedDate,
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
        )

        TudeeSlider(sliderState, modifier = Modifier.padding(horizontal = 6.dp))

        TudeeText(
            text = stringResource(R.string.overview),
            style = Theme.textStyle.title.large.copy(color = Theme.colors.title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tasks.groupBy { it.state }.entries.forEach {
                OverviewCard(it.key, it.value.count(), modifier = Modifier.weight(1f))
            }
        }
    }
}

