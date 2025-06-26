package com.moscow.tudee.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import com.moscow.tudee.presentation.model.BottomNavigationDestination
import com.moscow.tudee.presentation.navigation.extensions.selectNavigationItem


@Composable
fun BottomNavBar(
    navController: NavController,
    bottomNavigationItems: List<BottomNavigationDestination>,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.surfaceHigh)
            .navigationBarsPadding()
            .padding(vertical = 16.dp, horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        bottomNavigationItems.forEachIndexed { index, screen ->

            val isSelected = when {
                screen.route::class.simpleName == "HomeScreen" ->
                    currentRoute?.contains("Home") == true

                screen.route::class.simpleName == "TasksScreen" ->
                    currentRoute?.contains("Task") == true

                screen.route::class.simpleName == "CategoriesScreen" ->
                    currentRoute?.contains("Categor") == true

                else -> false
            }
            val icon = if (isSelected) screen.selectedIcon else screen.notSelectedIcon

            Crossfade(targetState = isSelected) { selected ->
                NavBarIcon(
                    icon = icon,
                    isSelected = selected,
                    onClick = {
                        navController.selectNavigationItem(screen.route)
                    },
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
    @DrawableRes icon: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    contentDescription: String?
) {
    val backgroundColor = if (isSelected) Theme.colors.primaryVariant else Theme.colors.surfaceHigh

    val tintColor = if (isSelected) Theme.colors.primary else Theme.colors.hint


    Icon(
        painter = painterResource(id = icon),
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
        Column {
            Spacer(modifier = Modifier.weight(1f))

            BottomNavBar(
                navController = TODO(),
                bottomNavigationItems = TODO(),
                modifier = TODO()
            )
        }
    }
}
