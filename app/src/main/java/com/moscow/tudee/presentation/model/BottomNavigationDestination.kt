package com.moscow.tudee.presentation.model

import androidx.annotation.DrawableRes
import com.moscow.tudee.R
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.navigation.entry.CategoriesScreen
import com.moscow.tudee.presentation.navigation.entry.HomeScreen
import com.moscow.tudee.presentation.navigation.entry.TasksScreen

enum class BottomNavigationDestination(
    val route: Any,
    @DrawableRes val notSelectedIcon: Int,
    @DrawableRes val selectedIcon: Int,
    ){
    Home(
        route = HomeScreen,
        notSelectedIcon = R.drawable.ic_home_outlined,
        selectedIcon = R.drawable.ic_home_filled ,
    ),

    Tasks(
        route = TasksScreen(Task.Status.TODO),
        notSelectedIcon = R.drawable.ic_document_outlined,
        selectedIcon = R.drawable.ic_document_filled,
    ),

    Categories(
        route = CategoriesScreen,
        notSelectedIcon = R.drawable.ic_menu_circle_outlined,
        selectedIcon = R.drawable.ic_menu_circle_filled ,
    )

}