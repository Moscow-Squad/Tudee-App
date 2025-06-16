package com.moscow.tudee.presentation.designSystem.component.slider

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors
import com.moscow.tudee.presentation.designSystem.typography.DefaultTextStyle

@Composable
fun TudeeSlider(
    state: SliderState,
    modifier: Modifier = Modifier
) {
    val content = SliderContent.from(state)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 15.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = content.title),
                    color = colors.title,
                    style = DefaultTextStyle.title.small
                )
                Image(
                    painter = painterResource(id = content.emojiState),
                    contentDescription = null,
                )
            }

            Text(
                text = stringResource(id = content.description),
                color = colors.body,
                style = DefaultTextStyle.body.small
            )
        }

        Box(
            modifier = modifier.padding(top = 17.dp),
            contentAlignment = Alignment.Center)
        {
            Image(
                painter = painterResource(R.drawable.ic_moon_pit_big),
                contentDescription = "frame_moon_pit",
                modifier = Modifier.size(76.dp)
            )

            Image(
                painter = painterResource(id = content.imageResId),
                contentDescription = "Tudee Slider Image",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TudeeSliderPreview() {
    TudeeSlider(
        state = SliderState.ZERO_PROGRESS
    )
}