package org.yarokovisty.delivery.feature.history.main.presentation.intent

import org.yarokovisty.delivery.core.common.presentation.Intent

internal sealed interface HistoryMainIntent : Intent {

    data object LoadData : HistoryMainIntent
    data class OpenOrderDetail(val orderId: String) : HistoryMainIntent
}
