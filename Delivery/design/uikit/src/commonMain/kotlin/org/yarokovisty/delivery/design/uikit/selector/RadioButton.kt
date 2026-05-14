package org.yarokovisty.delivery.design.uikit.selector

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import delivery.design.uikit.generated.resources.Res
import delivery.design.uikit.generated.resources.ic_check
import org.jetbrains.compose.resources.painterResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.HorizontalGap

@Composable
fun RadioButton(
    selected: Boolean,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    textStyle: TextStyle = DeliveryTheme.typography.paragraph16Medium,
    textColor: Color = DeliveryTheme.colorScheme.textPrimary,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(
            enabled = enabled,
            onClick = onClick,
        )
    ) {
        RadioButton(selected, enabled)

        HorizontalGap(16.dp)

        Text(text, style = textStyle, color = textColor)
    }
}

@Composable
private fun RadioButton(
    selected: Boolean,
    enabled: Boolean
) {
    val borderColor = when {
        selected && enabled -> DeliveryTheme.colorScheme.brandPrimary
        enabled -> DeliveryTheme.colorScheme.borderLight
        selected -> DeliveryTheme.colorScheme.brandDisabled
        else -> DeliveryTheme.colorScheme.borderExtraLight
    }
    val backgroundColor = when {
        selected && enabled -> DeliveryTheme.colorScheme.brandPrimary
        selected -> DeliveryTheme.colorScheme.brandDisabled
        else -> Color.Transparent
    }
    val animatedBorderColor by animateColorAsState(borderColor)
    val animatedBackgroundColor by animateColorAsState(backgroundColor)

    Box(
        modifier = Modifier
            .border(2.dp, animatedBorderColor, CircleShape)
            .size(24.dp)
            .background(animatedBackgroundColor, CircleShape)
            .clip(CircleShape)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(
                selected,
                enter = scaleIn(
                    initialScale = 0.5f,
                    animationSpec = tween(100)
                ),
                exit = shrinkOut(
                    animationSpec = tween(100),
                    shrinkTowards = Alignment.Center
                )
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_check),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun RadioButtonPreview() {
    DeliveryTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.background(DeliveryTheme.colorScheme.bgPrimary)
        ) {
            RadioButton(selected = true, enabled = true, text = "RadioButton", onClick = {})

            RadioButton(selected = false, enabled = true, text = "RadioButton", onClick = {})

            RadioButton(selected = false, enabled = false, text = "RadioButton", onClick = {})
        }
    }
}
