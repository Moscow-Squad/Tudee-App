package com.moscow.tudee.presentation.designSystem.component.modifier

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors

@Composable
fun Modifier.bottomBorder(
    color: Color = colors.stroke,
    borderSize: Dp = 1.dp
): Modifier {
    return this.then(
        Modifier.drawBehind {
            drawLine(
                color = color,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = borderSize.toPx()
            )
        }
    )
}