package com.moscow.tudee.presentation.category

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors
import com.moscow.tudee.presentation.designSystem.theme.Theme.textStyle
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.component.CategoryCard

data class Category(val name: String, val count: Int, val icon: Int)

@Composable
fun CategoriesScreen(
    categories: List<Category>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.surface)
            .padding(16.dp)
    ) {
        // TopBar, FAB and BottomNavBar ????
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
            items(categories.size) { currentCategory ->
                CategoryCard(
                    icon = painterResource(categories[currentCategory].icon),
                    label = categories[currentCategory].name,
                    count = categories[currentCategory].count,
                    iconTint = Color.Unspecified,
                    modifier = Modifier.clickable(onClick = {})
                )
            }
        }
    }
}


@Preview(showBackground = true, apiLevel = 34, showSystemUi = true)
@Composable
fun CategoriesScreenPreview() {
    val categories: List<Category> = listOf(
        Category(name = "Education", count = 23, icon = R.drawable.ic_book_open),
        Category(name = "Shopping", count = 4, icon = R.drawable.ic_shopping_cart),
        Category(name = "Medical", count = 23, icon = R.drawable.ic_hospital_location),
        Category(name = "Gym", count = 1, icon = R.drawable.ic_body_part_muscle),
        Category(name = "Entertainment", count = 52, icon = R.drawable.ic_baseball_bat),
        Category(name = "Cooking", count = 23, icon = R.drawable.ic_chef),
        Category(name = "Family & Friends", count = 23, icon = R.drawable.ic_user_multiple),
        Category(name = "Traveling", count = 0, icon = R.drawable.ic_airplane),
        Category(name = "Agriculture", count = 23, icon = R.drawable.ic_plant),
        Category(name = "Coding", count = 23, icon = R.drawable.ic_developer),
        Category(name = "Adoration", count = 23, icon = R.drawable.ic_quran),
        Category(name = "Fixing Bugs", count = 23, icon = R.drawable.ic_bug),
        Category(name = "Cleaning", count = 23, icon = R.drawable.ic_blush_brush),
        Category(name = "Work", count = 23, icon = R.drawable.ic_briefcase),
        Category(name = "Budgeting", count = 23, icon = R.drawable.ic_money_bag),
        Category(name = "Self-care", count = 23, icon = R.drawable.ic_in_love),
        Category(name = "Event", count = 1, icon = R.drawable.ic_birthday_cake),
        Category(name = "Self-care", count = 23, icon = R.drawable.ic_in_love),
        Category(name = "Self-care", count = 44, icon = R.drawable.ic_in_love),
        Category(name = "Self-care", count = 23, icon = R.drawable.ic_in_love),
        Category(name = "Self-care", count = 23, icon = R.drawable.ic_in_love),
        Category(name = "Self-care", count = 23, icon = R.drawable.ic_in_love),
        Category(name = "Self-care", count = 23, icon = R.drawable.ic_in_love),
        Category(name = "Self-care", count = 23, icon = R.drawable.ic_in_love),
    )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CategoriesScreen(categories)
    }
}