package org.yarokovisty.delivery.feature.history.details.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import delivery.feature.history.details.generated.resources.Res
import delivery.feature.history.details.generated.resources.history_details_order_address_title
import delivery.feature.history.details.generated.resources.history_details_order_address_value
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.point.domain.entity.Address
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.VerticalGap
import org.yarokovisty.delivery.design.uikit.text.Paragraph12Regular
import org.yarokovisty.delivery.design.uikit.text.Paragraph16Regular

@Composable
internal fun AddressContent(
    point: DeliveryPoint,
    address: Address
) {
    Column {
        Paragraph12Regular(
            text = stringResource(Res.string.history_details_order_address_title),
            color = DeliveryTheme.colorScheme.textTertiary
        )

        VerticalGap(2.dp)

        Paragraph16Regular(
            text = stringResource(
                Res.string.history_details_order_address_value,
                point.name,
                address.street,
                address.house,
                address.apartment
            )
        )
    }
}
