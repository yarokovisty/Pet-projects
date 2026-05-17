package org.yarokovisty.delivery.feature.delivery.order.presentation.intent

import org.yarokovisty.delivery.core.common.presentation.Intent

internal sealed interface ConfirmationOrderIntent : Intent {

    data object LoadData : ConfirmationOrderIntent
    data object EditReceiver : ConfirmationOrderIntent
    data object EditSender : ConfirmationOrderIntent
    data object EditReceiverAddress : ConfirmationOrderIntent
    data object EditSenderAddress : ConfirmationOrderIntent
    data object CheckoutOrder : ConfirmationOrderIntent
    data object Back : ConfirmationOrderIntent
}
