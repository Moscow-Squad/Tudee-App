package com.moscow.tudee.presentation.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme

val navItems = listOf(
    R.drawable.ic_home_filled to R.drawable.ic_home_outlined,
    R.drawable.ic_document_filled to R.drawable.ic_document_outlined,
    R.drawable.ic_menu_circle_filled to R.drawable.ic_menu_circle_outlined
)

@Composable
fun BottomNavBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.surfaceHigh)
            .padding(vertical = 16.dp, horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navItems.forEachIndexed { index, (filledIcon, outlineIcon) ->
            val isSelected = index == selectedIndex
            val iconRes = if (isSelected) filledIcon else outlineIcon

            NavBarIcon(
                iconRes = iconRes,
                isSelected = isSelected,
                onClick = { onItemSelected(index) },
                contentDescription = when (index) {
                    0 -> stringResource(R.string.home)
                    1 -> stringResource(R.string.tasks)
                    2 -> stringResource(R.string.options)
                    else -> null
                }
            )
        }
    }
}

@Composable
private fun NavBarIcon(
    iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    contentDescription: String?
) {
    Icon(
        painter = painterResource(id = iconRes),
        contentDescription = contentDescription,
        tint = if (isSelected) Theme.colors.primary else Theme.colors.hint,
        modifier = Modifier
            .size(42.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .background(
                if (isSelected) Theme.colors.primaryVariant
                else Theme.colors.surfaceHigh
            )
            .padding(9.dp)
            .size(24.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    TudeeTheme {
        var selectedIndex by remember { mutableStateOf(0) }

        Column {
            Spacer(modifier = Modifier.weight(1f))

            BottomNavBar(
                selectedIndex = selectedIndex,
                onItemSelected = { selectedIndex = it }
            )
        }
    }
}
