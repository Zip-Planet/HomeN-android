package com.devndev.homen.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.theme.BottomGray
import com.devndev.homen.ui.theme.HomeNTheme

@Composable
fun HomeNTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    maxChar: Int = 8,
    enabled: Boolean = true,
    regex: Regex? = null
) {
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
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        textStyle = HomeNTheme.typography.suitMedium.copy(
            fontSize = 16.sp,
            color = Color.Black
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .padding(horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            style = HomeNTheme.typography.suitRegular,
                            color = BottomGray,
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }
                    innerTextField()
                }

                Text(
                    text = "${value.length}/$maxChar",
                    style = HomeNTheme.typography.suitRegular,
                    color = BottomGray,
                    fontSize = 12.sp
                )

            }
        }
    )
}
