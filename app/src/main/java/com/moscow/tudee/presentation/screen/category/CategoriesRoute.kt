package com.moscow.tudee.presentation.screen.category

import androidx.navigation.NavGraphBuilder
import androidx.navigation.toRoute
import com.moscow.tudee.presentation.screen.category.categoriesScreen.CategoryScreen
import com.moscow.tudee.presentation.screen.category.categoryTasksScreen.CategoryTasksScreen
import com.moscow.tudee.presentation.navigation.entry.BottomNavigationType
import com.moscow.tudee.presentation.navigation.entry.CategoriesScreen
import com.moscow.tudee.presentation.navigation.extensions.tudeeComposable
import kotlinx.serialization.Serializable

fun NavGraphBuilder.categoriesRoute(
    navigateToCategoryTasks: (Long) -> Unit
) {
    tudeeComposable<CategoriesScreen> { backStackEntry ->
        val args = backStackEntry.toRoute<Categories>()
        val result = backStackEntry.savedStateHandle.remove<Int>("result_message")

        CategoryScreen(
            messageId = result ?: args.messageId,
            navigateToCategoryTasks = navigateToCategoryTasks
        )
    }
}

fun NavGraphBuilder.categoryTasksRoute(
    isBottomNavigationType:(Boolean)-> Unit,
    navigateBackToCategoryScreen: (Int?) -> Unit,
    navigateBack: () -> Unit
) {
    tudeeComposable<CategoryTasks> {
        val args = it.toRoute<CategoryTasks>()
        isBottomNavigationType(CategoryTasks.isBottomNavigationVisible)
        CategoryTasksScreen(
            args.categoryId,
            navigateBack = navigateBack,
            navigateBackToCategoryScreen = navigateBackToCategoryScreen
        )
    }
}

@Serializable
data class CategoryTasks(
    val categoryId: Long
) {
    companion object:BottomNavigationType {
        override val isBottomNavigationVisible: Boolean
            get() = false

    }
}

@Serializable
data class Categories(
    val messageId: Int? = null
)

