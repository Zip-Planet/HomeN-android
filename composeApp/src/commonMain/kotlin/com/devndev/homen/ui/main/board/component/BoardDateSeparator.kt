package com.devndev.homen.ui.main.board.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.theme.Gray808080
import com.devndev.homen.ui.theme.Gray8E8E8E
import com.devndev.homen.ui.theme.GrayE7
import com.devndev.homen.ui.theme.HomeNTheme

@Composable
fun BoardDateSeparator(
    date: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .weight(1f)
                .height(0.5.dp)
                .background(Gray808080)
        )
        Text(
            text = date,
            modifier = Modifier.padding(horizontal = 21.dp),
            style = HomeNTheme.typography.suitMedium,
            fontSize = 12.sp,
            color = Gray808080
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
                .height(0.5.dp)
                .background(Gray808080)
        )
    }
}
