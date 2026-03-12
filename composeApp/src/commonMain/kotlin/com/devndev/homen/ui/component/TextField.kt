package com.devndev.homen.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.theme.BottomGray
import com.devndev.homen.ui.theme.HomeNTheme

/**
 * 가입 및 설정 화면에서 사용되는 밑줄 스타일의 입력 필드
 */
@Composable
fun HomeNUnderlineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    maxChar: Int = 8,
    enabled: Boolean = true,
    regex: Regex? = null
) {
    Column(modifier = modifier) {
        BasicTextField(
            value = value,
            onValueChange = { input ->
                val isLengthValid = input.length <= maxChar
                val isRegexValid = regex == null || input.matches(regex)
                
                if (isLengthValid && isRegexValid) {
                    onValueChange(input)
                }
            },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            textStyle = HomeNTheme.typography.suitMedium.copy(
                fontSize = 16.sp,
                color = Color.Black
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 13.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            style = HomeNTheme.typography.suitRegular,
                            color = BottomGray,
                            fontSize = 12.sp
                        )
                    }
                    innerTextField()
                    
                    if (value.isNotEmpty()) {
                        Text(
                            text = "${value.length}/$maxChar",
                            style = HomeNTheme.typography.suitRegular,
                            color = BottomGray,
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                }
            }
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = if (value.isNotEmpty()) Color.Black else Color(0xFFE0E3EA)
        )
    }
}
