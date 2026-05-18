package org.yarokovisty.delivery.feature.delivery.direction.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import delivery.design.resources.generated.resources.Res
import delivery.design.resources.generated.resources.ic_arrow_small_right
import org.jetbrains.compose.resources.painterResource
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.text.Paragraph16Regular
import org.yarokovisty.delivery.feature.delivery.direction.presentation.intent.DirectionIntent
import org.yarokovisty.delivery.feature.delivery.direction.presentation.state.DirectionContentState

@Composable
internal fun ContentScreen(
    state: DirectionContentState,
    onIntent: (DirectionIntent) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(state.deliveryPoints, key = { it.id }) { point ->
            DeliveryPointItem(
                point = point,
                onClick = { onIntent(DirectionIntent.SelectDeliveryPoint(point)) }
            )
        }
    }
}

@Composable
private fun DeliveryPointItem(
    point: DeliveryPoint,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Paragraph16Regular(
            text = point.name,
            color = DeliveryTheme.colorScheme.textPrimary,
            modifier = Modifier.weight(1f)
        )

        Icon(
            painter = painterResource(Res.drawable.ic_arrow_small_right),
            contentDescription = null,
            tint = DeliveryTheme.colorScheme.indicatorLight
        )
    }
}

@Preview
@Composable
private fun DeliveryContentScreenPreview() {
    val state = DirectionContentState(
        deliveryPoints = listOf(
            DeliveryPoint(
                id = "0",
                name = "Москва",
                latitude = 0.0,
                longitude = 0.0
            ),
            DeliveryPoint(
                id = "1",
                name = "Санкт-Петербург",
                latitude = 1.0,
                longitude = 1.0
            ),
            DeliveryPoint(
                id = "2",
                name = "Новосибирск",
                latitude = 1.0,
                longitude = 1.0
            )
        )
    )

    DeliveryTheme {
        ContentScreen(state, onIntent = {})
    }
}
