package com.moscow.tudee.presentation.designSystem.color

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val lightThemeColors = TudeeColors(
    primary = Color(0xFF49BAF2),
    secondary = Color(0xFFF49061),
    primaryVariant = Color(0xFFEFF9FE),
    primaryGradient = Brush.linearGradient(
        colors = listOf( Color(0xFF3A9CCD),Color(0xFF49BAF2))
    ),
    title = Color(0xDE1F1F1F),
    body = Color(0x991F1F1F),
    hint = Color(0x611F1F1F),
    stroke = Color(0x1F1F1F1F),
    surfaceLow = Color(0xFFF0F0F0),
    surface = Color(0xFFF9F9F9),
    surfaceHigh = Color(0xFFFFFFFF),
    onPrimary = Color(0xDEFFFFFF),
    onPrimaryCaption = Color(0xB2FFFFFF),
    onPrimaryCard = Color(0x29FFFFFF),
    onPrimaryStroke = Color(0x99FFFFFF),
    disable = Color(0xFFE8EBED),
    pinkAccent = Color(0xFFF4869A),
    yellowAccent = Color(0xFFF2C849),
    yellowVariant = Color(0xFFF7F2E4),
    greenAccent = Color(0xFF76C499),
    greenVariant = Color(0xFFE4F2EA),
    purpleAccent = Color(0xFF9887F5),
    purpleVariant = Color(0xFFEEEDF7),
    error = Color(0xFFE55C5C),
    errorVariant = Color(0xFFFCE8E8),
    overlay = Color(0x5249BAF2),
    emojiTint = Color(0xDE1F1F1F)
)