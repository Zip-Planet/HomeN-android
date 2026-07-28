package com.devndev.homen.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.theme.BottomGray
import com.devndev.homen.ui.theme.HomeNTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeNTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    maxChar: Int = 8,
    enabled: Boolean = true,
    regex: Regex? = null,
    backgroundColor: Color = Color.White
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
                    .background(backgroundColor, RoundedCornerShape(10.dp))
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

/**
 * 하단에 밑줄이 있는 텍스트 필드
 */
@Composable
fun HomeNUnderlineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    regex: Regex? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(modifier = modifier.fillMaxWidth()) {
        BasicTextField(
            value = value,
            onValueChange = { input ->
                val isRegexValid = regex == null || input.matches(regex)

                if (isRegexValid) {
                    onValueChange(input)
                }
            },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            textStyle = HomeNTheme.typography.suitMedium.copy(
                fontSize = 16.sp,
                color = Color.Black
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            style = HomeNTheme.typography.suitMedium,
                            color = BottomGray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            }
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.Black
        )
    }
}

@Composable
fun HomeNLongTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    maxChar: Int = 20,
    enabled: Boolean = true,
    regex: Regex? = null,
    height: Int = 72,
    textStyle: TextStyle = HomeNTheme.typography.suitBold
) {
    BasicTextField(
        value = value,
        onValueChange = { input ->
            if (maxChar != 0) {
                val isLengthValid = input.length <= maxChar
                val isRegexValid = regex == null || input.matches(regex)

                if (isLengthValid && isRegexValid) {
                    onValueChange(input)
                }
            } else {
                onValueChange(input)
            }
        },
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp),
        textStyle = textStyle.copy(
            fontSize = 14.sp,
            color = Color.Black
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .padding(horizontal = 15.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            style = HomeNTheme.typography.suitRegular,
                            color = BottomGray,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }
                    innerTextField()
                }
                Spacer(modifier = Modifier.width(10.dp))

                if (maxChar != 0) {
                    Text(
                        text = "${value.length}/$maxChar",
                        style = HomeNTheme.typography.suitRegular,
                        color = BottomGray,
                        fontSize = 12.sp
                    )
                }
            }
        }
    )
}

/**
 * 숫자만 입력 가능한 텍스트 필드 (단위 포함)
 */
@Composable
fun HomeNNumberTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    suffix: String = "P",
    enabled: Boolean = true,
    backgroundColor: Color = Color.White
) {
    BasicTextField(
        value = value,
        onValueChange = { input ->
            // 숫자만 허용
            if (input.all { it.isDigit() }) {
                onValueChange(input)
            }
        },
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textStyle = HomeNTheme.typography.suitMedium.copy(
            fontSize = 14.sp,
            color = Color.Black
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor, RoundedCornerShape(10.dp))
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
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }
                    innerTextField()
                }

                Text(
                    text = suffix,
                    style = HomeNTheme.typography.suitBold,
                    color = Color.Black,
                    fontSize = 14.sp
                )
            }
        }
    )
}

@Preview
@Composable
fun PreviewHomeNTextField() {
    HomeNTheme {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            HomeNLongTextField(
                value = "",
                onValueChange = {},
                hint = "청소(쓸기/닦기)",
                modifier = Modifier,
                maxChar = 20,
                enabled = true,
                regex = null,
                height = 72
            )

            HomeNNumberTextField(
                value = "",
                onValueChange = {},
                hint = "예 : 1,600",
                suffix = "P"
            )
        }
    }
}
