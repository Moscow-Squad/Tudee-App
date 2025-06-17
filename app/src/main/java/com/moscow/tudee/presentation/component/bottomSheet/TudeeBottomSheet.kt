package com.moscow.tudee.presentation.component.bottomSheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.designSystem.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TudeeBottomSheet(
    modifier: Modifier = Modifier,
    containerColor: Color = Theme.colors.surface,
    expanded: Boolean = true,
    contentHorizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    onDismissRequest: (() -> Unit)?,
    scrimColor: Color = Color.Black.copy(alpha = 0.5f),
    contentPadding: PaddingValues = PaddingValues(
        start = 16.dp,
        end = 16.dp,
        bottom = 24.dp
    ),
    content: @Composable ColumnScope.() -> Unit,
) {

    val sheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = expanded,
        )

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest ?: {},
        properties = ModalBottomSheetProperties(shouldDismissOnBackPress = onDismissRequest != null),
        containerColor = containerColor,
        scrimColor = scrimColor,
    ) {
        Column(
            horizontalAlignment = contentHorizontalAlignment,
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(paddingValues = contentPadding)
        ) {
            content()
        }
    }
}


@Preview
@Composable
private fun TudeeBottomSheetPreview() {

    var show by remember { mutableStateOf(false) }
    Box(Modifier.fillMaxSize()) {


        if (show) {

            TudeeBottomSheet(
                onDismissRequest = {},

                ) {
                Column {
                    Text(text = "bottom sheet content")
                    Text(text = "bottom sheet content")
                    Text(text = "bottom sheet content")
                    Text(text = "bottom sheet content")
                }
            }
        }

        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = {
                show = true
            }) {
            Text(text = "Show bottom sheet")
        }
    }
}
