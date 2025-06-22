package com.moscow.tudee.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme

@Preview
@Composable
private fun EmptyState(modifier: Modifier = Modifier) {

    Box (modifier = Modifier
        .fillMaxWidth()
        .height(160.dp)
        .padding(horizontal = 16.dp)){
         Box(Modifier
             .size(144.dp)
             .align (Alignment.BottomEnd)){
             Image(
                 painter = painterResource(R.drawable.im_robot_normal),
                 contentDescription = "",
                 contentScale = ContentScale.FillBounds,
                 modifier = Modifier.align(Alignment.BottomEnd)
             )
         }
        Column(
            modifier = Modifier
                
                .background(
                    color = Theme.colors.surfaceHigh, shape = RoundedCornerShape(
                        topEnd = 16.dp, topStart = 16.dp, bottomEnd = 2.dp, bottomStart = 16.dp
                    )
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            TudeeText(
                text = "No tasks for today!",
                style = Theme.textStyle.title.small,
                color = Theme.colors.body

            )
            TudeeText(
                text = "Tap the + button to add your first one.",
                style = Theme.textStyle.body.small,
                color = Theme.colors.hint
            )

        }

    }
}