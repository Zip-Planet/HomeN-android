package com.devndev.homen.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.BottomGray
import com.devndev.homen.ui.theme.ButtonGray
import com.devndev.homen.ui.theme.Gray7C
import com.devndev.homen.ui.theme.GrayCA
import com.devndev.homen.ui.theme.HomeNTheme

@Composable
fun StepItem(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    width: Dp = 43.dp,
    height: Dp = 17.dp,
    fontSize: TextUnit = 10.sp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(width = width, height = height)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(28.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = HomeNTheme.typography.suitBold,
            fontSize = fontSize,
            color = textColor,
            maxLines = 1
        )
    }
}

/**
 * 가로 점선 컴포넌트
 */
@Composable
fun HorizontalDashedLine(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFE0E3EA),
    strokeWidth: Float = 5f,
    dashLength: Float = 10f,
    gapLength: Float = 10f
) {
    Canvas(modifier = modifier) {
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, gapLength), 0f)
        drawLine(
            color = color,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            pathEffect = pathEffect,
            strokeWidth = strokeWidth,
        )
    }
}

@Composable
fun HomeNStepIndicator(
    currentStep: Int,
    stepTitles: List<String>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top, // 텍스트가 아래로 배치되므로 상단 정렬
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        stepTitles.forEachIndexed { index, title ->
            val step = index + 1
            val isActive = currentStep >= step
            val isLast = index == stepTitles.size - 1

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val backgroundColor = if (isActive) Color.Black else BottomGray
                val textColor = if (isActive) Color.White else ButtonGray

                Box(
                    modifier = Modifier
                        .size(13.dp)
                        .clip(CircleShape)
                        .background(color = backgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = step.toString(),
                        style = HomeNTheme.typography.suitBold,
                        fontSize = 10.sp,
                        color = textColor
                    )
                }

                Text(
                    text = title,
                    style = HomeNTheme.typography.suitBold,
                    fontSize = 12.sp,
                    color = backgroundColor
                )
            }

            if (!isLast) {
                val isLineActive = currentStep >= step + 1
                HorizontalDashedLine(
                    modifier = Modifier
                        .weight(1f)
                        .height(13.dp)
                        .padding(6.dp),
                    color = if (isLineActive) Gray7C else GrayCA,
                    strokeWidth = 4f,
                    dashLength = 10f,
                    gapLength = 5f
                )
            }
        }
    }
}
