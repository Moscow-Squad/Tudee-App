package com.moscow.tudee.presentation.screen.category.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import com.moscow.tudee.R
import com.moscow.tudee.presentation.component.PrimaryButton
import com.moscow.tudee.presentation.component.SecondaryButton
import com.moscow.tudee.presentation.component.bottomSheet.TudeeBottomSheet
import com.moscow.tudee.presentation.component.modifier.dashedBorder
import com.moscow.tudee.presentation.component.TudeeTextField
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors
import com.moscow.tudee.presentation.designSystem.theme.Theme.textStyle

@Composable
fun AddCategoryBottomSheet(
    categoryTitle: String,
    onNewCategory: (String) -> Unit,
    onDismissRequest: () -> Unit,
    image: AsyncImagePainter? = null,
    onCategoryTitleChange: (String) -> Unit,
    onEditImage: () -> Unit,
    onDeleteCategory: () -> Unit
) {
    val isEnabled = remember {
        categoryTitle.isNotBlank() && image != null
    }

    TudeeBottomSheet(
        onDismissRequest = onDismissRequest,
        contentHorizontalAlignment = Alignment.Start
    ) {
        Column(
            Modifier
                .background(colors.surface)
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Add new category",
                    style = textStyle.title.large,
                    color = colors.title,
                )

                AnimatedVisibility(
                    visible = image != null
                ) {
                    Text(
                        modifier = Modifier
                            .clickable(onClick = onDeleteCategory),
                        text = "Delete",
                        style = textStyle.label.large,
                        color = colors.error,
                    )
                }
            }

            TudeeTextField(
                value = categoryTitle,
                onValueChange = onCategoryTitleChange,
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
                color = colors.title
            )

            Box(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 36.dp)
                    .background(colors.surface)
                    .dashedBorder(1.dp, colors.stroke, 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(onClick = onEditImage),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedContent(
                        targetState = image != null,
                    ) { hasImage ->
                        if (hasImage) {
                            Box(
                                Modifier
                                    .background(
                                        color = Color(0x1A000000),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .clip(RoundedCornerShape(16.dp))
                            ) {
                                Image(
                                    modifier = Modifier.size(112.dp, 113.dp),
                                    painter = image!!,
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "Category Image",
                                )
                                Icon(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .background(
                                            color = colors.surfaceHigh,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .padding(6.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .size(20.dp),
                                    painter = painterResource(R.drawable.ic_pencil_edit),
                                    contentDescription = "Edit Image",
                                    tint = colors.secondary,
                                )
                            }
                        } else {
                            Column(
                                modifier = Modifier.padding(32.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_image_add),
                                    contentDescription = "Add Image",
                                )
                                Spacer(Modifier.padding(top = 8.dp))
                                Text(
                                    text = "Upload",
                                    style = textStyle.label.medium,
                                    color = colors.hint,
                                )
                            }
                        }
                    }
                }
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .shadow(elevation = 4.dp)
                .background(colors.surfaceHigh)
        ) {
            PrimaryButton(
                text = "Add",
                onClick = { onNewCategory(categoryTitle) },
                isEnabled = isEnabled,
                backgroundColor = if (isEnabled) colors.primaryGradient else Brush.linearGradient(listOf(colors.disable, colors.disable)),
                textColor = if (isEnabled) colors.onPrimary.copy(0.87f) else colors.stroke.copy(0.12f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 16.dp, end = 16.dp)
            )

            SecondaryButton(
                text = "Cancel",
                onClick = onDismissRequest,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddCategoryBottomSheetPreview() {
    var show by remember { mutableStateOf(true) }
    if (show) {
        AddCategoryBottomSheet(
            onDismissRequest = { show = false },
            onNewCategory = { },
            categoryTitle = "",
            image = null,
            onCategoryTitleChange = {},
            onEditImage = {},
            onDeleteCategory = {}
        )
    }
}