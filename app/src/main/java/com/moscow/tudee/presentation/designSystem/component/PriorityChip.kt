package com.moscow.tudee.presentation.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme

@Composable
fun PriorityChip(
    text: String,
    backgroundColor: Color,
    icon: Painter,
    contentColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = stringResource(R.string.priority_icon),
            tint = contentColor,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = text,
            color = contentColor,
            style = Theme.textStyle.label.small,
        )
    }
}