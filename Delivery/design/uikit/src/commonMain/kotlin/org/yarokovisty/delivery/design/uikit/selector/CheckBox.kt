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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import delivery.design.resources.generated.resources.Res
import delivery.design.resources.generated.resources.ic_check
import org.jetbrains.compose.resources.painterResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.HorizontalGap

@Composable
fun CheckBox(
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    enabled: Boolean = true,
    text: String? = null,
    textStyle: TextStyle = DeliveryTheme.typography.paragraph16Medium,
    textColor: Color = DeliveryTheme.colorScheme.textPrimary,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CheckBox(checked, enabled, onCheckedChange)

        if (!text.isNullOrBlank()) {
            HorizontalGap(16.dp)

            Text(text = text, style = textStyle, color = textColor)
        }
    }
}

@Composable
private fun CheckBox(
    checked: Boolean,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    val borderColor = when {
        checked && enabled -> DeliveryTheme.colorScheme.brandPrimary
        enabled -> DeliveryTheme.colorScheme.borderLight
        checked -> DeliveryTheme.colorScheme.brandDisabled
        else -> DeliveryTheme.colorScheme.borderExtraLight
    }
    val backgroundColor = when {
        checked && enabled -> DeliveryTheme.colorScheme.brandPrimary
        checked -> DeliveryTheme.colorScheme.brandDisabled
        else -> Color.Transparent
    }
    val animatedBorderColor by animateColorAsState(borderColor)
    val animatedBackgroundColor by animateColorAsState(backgroundColor)

    Box(
        modifier = Modifier
            .border(2.dp, animatedBorderColor, RoundedCornerShape(6.dp))
            .size(24.dp)
            .background(animatedBackgroundColor, RoundedCornerShape(6.dp))
            .clip(RoundedCornerShape(6.dp))
            .clickable(
                enabled = enabled,
                onClick = { onCheckedChange(!checked) }
            )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(
                checked,
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
private fun CheckBoxPreview() {
    DeliveryTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.background(DeliveryTheme.colorScheme.bgPrimary)
        ) {
            CheckBox(onCheckedChange = {})

            CheckBox(
                text = "Checkbox",
                checked = true,
                onCheckedChange = {}
            )

            CheckBox(
                text = "Checkbox",
                checked = true,
                enabled = false,
                onCheckedChange = {}
            )

            CheckBox(
                text = "Checkbox",
                enabled = false,
                checked = false,
                onCheckedChange = {}
            )
        }
    }
}
