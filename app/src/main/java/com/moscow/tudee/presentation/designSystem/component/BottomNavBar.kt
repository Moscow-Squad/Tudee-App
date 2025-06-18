package com.moscow.tudee.presentation.designSystem.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.moscow.tudee.R
import com.moscow.tudee.presentation.navigation.extensions.navigateSafe
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme
import com.moscow.tudee.presentation.model.BottomNavigationDestination


@Composable
fun BottomNavBar(
    navController: NavController,
    bottomNavigationItems:List<BottomNavigationDestination>,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.surfaceHigh)
            .padding(vertical = 16.dp, horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        bottomNavigationItems.forEachIndexed{ index,screen->
            val isSelected = currentRoute == screen.route::class.java.name
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
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colors.primaryVariant else Theme.colors.surfaceHigh
    )
    val tintColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colors.primary else Theme.colors.hint
    )

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



private fun NavController.selectNavigationItem(route: Any) {
    navigateSafe(route) {
        popUpTo(graph.findStartDestination().id) {

            /**
             *  Preserves the state of the screen you're leaving.
             *  For example, if you're on a list screen and scroll down,
             *  then switch tabs and come back, you'll return to the same scroll position
             */

            saveState = true
        }

        /**
         * Prevents creating duplicate instances of the same destination.
         * If you're already on the "Home" tab and tap "Home" again,
         * it won't create a new Home screen instance
         */
        launchSingleTop = true

        /**
         * When returning to a previously visited tab,
         * it restores the saved state (scroll position, form data, etc.)
         */
        restoreState = true
    }
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
