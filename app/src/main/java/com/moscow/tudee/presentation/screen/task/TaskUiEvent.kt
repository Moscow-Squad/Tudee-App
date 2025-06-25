package com.moscow.tudee.presentation.screen.task
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme

sealed interface TaskUiEvent {
    data class ShowSnackBar(
        val snackBarUi: SnackBarUi
    ) : TaskUiEvent
}
data class SnackBar(
    val message: String,
    @DrawableRes val icon: Int,
    val iconBackground: Color,
    val iconTint: Color
)
enum class SnackBarType {
    SUCCESS, ERROR
}

@Composable
fun getSnackBarVisuals(type: SnackBarType): SnackBar {
    return when (type) {
        SnackBarType.SUCCESS -> SnackBar(
            icon = R.drawable.ic_checkmark_badge,
            iconBackground = Theme.colors.greenVariant,
            iconTint = Theme.colors.greenAccent,
            message = stringResource(R.string.deleted_task_successfully),
        )

        SnackBarType.ERROR -> SnackBar(
            icon = R.drawable.ic_information_diamond,
            iconBackground = Theme.colors.errorVariant,
            iconTint = Theme.colors.error,
            message = stringResource(R.string.some_error_happened),
        )
    }
}
