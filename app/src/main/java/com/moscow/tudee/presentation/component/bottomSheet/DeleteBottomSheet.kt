package com.moscow.tudee.presentation.component.bottomSheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.PrimaryButton
import com.moscow.tudee.presentation.component.SecondaryButton
import com.moscow.tudee.presentation.component.TudeeText
import com.moscow.tudee.presentation.component.TudeeTextButton
import com.moscow.tudee.presentation.designSystem.theme.Theme

@Composable
fun DeleteBottomSheet(
    title: String,
    onDelete: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    description: String = stringResource(R.string.are_you_sure_to_continue),
    image: Int = R.drawable.im_robot_normal,
) {
    TudeeBottomSheet(
        onDismissRequest = onDismiss,
        contentPadding = PaddingValues(
            start = 0.dp,
            end = 0.dp,
            bottom = 0.dp
        ),
        modifier = modifier
    ) {
        TudeeText(
            text = title,
            style = Theme.textStyle.title.large,
            color = Theme.colors.title,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp)
        )

        TudeeText(
            text = description,
            style = Theme.textStyle.body.large,
            color = Theme.colors.body,
            modifier = Modifier
                .padding(top = 12.dp, start = 16.dp)
                .align(Alignment.Start)
        )

        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 12.dp)
                .align(Alignment.CenterHorizontally)
        )

        Column(
            Modifier
                .padding(top = 24.dp)
                .background(Theme.colors.surfaceHigh)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            PrimaryButton(
                text = stringResource(R.string.delete),
                onClick = onDelete,
                backgroundColor = SolidColor(Theme.colors.errorVariant),
                textColor = Theme.colors.error,
                modifier = Modifier.fillMaxWidth()
            )

            SecondaryButton(
                text = stringResource(R.string.cancel),
                icon = null,
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )
        }
    }
}

@Preview
@Composable
fun DeleteBottomSheetPreview() {
    var show by remember { mutableStateOf(false) }
    Box(Modifier.fillMaxSize()) {
        if (show) {
            DeleteBottomSheet(
                onDismiss = {},
                title = "Delete Task",
                description = "Are you sure you want to continue?",
                onDelete = { show = false },
                image = R.drawable.im_robot_normal

            )
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