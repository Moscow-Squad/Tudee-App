package com.moscow.tudee.presentation.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.moscow.tudee.presentation.component.bottomSheet.DeleteBottomSheet

@Composable
fun DeleteCategoryBottomSheet(
    title: String,
    onDelete: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    DeleteBottomSheet(
        title = title,
        onDelete = onDelete,
        onDismiss = onDismiss,
        modifier = modifier
    )
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun DeleteCategoryBottomSheetPreview() {
    var show by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        if (show) {
            DeleteCategoryBottomSheet(
                title = "Delete Category",
                onDelete = {show = false},
                onDismiss = {show = false}
            )
        }
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = { show = true }
        ) {
            Text(text = "Show bottom sheet")
        }
    }
}