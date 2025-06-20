package com.moscow.tudee.presentation.category.categoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.ObserveAsEvent
import com.moscow.tudee.presentation.category.categoryScreen.component.AddCategoryBottomSheet
import com.moscow.tudee.presentation.component.bottomSheet.DeleteBottomSheet
import com.moscow.tudee.presentation.designSystem.component.CategoryCard
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors
import com.moscow.tudee.presentation.designSystem.theme.Theme.textStyle
import com.moscow.tudee.presentation.util.getPredefinedIconRes
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = koinViewModel(),
    navigateToCategoryTasks : (Long) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    ObserveAsEvent(viewModel.uiEvent) { event ->
        when (event) {
            is CategoriesEvents.NavigateToTasks -> {
               navigateToCategoryTasks( event.categoryId)
            }
        }

    }
    CategoryContent(
        uiState.value,
        viewModel
    )
}

@Composable
fun CategoryContent(
    uiState: CategoriesScreenState,
    categoriesInteractionListener: CategoriesInteractionListener,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.surface)
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(colors.surfaceHigh),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text( // Should be replace to topBar?
                text = "Categories",
                style = textStyle.title.large,
                color = colors.title
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(uiState.categories) { currentCategory ->
                if(currentCategory.isPredefined){
                    CategoryCard(
                        icon = painterResource(getPredefinedIconRes(currentCategory.title)),
                        label = currentCategory.title,
                        count = currentCategory.numberOfTasksInCategory,
                        iconTint = Color.Unspecified,
                        modifier = Modifier.clickable(onClick = {
                            categoriesInteractionListener.onCategoryClick(currentCategory.id)
                        })
                    )
                }else {
                    CategoryCard(
                        icon = rememberAsyncImagePainter(currentCategory.iconUrl),
                        label = currentCategory.title,
                        count = currentCategory.numberOfTasksInCategory,
                        iconTint = Color.Unspecified,
                        modifier = Modifier.clickable(onClick = {
                            categoriesInteractionListener.onCategoryClick(currentCategory.id)
                        })
                    )
                }

            }
        }
        Button(onClick = { categoriesInteractionListener.onShowAddCategoryBottomSheet() }) {
            if (uiState.isAddCategoryBottomSheetShow)
                AddCategoryBottomSheet(
                    onNewCategory = { categoriesInteractionListener.onHideAddCategoryBottomSheet()},
                    onDismissRequest = { categoriesInteractionListener.onHideAddCategoryBottomSheet()},
                )
        }
    }
}

@Preview
@Composable
fun CategoryScreenPreview() {
    CategoryContent(
        uiState = CategoriesScreenState(

            isAddCategoryBottomSheetShow = false
        ),
        categoriesInteractionListener = object : CategoriesInteractionListener {

            override fun onCategoryClick(categoryID: Long) {
                TODO("Not yet implemented")
            }


            override fun onShowAddCategoryBottomSheet() {}
            override fun onHideAddCategoryBottomSheet() {
                TODO("Not yet implemented")
            }


        }
    )
}
