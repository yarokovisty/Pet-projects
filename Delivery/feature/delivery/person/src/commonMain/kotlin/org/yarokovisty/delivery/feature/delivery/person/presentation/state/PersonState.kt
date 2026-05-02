package org.yarokovisty.delivery.feature.delivery.person.presentation.state

import org.yarokovisty.delivery.common.delivery.person.domain.entity.PersonInfo
import org.yarokovisty.delivery.common.validation.error.NameValidationError
import org.yarokovisty.delivery.common.validation.error.PhoneValidationError
import org.yarokovisty.delivery.core.common.presentation.State

internal data class PersonState(
    val stepState: StepState,
    val contentState: ContentState
) : State

internal data class StepState(
    val progress: Int,
    val maxProgress: Int
)

internal data class ContentState(
    val person: PersonInfo?,
    val firstname: NameState,
    val lastname: NameState,
    val middlename: String,
    val phoneNumber: PhoneNumberState,
)

internal data class NameState(
    val value: String,
    val fieldStatus: NameFieldStatus,
)

internal sealed interface NameFieldStatus {

    data object NotValidated : NameFieldStatus

    data object Valid : NameFieldStatus

    data class Invalid(val error: NameValidationError) : NameFieldStatus
}

internal data class PhoneNumberState(
    val value: String,
    val fieldStatus: PhoneFieldStatus
)

internal sealed interface PhoneFieldStatus {
    data object NotValidated : PhoneFieldStatus

    data object Valid : PhoneFieldStatus

    data class Invalid(val error: PhoneValidationError) : PhoneFieldStatus
}
