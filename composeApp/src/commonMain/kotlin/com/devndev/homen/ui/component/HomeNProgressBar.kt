package com.devndev.homen.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.Blue4736FC

@Composable
fun HomeNProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    height: Dp = 14.dp,
    color: Color = Blue4736FC,
    trackColor: Color = BackgroundGray
) {
    val shape = RoundedCornerShape(99.dp)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(color = trackColor, shape = shape)
            .clip(shape)
    ) {
        if (progress > 0f) {
            Box(
                modifier = Modifier
                    .padding(3.dp)
                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .background(color = color, shape = shape)
            )
        }
    }
}
