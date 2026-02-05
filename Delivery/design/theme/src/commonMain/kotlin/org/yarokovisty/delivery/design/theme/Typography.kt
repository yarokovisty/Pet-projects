package org.yarokovisty.delivery.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

internal val LocalDeliveryTypography = staticCompositionLocalOf<DeliveryTypography> {
    error("No DeliveryTypography provided")
}

internal val deliveryTypography: DeliveryTypography
    @Composable
    get() = DeliveryTypography(
        buttonSemibold = TextStyle(
            color = deliveryColorScheme.textInvert,
            fontSize = 16.sp,
            fontWeight = FontWeight.W600,
            fontFamily = Inter()
        ),
        paragraph14Medium = TextStyle(
            color = deliveryColorScheme.textSecondary,
            fontSize = 14.sp,
            fontWeight = FontWeight.W500,
            fontFamily = Inter()
        ),
        paragraph14Underline = TextStyle(
            color = deliveryColorScheme.textTertiary,
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            fontFamily = Inter()
        ),
        paragraph16Regular = TextStyle(
            color = deliveryColorScheme.textSecondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            fontFamily = Inter()
        ),
        titleH1 = TextStyle(
            color = deliveryColorScheme.textPrimary,
            fontSize = 32.sp,
            fontWeight = FontWeight.W700,
            fontFamily = Inter()
        ),
        titleH2 = TextStyle(
            color = deliveryColorScheme.textPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.W700,
            fontFamily = Inter()
        ),
        titleSubtitle = TextStyle(
            color = deliveryColorScheme.textTertiary,
            fontSize = 16.sp,
            fontWeight = FontWeight.W300
        ),
    )

@Immutable
data class DeliveryTypography(
    val buttonSemibold: TextStyle,
    val paragraph14Medium: TextStyle,
    val paragraph14Underline: TextStyle,
    val paragraph16Regular: TextStyle,
    val titleH1: TextStyle,
    val titleH2: TextStyle,
    val titleSubtitle: TextStyle,
)
