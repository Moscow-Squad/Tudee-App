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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.component.OutlinedIconButton
import com.moscow.tudee.presentation.component.SecondaryButton
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.component.bottomSheet.TudeeBottomSheet
import com.moscow.tudee.presentation.designSystem.component.PriorityChip
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.screen.home.HomeState
import com.moscow.tudee.presentation.screen.home.getBackgroundColor
import com.moscow.tudee.presentation.screen.home.getColor
import com.moscow.tudee.presentation.screen.home.getIcon
import com.moscow.tudee.presentation.screen.home.getText
import com.moscow.tudee.presentation.util.getPredefinedIconRes
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime

@Composable
fun TaskDetailsBottomSheet(
    task: HomeState.HomeTask,
    onDismiss: () -> Unit,
    onEditClick: () -> Unit,
    onMoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TudeeBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
        ) {

            TudeeText(
                text = stringResource(R.string.task_details),
                style = Theme.textStyle.title.large,
                color = Theme.colors.title
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Theme.colors.surfaceHigh)
                    .padding(12.dp)
            ) {
                Image(
                    painter = if (task.category?.isPredefined == true) painterResource(
                        getPredefinedIconRes(
                            task.category.title
                        )
                    ) else rememberAsyncImagePainter(task.category?.iconUri),
                    contentDescription = task.category?.title,
                    modifier = Modifier
                        .size(32.dp)
                )
            }

            TudeeText(
                text = task.title,
                style = Theme.textStyle.title.medium,
                color = Theme.colors.title
            )

            TudeeText(
                text = task.description,
                style = Theme.textStyle.body.small,
                color = Theme.colors.body,
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
                TaskStatus(
                    status = task.status.getText(),
                    backgroundColor = task.status.getBackgroundColor(),
                    statusColor = task.status.getColor(),
                    dotColor = task.status.getColor()
                )
                task.priority?.let {
                    PriorityChip(
                        text = task.priority.getText(),
                        backgroundColor = task.priority.getColor(),
                        icon = painterResource(task.priority.getIcon()),
                        selected = true
                    )
                }

            }

            if (task.status != Task.Status.DONE) {
                val movedToStatus = when (task.status) {
                    Task.Status.TODO -> Task.Status.IN_PROGRESS
                    Task.Status.IN_PROGRESS -> Task.Status.DONE
                    Task.Status.DONE -> Task.Status.TODO
                }
                Row(
                    modifier = Modifier.padding(top = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    OutlinedIconButton(
                        icon = R.drawable.ic_pencil_edit,
                        onClick = {
                            onEditClick()
                        }
                    )

                    SecondaryButton(

                        text = stringResource(R.string.move_to, movedToStatus.getText()),
                        onClick = {
                            onMoveClick()
                        },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun TaskDetailsBottomSheetPreview() {
    TaskDetailsBottomSheet(
        task = HomeState.HomeTask(
            id = 1,
            title = "Organize Study Desk",
            description = "Solve all exercises from page 45 to 50 in the textbook, Solve all exercises from page 45 to 50 in the textbook.",
            priority = Task.Priority.HIGH,
            status = Task.Status.IN_PROGRESS,
            date = LocalDateTime.now().toKotlinLocalDateTime(),

            ),
        onEditClick = {},
        onMoveClick = {},
        onDismiss = {}
    )
}