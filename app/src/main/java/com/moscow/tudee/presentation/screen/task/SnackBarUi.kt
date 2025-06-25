package com.moscow.tudee.presentation.screen.task

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable

@Stable
data class SnackBarUi(
    val type: SnackBarType,
    @StringRes val messageId: Int
)
