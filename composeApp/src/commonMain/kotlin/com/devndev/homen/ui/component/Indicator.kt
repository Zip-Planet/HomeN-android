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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.theme.BackgroundGray
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
            cap = StrokeCap.Round
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

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                StepItem(
                    text = "STEP $step",
                    height = 13.dp,
                    width = 39.dp,
                    fontSize = 8.sp,
                    backgroundColor = if (isActive) Gray7C else GrayCA,
                    textColor = if (isActive) Color.White else BackgroundGray
                )

                Text(
                    text = title,
                    style = HomeNTheme.typography.suitBold,
                    fontSize = 12.sp,
                    color = if (isActive) Gray7C else GrayCA
                )
            }

            if (!isLast) {
                val isLineActive = currentStep >= step + 1
                HorizontalDashedLine(
                    modifier = Modifier
                        .weight(1f)
                        .height(13.dp)
                        .padding(2.dp),
                    color = if (isLineActive) Gray7C else GrayCA,
                    strokeWidth = 4f,
                    dashLength = 6f,
                    gapLength = 18f
                )
            }
        }
    }
}
