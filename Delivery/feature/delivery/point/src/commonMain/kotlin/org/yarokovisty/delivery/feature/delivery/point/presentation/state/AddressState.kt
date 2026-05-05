package org.yarokovisty.delivery.feature.delivery.point.presentation.state

import org.yarokovisty.delivery.common.validation.error.AddressValidationError

internal data class InputState(
    val value: String,
    val fieldStatus: FieldStatus
) {

    companion object {

        val INITIAL = InputState(
            value = "",
            fieldStatus = FieldStatus.NotValidated
        )
    }
}

internal sealed interface FieldStatus {

    data object NotValidated : FieldStatus

    data object Valid : FieldStatus

    data class Invalid(val error: AddressValidationError) : FieldStatus
}
