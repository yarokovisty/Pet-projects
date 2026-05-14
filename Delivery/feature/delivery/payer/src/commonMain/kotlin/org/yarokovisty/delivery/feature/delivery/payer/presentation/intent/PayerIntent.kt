package org.yarokovisty.delivery.feature.delivery.payer.presentation.intent

import org.yarokovisty.delivery.common.delivery.payer.domain.entity.Payer
import org.yarokovisty.delivery.core.common.presentation.Intent

internal sealed interface PayerIntent : Intent {

    data class SelectPayer(val payer: Payer) : PayerIntent

    data object ClickContinue : PayerIntent

    data object Back : PayerIntent
}
