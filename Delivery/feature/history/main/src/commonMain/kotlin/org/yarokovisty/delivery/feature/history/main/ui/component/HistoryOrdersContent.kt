package org.yarokovisty.delivery.feature.history.main.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import delivery.feature.history.main.generated.resources.Res
import delivery.feature.history.main.generated.resources.history_main_order_more
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.delivery.order.domain.entity.Order
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.text.Paragraph14RegularUnderline

@Composable
internal fun HistoryOrdersContent(
    orders: List<Order>,
    onDetailClick: (Order) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(orders, key = { it.id }) { order ->
            OrderItem(order, onDetailClick = { onDetailClick(order) })
        }
    }
}

@Composable
private fun OrderItem(
    order: Order,
    onDetailClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        OrderStatusItem(order.status)

        OrderIdItem(order.id)

        OrderAddressItem(order.receiverPoint, order.receiverAddress)

        DetailButton(onClick = onDetailClick)

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = DeliveryTheme.colorScheme.borderExtraLight
        )
    }
}

@Composable
private fun DetailButton(onClick: () -> Unit) {
    Paragraph14RegularUnderline(
        text = stringResource(Res.string.history_main_order_more),
        color = DeliveryTheme.colorScheme.textQuaternary,
        modifier = Modifier.clickable(onClick = onClick)
    )
}
