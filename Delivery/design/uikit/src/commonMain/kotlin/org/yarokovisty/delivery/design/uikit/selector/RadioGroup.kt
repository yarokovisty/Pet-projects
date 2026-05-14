package org.yarokovisty.delivery.design.uikit.selector

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.yarokovisty.delivery.design.theme.DeliveryTheme

@Composable
fun RadioGroup(
    selected: String,
    group: List<String>,
    onSelect: (String) -> Unit,
    enabled: Boolean = true,
    itemSpacePadding: Dp = 16.dp,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(itemSpacePadding)
    ) {
        group.forEach { item ->
            RadioButton(
                selected = item == selected,
                text = item,
                enabled = enabled,
                onClick = {
                    onSelect(item)
                }
            )
        }
    }
}

@Preview
@Composable
private fun RadioGroupPreview() {
    DeliveryTheme {
        Box(modifier = Modifier.background(DeliveryTheme.colorScheme.bgPrimary)) {
            RadioGroup(
                selected = "RadioButton1",
                group = listOf("RadioButton1", "RadioButton2", "RadioButton3"),
                onSelect = {},
            )
        }
    }
}
