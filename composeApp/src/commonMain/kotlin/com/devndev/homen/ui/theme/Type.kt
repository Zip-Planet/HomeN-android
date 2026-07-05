package com.devndev.homen.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.suit_variable
import org.jetbrains.compose.resources.Font

@Immutable
data class HomeNTypography(
    val suitLight: TextStyle,
    val suitRegular: TextStyle,
    val suitMedium: TextStyle,
    val suitSemiBold: TextStyle,
    val suitBold: TextStyle,
    val suitExtraBold: TextStyle,
    val suitHeavy: TextStyle
)

@Composable
fun getTypography(): HomeNTypography {
    val suitFamily = FontFamily(
        Font(Res.font.suit_variable, FontWeight.W300),
        Font(Res.font.suit_variable, FontWeight.W400),
        Font(Res.font.suit_variable, FontWeight.W500),
        Font(Res.font.suit_variable, FontWeight.W600),
        Font(Res.font.suit_variable, FontWeight.W700),
        Font(Res.font.suit_variable, FontWeight.W800),
        Font(Res.font.suit_variable, FontWeight.W900)
    )

    val baseStyle = TextStyle(
        fontFamily = suitFamily,
        lineHeight = 1.6.em,
        letterSpacing = (-0.02).em
    )

    return HomeNTypography(
        suitLight = baseStyle.copy(fontWeight = FontWeight.W300),
        suitRegular = baseStyle.copy(fontWeight = FontWeight.W400),
        suitMedium = baseStyle.copy(fontWeight = FontWeight.W500),
        suitSemiBold = baseStyle.copy(fontWeight = FontWeight.W600),
        suitBold = baseStyle.copy(fontWeight = FontWeight.W700),
        suitExtraBold = baseStyle.copy(fontWeight = FontWeight.W800),
        suitHeavy = baseStyle.copy(fontWeight = FontWeight.W900)
    )
}

val LocalHomeNTypography = staticCompositionLocalOf {
    HomeNTypography(
        suitLight = TextStyle.Default,
        suitRegular = TextStyle.Default,
        suitMedium = TextStyle.Default,
        suitSemiBold = TextStyle.Default,
        suitBold = TextStyle.Default,
        suitExtraBold = TextStyle.Default,
        suitHeavy = TextStyle.Default
    )
}
