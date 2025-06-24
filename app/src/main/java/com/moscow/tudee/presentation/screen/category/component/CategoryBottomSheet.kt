package com.moscow.tudee.presentation.screen.category.component

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.PrimaryButton
import com.moscow.tudee.presentation.component.SecondaryButton
import com.moscow.tudee.presentation.component.bottomSheet.TudeeBottomSheet
import com.moscow.tudee.presentation.component.modifier.dashedBorder
import com.moscow.tudee.presentation.components.TudeeTextField
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.util.rememberGalleryPicker

@Composable
fun CategoryBottomSheet(
    initialTitle: String? = null,
    initialImageUri: Uri? = null,
    isEdit: Boolean = false,
    onConfirm: (String, Uri?) -> Unit = { _, _ -> },
    onDismiss: () -> Unit = {},
    onShowDeleteCategory: () -> Unit = {}
) {
    var categoryTitle by rememberSaveable { mutableStateOf(initialTitle.orEmpty()) }
    var selectedImageUri by rememberSaveable { mutableStateOf(initialImageUri) }
    val pickImage = rememberGalleryPicker { uri -> selectedImageUri = uri }

    val isEnabled = if (isEdit) {
        categoryTitle.isNotBlank()
    } else {
        categoryTitle.isNotBlank() && selectedImageUri != null
    }

    TudeeBottomSheet(
        onDismissRequest = onDismiss,
        contentHorizontalAlignment = Alignment.Start
    ) {
        Column(
            Modifier
                .background(Theme.colors.surface)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isEdit) stringResource(R.string.edit_category) else stringResource(R.string.add_new_category),
                    style = Theme.textStyle.title.large,
                    color = Theme.colors.title
                )
                AnimatedVisibility(visible = isEdit) {
                    Text(
                        text = stringResource(R.string.delete),
                        style = Theme.textStyle.label.large,
                        color = Theme.colors.error,
                        modifier = Modifier.clickable(
                            onClick = onShowDeleteCategory, indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        )
                    )
                }
            }

            TudeeTextField(
                value = categoryTitle,
                onValueChange = {
                    if (it.length <= 20) {
                        categoryTitle = it
                    }
                },
                singleLine = true,
                hint = stringResource(R.string.category_title),
                startIcon = painterResource(R.drawable.ic_menu_circle_outlined),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .height(56.dp),
                keyboardOptions = KeyboardOptions.Default,
            )

            Text(
                text = stringResource(R.string.category_image),
                style = Theme.textStyle.title.medium,
                color = Theme.colors.title
            )

            Box(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 24.dp)
                    .background(Theme.colors.surface)
                    .dashedBorder(1.dp, Theme.colors.stroke, 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(onClick = pickImage),
                contentAlignment = Alignment.Center
            ) {
                if (selectedImageUri != null) {
                    Box(
                        Modifier
                            .background(Theme.colors.stroke, RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Category Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(112.dp, 112.dp)
                        )
                        Icon(
                            painter = painterResource(R.drawable.ic_pencil_edit_filled),
                            contentDescription = "Replace Image",
                            tint = Theme.colors.secondary,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(32.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Theme.colors.surfaceHigh)
                                .padding(6.dp)
                        )
                    }
                } else {
                    Column(
                        Modifier.padding(32.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_image_add),
                            contentDescription = "Add Image",
                            tint = Theme.colors.hint
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Upload",
                            style = Theme.textStyle.label.medium,
                            color = Theme.colors.hint
                        )
                    }
                }
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .shadow(4.dp)
                .background(Theme.colors.surfaceHigh)
        ) {
            PrimaryButton(
                text = if (isEdit) stringResource(R.string.save) else stringResource(R.string.add_bottom_sheet),
                onClick = { onConfirm(categoryTitle, selectedImageUri) },
                isEnabled = isEnabled,
                backgroundColor = if (isEnabled)
                    Theme.colors.primaryGradient
                else
                    Brush.linearGradient(listOf(Theme.colors.disable, Theme.colors.disable)),
                textColor = if (isEnabled)
                    Theme.colors.onPrimary.copy(0.87f)
                else
                    Theme.colors.stroke.copy(0.12f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 16.dp, end = 16.dp)
            )

            SecondaryButton(
                text = stringResource(R.string.cancel_bottom_sheet),
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            )
        }
    }
}
