package org.yarokovisty.delivery.feature.profile.main.presentation.state

import org.yarokovisty.delivery.common.validation.error.EmailValidationError
import org.yarokovisty.delivery.core.common.presentation.State
import org.yarokovisty.delivery.feature.profile.main.domain.entity.User

internal data class ProfileState(
    val loading: Boolean,
    val error: Boolean,
    val content: org.yarokovisty.delivery.feature.profile.main.presentation.state.ContentState?
) : State

internal data class ContentState(
    val user: User,
    val firstname: String,
    val lastname: String,
    val middlename: String,
    val city: String,
    val phone: String,
    val email: org.yarokovisty.delivery.feature.profile.main.presentation.state.EmailFieldState,
    val downloadingDataUpdate: Boolean,
)

internal data class EmailFieldState(
    val text: String,
    val status: org.yarokovisty.delivery.feature.profile.main.presentation.state.EmailFieldStatus
)

internal sealed interface EmailFieldStatus {

    data object NotValidated : org.yarokovisty.delivery.feature.profile.main.presentation.state.EmailFieldStatus

    data class Invalid(val reason: EmailValidationError) :
        org.yarokovisty.delivery.feature.profile.main.presentation.state.EmailFieldStatus

    data object Valid : org.yarokovisty.delivery.feature.profile.main.presentation.state.EmailFieldStatus
}
