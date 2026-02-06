package com.devndev.homen.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

object HomeNTheme {
    val typography: HomeNTypography
        @Composable
        get() = LocalHomeNTypography.current
}

@Composable
fun HomeNTheme(
    content: @Composable () -> Unit
) {
    val typography = getTypography()

    CompositionLocalProvider(
        LocalHomeNTypography provides typography
    ) {
        MaterialTheme(
            content = content
        )
    }
}