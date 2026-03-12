package com.devndev.homen.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
 * 집 생성 등 단계가 있는 화면에서 사용하는 전체 인디케이터
 * @param currentStep 현재 활성화된 단계 (1, 2, 3)
 */
@Composable
fun HomeNStepIndicator(
    currentStep: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp) // 명세의 gap: 3px 반영
    ) {
        // Step 1
        StepItem(
            text = "STEP 1",
            backgroundColor = if (currentStep >= 1) Color.Black else Color(0xFFE0E3EA),
            textColor = if (currentStep >= 1) Color.White else Color(0xFFB2B2B2)
        )

        // 연결 점들 (....)
        Text(
            text = "······",
            color = Color(0xFFE0E3EA),
            fontSize = 10.sp,
            modifier = Modifier.padding(horizontal = 2.dp)
        )

        // Step 2
        StepItem(
            text = "STEP 2",
            backgroundColor = if (currentStep >= 2) Color.Black else Color(0xFFE0E3EA),
            textColor = if (currentStep >= 2) Color.White else Color(0xFFB2B2B2)
        )

        // 연결 점들 (....)
        Text(
            text = "······",
            color = Color(0xFFE0E3EA),
            fontSize = 10.sp,
            modifier = Modifier.padding(horizontal = 2.dp)
        )

        // Step 3
        StepItem(
            text = "STEP 3",
            backgroundColor = if (currentStep >= 3) Color.Black else Color(0xFFE0E3EA),
            textColor = if (currentStep >= 3) Color.White else Color(0xFFB2B2B2)
        )
    }
}
