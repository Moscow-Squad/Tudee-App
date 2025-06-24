package com.moscow.tudee.presentation.category.categoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.moscow.tudee.R
import com.moscow.tudee.presentation.ObserveAsEvent
import com.moscow.tudee.presentation.category.CategoriesEvents
import com.moscow.tudee.presentation.category.CategoriesScreenState
import com.moscow.tudee.presentation.category.categoryScreen.component.CategoryBottomSheet
import com.moscow.tudee.presentation.category.categoryScreen.component.CategorySnackBar
import com.moscow.tudee.presentation.component.CustomFAB
import com.moscow.tudee.presentation.designSystem.component.CategoryCard
import com.moscow.tudee.presentation.designSystem.component.TopBar
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.util.getPredefinedIconRes
import com.moscow.tudee.presentation.util.saveUriToInternalStorage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CategoryScreen(
    messageId: Int? = null,
    viewModel: CategoryViewModel = koinViewModel(),
    navigateToCategoryTasks: (Long) -> Unit
) {
    LaunchedEffect(messageId) {
        viewModel.onReturnedFromEditWithMessage(messageId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ObserveAsEvent(viewModel.uiEvent) { event ->
        when (event) {
            is CategoriesEvents.NavigateToTasks -> {
                navigateToCategoryTasks(event.categoryId)
            }
        }
    }

    CategoryContent(uiState = uiState, listener = viewModel)
}


@Composable
fun CategoryContent(
    uiState: CategoriesScreenState,
    listener: CategoriesInteractionListener
) {
    val context = LocalContext.current
    Scaffold(
        containerColor = Theme.colors.surface,
        topBar = { CategoriesTopBar() },
        floatingActionButton = { AddCategoryFAB(listener) },
    ) { padding ->
        CategoriesGrid(padding, uiState, listener)
    }

    CategorySnackBar(uiState = uiState, onHide = listener::onHideSnackBar)

    if (uiState.isAddCategoryBottomSheetShow) {
        CategoryBottomSheet(
            onConfirm = { title, iconUri ->
                iconUri
                    ?.let { context.saveUriToInternalStorage(it) }
                    ?.let { savedUri ->
                        listener.onAddCategory(
                            CategoriesScreenState.CategoryUi(
                                title = title,
                                iconUrl = savedUri.toString()
                            )
                        )
                    }
            },
            onDismiss = { listener.onHideAddCategoryBottomSheet() }
        )
    }
}

@Composable
private fun AddCategoryFAB(categoriesInteractionListener: CategoriesInteractionListener) {
    CustomFAB(
        icon = R.drawable.ic_add,
        onClick = { categoriesInteractionListener.onShowAddCategoryBottomSheet() }
    )
}

@Composable
private fun CategoriesTopBar() {
    TopBar(
        title = stringResource(R.string.categories),
        modifier = Modifier.background(Theme.colors.surfaceHigh)
    )
}

@Composable
private fun CategoriesGrid(
    padding: PaddingValues,
    uiState: CategoriesScreenState,
    listener: CategoriesInteractionListener
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(104.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
        modifier = Modifier.padding(padding),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = uiState.categories,
            key = { it.id }
        ) { category ->
            CategoryGridItem(category = category) {
                listener.onCategoryClick(it)
            }
        }
    }
}

@Composable
private fun CategoryGridItem(
    category: CategoriesScreenState.CategoryUi,
    onClick: (Long) -> Unit
) {
    val iconPainter = if (category.isPredefined) {
        painterResource(getPredefinedIconRes(category.title))
    } else {
        rememberAsyncImagePainter(category.iconUrl)
    }

    CategoryCard(
        icon = iconPainter,
        label = category.title,
        count = category.numberOfTasksInCategory,
        isPredefined = category.isPredefined,
        iconTint = Color.Unspecified,
        modifier = Modifier.clickable { onClick(category.id) }
    )
}

@Preview
@Composable
fun CategoryScreenPreview() {
    CategoryContent(
        uiState = CategoriesScreenState(
            isAddCategoryBottomSheetShow = false
        ),
        listener = object : CategoriesInteractionListener {
            override fun onCategoryClick(categoryID: Long) {}
            override fun onHideSnackBar() {}
            override fun onShowAddCategoryBottomSheet() {}
            override fun onAddCategory(categoryUi: CategoriesScreenState.CategoryUi) {}
            override fun onHideAddCategoryBottomSheet() {}
        }
    )
}
