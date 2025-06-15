package com.moscow.tudee.presentation.designSystem.typography

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.moscow.tudee.R

val DefaultTextStyle = TudeeTextStyle(
    headline = SizedTextStyle(
        large = TextStyle(
            fontSize = 28.sp,
            lineHeight = 30.sp,
            fontFamily = FontFamily(Font(R.font.nunito)),
            fontWeight = FontWeight.SemiBold
        ),
        medium = TextStyle(
            fontSize = 24.sp,
            lineHeight = 28.sp,
            fontFamily = FontFamily(Font(R.font.nunito)),
            fontWeight = FontWeight.SemiBold
        ),
        small = TextStyle(
            fontSize = 20.sp,
            lineHeight = 24.sp,
            fontFamily = FontFamily(Font(R.font.nunito)),
            fontWeight = FontWeight.SemiBold
        )

    ),
    title = SizedTextStyle(
        large = TextStyle(
            fontSize = 20.sp,
            lineHeight = 24.sp,
            fontFamily = FontFamily(Font(R.font.nunito)),
            fontWeight = FontWeight.Medium
        ),
        medium = TextStyle(
            fontSize = 18.sp,
            lineHeight = 22.sp,
            fontFamily = FontFamily(Font(R.font.nunito)),
            fontWeight = FontWeight.Medium
        ),
        small = TextStyle(
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontFamily = FontFamily(Font(R.font.nunito)),
            fontWeight = FontWeight.Medium
        )
    ),
    body = SizedTextStyle(
        large = TextStyle(
            fontSize = 18.sp,
            lineHeight = 22.sp,
            fontFamily = FontFamily(Font(R.font.nunito)),
            fontWeight = FontWeight.Normal
        ),
        medium = TextStyle(
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontFamily = FontFamily(Font(R.font.nunito)),
            fontWeight = FontWeight.Normal
        ),
        small = TextStyle(
            fontSize = 14.sp,
            lineHeight = 17.sp,
            fontFamily = FontFamily(Font(R.font.nunito)),
            fontWeight = FontWeight.Normal
        )
    ),
    label = SizedTextStyle(
        large = TextStyle(
            fontSize = 16.sp,
            lineHeight = 19.sp,
            fontFamily = FontFamily(Font(R.font.nunito)),
            fontWeight = FontWeight.Medium
        ),
        medium = TextStyle(
            fontSize = 14.sp,
            lineHeight = 17.sp,
            fontFamily = FontFamily(Font(R.font.nunito)),
            fontWeight = FontWeight.Medium
        ),
        small = TextStyle(
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = FontFamily(Font(R.font.nunito)),
            fontWeight = FontWeight.Medium
        )
    )
)