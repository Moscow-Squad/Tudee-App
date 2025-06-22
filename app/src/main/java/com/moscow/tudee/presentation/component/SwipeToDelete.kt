import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme
import kotlinx.coroutines.delay


@Composable
fun SwipeToDeleteItem(
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    animationDuration: Long = 100L,
    icon: Int = R.drawable.ic_delete,
    backgroundColor: Color = Theme.colors.errorVariant,
    content: @Composable () -> Unit
) {
    val swipeState = rememberSwipeToDismissBoxState()
    var visible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = visible, modifier = modifier, exit = shrinkVertically() + fadeOut()
    ) {
        SwipeToDismissBox(
            state = swipeState, enableDismissFromStartToEnd = false, backgroundContent = {
                SwipeBackground(
                    icon = icon, backgroundColor = backgroundColor
                )
            }) {
            content()
        }
    }

    if (swipeState.currentValue == SwipeToDismissBoxValue.EndToStart && visible) {
        visible = false
        LaunchedEffect(Unit) {
            delay(animationDuration)
            onDelete()
        }
    }
}

@Composable
private fun SwipeBackground(
    icon: Int, backgroundColor: Color
) {
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Icon(
            modifier = Modifier.padding(end = 12.dp),
            imageVector = ImageVector.vectorResource(icon),
            contentDescription = stringResource(R.string.delete_item),
            tint = Theme.colors.error,
        )
    }
}

