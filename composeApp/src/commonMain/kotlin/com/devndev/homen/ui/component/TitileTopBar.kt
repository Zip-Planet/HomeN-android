package com.devndev.homen.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.back_arrow
import org.jetbrains.compose.resources.painterResource

@Composable
fun TitleTopBar(
    title: String,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    isBackVisible: Boolean = true
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 27.dp, start = 12.dp, bottom = 8.dp, end = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isBackVisible) {
                Icon(
                    painter = painterResource(Res.drawable.back_arrow),
                    contentDescription = "back",
                    modifier = Modifier.height(9.5.dp).width(13.dp)
                )
            }
            Text(
                text = title,
                style = HomeNTheme.typography.suitExtraBold,
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }
}
