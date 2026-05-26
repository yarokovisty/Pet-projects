package org.yarokovisty.delivery.feature.profile.main.presentation.state

import org.yarokovisty.delivery.common.profile.main.domain.entity.User
import org.yarokovisty.delivery.common.validation.error.EmailValidationError
import org.yarokovisty.delivery.core.common.presentation.State

internal data class ProfileState(
    val loading: Boolean,
    val error: Boolean,
    val content: ContentState?,
    val logoutScreenVisible: Boolean,
) : State

internal data class ContentState(
    val user: User,
    val firstname: String,
    val lastname: String,
    val middlename: String,
    val city: String,
    val phone: String,
    val email: EmailFieldState,
    val downloadingDataUpdate: Boolean,
)

internal data class EmailFieldState(
    val text: String,
    val status: EmailFieldStatus
)

internal sealed interface EmailFieldStatus {

    data object NotValidated : EmailFieldStatus

    data class Invalid(val reason: EmailValidationError) : EmailFieldStatus

    data object Valid : EmailFieldStatus
}
