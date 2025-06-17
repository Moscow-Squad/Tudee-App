package com.moscow.tudee.presentation.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme

@Composable
fun EditButtonComponent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = Theme.colors.stroke,
                shape = CircleShape
            )
            .padding(vertical = 16.dp, horizontal = 24.dp),

    ){
        Icon(
            painter = painterResource(id = R.drawable.ic_pencil_edit),
            tint = Theme.colors.primary,
            contentDescription = "edit icon",
            modifier = Modifier.size(24.dp),
        )
    }
}