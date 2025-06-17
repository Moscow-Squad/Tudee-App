package com.moscow.tudee.presentation.designSystem.component

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun TudeeText(
    value:String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    maxLines:Int = 1 ) {
    BasicText(
        text = value,
        modifier = modifier,
        style = textStyle,
        maxLines = maxLines
    )
}