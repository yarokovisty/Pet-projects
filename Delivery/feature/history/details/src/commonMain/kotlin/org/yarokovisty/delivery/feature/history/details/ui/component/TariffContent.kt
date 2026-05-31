package org.yarokovisty.delivery.feature.history.details.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import delivery.feature.history.details.generated.resources.Res
import delivery.feature.history.details.generated.resources.history_details_order_tariff_default
import delivery.feature.history.details.generated.resources.history_details_order_tariff_express
import delivery.feature.history.details.generated.resources.history_details_order_tariff_title
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.OptionType
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.VerticalGap
import org.yarokovisty.delivery.design.uikit.text.Paragraph12Regular
import org.yarokovisty.delivery.design.uikit.text.Paragraph16Regular

@Composable
internal fun TariffContent(tariff: OptionType) {
    Column {
        Paragraph12Regular(
            text = stringResource(Res.string.history_details_order_tariff_title),
            color = DeliveryTheme.colorScheme.textTertiary
        )

        VerticalGap(2.dp)

        Paragraph16Regular(tariff.getText())
    }
}

@Composable
private fun OptionType.getText(): String =
    when (this) {
        OptionType.DEFAULT -> stringResource(Res.string.history_details_order_tariff_default)
        OptionType.EXPRESS -> stringResource(Res.string.history_details_order_tariff_express)
    }
