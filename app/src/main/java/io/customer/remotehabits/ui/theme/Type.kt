package io.customer.remotehabits.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.customer.remotehabits.R

private val RobotoFontFamily = FontFamily(
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_regular, FontWeight.W400),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_thin, FontWeight.Thin),
    Font(R.font.roboto_light, FontWeight.Light)
)

data class RHTypography(
    val h1: TextStyle = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 36.sp,
        letterSpacing = 0.sp
    ),
    val h2: TextStyle = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 22.sp,
        letterSpacing = 0.sp
    ),
    val h3: TextStyle = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 0.1.sp
    ),
    val input: TextStyle = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        letterSpacing = 0.1.sp
    ),
    val body: TextStyle = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    val button: TextStyle = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    val caption: TextStyle = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        letterSpacing = 0.sp
    )
)
