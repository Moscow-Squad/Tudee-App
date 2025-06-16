package com.moscow.tudee.presentation.designSystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.presentation.designSystem.component.modifier.bottomBorder
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors
import com.moscow.tudee.presentation.designSystem.theme.Theme.textStyle

@Composable
fun Tabs(
    tabs: List<Tab>,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .bottomBorder(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEachIndexed { index, tabData ->
            TabItem(
                tabLabel = tabData.label,
                counter = tabData.count.toString(),
                isSelected = index == selectedTabIndex,
                onClick = { selectedTabIndex = index }
            )
        }
    }
}

@Composable
fun TabItem(
    tabLabel: String,
    counter : String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    var contentWidth by remember { mutableStateOf(0.dp) }

    val underlineScale by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = tween(durationMillis = 200),
        label = ""
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.onSizeChanged { size ->
                contentWidth = with(density) { size.width.toDp() }
            }
        ) {
            Text(
                text = tabLabel,
                style = if (isSelected) textStyle.label.medium else textStyle.label.small,
                color = if (isSelected) colors.title else colors.hint
            )

            AnimatedVisibility(
                visible = isSelected,
                enter = fadeIn() + slideInHorizontally(),
                exit = fadeOut() + slideOutHorizontally()
            ) {
                Text(
                    text = counter,
                    style = textStyle.label.medium,
                    color = colors.body,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .width(contentWidth)
                .height(4.dp)
                .graphicsLayer { scaleX = underlineScale }
                .background(
                    color = colors.secondary,
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
        )
    }
}

data class Tab(val label: String, val count: Int)

@Preview(showBackground = true)
@Composable
fun TabPreview() {
    val sampleTabs = listOf(
        Tab(label = "In progress", count = 14),
        Tab(label = "To Do", count = 23),
        Tab(label = "Done", count = 58)
    )
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Tabs(tabs = sampleTabs)
    }
}