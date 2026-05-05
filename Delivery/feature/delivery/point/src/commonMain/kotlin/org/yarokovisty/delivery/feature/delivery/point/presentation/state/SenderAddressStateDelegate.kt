package org.yarokovisty.delivery.feature.delivery.point.presentation.state

import org.yarokovisty.delivery.common.delivery.point.domain.entity.Address
import org.yarokovisty.delivery.common.delivery.presentation.StepState
import org.yarokovisty.delivery.common.validation.error.AddressValidationError

internal fun initial(currentStep: Int, maxSteps: Int): SenderAddressState =
    SenderAddressState(
        stepState = StepState(
            progress = currentStep,
            maxProgress = maxSteps
        ),
        contentState = SenderAddressContentState(
            street = InputState.INITIAL,
            house = InputState.INITIAL,
            apartment = InputState.INITIAL,
            comment = ""
        )
    )

internal fun SenderAddressState.updateStreet(street: String): SenderAddressState =
    copy(
        contentState = contentState.copy(
            street = contentState.street.copy(
                value = street,
                fieldStatus = FieldStatus.NotValidated
            )
        )
    )

internal fun SenderAddressState.streetValid(): SenderAddressState =
    copy(
        contentState = contentState.copy(
            street = contentState.street.copy(fieldStatus = FieldStatus.Valid)
        )
    )

internal fun SenderAddressState.streetInvalid(error: AddressValidationError): SenderAddressState =
    copy(
        contentState = contentState.copy(
            street = contentState.street.copy(fieldStatus = FieldStatus.Invalid(error))
        )
    )

internal fun SenderAddressState.updateHouse(house: String): SenderAddressState =
    copy(
        contentState = contentState.copy(
            house = contentState.house.copy(
                value = house,
                fieldStatus = FieldStatus.NotValidated
            )
        )
    )

internal fun SenderAddressState.houseValid(): SenderAddressState =
    copy(
        contentState = contentState.copy(
            house = contentState.house.copy(fieldStatus = FieldStatus.Valid)
        )
    )

internal fun SenderAddressState.houseInvalid(error: AddressValidationError): SenderAddressState =
    copy(
        contentState = contentState.copy(
            house = contentState.house.copy(fieldStatus = FieldStatus.Invalid(error))
        )
    )

internal fun SenderAddressState.updateApartment(apartment: String): SenderAddressState =
    copy(
        contentState = contentState.copy(
            apartment = contentState.apartment.copy(
                value = apartment,
                fieldStatus = FieldStatus.NotValidated
            )
        )
    )

internal fun SenderAddressState.apartmentValid(): SenderAddressState =
    copy(
        contentState = contentState.copy(
            apartment = contentState.apartment.copy(fieldStatus = FieldStatus.Valid)
        )
    )

internal fun SenderAddressState.apartmentInvalid(error: AddressValidationError): SenderAddressState =
    copy(
        contentState = contentState.copy(
            apartment = contentState.apartment.copy(fieldStatus = FieldStatus.Invalid(error))
        )
    )

internal fun SenderAddressState.updateComment(comment: String): SenderAddressState =
    copy(
        contentState = contentState.copy(comment = comment)
    )

internal fun SenderAddressState.getSenderAddress(): Address =
    Address(
        street = contentState.street.value,
        house = contentState.house.value,
        apartment = contentState.apartment.value,
        comment = contentState.comment,
        nonContacted = null
    )
