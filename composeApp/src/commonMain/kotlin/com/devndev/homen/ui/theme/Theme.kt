package com.devndev.homen.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

object HomeNTheme {
    val typography: HomeNTypography
        @Composable
        get() = LocalHomeNTypography.current
        
    val dimensions: HomeNDimensions
        @Composable
        get() = LocalDimensions.current
}

@Composable
fun HomeNTheme(
    content: @Composable () -> Unit
) {
    val typography = getTypography()
    val dimensions = HomeNDimensions()

    CompositionLocalProvider(
        LocalHomeNTypography provides typography,
        LocalDimensions provides dimensions
    ) {
        MaterialTheme(
            content = content
        )
    }
}
