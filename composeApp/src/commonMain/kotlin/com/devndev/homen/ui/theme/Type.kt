package com.devndev.homen.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.suit_variable
import org.jetbrains.compose.resources.Font

@Immutable
data class HomeNTypography(
    val suitRegular: TextStyle,
    val suitMedium: TextStyle,
    val suitBold: TextStyle,
    val suitExtraBold: TextStyle
)

@Composable
fun getTypography(): HomeNTypography {
    val suitFamily = FontFamily(
        Font(Res.font.suit_variable, FontWeight.Normal)
    )

    val baseStyle = TextStyle(
        fontFamily = suitFamily,
        fontSize = 24.sp,
        lineHeight = 1.32.em,
        letterSpacing = (-0.02).em
    )

    return HomeNTypography(
        suitRegular = baseStyle.copy(fontWeight = FontWeight.W400),
        suitMedium = baseStyle.copy(fontWeight = FontWeight.W500),
        suitBold = baseStyle.copy(fontWeight = FontWeight.W700),
        suitExtraBold = baseStyle.copy(fontWeight = FontWeight.W800)
    )
}

val LocalHomeNTypography = staticCompositionLocalOf {
    HomeNTypography(
        suitRegular = TextStyle.Default,
        suitMedium = TextStyle.Default,
        suitBold = TextStyle.Default,
        suitExtraBold = TextStyle.Default
    )
}
