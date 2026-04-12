package org.yarokovisty.delivery.feature.login.impl.presentation.event

import org.yarokovisty.delivery.core.common.presentation.Event

internal sealed interface LoginEvent : Event {

    data object OtpRequestError : LoginEvent

    data object SigninError : LoginEvent
}
