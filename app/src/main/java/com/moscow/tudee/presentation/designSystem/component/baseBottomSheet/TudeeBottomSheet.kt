package com.moscow.tudee.presentation.designSystem.component.baseBottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.designSystem.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TudeeBottomSheet(
    containerColor: Color = Theme.colors.surface,
    expanded: Boolean = true,
    contentHorizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    onDismissRequest: (() -> Unit)?,
    scrimColor: Color = Color.Black.copy(alpha = 0.5f),
    content: @Composable ColumnScope.() -> Unit,
) {

    val sheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = expanded,
            confirmValueChange = { onDismissRequest != null },
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
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp),
        ) {
            content()
        }
    }
}