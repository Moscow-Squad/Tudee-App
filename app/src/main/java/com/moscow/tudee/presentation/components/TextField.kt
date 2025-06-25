package com.moscow.tudee.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.designSystem.theme.Theme

@Composable
fun TudeeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    singleLine: Boolean,
    hint: String,
    modifier: Modifier = Modifier,
    startIcon: Painter? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) Theme.colors.primary else Theme.colors.stroke,
        label = "BorderColor"
    )

    val iconColor by animateColorAsState(
        targetValue = if (value.isEmpty()) Theme.colors.hint else Theme.colors.body,
        label = "IconColor"
    )

    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = if (startIcon != null) 16.dp else 12.dp)

    ) {
        Row(
            modifier = Modifier
                .matchParentSize()
        ) {
            if (startIcon != null) {
                Image(
                    painter = startIcon,
                    colorFilter = ColorFilter.tint(iconColor),
                    contentDescription = stringResource(R.string.text_field_icon),
                    modifier = Modifier.size(24.dp)
                )

                Box(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .width(1.dp)
                        .height(30.dp)
                        .background(Theme.colors.stroke)
                )
            }
            TudeeBasicTextField(
                value = value,
                onValueChange = onValueChange,
                hint = hint,
                keyboardOptions = keyboardOptions,
                singleLine = singleLine,
                interactionSource = interactionSource,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            )
        }
    }
}

@Composable
private fun TudeeBasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    keyboardOptions: KeyboardOptions,
    singleLine: Boolean,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        interactionSource = interactionSource,
        textStyle = Theme.textStyle.body.medium.copy(
            color = Theme.colors.body,
        ),
        cursorBrush = SolidColor(Theme.colors.primary),
        modifier = modifier,
    ) { innerTextField ->
        Box {
            if (value.isEmpty()) {
                TudeeText(
                    text = hint,
                    color = Theme.colors.hint,
                    maxLines = 1,
                    style = Theme.textStyle.label.medium,

                )
            }
            innerTextField()
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun TextFieldPreview() {

    var text by remember { mutableStateOf("") }
    TudeeTextField(
        value = text,
        onValueChange = { text = it },
        keyboardOptions = KeyboardOptions.Default,
        singleLine = true,
        hint = "Enter your name",
        startIcon = painterResource(id = R.drawable.ic_user),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    )

}
