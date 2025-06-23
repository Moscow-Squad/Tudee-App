package com.moscow.tudee.presentation.category.categoryScreen

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
import com.moscow.tudee.presentation.component.CustomFAB
import com.moscow.tudee.presentation.designSystem.component.CategoryCard
import com.moscow.tudee.presentation.designSystem.component.ErrorSnackBar
import com.moscow.tudee.presentation.designSystem.component.SuccessSnackBar
import com.moscow.tudee.presentation.designSystem.component.TopBar
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.util.getPredefinedIconRes
import com.moscow.tudee.presentation.util.saveUriToInternalStorage
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = koinViewModel(),
    navigateToCategoryTasks: (Long) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    ObserveAsEvent(viewModel.uiEvent) { event ->
        when (event) {
            is CategoriesEvents.NavigateToTasks -> {
                navigateToCategoryTasks(event.categoryId)
            }
        }
    }
    CategoryContent(
        uiState.value, viewModel
    )
}

@Composable
fun CategoryContent(
    uiState: CategoriesScreenState,
    listener: CategoriesInteractionListener,
) {
    Scaffold(
        topBar = {
            CategoriesTopBar()
        },
        floatingActionButton = {
            AddCategoryFAB(listener)
        },
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(vertical = 12.dp),
            modifier = Modifier.padding(padding),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(
                items = uiState.categories, key = { it.id }
            ) { currentCategory ->
                val iconPainter = if (currentCategory.isPredefined) {
                    painterResource(getPredefinedIconRes(currentCategory.title))
                } else {
                    rememberAsyncImagePainter(currentCategory.iconUrl)
                }

                CategoryCard(
                    icon = iconPainter,
                    label = currentCategory.title,
                    count = currentCategory.numberOfTasksInCategory,
                    iconTint = Color.Unspecified,
                    modifier = Modifier.clickable {
                        listener.onCategoryClick(currentCategory.id)
                    })
            }
        }
    }

    if (uiState.isSnackBarShow) {
        val icon = if (uiState.successMessage != null) {
            painterResource(id = R.drawable.ic_checkmark_badge)
        } else {
            painterResource(id = R.drawable.ic_information_diamond)
        }

        val messageId = uiState.successMessage ?: uiState.errorMessage
        messageId?.let {
            if (uiState.successMessage != null) {
                SuccessSnackBar(
                    message = stringResource(id = it)
                )
            } else {
                ErrorSnackBar(
                    message = stringResource(id = it)
                )
            }
        }

            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(3000)
                listener.onHideSnackBar()
            }
        }


    val context = LocalContext.current

    if (uiState.isAddCategoryBottomSheetShow)
        CategoryBottomSheet(
            onConfirm = { title, iconUri ->
                if (iconUri != null) {
                    val savedUri = context.saveUriToInternalStorage(iconUri)
                    if (savedUri != null) {
                        listener.onAddCategory(
                            CategoriesScreenState.CategoryUi(
                                title = title,
                                iconUrl = savedUri.toString(),
                            )
                        )
                    }
                }
            },
            onDismiss = { listener.onHideAddCategoryBottomSheet() },
        )

}

@Composable
private fun AddCategoryFAB(categoriesInteractionListener: CategoriesInteractionListener) {
    CustomFAB(
        icon = R.drawable.ic_add,
        onClick = { categoriesInteractionListener.onShowAddCategoryBottomSheet() })
}

@Composable
private fun CategoriesTopBar() {
    TopBar(
        title = "Categories",
    )
}


@Preview
@Composable
fun CategoryScreenPreview() {
    CategoryContent(
        uiState = CategoriesScreenState(

            isAddCategoryBottomSheetShow = false
        ), listener = object : CategoriesInteractionListener {

            override fun onCategoryClick(categoryID: Long) {
                TODO("Not yet implemented")
            }

            override fun onHideSnackBar() {
                TODO("Not yet implemented")
            }

            override fun onShowAddCategoryBottomSheet() {}
            override fun onAddCategory(category: CategoriesScreenState.CategoryUi) {
                TODO("Not yet implemented")
            }

            override fun onHideAddCategoryBottomSheet() {
                TODO("Not yet implemented")
            }
        })
}
