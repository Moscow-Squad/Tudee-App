package com.moscow.tudee.presentation.ui.categories

import androidx.navigation.NavGraphBuilder
import androidx.navigation.toRoute
import com.moscow.tudee.presentation.category.categoryScreen.CategoryScreen
import com.moscow.tudee.presentation.category.categoryTasks.CategoryTasksScreen
import com.moscow.tudee.presentation.navigation.entry.CategoriesScreen
import com.moscow.tudee.presentation.navigation.entry.CategoryTasks

import com.moscow.tudee.presentation.navigation.entry.TudeeAppBar
import com.moscow.tudee.presentation.navigation.extensions.tudeeComposable

fun NavGraphBuilder.categoriesRoute(
    appBar: (TudeeAppBar) -> Unit,
    isBottomNavigationVisible: (Boolean) -> Unit,
    navigateToCategoryTasks: (Long) -> Unit
) {
    tudeeComposable<CategoriesScreen> {
        CategoryScreen(navigateToCategoryTasks = navigateToCategoryTasks)

    }
    tudeeComposable<CategoryTasks> {
        val args = it.toRoute<CategoryTasks>()
        CategoryTasksScreen(args.categoryId)
    }
}
