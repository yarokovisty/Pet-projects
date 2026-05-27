package org.yarokovisty.delivery.feature.delivery.order.presentation.intent

import org.yarokovisty.delivery.core.common.presentation.Intent

internal sealed interface SuccessOrderIntent : Intent {

    data object Back : SuccessOrderIntent
    data object CheckStatus : SuccessOrderIntent
}
