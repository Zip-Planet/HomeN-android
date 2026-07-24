package com.devndev.homen.ui.main.assignment.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.navigate_before_icon
import homen.composeapp.generated.resources.navigate_next_icon
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HistoryWeekSelector(
    weekOffset: Int,
    onWeekSelected: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = Modifier.width(52.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (weekOffset > 1) {
                Icon(
                    painter = painterResource(Res.drawable.navigate_before_icon),
                    contentDescription = "Older",
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onWeekSelected(weekOffset - 1) },
                    tint = Color.Black
                )
            } else {
                Spacer(modifier = Modifier.width(5.dp))
            }

            Text(
                text = "${weekOffset}주 전",
                style = HomeNTheme.typography.suitSemiBold,
                fontSize = 12.sp,
                color = Color.Black
            )

            if (weekOffset < 4) {
                Icon(
                    painter = painterResource(Res.drawable.navigate_next_icon),
                    contentDescription = "Newer",
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onWeekSelected(weekOffset + 1) },
                    tint = Color.Black
                )
            } else {
                Spacer(modifier = Modifier.width(5.dp))
            }
        }
        Row() {
            val width = if (weekOffset in 2..<4)  {
                52
            } else if (weekOffset == 4){
                42
            } else {
                40
            }
            if (weekOffset == 1) {
                Spacer(modifier = Modifier.width(10.dp))
            }
            HorizontalDivider(
                thickness = 0.5.dp,
                color = Color.Black,
                modifier = Modifier.width(width.dp)
            )

            if (weekOffset == 4) {
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@Preview
@Composable
fun HistoryWeekSelectorPreview() {
    HomeNTheme {
        HistoryWeekSelector(
            weekOffset = 1,
            onWeekSelected = {}
        )
    }
}
