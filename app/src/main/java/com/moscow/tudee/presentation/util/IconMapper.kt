package com.moscow.tudee.presentation.util

import com.moscow.tudee.R

fun getPredefinedIconRes(title: String): Int = when (title.lowercase()) {
    "shopping" -> R.drawable.ic_shopping_cart
    "education" -> R.drawable.ic_book_open
    "medical" -> R.drawable.ic_hospital_location
    "gym" -> R.drawable.ic_body_part_muscle
    "entertainment" -> R.drawable.ic_baseball_bat
    "cooking" -> R.drawable.ic_chef
    "family & friend" -> R.drawable.ic_user_multiple
    "traveling" -> R.drawable.ic_airplane
    "agriculture" -> R.drawable.ic_plant
    "coding" -> R.drawable.ic_developer
    "adoration" -> R.drawable.ic_quran
    "fixing bugs" -> R.drawable.ic_bug
    "cleaning" -> R.drawable.ic_blush_brush
    "work" -> R.drawable.ic_briefcase
    "budgeting" -> R.drawable.ic_money_bag
    "self-care" -> R.drawable.ic_in_love
    "event" -> R.drawable.ic_birthday_cake
    else -> R.drawable.ic_developer
}
