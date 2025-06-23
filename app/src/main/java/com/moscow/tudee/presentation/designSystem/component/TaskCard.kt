package com.moscow.tudee.presentation.designSystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.presentation.category.getPriorityFromString
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import com.moscow.tudee.presentation.util.getPredefinedIconRes

@Composable
fun TaskCard(
    category: Category,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    date: String? = null,
    priorityChip: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Theme.colors.surfaceHigh)
            .fillMaxWidth()
            .animateContentSize()
            .padding(start = 4.dp, end = 12.dp, top = 4.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = if (category.isPredefined) painterResource(getPredefinedIconRes(category.title))
                    else rememberAsyncImagePainter(category.iconUri),
                contentDescription = stringResource(R.string.task_card_icon),
                modifier = Modifier
                    .size(56.dp)
                    .padding(12.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AnimatedVisibility(
                    visible = date != null,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .background(Theme.colors.surface)
                            .padding(vertical = 6.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar_favorite),
                            contentDescription = stringResource(R.string.calender_icon),
                            tint = Theme.colors.body,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = date.orEmpty(),
                            style = Theme.textStyle.label.small,
                            color = Theme.colors.body
                        )
                    }
                }

                priorityChip()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = title,
                style = Theme.textStyle.label.large,
                color = Theme.colors.body
            )
            Text(
                text = description,
                style = Theme.textStyle.label.small,
                color = Theme.colors.hint,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 330, )
@Composable
fun TaskCardWithoutDatePreview() {
    TudeeTheme {
        TaskCard(
            category = Category(
                id = 1,
                title = "Study",
                iconUri = "",
                isPredefined = true
            ),
            title = "Review Flashcards",
            description = "Study biology flashcards for 15 minutes",
        ) {
            PriorityChip(
                priority = getPriorityFromString(stringResource(R.string.high)),
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 330, )
@Composable
fun TaskCardWithDatePreview() {
    TudeeTheme {
        TaskCard(
            category = Category(
                id = 1,
                title = "Study",
                iconUri = "",
                isPredefined = true
            ),
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            date = "03/12/2025",
        ) {
            PriorityChip(
                priority = getPriorityFromString(stringResource(R.string.medium))
            )
        }
    }
}