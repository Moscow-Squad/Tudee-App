package com.moscow.tudee.presentation.util

import com.moscow.tudee.R

object PredefinedTitles {
    const val SHOPPING = "shopping"
    const val EDUCATION = "education"
    const val MEDICAL = "medical"
    const val GYM = "gym"
    const val ENTERTAINMENT = "entertainment"
    const val COOKING = "cooking"
    const val FAMILY_AND_FRIEND = "family & friend"
    const val TRAVELING = "traveling"
    const val AGRICULTURE = "agriculture"
    const val CODING = "coding"
    const val ADORATION = "adoration"
    const val FIXING_BUGS = "fixing bugs"
    const val CLEANING = "cleaning"
    const val WORK = "work"
    const val BUDGETING = "budgeting"
    const val SELF_CARE = "self-care"
    const val EVENT = "event"
    const val QURAN = "quran"
}

fun getPredefinedIconRes(title: String): Int = when (title.lowercase()) {
    PredefinedTitles.SHOPPING -> R.drawable.ic_shopping_cart
    PredefinedTitles.EDUCATION -> R.drawable.ic_book_open
    PredefinedTitles.MEDICAL -> R.drawable.ic_hospital_location
    PredefinedTitles.GYM -> R.drawable.ic_body_part_muscle
    PredefinedTitles.ENTERTAINMENT -> R.drawable.ic_baseball_bat
    PredefinedTitles.COOKING -> R.drawable.ic_chef
    PredefinedTitles.FAMILY_AND_FRIEND -> R.drawable.ic_user_multiple
    PredefinedTitles.TRAVELING -> R.drawable.ic_airplane
    PredefinedTitles.AGRICULTURE -> R.drawable.ic_plant
    PredefinedTitles.CODING -> R.drawable.ic_developer
    PredefinedTitles.ADORATION -> R.drawable.ic_quran
    PredefinedTitles.FIXING_BUGS -> R.drawable.ic_bug
    PredefinedTitles.CLEANING -> R.drawable.ic_blush_brush
    PredefinedTitles.WORK -> R.drawable.ic_briefcase
    PredefinedTitles.BUDGETING -> R.drawable.ic_money_bag
    PredefinedTitles.SELF_CARE -> R.drawable.ic_in_love
    PredefinedTitles.EVENT -> R.drawable.ic_birthday_cake
    PredefinedTitles.QURAN -> R.drawable.ic_quran
    else -> R.drawable.ic_developer
}
