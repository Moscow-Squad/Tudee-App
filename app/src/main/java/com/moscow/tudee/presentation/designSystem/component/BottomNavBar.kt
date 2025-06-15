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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme

val navItems = listOf(
    R.drawable.home_filled to R.drawable.home,
    R.drawable.list_filled to R.drawable.list,
    R.drawable.options_filled to R.drawable.options
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
                    0 -> "Home"
                    1 -> "Tasks"
                    2 -> "Options"
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
    Box(
        modifier = Modifier
            .size(42.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .background(
                if (isSelected) Theme.colors.primaryVariant
                else Theme.colors.surfaceHigh
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            tint = if (isSelected) Theme.colors.primary else Theme.colors.hint,
            modifier = Modifier.size(24.dp)
        )
    }
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
