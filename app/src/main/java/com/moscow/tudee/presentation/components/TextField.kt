package com.moscow.tudee.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme


@Composable
fun TextField(
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    singleLine: Boolean,
    modifier: Modifier = Modifier,
    value: String = "",
    hint:String = "",
    startIcon: Painter? = null,
    size: Int = 24,
    paddingValues: Int = 16
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()


    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = if (isFocused) Theme.colors.primary else Theme.colors.stroke,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = paddingValues.dp)

    ) {
        Row(modifier=Modifier.fillMaxWidth().height(size.dp), verticalAlignment = Alignment.CenterVertically ) {
            if (startIcon != null) {
                Image(
                    painter = startIcon,
                    colorFilter = ColorFilter.tint(if (value.isEmpty()) Theme.colors.hint else Theme.colors.body),
                    contentDescription = "Text Field Icon",
                    modifier = Modifier.size(24.dp)
                )

                Image(
                    painter = painterResource(R.drawable.ic_line),
                    contentDescription = "divider",
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
            BasicTextField(

                modifier = Modifier.weight(1f),
                enabled = true,
                readOnly = false,
                singleLine = singleLine,
                textStyle = Theme.textStyle.body.medium.copy(
                    color = Theme.colors.body,

                ),
                cursorBrush = SolidColor(Theme.colors.primary),
                value = value,
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                onValueChange = { onValueChange(it) },
                decorationBox = { innerTextField ->
                    if (value.isBlank()) {
                        BasicText(
                            text = hint,
                            minLines = 1,
                            maxLines = 1,
                            style =  Theme.textStyle.label.medium.copy(

                                color = Theme.colors.hint
                            ),
                        )
                    }
                }
            )
        }

    }
}

    @Preview(showBackground = true)
    @Composable
    private fun TextFieldPreview() {

        Column (Modifier.fillMaxWidth().padding(16.dp)){

            Spacer(Modifier.height(150.dp))
            TextField(
              {},
                singleLine = false, keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ), size = 150
            )
            Spacer(Modifier.height(150.dp))
            TextField(
               {},
                singleLine = true,
                hint = "Full name",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                startIcon = painterResource(R.drawable.ic_user)
            )

        }
    }