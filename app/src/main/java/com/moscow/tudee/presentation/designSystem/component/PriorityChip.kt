package com.moscow.tudee.presentation.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.R

@Composable
fun PriorityChip(
    text: String,
    backgroundColor: Color,
    icon: Painter,
    contentColor: Color = Color.White
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = "priority icon",
            tint = contentColor,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = text,
            color = contentColor,
            style = Theme.textStyle.label.small,
        )
    }
}

@Composable
fun HighPriorityChip() {
    PriorityChip(
        text = "High",
        backgroundColor = Theme.colors.pinkAccent,
        icon = painterResource(id = R.drawable.flag)
    )
}

@Composable
fun MediumPriorityChip() {
    PriorityChip(
        text = "Medium",
        backgroundColor = Theme.colors.yellowAccent,
        icon = painterResource(id = R.drawable.alert)
    )
}

@Composable
fun LowPriorityChip() {
    PriorityChip(
        text = "Low",
        backgroundColor = Theme.colors.greenAccent,
        icon = painterResource(id = R.drawable.trade_down)
    )
}


@Preview(showBackground = true)
@Composable
fun PriorityChipPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        HighPriorityChip()
        MediumPriorityChip()
        LowPriorityChip()
    }
}
