package com.moscow.tudee.presentation.task.calender
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.tudee.R
import com.moscow.tudee.presentation.designSystem.theme.Theme
import com.moscow.tudee.presentation.designSystem.theme.TudeeTheme

@Composable
fun Header(
    date : String = "May 2023"
) {
    Row (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
        ,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_head_back),
                contentDescription = "Previous",
                modifier = Modifier
                    .border(1.dp, Theme.colors.stroke, CircleShape)
                    .padding(10.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = date,
                style = Theme.textStyle.label.medium,
                )
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_head_back),
                    contentDescription = "down",
                    modifier = Modifier
                        .rotate(270f)
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .size(16.dp)
                )
            }
        }
        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_head_back),
                contentDescription = "Next",
                modifier = Modifier
                    .rotate(180f)
                    .border(1.dp, Theme.colors.stroke, CircleShape)
                    .padding(10.dp)

            )
        }
    }
}

@Preview(showBackground = true, apiLevel = 33)
@Composable
private fun HeaderPreview() {
    TudeeTheme {
        Header()
    }

}
