package com.moscow.tudee.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.designSystem.typography.LocalTudeeTextStyle

@Composable
fun TudeeTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTudeeTextStyle.current.label.medium,
    colors: Color? = Color.Blue,
    fontSize: TextUnit? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
) {
    TudeeText(
        text = text,
        style = style,
        color = colors,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        modifier = modifier
            .clickable(
                onClick = onClick
            )
            .padding(8.dp)
    )
}

