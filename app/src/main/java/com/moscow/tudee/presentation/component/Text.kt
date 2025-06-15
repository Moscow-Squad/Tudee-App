package com.moscow.tudee.presentation.component

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.moscow.tudee.presentation.designSystem.theme.Theme

@Composable
fun TudeeText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = Theme.textStyle.body.medium,
    color: Color? = null,
    fontSize: TextUnit? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    lineHeight: TextUnit = TextUnit.Unspecified,
    letterSpacing: TextUnit = TextUnit.Unspecified
) {
    val finalStyle = style.merge(
        TextStyle(
            color = color ?: Color.Unspecified,
            fontSize = fontSize ?: TextUnit.Unspecified,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            textAlign = textAlign,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing
        )
    )

    BasicText(
        text = text,
        modifier = modifier,
        style = finalStyle,
        maxLines = maxLines,
        overflow = overflow
    )

}

