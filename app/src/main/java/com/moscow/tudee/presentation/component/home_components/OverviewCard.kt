package com.moscow.tudee.presentation.component.home_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.home.TaskState

@Composable
fun OverviewCard(taskState: TaskState, count: Int, modifier: Modifier = Modifier) {
    val offsetX = 55
    val offsetY = -50

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                color = when (taskState) {
                    TaskState.DONE -> Theme.colors.greenAccent
                    TaskState.IN_PROGRESS -> Theme.colors.yellowAccent
                    TaskState.TODO -> Theme.colors.purpleAccent
                },
                shape = RoundedCornerShape(20.dp)
            )
            .padding(12.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.img_overview_card_background),
            contentDescription = stringResource(R.string.overview_card_background),
            modifier = Modifier
                .offset(x = offsetX.dp, y = offsetY.dp)
                .align(Alignment.TopEnd)
        )

        Column(
            Modifier.wrapContentWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = when (taskState) {
                    TaskState.DONE -> painterResource(R.drawable.ic_done_tasks)
                    TaskState.IN_PROGRESS -> painterResource(R.drawable.ic_in_progress_tasks)
                    TaskState.TODO -> painterResource(R.drawable.ic_to_do_tasks)
                },
                contentDescription = stringResource(R.string.overview_card_icon),
                modifier = Modifier
                    .border(
                        1.dp,
                        Theme.colors.surfaceHigh.copy(alpha = 0.12f),
                        RoundedCornerShape(12.dp)
                    )
                    .background(
                        Theme.colors.surfaceHigh.copy(alpha = 0.24f),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(8.dp)
            )

            TudeeText(
                text = count.toString(),
                style = Theme.textStyle.headline.medium.copy(color = Theme.colors.onPrimary)
            )
            TudeeText(
                text = stringResource(taskState.labelResInt),
                style = Theme.textStyle.label.small.copy(color = Theme.colors.onPrimaryCaption)
            )
        }
    }
}
