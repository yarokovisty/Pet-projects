package org.yarokovisty.delivery.feature.delivery.main.impl.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.intent.DeliveryMainIntent
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.DeliveryMainState

@Composable
internal fun ContentScreen(
    state: DeliveryMainState,
    onIntent: (DeliveryMainIntent) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        item {
            Headline()
        }

        item {
            when {
                state.loading ->
                    DeliveryCalculatorCardSkeleton()
                state.deliveryCalculatorContent != null ->
                    DeliveryCalculatorCard(state.deliveryCalculatorContent, onIntent)
            }
        }

        item {
            TrackerCard(state.trackerContent, onIntent)
        }
    }
}
