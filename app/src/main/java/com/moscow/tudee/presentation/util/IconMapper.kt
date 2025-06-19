package com.moscow.tudee.presentation.util

import com.moscow.tudee.R

fun getPredefinedIconRes(title: String): Int = when (title.lowercase()) {
    "quran" -> R.drawable.ic_quran
    "shopping" -> R.drawable.ic_shopping_cart
    "education" -> R.drawable.ic_book_open
    else -> R.drawable.ic_chef
}