package com.moscow.tudee.presentation.designSystem.component

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
import com.moscow.tudee.domain.entity.SliderContent
import com.moscow.tudee.domain.entity.SliderState
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
            modifier = Modifier.weight(1f)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = content.title),
                    color = colors.title,
                    style = DefaultTextStyle.title.small
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = content.emojiState),
                    contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = content.description),
                color = colors.body,
                style = DefaultTextStyle.body.small
            )
        }
        Spacer(modifier = Modifier.width(12.dp))

        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.ic_moon_pit_big),
                contentDescription = null,
                modifier = Modifier.size(76.dp)
            )

            Image(
                painter = painterResource(id = content.imageResId),
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TudeeSliderPreview() {
    TudeeSlider(
        state = SliderState.TADAA
    )
}