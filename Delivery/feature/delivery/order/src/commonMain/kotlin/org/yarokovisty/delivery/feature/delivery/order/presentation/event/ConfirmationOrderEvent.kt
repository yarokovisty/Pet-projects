package org.yarokovisty.delivery.feature.delivery.order.presentation.event

import org.yarokovisty.delivery.core.common.presentation.Event

internal sealed interface ConfirmationOrderEvent : Event {

    data object CheckoutOrderError : ConfirmationOrderEvent
}
