package org.yarokovisty.delivery.feature.history.main.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import delivery.feature.history.main.generated.resources.Res
import delivery.feature.history.main.generated.resources.history_main_order_address_title
import delivery.feature.history.main.generated.resources.history_main_order_address_value
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.point.domain.entity.Address
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.VerticalGap
import org.yarokovisty.delivery.design.uikit.text.Paragraph12Regular
import org.yarokovisty.delivery.design.uikit.text.Paragraph16Regular

@Composable
internal fun OrderAddressItem(
    point: DeliveryPoint,
    address: Address
) {
    val addressText = stringResource(
        Res.string.history_main_order_address_value,
        point.name,
        address.street,
        address.house,
        address.apartment
    )

    Column {
        Paragraph12Regular(
            text = stringResource(Res.string.history_main_order_address_title),
            color = DeliveryTheme.colorScheme.textTertiary
        )

        VerticalGap(2.dp)

        Paragraph16Regular(text = addressText)
    }
}
