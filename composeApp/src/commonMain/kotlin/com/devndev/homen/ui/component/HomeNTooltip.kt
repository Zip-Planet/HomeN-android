package com.devndev.homen.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.light_bulb
import homen.composeapp.generated.resources.x_btn
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeNTooltip(
    title: String,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
    messages: List<String> = emptyList(),
) {
    val bubbleShape = RoundedCornerShape(
        topStart = 10.dp,
        topEnd = 0.dp,
        bottomEnd = 10.dp,
        bottomStart = 10.dp
    )

    Box(
        modifier = modifier
            .width(265.dp)
            .shadow(
                elevation = 20.dp,
                shape = bubbleShape,
                ambientColor = Color.Black.copy(alpha = 0.5f),
                spotColor = Color.Black.copy(alpha = 0.5f)
            )
            .background(color = Color.White, shape = bubbleShape)
    ) {
        Column(
            modifier = Modifier.padding(start = 15.dp, end = 8.dp, top = 15.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.light_bulb),
                    contentDescription = null,
                    modifier = Modifier.size(15.dp),
                    tint = Color.Black
                )

                Text(
                    text = title,
                    style = HomeNTheme.typography.suitExtraBold,
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (messages.isNotEmpty()) {
                messages.forEach { message ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier.size(15.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "·",
                                style = HomeNTheme.typography.suitExtraBold,
                                fontSize = 12.sp,
                                color = Color.Black
                            )
                        }

                        Text(
                            text = message,
                            style = HomeNTheme.typography.suitRegular.copy(lineHeight = 1.5.em),
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            color = Color.Black,
                        )
                    }
                }
            }
        }

        Icon(
            painter = painterResource(Res.drawable.x_btn),
            contentDescription = "close",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp, end = 8.dp)
                .size(10.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onCloseClick()
                },
            tint = Color.Black
        )
    }
}

@Preview()
@Composable
fun TooltipPreview() {
    HomeNTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            HomeNTooltip(
                title = "설정 후에도 수정 가능해요",
                messages = listOf(
                    "집 이름과 배경은 언제든 바꿀 수 있어요.",
                    "초대 코드는 마이페이지에서 다시 확인할 수 있습니다."
                ),
                onCloseClick = { }
            )
        }
    }
}
