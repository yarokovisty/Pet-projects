package org.yarokovisty.delivery.feature.history.details.presentation.intent

import org.yarokovisty.delivery.core.common.presentation.Intent

internal sealed interface OrderDetailsIntent : Intent {

    data object LoadData : OrderDetailsIntent

    data object Back : OrderDetailsIntent

    data object OpenCancellationScreen : OrderDetailsIntent

    data object CloseCancellationScreen : OrderDetailsIntent

    data object ConfirmCancellation : OrderDetailsIntent
    data object OpenLoginScreen : OrderDetailsIntent
}
