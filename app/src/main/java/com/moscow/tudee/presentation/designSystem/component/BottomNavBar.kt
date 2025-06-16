package com.moscow.tudee.presentation.designSystem.component

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

private val navItems = listOf(
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

            Crossfade(targetState = isSelected) { selected ->
                NavBarIcon(
                    filledIconRes   = filledIcon,
                    outlineIconRes  = outlineIcon,
                    isSelected      = selected,
                    onClick         = { onItemSelected(index) },
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
}

@Composable
private fun NavBarIcon(
    filledIconRes: Int,
    outlineIconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    contentDescription: String?
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colors.primaryVariant else Theme.colors.surfaceHigh
    )
    val tintColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colors.primary else Theme.colors.hint
    )

    Icon(
        painter = painterResource(id = if (isSelected) filledIconRes else outlineIconRes),
        contentDescription = contentDescription,
        tint = tintColor,
        modifier = Modifier
            .size(42.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
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
