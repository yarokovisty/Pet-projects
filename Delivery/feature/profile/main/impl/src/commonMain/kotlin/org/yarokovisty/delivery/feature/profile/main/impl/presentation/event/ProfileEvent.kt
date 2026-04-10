package org.yarokovisty.delivery.feature.profile.main.impl.presentation.event

import org.yarokovisty.delivery.common.presentation.Event

internal sealed interface ProfileEvent : Event {

    data object UpdateUserDataSuccess : ProfileEvent
    data object UpdateUserDataError : ProfileEvent
}
