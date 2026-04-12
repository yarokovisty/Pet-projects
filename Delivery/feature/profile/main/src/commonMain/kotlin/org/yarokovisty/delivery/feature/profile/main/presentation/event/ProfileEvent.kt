package org.yarokovisty.delivery.feature.profile.main.presentation.event

import org.yarokovisty.delivery.core.common.presentation.Event

internal sealed interface ProfileEvent : Event {

    data object UpdateUserDataSuccess : org.yarokovisty.delivery.feature.profile.main.presentation.event.ProfileEvent
    data object UpdateUserDataError : org.yarokovisty.delivery.feature.profile.main.presentation.event.ProfileEvent
}
