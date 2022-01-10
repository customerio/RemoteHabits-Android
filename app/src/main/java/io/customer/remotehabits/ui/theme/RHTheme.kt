package io.customer.remotehabits.ui.theme

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

object RHTheme {

    val colors: RHColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: RHTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}

class RHColors(
    background: Color,
    backgroundVariant: Color,
    surface: Color,
    primary: Color,
    accent: Color,
    textPrimary: Color,
    textSecondary: Color,
    textTertiary: Color,
    underline: Color,
    cardBackground: Color,
    isLight: Boolean
) {
    var background by mutableStateOf(background)
        private set
    var backgroundVariant by mutableStateOf(backgroundVariant)
        private set
    var surface by mutableStateOf(surface)
        private set
    var primary by mutableStateOf(primary)
        private set
    var accent by mutableStateOf(accent)
        private set
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var textSecondary by mutableStateOf(textSecondary)
        private set
    var textTertiary by mutableStateOf(textTertiary)
        private set
    var underline by mutableStateOf(underline)
        private set
    var cardBackground by mutableStateOf(cardBackground)
        private set
    var isLight by mutableStateOf(isLight)
        internal set

    fun copy(
        background: Color = this.background,
        backgroundVariant: Color = this.backgroundVariant,
        surface: Color = this.surface,
        primary: Color = this.primary,
        accent: Color = this.accent,
        textPrimary: Color = this.textPrimary,
        textSecondary: Color = this.textSecondary,
        textTertiary: Color = this.textTertiary,
        underline: Color = this.underline,
        cardBackground: Color = this.cardBackground,
        isLight: Boolean = this.isLight
    ): RHColors = RHColors(
        background = background,
        backgroundVariant = backgroundVariant,
        surface = surface,
        primary = primary,
        accent = accent,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        textTertiary = textTertiary,
        underline = underline,
        cardBackground = cardBackground,
        isLight = isLight
    )

    fun updateColorsFrom(other: RHColors) {
        background = other.background
        backgroundVariant = other.backgroundVariant
        surface = other.surface
        primary = other.primary
        accent = other.accent
        textPrimary = other.textPrimary
        textSecondary = other.textSecondary
        textTertiary = other.textTertiary
        underline = other.underline
        cardBackground = other.cardBackground
    }
}

fun lightColors(
    background: Color = Yellow,
    backgroundVariant: Color = YellowVariant,
    surface: Color = Clementine,
    primary: Color = Purple700,
    accent: Color = Purple300,
    textPrimary: Color = Color.Black,
    textSecondary: Color = Gray900,
    textTertiary: Color = Gray500,
    underline: Color = Gray300,
    cardBackground: Color = Color.White,
): RHColors = RHColors(
    background = background,
    backgroundVariant = backgroundVariant,
    surface = surface,
    primary = primary,
    accent = accent,
    textPrimary = textPrimary,
    textSecondary = textSecondary,
    textTertiary = textTertiary,
    underline = underline,
    cardBackground = cardBackground,
    isLight = true
)

fun darkColors(
    background: Color = Gray950,
    backgroundVariant: Color = Gray950Variant,
    surface: Color = Gray900,
    primary: Color = Purple700,
    accent: Color = Purple300,
    textPrimary: Color = Color.White,
    textSecondary: Color = Gray900,
    textTertiary: Color = Gray500,
    underline: Color = Gray500,
    cardBackground: Color = Gray900,
): RHColors = RHColors(
    background = background,
    backgroundVariant = backgroundVariant,
    surface = surface,
    primary = primary,
    accent = accent,
    textPrimary = textPrimary,
    textSecondary = textSecondary,
    textTertiary = textTertiary,
    underline = underline,
    cardBackground = cardBackground,
    isLight = false
)

internal val LocalColors = staticCompositionLocalOf { lightColors() }
internal val LocalTypography = staticCompositionLocalOf { RHTypography() }
