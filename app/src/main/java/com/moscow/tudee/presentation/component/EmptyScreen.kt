package com.moscow.tudee.presentation.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.no_tasks_here),
    description: String = stringResource(R.string.tap_the_button_to_add_your_first_one)
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd,
    ) {
        Box(
            modifier = Modifier
                .padding(top = 12.dp, end = 18.dp)
                .height(149.dp)
                .width(148.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.circle_background),
                contentDescription = "circle background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 5.dp)
            )
            Image(
                painter = painterResource(R.drawable.im_robot_normal),
                contentDescription = "normal robot",
                modifier = Modifier.padding(top = 45.dp, start = 42.dp)
            )
            ColumnOfDots(
                modifier = Modifier
                    .padding(top = 65.dp, start = 19.dp)
            )
        }
        MessageBox(
            title = title,
            modifier = Modifier.padding(bottom = 90.dp, end = 147.dp),
            description = description
        )
    }
}

@Composable
fun CategoryEmptyScreen(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.no_tasks_here)
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd,
    ) {
        Box(
            modifier = Modifier
                .padding(top = 12.dp, end = 18.dp)
                .height(149.dp)
                .width(148.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.circle_background),
                contentDescription = "circle background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 5.dp)
            )
            Image(
                painter = painterResource(R.drawable.im_robot_normal),
                contentDescription = "normal robot",
                modifier = Modifier.padding(top = 45.dp, start = 42.dp)
            )
            ColumnOfDots(
                modifier = Modifier
                    .padding(top = 65.dp, start = 19.dp)
            )
        }
        MessageBoxTitleOnly(
            title = title,
            modifier = Modifier.padding(bottom = 90.dp, end = 147.dp)
        )
    }
}

@Composable
fun ColumnOfDots(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Icon(
            painter = painterResource(R.drawable.ic_moon_pit_big),
            contentDescription = stringResource(R.string.circle_big),
            tint = (Theme.colors.surfaceHigh),
            modifier = Modifier.padding(end = 9.dp, bottom = 3.dp)
        )
        Icon(
            painter = painterResource(R.drawable.ic_moon_pit_medium),
            contentDescription = stringResource(R.string.circle_medium),
            tint = (Theme.colors.surfaceHigh),
            modifier = Modifier.padding(start = 10.dp)
        )
        Icon(
            painter = painterResource(R.drawable.ic_moon_pit_small),
            contentDescription = stringResource(R.string.circle_small),
            tint = (Theme.colors.surfaceHigh),
            modifier = Modifier.padding(top = 5.dp, start = 18.dp, bottom = 1.dp)
        )
    }
}

@Composable
fun MessageBox(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .shadow(
                4.dp,
                RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp, bottomStart = 16.dp),
                ambientColor = Theme.colors.body,
                spotColor = Theme.colors.hint
            )
            .background(
                Theme.colors.surfaceHigh,
                RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp, bottomStart = 16.dp)
            )
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Text(
            text = title,
            style = Theme.textStyle.title.small,
            color = Theme.colors.body
        )
        Text(
            text = description,
            style = Theme.textStyle.body.small,
            color = Theme.colors.hint
        )
    }
}

@Composable
fun MessageBoxTitleOnly(
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .shadow(
                4.dp,
                RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp, bottomStart = 16.dp),
                ambientColor = Theme.colors.body,
                spotColor = Theme.colors.hint
            )
            .background(
                Theme.colors.surfaceHigh,
                RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp, bottomStart = 16.dp)
            )
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Text(
            text = title,
            style = Theme.textStyle.title.small,
            color = Theme.colors.body
        )
    }
}

@Preview(apiLevel = 33, uiMode = UI_MODE_NIGHT_NO)
@Preview(apiLevel = 33, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun EmptyScreenPreview() {
    TudeeTheme {
        EmptyScreen(
            title = "blaalla",
            modifier = Modifier.background(Theme.colors.surface)
        )
    }
}

@Preview
@Composable
private fun ColumnOfDotsPreview() {
    TudeeTheme {
        ColumnOfDots()
    }
}

@Preview(apiLevel = 33)
@Composable
private fun MessageBoxPreview() {
    TudeeTheme {
        MessageBox(
            title = "blablabla",
            description = "nnnnnnnnnnnnnnnnn"
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun CategoryEmptyScreenPreview() {
    TudeeTheme {
        CategoryEmptyScreen(
            title = "No tasks in this category",
            modifier = Modifier.background(Theme.colors.surface)
        )
    }
}
