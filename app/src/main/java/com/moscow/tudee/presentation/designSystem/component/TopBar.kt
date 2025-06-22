package com.moscow.tudee.presentation.designSystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme

@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier,
    startIcon: Painter? = null,
    onStartClick: () -> Unit = {},
    endIcon: Painter? = null,
    onEndClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (startIcon != null) {
            TopBarIcon(
                painter = startIcon,
                contentDescription = stringResource(R.string.back_button),
                onClick = onStartClick
            )
            Spacer(Modifier.width(12.dp))
        }

        Text(
            text = title,
            style = Theme.textStyle.title.large,
            color = Theme.colors.title,
            modifier = Modifier.weight(1f)
        )

        if (endIcon != null) {
            TopBarIcon(
                painter = endIcon,
                contentDescription = stringResource(R.string.edit_category_icon),
                onClick = onEndClick
            )
        }
    }
}

@Composable
private fun TopBarIcon(
    painter: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    Icon(
        painter = painter,
        contentDescription = contentDescription,
        tint = Theme.colors.body,
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .border(1.dp, Theme.colors.stroke, CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication       = null,
                onClick = onClick
            )
            .padding(10.dp)
            .size(20.dp)
    )
}


@Preview(showBackground = true, name = "No Icons")
@Composable
fun TopBarPreview_NoIcons() {
    TopBar(title = "Reading novel")
}

@Preview(showBackground = true, name = "Only Start Icon")
@Composable
fun TopBarPreview_StartIcon() {
    TopBar(
        title = "Reading novel",
        startIcon = painterResource(id = R.drawable.ic_arrow_head_back)
    )
}

@Preview(showBackground = true, name = "Both Icons")
@Composable
fun TopBarPreview_BothIcons() {
    TopBar(
        title = "Reading novel",
        startIcon = painterResource(id = R.drawable.ic_arrow_head_back),
        endIcon = painterResource(id = R.drawable.ic_pencil_edit)
    )
}