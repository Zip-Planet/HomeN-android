package com.devndev.homen.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devndev.homen.OsType
import com.devndev.homen.getPlatform

/**
 * 플랫폼별 패딩 정의
 */
data class HomeNDimensions(
    val topPadding: Dp = 42.dp,
    val horizontalPadding: Dp = 17.dp,
    val bottomPadding: Dp = if (getPlatform() == OsType.IOS) 0.dp else 34.dp
)

val LocalDimensions = staticCompositionLocalOf { HomeNDimensions() }
