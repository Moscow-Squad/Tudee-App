package com.moscow.tudee.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.domain.entity.Task
import com.moscow.tudee.presentation.component.modifier.bottomBorder
import com.moscow.tudee.presentation.designSystem.theme.Theme.colors
import com.moscow.tudee.presentation.designSystem.theme.Theme.textStyle
import com.moscow.tudee.presentation.screen.category.component.Tab
import com.moscow.tudee.presentation.screen.category.component.TabItem

data class TabItem(val label: String, val count: Int, val status: Task.Status)

@Composable
fun Tabs(
    tabs: List<TabItem>,
    selectedStatus: Task.Status,
    onTabClick: (Task.Status) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .bottomBorder(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEach { tab ->
            Tab(
                modifier = Modifier.weight(1f),
                tabLabel = tab.label,
                counter = tab.count.toString(),
                isSelected = tab.status == selectedStatus,
                onClick = { onTabClick(tab.status) }
            )
        }
    }
}

@Composable
fun Tab(
    tabLabel: String,
    counter: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    var contentWidth by remember { mutableStateOf(0.dp) }
    val underlineScale by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = tween(durationMillis = 200)
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable(
            onClick = onClick,
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.onSizeChanged { size ->
                contentWidth = with(density) { size.width.toDp() }
            }
        ) {
            Text(
                modifier = Modifier.animateContentSize(),
                text = tabLabel,
                style = if (isSelected) textStyle.label.medium else textStyle.label.small,
                color = if (isSelected) colors.title else colors.hint
            )
            AnimatedVisibility(
                visible = isSelected,
                enter = fadeIn() + slideInHorizontally(),
                exit = fadeOut() + slideOutHorizontally()
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clip(CircleShape)
                        .background(colors.surface)
                        .padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = counter,
                        style = textStyle.label.medium,
                        color = colors.body
                    )
                }
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

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun TabsPreview() {
    var selectedStatus by rememberSaveable { mutableStateOf(Task.Status.TODO) }
    val sampleTabs = listOf(
        TabItem("To Do", 23, Task.Status.TODO),
        TabItem("In progress", 14, Task.Status.IN_PROGRESS),
        TabItem("Done", 58, Task.Status.DONE)
    )
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Tabs(
            tabs = sampleTabs,
            selectedStatus = selectedStatus,
            onTabClick = { selectedStatus = it }
        )
    }
}
