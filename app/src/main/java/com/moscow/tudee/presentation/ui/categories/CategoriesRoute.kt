package com.moscow.tudee.presentation.ui.categories

import androidx.navigation.NavGraphBuilder
import androidx.navigation.toRoute
import com.moscow.tudee.presentation.category.categoryScreen.CategoryScreen
import com.moscow.tudee.presentation.category.categoryTasks.CategoryTasksScreen
import com.moscow.tudee.presentation.navigation.entry.CategoriesScreen
import com.moscow.tudee.presentation.navigation.entry.CategoryTasks

import com.moscow.tudee.presentation.navigation.extensions.tudeeComposable

fun NavGraphBuilder.categoriesRoute(
    navigateToCategoryTasks: (Long) -> Unit
) {
    tudeeComposable<CategoriesScreen>{
        CategoryScreen(navigateToCategoryTasks = navigateToCategoryTasks)
    }
}

fun NavGraphBuilder.categoriesTasksRoute(

) {
    tudeeComposable<CategoryTasks>{
        val args = it.toRoute<CategoryTasks>()
        CategoryTasksScreen (args.categoryId)
    }
}
