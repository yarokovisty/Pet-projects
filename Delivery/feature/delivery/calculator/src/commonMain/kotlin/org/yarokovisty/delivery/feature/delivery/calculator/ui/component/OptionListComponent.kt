package org.yarokovisty.delivery.feature.delivery.calculator.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.Option
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.OptionType
import org.yarokovisty.delivery.design.theme.DeliveryTheme

@Composable
internal fun OptionListComponent(
    options: List<Option>,
    onSelectOption: (Option) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(options, key = { it.id }) { option ->
            OptionItemComponent(option, onClick = { onSelectOption(option) })
        }
    }
}

@Preview
@Composable
private fun OptionListComponentPreview() {
    val options = listOf(
        Option(
            id = "1",
            price = 1.0,
            days = 1,
            type = OptionType.DEFAULT
        ),
        Option(
            id = "2",
            price = 2.0,
            days = 2,
            type = OptionType.EXPRESS
        )
    )

    DeliveryTheme {
        Box(modifier = Modifier.background(DeliveryTheme.colorScheme.bgPrimary)) {
            OptionListComponent(options, onSelectOption = {})
        }
    }
}
