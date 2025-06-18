package com.moscow.tudee.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.designSystem.theme.Theme

@Composable
fun OutlinedIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    iconColor: Color = Theme.colors.primary,
    strokeColor: Color = Theme.colors.stroke,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = strokeColor,
                shape = CircleShape
            )
            .clickable { onClick() }
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ){
        Icon(
            painter = painterResource(id = icon),
            tint = iconColor,
            contentDescription = "edit icon",
            modifier = Modifier.size(24.dp),
        )
    }
}