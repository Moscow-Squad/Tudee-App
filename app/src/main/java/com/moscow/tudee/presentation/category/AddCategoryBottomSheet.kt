package com.moscow.tudee.presentation.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.PrimaryButton
import com.moscow.tudee.presentation.component.SecondaryButton
import com.moscow.tudee.presentation.component.bottomSheet.TudeeBottomSheet
import com.moscow.tudee.presentation.component.modifier.dashedBorder
import com.moscow.tudee.presentation.components.TudeeTextField
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors
import com.moscow.tudee.presentation.designSystem.theme.Theme.textStyle

@Composable
fun AddCategoryBottomSheet() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Add new category",
            style = textStyle.title.large,
            color = colors.title,
        )

        TudeeTextField(
            value = "",
            onValueChange = {},
            keyboardOptions = KeyboardOptions.Default,
            singleLine = true,
            hint = "Category Title",
            startIcon = painterResource(R.drawable.ic_menu_circle_outlined),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .height(56.dp)
        )

        Text(
            text = "Category image",
            style = textStyle.title.medium,
            color = colors.title,
        )

        Box(
            modifier = Modifier
                .background(colors.surface)
                .padding(top = 8.dp, bottom = 36.dp)
                .dashedBorder(1.dp, colors.stroke, 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_image_add),
                    contentDescription = "Add Image",
                )
                Text(
                    text = "Upload",
                    style = textStyle.label.medium,
                    color = colors.hint,
                )
            }
        }

        PrimaryButton(
            text = "Add",
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
        )

        SecondaryButton(
            text = "Cancel",
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun AddCategoryBottomSheetPreview() {
    var show by remember { mutableStateOf(true) }
    if (show) {
        TudeeBottomSheet(onDismissRequest = {}) {
            AddCategoryBottomSheet()
        }
    }
}