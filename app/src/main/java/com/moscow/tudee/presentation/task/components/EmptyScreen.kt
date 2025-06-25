package com.moscow.tudee.presentation.task.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme

@Composable
fun EmptyScreen(
    title: String,
    modifier: Modifier = Modifier
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
            modifier = Modifier.padding(bottom = 90.dp, end = 147.dp)
        )
    }
}

@Composable
fun ColumnOfDots(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Icon(
            painter = painterResource(R.drawable.ic_moon_pit_big),
            contentDescription = "circle big",
            tint = (Theme.colors.surfaceHigh),
            modifier = Modifier.padding(end = 9.dp, bottom = 3.dp)
        )
        Icon(
            painter = painterResource(R.drawable.ic_moon_pit_medium),
            contentDescription = "circle medium",
            tint = (Theme.colors.surfaceHigh),
            modifier = Modifier.padding(start = 10.dp)
        )
        Icon(
            painter = painterResource(R.drawable.ic_moon_pit_small),
            contentDescription = "circle small",
            tint = (Theme.colors.surfaceHigh),
            modifier = Modifier.padding(top = 5.dp, start = 18.dp, bottom = 1.dp)
        )
    }
}

@Composable
fun MessageBox(
    title: String,
    modifier: Modifier = Modifier
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
            .padding(vertical = 12.dp, horizontal = 8.dp)
    ) {
        Text(
            text = title,
            style = Theme.textStyle.title.small,
            color = Theme.colors.body
        )
        Text(
            text = "Tap the + button to add your first one.",
            style = Theme.textStyle.body.small,
            color = Theme.colors.hint
        )
    }
}

@Preview(apiLevel = 33, uiMode = UI_MODE_NIGHT_NO)
@Preview(apiLevel = 33, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun EmptyScreenPreview() {
    TudeeTheme {
        EmptyScreen(
            "blaalla",
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
        )
    }

}