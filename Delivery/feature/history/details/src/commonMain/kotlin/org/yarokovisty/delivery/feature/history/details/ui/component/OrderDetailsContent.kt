package org.yarokovisty.delivery.feature.history.details.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import delivery.design.resources.generated.resources.ic_arrow_left
import delivery.feature.history.details.generated.resources.Res
import delivery.feature.history.details.generated.resources.history_details_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.TopBar
import delivery.design.resources.generated.resources.Res as designRes

@Composable
internal fun OrderDetailsContent(
    order: Order,
    onBack: () -> Unit,
    onCancel: () -> Unit
) {
    Column {
        OrderDetailsTopBar(orderId = order.id, onBack = onBack)

        DetailsCard(
            order = order,
            onCancel = onCancel,
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp)
        )
    }
}

@Composable
private fun OrderDetailsTopBar(orderId: String, onBack: () -> Unit) {
    TopBar(
        title = {
            Text(
                text = stringResource(Res.string.history_details_title, orderId),
                color = DeliveryTheme.colorScheme.textPrimary,
                style = DeliveryTheme.typography.titleH2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = painterResource(designRes.drawable.ic_arrow_left),
        onNavIconClick = onBack
    )
}
