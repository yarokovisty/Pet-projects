package org.yarokovisty.delivery.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val LocalDeliveryColorScheme = staticCompositionLocalOf<DeliveryColorScheme> {
    error("No DeliveryColorScheme provided")
}

internal val deliveryColorScheme: DeliveryColorScheme
    @Composable
    get() = if (isSystemInDarkTheme()) deliveryDarkColorScheme else deliveryLightColorScheme

private val deliveryLightColorScheme = DeliveryColorScheme(
    brandPrimary = BrandPrimary,
    brandHover = BrandHover,
    brandExtraLight = BrandExtraLight,
    brandDisabled = BrandDisabled,
    bgPrimary = BgPrimaryLight,
    bgSecondary = BgSecondaryLight,
    bgTertiary = BgTertiaryLight,
    bgDisable = BgDisableLight,
    borderExtraLight = BorderExtraLight,
    borderLight = BorderLight,
    borderMedium = BorderMedium,
    textInvert = TextInvert,
    textPrimary = TextPrimaryLight,
    textSecondary = TextSecondaryLight,
    textTertiary = TextTertiaryLight,
    textQuaternary = TextQuaternaryLight,
    textError = TextError,
    indicatorError = IndicatorError,
    indicatorAttention = IndicatorAttention,
    indicatorPositive = IndicatorPositive,
    indicatorLight = IndicatorLight,
    indicatorMedium = IndicatorMedium,
)

private val deliveryDarkColorScheme = DeliveryColorScheme(
    brandPrimary = BrandPrimary,
    brandHover = BrandHover,
    brandExtraLight = BrandExtraLight,
    brandDisabled = BrandDisabled,
    bgPrimary = BgPrimaryDark,
    bgSecondary = BgSecondaryDark,
    bgTertiary = BgTertiaryDark,
    bgDisable = BgDisableDark,
    borderExtraLight = BorderExtraLight,
    borderLight = BorderLight,
    borderMedium = BorderMedium,
    textInvert = TextInvert,
    textPrimary = TextPrimaryDark,
    textSecondary = TextSecondaryDark,
    textTertiary = TextTertiaryDark,
    textQuaternary = TextQuaternaryDark,
    textError = TextError,
    indicatorError = IndicatorError,
    indicatorAttention = IndicatorAttention,
    indicatorPositive = IndicatorPositive,
    indicatorLight = IndicatorLight,
    indicatorMedium = IndicatorMedium,
)

@Immutable
data class DeliveryColorScheme(
    val brandPrimary: Color,
    val brandHover: Color,
    val brandExtraLight: Color,
    val brandDisabled: Color,
    val bgPrimary: Color,
    val bgSecondary: Color,
    val bgTertiary: Color,
    val bgDisable: Color,
    val borderExtraLight: Color,
    val borderLight: Color,
    val borderMedium: Color,
    val textInvert: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textQuaternary: Color,
    val textError: Color,
    val indicatorError: Color,
    val indicatorAttention: Color,
    val indicatorPositive: Color,
    val indicatorLight: Color,
    val indicatorMedium: Color,
)
