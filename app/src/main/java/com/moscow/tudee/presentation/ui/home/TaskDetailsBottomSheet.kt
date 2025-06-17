package com.moscow.tudee.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.SecondaryButton
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.component.bottomSheet.TudeeBottomSheet
import com.moscow.tudee.presentation.designSystem.component.PriorityChip
import com.moscow.tudee.presentation.designSystem.theme.Theme

@Composable
fun TaskDetailsBottomSheet(
    onDismiss: () -> Unit,
) {
    TudeeBottomSheet(
        onDismissRequest = onDismiss,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            TudeeText(
                text = stringResource(R.string.task_details),
                style = Theme.textStyle.title.large.copy(
                    color = Theme.colors.title,
                )
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Theme.colors.surfaceHigh)
                    .padding(12.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_chef),
                    contentDescription = "task category"
                )
            }

            TudeeText(
                text = "Organize Study Desk",
                style = Theme.textStyle.title.medium.copy(
                    color = Theme.colors.title,
                )
            )

            TudeeText(
                text = "Solve all exercises from page 45 to 50 in the textbook, Solve all exercises from page 45 to 50 in the textbook.",
                style = Theme.textStyle.body.small.copy(
                    color = Theme.colors.body,
                )
            )

            Spacer(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Theme.colors.stroke)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TaskStatusComponent(
                    modifier = Modifier,
                    status = "In progress",
                    backgroundColor = Theme.colors.purpleVariant,
                    statusColor = Theme.colors.purpleAccent,
                    dotColor = Theme.colors.purpleAccent
                )

                PriorityChip(
                    text = "High",
                    backgroundColor = Theme.colors.greenAccent,
                    icon = painterResource(R.drawable.ic_trade_down),
                    contentColor = Theme.colors.onPrimary,
                )

            }

            // not appears if status id done
            Row(
                modifier = Modifier.padding(top = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                EditButtonComponent()

                SecondaryButton(
                    text = stringResource(R.string.move_to,"in progress"),
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    icon = null,
                )
            }

        }
    }

}

@Preview
@Composable
private fun TaskDetailsBottomSheetPreview() {
    TaskDetailsBottomSheet(
        onDismiss = {}
    )
}