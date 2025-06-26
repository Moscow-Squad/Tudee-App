package com.moscow.tudee.presentation.component.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.component.tudeeSwitch.TudeeSwitch
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.typography.CherryBomb

@Composable
fun HomeTopAppBar(
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(Theme.colors.primary)
                .statusBarsPadding()
                .padding(vertical = 12.dp)
                .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Absolute.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_header),
            contentDescription = null,
            modifier =
                Modifier
                    .padding(end = 8.dp)

        )
        Column {
            TudeeText(
                text = title,
                fontFamily = CherryBomb,
                fontSize = 18.sp,
                color = Theme.colors.onPrimary.copy(.87f),
            )
            TudeeText(
                text = subTitle,
                color = Theme.colors.onPrimaryCaption.copy(.70f),
                style = Theme.textStyle.label.small,
                )
        }

        Spacer(modifier = Modifier.weight(1f))

        TudeeSwitch()
    }
}