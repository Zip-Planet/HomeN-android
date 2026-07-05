package com.devndev.homen.ui.main.board.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.component.Dot
import com.devndev.homen.ui.theme.BlueCAEAFC
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.binocular_icon
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun NormalMessage(
    drawableResource: DrawableResource,
    title: String,
    messages: List<String>,
    isMine: Boolean
) {
    val backgroundColor = if (isMine) BlueCAEAFC else Color.White
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .padding(horizontal = 15.dp, vertical = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(drawableResource),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = Color.Black
            )
            Text(
                text = title,
                style = HomeNTheme.typography.suitExtraBold,
                fontSize = 16.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(13.dp))

        messages.forEachIndexed { index, message ->
            MessageItem(message)
            if (index != messages.lastIndex) {
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

@Composable
fun MessageItem(message: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Dot(
            width = 18,
            dotSize = 3
        )

        Text(
            text = message,
            style = HomeNTheme.typography.suitRegular,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Preview
@Composable
fun NormalMessagePreview() {
    NormalMessage(
        drawableResource = Res.drawable.binocular_icon,
        title = "페어봇 시스템 카드 4종",
        messages = listOf("주간 리포트", "분담안 제안", "분담안 확정", "리워드 달성"),
        isMine = true
    )
}