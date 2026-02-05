package org.yarokovisty.delivery.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val appLightColorScheme = lightColorScheme(
    primary = BrandPrimary,
    onPrimary = TextInvert,
    primaryContainer = BrandHover,
    onPrimaryContainer = TextInvert,

    background = BgPrimaryLight,
    onBackground = TextPrimaryLight,

    surface = BgSecondaryLight,
    onSurface = TextPrimaryLight,

    error = IndicatorError,
    onError = TextInvert,

    outline = BorderLight,
    outlineVariant = BorderExtraLight,
)

private val appDarkColorScheme = darkColorScheme(
    primary = BrandPrimary,
    onPrimary = TextInvert,
    primaryContainer = BrandHover,
    onPrimaryContainer = TextInvert,

    background = BgPrimaryDark,
    onBackground = TextPrimaryDark,

    surface = BgSecondaryDark,
    onSurface = TextPrimaryDark,

    error = IndicatorError,
    onError = TextInvert,

    outline = BorderMedium,
    outlineVariant = BorderLight,
)

internal val deliveryColorScheme: ColorScheme
    @Composable
    get() = if (isSystemInDarkTheme()) appDarkColorScheme else appLightColorScheme
