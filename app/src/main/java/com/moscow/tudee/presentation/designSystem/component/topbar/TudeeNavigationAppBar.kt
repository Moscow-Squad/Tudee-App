package com.moscow.tudee.presentation.designSystem.component.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.theme.Theme

@Composable
fun TudeeNavigationAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.surfaceHigh)
            .statusBarsPadding()
            .padding(vertical = 20.dp)
            .padding(horizontal = 16.dp),
    ) {

        Box(
            modifier = modifier
                .clip(CircleShape)
                .size(40.dp)
                .clickable {
                    navigateBack()
                }
                .padding(end = 12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_head_back),
                tint = Theme.colors.body.copy(alpha = .6f),
                contentDescription = "navigate back",
            )
        }
        TudeeText(
            text = title
        )

        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = modifier
                .clip(CircleShape)
                .size(40.dp)
                .clickable {

                }
                .padding(end = 16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_pencil_edit),
                tint = Theme.colors.body.copy(alpha = .6f),
                contentDescription = "navigate back",
            )
        }

    }
}